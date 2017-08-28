package edu.umn.cs.crisys.safety.analysis.ast;

import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.FeatureInstance;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;

public class SafetyVar extends VarDecl implements SafetyASTElement{
	
	public final EObject reference;
	public final ComponentInstance compInst;
    public final FeatureInstance featInst;
    
    public SafetyVar(String name, Type type, EObject reference, ComponentInstance compInst, FeatureInstance featInst) {
		super(name, type);
		this.reference = reference;
		this.compInst = compInst;
		this.featInst = featInst;
	}
	
	public SafetyVar(String name, Type type, EObject reference, ComponentInstance compInst) {
		this(name, type, reference, compInst, null);
	}
	
	public SafetyVar(String name, Type type, EObject reference) {
		this(name, type, reference, null, null);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String) {
			return this.id.equals((String) obj);
		}

		if (!(obj instanceof SafetyVar)) {
			return false;
		}
		SafetyVar otherVar = (SafetyVar) obj;
		return otherVar.compInst == compInst && otherVar.id.equals(id);
	}
	
	@Override
	public String toString(){
		if(compInst != null){
			return compInst.getInstanceObjectPath() + "." + id;
		}
		return id;
	}

}
