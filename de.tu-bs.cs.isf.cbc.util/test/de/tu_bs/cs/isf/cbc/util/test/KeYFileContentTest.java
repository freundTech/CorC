package de.tu_bs.cs.isf.cbc.util.test;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
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
				+ "    ( & wellFormed(heap)) -> {"
				+ "        heapAtPre := heap"
				+ "    } "
				+ "    \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}

	@Test
	void testSetHelper() {
		content.setHelper("foo.key");
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"foo.key\";"
				+ "\\programVariables {"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    ( & wellFormed(heap)) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	
	@Test
	void testSetProgramVariables() {
		content.setProgramVariables("foo; bar;");
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    foo;"
				+ "    bar;"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    ( & wellFormed(heap)) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
		
	}
	
	@Test
	void testSetGLobalConditions() {
		content.setGlobalConditions("foo & bar");
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    (foo & bar & wellFormed(heap)) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	@Test
	void testSetConfitionsObjectsCreated() {
		content.setConditionObjectsCreated("foo & bar");
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    (foo & bar & wellFormed(heap)) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}

	
	@Test
	void testSetSelfConditions() {
		content.setSelfConditions("foo & bar");
		assertEqualsNormalized(
				"\\javaSource \"location/src\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {"
				+ "    Heap heapAtPre;"
				+ "}"
				+ "\\problem {"
				+ "    (foo & bar & wellFormed(heap)) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
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
		
		String[] varNames = {
				"int foo",
				"static int bar",
				"non-null int foobar",
				"static non-null int baz",
		};
		
		for (String name : varNames) {
			var = CbcmodelFactory.eINSTANCE.createJavaVariable();
			var.setName(name);
			list.add(var);
		}
		
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
				+ "    ( & wellFormed(heap)) -> {"
				+ "        heapAtPre := heap"
				+ "    } \\<{}\\> ()"
				+ "}",
				content.getKeYStatementContent());
	}
	
	@Test
	void testAddVariablesArray() {
		EList<JavaVariable> list = new BasicEList<>();
		JavaVariable var;
		
		String[] varNames = {
				"int[] foo",
				"static int[] bar",
				"non-null int[] foobar",
				"static non-null int[] baz",
		};
		
		for (String name : varNames) {
			var = CbcmodelFactory.eINSTANCE.createJavaVariable();
			var.setName(name);
			list.add(var);
		}
		
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
				+ "        & foo.<created>=TRUE"
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
		
		String[] varNames = {
				"Object foo",
				"static Object bar",
				"non-null Object foobar",
				"static non-null Object baz",
		};
		
		for (String name : varNames) {
			var = CbcmodelFactory.eINSTANCE.createJavaVariable();
			var.setName(name);
			var.setKind(VariableKind.PARAM);
			list.add(var);
		}
		
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
				+ "        & Object::exactInstance(foobar)=TRUE"
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
		
		String[] varNames1 = {
				"int foo",
				"static int bar",
		};
		for (String name : varNames1) {
			var = CbcmodelFactory.eINSTANCE.createJavaVariable();
			var.setName(name);
			list.add(var);
		}
		
		JavaVariable returnVar = CbcmodelFactory.eINSTANCE.createJavaVariable();
		returnVar.setName("int here");
		returnVar.setKind(VariableKind.RETURN);
		list.add(returnVar);
		
		String[] varNames2 = {
				"non-null int foobar",
				"static non-null int baz",
		};
		for (String name : varNames2) {
			var = CbcmodelFactory.eINSTANCE.createJavaVariable();
			var.setName(name);
			list.add(var);
		}
		
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
				+ "    ( & wellFormed(heap)) -> {"
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
		field.setIsStatic(false);
		list.add(field);
		
		field = CbcclassFactory.eINSTANCE.createField();
		field.setName("bar");
		field.setType("Object");
		field.setVisibility(Visibility.PUBLIC);
		field.setIsStatic(false);
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
				+ "        & Object::exactInstance(self.bar)=TRUE"
				+ "        & self.bar.<created>=TRUE"
				+ "        & self.bar!=null"
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
