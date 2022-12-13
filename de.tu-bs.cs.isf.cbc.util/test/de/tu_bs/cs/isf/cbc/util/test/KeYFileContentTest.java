package de.tu_bs.cs.isf.cbc.util.test;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.util.KeYFileContent;

class KeYFileContentTest {
	KeYFileContent content;

	@BeforeEach
	void before() {
		content = new KeYFileContent();
		content.setLocation("location/");
		content.setSrcFolder("src");
	}

	@Test
	void testEmptyKeyFile() {
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    (wellFormed(heap)) -> {"
				+ "        heapAtPre := heap"
				+ "    } "
				+ "    \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	@Test
	void testAddNullVariables() {
		assertNull(content.readVariables(null));
	}
	
	@Test
	void testAddVariables() {
		EList<JavaVariable> list = new BasicEList<>();
		JavaVariable var;
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int");
		var.setName("foo");
		list.add(var);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int");
		var.setName("bar");
		var.setIsStatic(true);
		list.add(var);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int");
		var.setName("foobar");
		var.setIsNonNull(true);
		list.add(var);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int");
		var.setName("baz");
		var.setIsNonNull(true);
		var.setIsStatic(true);
		list.add(var);
		
		
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Variables(), list);
		
		
		content.readVariables(variables);
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    int foo;"
				+ "    int bar;"
				+ "    int foobar;"
				+ "    int baz;"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    (wellFormed(heap)) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	@Test
	void testAddVariablesArray() {
		EList<JavaVariable> list = new BasicEList<>();
		JavaVariable var;
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int[]");
		var.setName("foo");
		list.add(var);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int[]");
		var.setName("bar");
		var.setIsStatic(true);
		list.add(var);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int[]");
		var.setName("foobar");
		var.setIsNonNull(true);
		list.add(var);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int[]");
		var.setName("baz");
		var.setIsNonNull(true);
		var.setIsStatic(true);
		list.add(var);
		
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Variables(), list);
		
		
		content.readVariables(variables);
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    int[] foo;"
				+ "    int[] bar;"
				+ "    int[] foobar;"
				+ "    int[] baz;"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    ("
				+ "        foo.<created>=TRUE"
				+ "        & bar.<created>=TRUE"
				+ "        & foobar.<created>=TRUE"
				+ "        & baz.<created>=TRUE "
				+ "        & wellFormed(heap)"
				+ "    ) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	@Test
	void testAddVariablesParam() {
		EList<JavaVariable> list = new BasicEList<>();
JavaVariable var;
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("Object");
		var.setName("foo");
		var.setKind(VariableKind.PARAM);
		list.add(var);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("Object");
		var.setName("bar");
		var.setIsStatic(true);
		var.setKind(VariableKind.PARAM);
		list.add(var);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("Object");
		var.setName("foobar");
		var.setIsNonNull(true);
		var.setKind(VariableKind.PARAM);
		list.add(var);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("Object");
		var.setName("baz");
		var.setIsNonNull(true);
		var.setIsStatic(true);
		var.setKind(VariableKind.PARAM);
		list.add(var);
		
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Variables(), list);
		
		
		content.readVariables(variables);
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    Object foo;"
				+ "    Object bar;"
				+ "    Object foobar;"
				+ "    Object baz;"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    ("
				+ "        Object::exactInstance(foobar)=TRUE"
				+ "        & foobar.<created>=TRUE"
				+ "        & foobar!=null"
				+ "        & Object::exactInstance(baz)=TRUE"
				+ "        & baz.<created>=TRUE"
				+ "        & baz!=null"
				+ "        & wellFormed(heap)"
				+ "    ) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	@Test
	void testAddVariablesReturn() {
		EList<JavaVariable> list = new BasicEList<>();
		JavaVariable var;
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int");
		var.setName("foo");
		list.add(var);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int");
		var.setName("bar");
		var.setIsStatic(true);
		list.add(var);
		
		JavaVariable returnVar = CbcmodelFactory.eINSTANCE.createJavaVariable();
		returnVar.setName("here");
		returnVar.setType("int");
		returnVar.setKind(VariableKind.RETURN);
		list.add(returnVar);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int");
		var.setName("foobar");
		var.setIsNonNull(true);
		list.add(var);
		
		var = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var.setType("int");
		var.setName("baz");
		var.setIsNonNull(true);
		var.setIsStatic(true);
		list.add(var);
		
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Variables(), list);
		
		
		assertEquals(returnVar, content.readVariables(variables));
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    int foo;"
				+ "    int bar;"
				+ "    int here;"
				+ "    int foobar;"
				+ "    int baz;"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    (wellFormed(heap)) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	@Test
	void testAddFields() {
		EList<Field> list = new BasicEList<>();
		Field field;
	
		
		field = CbcclassFactory.eINSTANCE.createField();
		field.setName("foo");
		field.setType("int");
		field.setVisibility(Visibility.PUBLIC);
		//field.setIsStatic(false);
		list.add(field);
		
		field = CbcclassFactory.eINSTANCE.createField();
		field.setName("bar");
		field.setType("Object");
		field.setVisibility(Visibility.PUBLIC);
		//field.setIsStatic(false);
		list.add(field);
		
		
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Fields(), list);
		
		
		content.readVariables(variables);
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    ("
				+ "        Object::exactInstance(self.bar)=TRUE"
				+ "        & self.bar.<created>=TRUE"
				+ "        & self.bar!=null"
				+ "        & wellFormed(heap)"
				+ "    ) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	@Test
	void testAddParams() {
		EList<Parameter> list = new BasicEList<>();
		Parameter param;
	
		
		param = CbcclassFactory.eINSTANCE.createParameter();
		param.setName("foo");
		param.setType("int");
		list.add(param);
		
		param = CbcclassFactory.eINSTANCE.createParameter();
		param.setName("bar");
		param.setType("Object");
		list.add(param);
		
		
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Params(), list);
		
		
		content.readVariables(variables);
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    int foo;"
				+ "    Object bar;"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    ("
				+ "        wellFormed(heap)"
				+ "    ) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	@Test
	void testAddParamsArray() {
		EList<Parameter> list = new BasicEList<>();
		Parameter param;
	
		
		param = CbcclassFactory.eINSTANCE.createParameter();
		param.setName("foo");
		param.setType("int[]");
		list.add(param);
		
		param = CbcclassFactory.eINSTANCE.createParameter();
		param.setName("bar");
		param.setType("Object[]");
		list.add(param);
		
		
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Params(), list);
		
		
		content.readVariables(variables);
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    int[] foo;"
				+ "    Object[] bar;"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    ("
				+ "        foo.<created> = TRUE"
				+ "        & bar.<created> = TRUE"
				+ "        & wellFormed(heap)"
				+ "    ) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	@Test
	void testAddParamsNonNull() {
		EList<Parameter> list = new BasicEList<>();
		Parameter param;
	
		
		param = CbcclassFactory.eINSTANCE.createParameter();
		param.setName("foo");
		param.setType("int");
		param.setIsNonNull(true);
		list.add(param);
		
		param = CbcclassFactory.eINSTANCE.createParameter();
		param.setName("bar");
		param.setType("Object");
		param.setIsNonNull(true);
		list.add(param);
		
		
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Params(), list);
		
		
		content.readVariables(variables);
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    int foo;"
				+ "    Object bar;"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    ("
				+ "        Object::exactInstance(bar)=TRUE"
				+ "        & bar.<created>=TRUE"
				+ "        & bar!=null"
				+ "        & wellFormed(heap)"
				+ "    ) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	@Test
	void testAddParamReturn() {
		EList<Parameter> list = new BasicEList<>();
		Parameter var;
		
		var = CbcclassFactory.eINSTANCE.createParameter();
		var.setName("foo");
		var.setType("int");
		list.add(var);
		
		var = CbcclassFactory.eINSTANCE.createParameter();
		var.setName("bar");
		var.setType("int");
		list.add(var);
		
		Parameter returnParam = CbcclassFactory.eINSTANCE.createParameter();
		returnParam.setName("ret");
		returnParam.setType("int");
		list.add(returnParam);
		
		var = CbcclassFactory.eINSTANCE.createParameter();
		var.setName("foobar");
		var.setType("int");
		list.add(var);
		
		var = CbcclassFactory.eINSTANCE.createParameter();
		var.setName("baz");
		var.setType("int");
		list.add(var);
		
		
		
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Params(), list);
		
		JavaVariable returnVar = content.readVariables(variables);
		
		assertEquals(returnVar.getName(), returnParam.getName());
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    int foo;"
				+ "    int bar;"
				+ "    int ret;"
				+ "    int foobar;"
				+ "    int baz;"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    ( wellFormed(heap)) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	

	@Test
	void testAddGlobalConditions() {
		EList<Condition> list = new BasicEList<>();
		Condition cond;
		
		cond = CbcmodelFactory.eINSTANCE.createCondition();
		cond.setName("cond1");
		list.add(cond);
		
		cond = CbcmodelFactory.eINSTANCE.createCondition();
		cond.setName("cond2");
		list.add(cond);
		
		cond = CbcmodelFactory.eINSTANCE.createCondition();
		cond.setName("cond3");
		list.add(cond);
		
		
		GlobalConditions conditions = CbcmodelFactory.eINSTANCE.createGlobalConditions();
		conditions.eSet(CbcmodelPackage.eINSTANCE.getGlobalConditions_Conditions(), list);
		
		content.readGlobalConditions(conditions);
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    ("
				+ "        cond1"
				+ "        & cond2"
				+ "        & cond3"
				+ "        & wellFormed(heap)"
				+ "    ) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	static void assertEqualsNormalized(String expected, String actual) {
		//KeYFileContent is really inconsistent with its spacing, so we normalize it.
		String expectedNormalized = expected.replaceAll("( |\n)+", " ").replaceAll("([^\\w]) ", "$1").replaceAll(" ([^\\w])", "$1");
		String actualNormalized = actual.replaceAll("( |\n)+", " ").replaceAll("([^\\w]) ", "$1").replaceAll(" ([^\\w])", "$1");
		
		assertEquals(expectedNormalized, actualNormalized);
	}

}
