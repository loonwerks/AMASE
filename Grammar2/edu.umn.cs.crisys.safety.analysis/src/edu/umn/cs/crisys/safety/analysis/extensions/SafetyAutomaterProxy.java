package edu.umn.cs.crisys.safety.analysis.extensions;

import org.eclipse.core.runtime.IConfigurationElement;

import edu.umn.cs.crisys.safety.analysis.ast.SafetyProgram;

public class SafetyAutomaterProxy extends ExtensionProxy implements SafetyAutomater {

	
	private SafetyAutomater extractor;

    protected SafetyAutomaterProxy(IConfigurationElement configElem) {
        super(configElem);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public SafetyProgram transform(SafetyProgram program) {
        SafetyAutomater extractor = getSafetyAutomater();

        if (extractor != null) {
            return extractor.transform(program);
        }
        return null;
    }

    private SafetyAutomater getSafetyAutomater() {
        if (extractor != null) {
            return extractor;
        }
        try {
            extractor = (SafetyAutomater) configElem.createExecutableExtension(ATT_CLASS);
        } catch (Exception e) {
            System.err.println("error instantiating safety automater in plugin "
                    + configElem.getDeclaringExtension().getContributor().getName());
        }
        return extractor;
    }
}
