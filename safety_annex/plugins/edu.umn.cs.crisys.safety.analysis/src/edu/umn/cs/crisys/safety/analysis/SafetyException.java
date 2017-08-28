package edu.umn.cs.crisys.safety.analysis;

public class SafetyException extends RuntimeException {

	/**
	 * SafetyException : this number may need to be changed. 
	 */
	private static final long serialVersionUID = 1L;

	public SafetyException(String message){
		super(message);
	}
}
