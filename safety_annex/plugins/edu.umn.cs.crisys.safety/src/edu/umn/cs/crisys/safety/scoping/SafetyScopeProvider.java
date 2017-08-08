/*
 * generated by Xtext
 */
package edu.umn.cs.crisys.safety.scoping;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;
import com.rockwellcollins.atc.agree.agree.NestedDotID;
import com.rockwellcollins.atc.agree.agree.impl.NestedDotIDImpl;

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
public class SafetyScopeProvider extends com.rockwellcollins.atc.agree.scoping.AgreeScopeProvider {
	@Override
	protected
	IScope scope_NamedElement(NestedDotID ctx, EReference ref) {
    	IScope scope = super.scope_NamedElement(ctx, ref);
    	return scope;
    }
	protected
	IScope scope_NamedElement(NestedDotIDImpl ctx, EReference ref) {
    	IScope scope = scope_NamedElement((NestedDotID) ctx, ref);
    	return scope;
    }
}
