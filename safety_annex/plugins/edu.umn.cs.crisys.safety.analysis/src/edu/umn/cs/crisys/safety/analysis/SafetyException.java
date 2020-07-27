package edu.umn.cs.crisys.safety.analysis;

import javax.swing.JOptionPane;

public class SafetyException extends RuntimeException {

	/**
	 * SafetyException : this number may need to be changed.
	 */
	private static final long serialVersionUID = 1L;

	public SafetyException(String message){
		JOptionPane.showMessageDialog(null, message, "Safety Annex Error", JOptionPane.ERROR_MESSAGE);
	}
}
