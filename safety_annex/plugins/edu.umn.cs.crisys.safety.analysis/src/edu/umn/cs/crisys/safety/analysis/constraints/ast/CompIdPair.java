package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import jkind.Assert;

public class CompIdPair {

	public final String compName;
	public final String idName;


	public CompIdPair(String compName, String idName) {
		Assert.isNotNull(compName);
		Assert.isNotNull(idName);
		this.compName = compName;
		this.idName = idName;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CompIdPair)) {
			return false;
		} else {
			CompIdPair otherPair = (CompIdPair) obj;
			if (compName.equals(otherPair.compName) && idName.equals(otherPair.idName)) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public String toString() {
		return compName + "_" + idName;
	}

	@Override
	public int hashCode() {
		int hash = compName.hashCode() + idName.hashCode();
		return hash;
	}

}