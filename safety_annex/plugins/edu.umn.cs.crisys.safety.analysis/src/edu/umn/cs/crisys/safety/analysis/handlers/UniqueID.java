package edu.umn.cs.crisys.safety.analysis.handlers;

import jkind.Assert;

/**
 *
 * UniqueID class contains the variable/function ID and its record type if applicable
 * This is used in ensuring unique names are created during the translation process.
 *
 */
public class UniqueID {

	public final String id;

	public UniqueID(String id) {
		Assert.isNotNull(id);
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UniqueID)) {
			return false;
		} else {
			UniqueID otherID = (UniqueID) obj;
			if (id.equals(otherID.id)) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public int hashCode() {
		int hash = id.hashCode();
		return hash;
	}

}
