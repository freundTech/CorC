package de.tu_bs.cs.isf.cbc.model.util;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

public class ConditionUtil {
	public static final Condition TRUE = CbcmodelFactory.eINSTANCE.createCondition();
	public static final Condition FALSE = CbcmodelFactory.eINSTANCE.createCondition();
	
	static {
		TRUE.setName("true");
		FALSE.setName("false");
	}
	
	public static Condition and(Condition first, Condition second) {
		Condition cond = CbcmodelFactory.eINSTANCE.createCondition();
		cond.setName(first.getName() + " & " + second.getName());
		
		return cond;
	}
	
	public static Condition or(Condition first, Condition second) {
		Condition cond = CbcmodelFactory.eINSTANCE.createCondition();
		cond.setName(first.getName() + " & " + second.getName());
		
		return cond;
	}
}
