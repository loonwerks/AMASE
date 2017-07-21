package edu.umn.cs.crisys.safety.analysis.ast;

import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.FeatureInstance;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;

public class FaultVar extends VarDecl implements FaultASTElement{
	
	public final EObject reference;
	public final ComponentInstance compInst;
    public final FeatureInstance featInst;
    
    public FaultVar(String name, Type type, EObject reference, ComponentInstance compInst, FeatureInstance featInst) {
		super(name, type);
		this.reference = reference;
		this.compInst = compInst;
		this.featInst = featInst;
	}
	
	public FaultVar(String name, Type type, EObject reference, ComponentInstance compInst) {
		this(name, type, reference, compInst, null);
	}
	
	public FaultVar(String name, Type type, EObject reference) {
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

		if (!(obj instanceof FaultVar)) {
			return false;
		}
		FaultVar otherVar = (FaultVar) obj;
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
