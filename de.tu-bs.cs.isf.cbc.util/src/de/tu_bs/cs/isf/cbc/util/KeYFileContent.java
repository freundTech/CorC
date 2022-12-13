package de.tu_bs.cs.isf.cbc.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.hamcrest.core.IsInstanceOf;
import org.stringtemplate.v4.compiler.CodeGenerator.primary_return;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcmodel.BaseVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;

public class KeYFileContent {	
	public static final String REGEX_BEFORE_VARIABLE_KEYWORD = "(?<![a-zA-Z0-9_\\.]|self\\.)(";
	public static final String REGEX_AFTER_VARIABLE_KEYWORD = ")(?![a-zA-Z0-9_])";
	public static final Pattern REGEX_THIS_KEYWORD = Pattern.compile("(?<![a-zA-Z0-9_])(this)(?![a-zA-Z0-9_])");
	public static final String OLD_VARS_SUFFIX = "_oldVal";
	public static final Pattern REGEX_RESULT_KEYWORD = Pattern.compile("(\\\\result)");
	
	
	private final String helper = "helper.key";
	private String location = "";
	private String srcFolder = "";
	private String statement = "";
	
	private Renaming renaming;
	private CbCFormula self;

	private List<BaseVariable> programVariables;
	private List<Condition> globalConditions;
	private List<Condition> preConditions;
	private List<Condition> postConditions;


	public KeYFileContent() {
		programVariables = new ArrayList<>();
		globalConditions = new ArrayList<>();
		preConditions = new ArrayList<>();
		postConditions = new ArrayList<>();
	}

	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setSrcFolder(String srcFolder) {
		this.srcFolder = srcFolder;
	}

	public void addPreCondition(Condition pre) {
		this.preConditions.add(pre);
	}

