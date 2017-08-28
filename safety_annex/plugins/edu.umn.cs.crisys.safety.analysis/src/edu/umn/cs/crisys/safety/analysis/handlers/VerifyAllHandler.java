package edu.umn.cs.crisys.safety.analysis.handlers;

public class VerifyAllHandler extends VerifyHandler {

	 @Override
	    public boolean isRecursive() {
	        return true;
	    }

	    @Override
	    protected String getJobName() {
	        return "AGREE - Verify All Layers";
	    }
}
