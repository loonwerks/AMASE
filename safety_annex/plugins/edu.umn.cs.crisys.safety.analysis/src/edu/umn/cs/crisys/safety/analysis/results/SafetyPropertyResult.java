package edu.umn.cs.crisys.safety.analysis.results;

import jkind.api.results.PropertyResult;
import jkind.api.results.Renaming;

public class SafetyPropertyResult extends PropertyResult {

	private final Renaming renaming;

	public SafetyPropertyResult(String name, Renaming renaming) {
		super(name, renaming);
		this.renaming = renaming;
	}

	public SafetyPropertyResult(String name, Renaming renaming, boolean invertStatus) {
		super(name, renaming, invertStatus);
		this.renaming = renaming;
	}

	public Renaming getRenaming() {
		return renaming;
	}

}
