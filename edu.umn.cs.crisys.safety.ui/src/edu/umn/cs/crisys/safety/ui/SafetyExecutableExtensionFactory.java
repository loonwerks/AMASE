package edu.umn.cs.crisys.safety.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;
import edu.umn.cs.crisys.safety.ui.internal.SafetyActivator;

public class SafetyExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
	@Override
	protected Bundle getBundle() {
		return SafetyActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return SafetyActivator.getInstance().getInjector(SafetyActivator.EDU_UMN_CS_CRISYS_SAFETY_SAFETY);
	}
}