	public void addPostCondition(Condition post) {
		this.postConditions.add(post);
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public JavaVariable readVariables(JavaVariables vars) {
		JavaVariable returnVariable = null;
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				if (var.getKind() == VariableKind.RETURN || var.getKind() == VariableKind.RETURNPARAM) {
					returnVariable = var;
				}
				programVariables.add(var);
			}
			for (Parameter param : vars.getParams()) {
				
				if (param.getName().equals("ret")) {
					returnVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
					returnVariable.setKind(VariableKind.RETURNPARAM);
					returnVariable.setName(param.getName());
					returnVariable.setType(param.getType());
				}
				programVariables.add(param);
			}
			for (Field field : vars.getFields()) {
				programVariables.add(field);
			}

		}
		return returnVariable;
	}
	
	public void readGlobalConditions(GlobalConditions conds) {
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditions.add(cond);
				}
			}
		}
	}

	public void readInvariants(List<Condition> invariants) {
		for (Condition c : invariants) {
			preConditions.add(c);
			postConditions.add(c);
		}
	}
	
	@Deprecated
	public void setPreFromCondition(String cond) {
		cond = Parser.getConditionFromCondition(cond);
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName(cond);
		
		setPreFromCondition(condition);
	}
	
	public void setPreFromCondition(Condition cond) {
		if (cond != null) {
			preConditions.add(cond);
		}
	}
	
	@Deprecated
	public void setPostFromCondition(String cond) {
		cond = Parser.getConditionFromCondition(cond);
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName(cond);
		
		setPreFromCondition(condition);
	}
	
	public void setPostFromCondition(Condition cond) {
		if (cond != null) {
			postConditions.add(cond);
		}
	}
	
	public void rename(Renaming renaming) {
		this.renaming = renaming;
	}
	
	public void addSelf(CbCFormula formula) {
		this.self = formula;
	}
	
	public String getKeYStatementContent() {
		return 	getKeYContent(true);
	}
	
	public String getKeYCImpliesCContent() {
		return  getKeYContent(false);
	}
	
	public String getKeYContent(boolean withStatement) {
		StringBuilder builder = new StringBuilder();
		builder.append(getKeyHeader());
		builder.append(getKeyProblem());
		if (withStatement) 
			builder.append(" \\<{" + statement + "}\\>");
		
		builder.append(" (" + getPostString() + ")}");
		return builder.toString();
	}
	
	private String getKeyHeader() {
		return MessageFormat.format(
				"\\javaSource \"{0}\";\n"
				+ "\\include \"{1}\";\n"
				+ "\\programVariables '{'\n"
				+ "{2}\n"
				+ "Heap heapAtPre;\n"
				+ "'}'", 
				location + srcFolder,
				helper,
				getProgramVariablesString());
	}
	
	private String getKeyProblem() {
		List<String> preConditions = new ArrayList<>();
		preConditions.add(getGlobalConditionsString());
		preConditions.add(getConditionObjectsCreatedString());
		preConditions.add(getSelfConditionsString());
	    preConditions.add("wellFormed(heap)");
		preConditions.removeIf(v -> v == null);
		
	    String preConditionsString = String.join(" & ", preConditions);
	    
	    List<String> postConditions = new ArrayList<>();
	    postConditions.add("heapAtPre := heap");
	    postConditions.add(getAssignment());
	    postConditions.removeIf(v -> v == null);
	    
	    String postConditionsString = String.join(" || ", postConditions);
	    
		return MessageFormat.format("\\problem '{'({0}) -> '{'"
				+ "{1}"
				+ "'}'", 
				preConditionsString, postConditionsString);
	}
	
	private String getProgramVariablesString() {
		StringBuilder builder = new StringBuilder();
		for (BaseVariable var : programVariables) {
			if (!(var instanceof Field)) {
				builder.append(var.getType() + " " + var.getName() + ";\n");
			}
		}
		
		return builder.toString();
	}

	private String getPreString() {
		return null;
	}
	
	private String getPostString() {
		return "";
	}
	
	private String getGlobalConditionsString() {
		String result = globalConditions.stream()
				.map(Condition::getName)
				.map(this::replaceThisWithSelf)
				.collect(Collectors.joining(" & "));
		if (result.equals("")) {
			return null;
		}
		return result;
	}
	
	private String getConditionObjectsCreatedString() {
		//TODO Improve model to get rid of instanceof checks
		List<String> conditions = new ArrayList<>();
		for (BaseVariable var : programVariables) {
			if (var instanceof JavaVariable) {
				if (((JavaVariable) var).getKind() == VariableKind.PARAM && var.isIsNonNull() && !isSimpleType(var.getType())) {
					conditions.add(var.getType() + "::exactInstance(" + var.getName() + ") = TRUE");
					conditions.add(var.getName() + ".<created> = TRUE");
					conditions.add(var.getName() + "!= null");
				}
				else if (var.getType().endsWith("[]")) {
					conditions.add(var.getName() + ".<created> = TRUE");
				}
			}
			else if (var instanceof Parameter) {
				if (var.getType().endsWith("[]")) {
					conditions.add(var.getName() + ".<created> = TRUE");
				}
				if (var.isIsNonNull() && !isSimpleType(var.getType())) {
					conditions.add(var.getType() + "::exactInstance(" + var.getName() + ") = TRUE");
					conditions.add(var.getName() + ".<created> = TRUE");
					conditions.add(var.getName() + "!= null");
				}
			}
			else if (var instanceof Field && !isSimpleType(var.getType())) {
				conditions.add(var.getType() + "::exactInstance(self." + var.getName() + ") = TRUE");
				conditions.add("self." + var.getName() + ".<created> = TRUE");
				conditions.add("self." + var.getName() + "!= null");
			}
		}
		
		if (conditions.size() == 0) {
			return null;
		}
		return String.join(" & ", conditions);
	}
	
	private String getSelfConditionsString() {
		return null;
	}
	
	private String getAssignment() {
		return null;
	}
	
	private String replaceThisWithSelf(String string) {
		return string.replaceAll(REGEX_THIS_KEYWORD.pattern(), "self");
	}
	
	private boolean isSimpleType(String type) {
		return type.matches("^(void|byte|short|int|double|char|long|float|boolean)$");
	}
}
