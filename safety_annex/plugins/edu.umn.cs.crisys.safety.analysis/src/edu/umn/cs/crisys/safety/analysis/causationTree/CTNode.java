package edu.umn.cs.crisys.safety.analysis.causationTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class CTNode extends CTAst {
	public boolean isFailure = false;
	public boolean isFeasible = true;
	public ArrayList<CTNode> childNodes = new ArrayList<>();
	public HashSet<String> idSet = new HashSet<>();

	public void addChildNode(CTNode childNode) {
		childNodes.add(childNode);
	}

	public void addChildNodes(List<CTNode> childNodesToAdd) {
		for (CTNode child : childNodesToAdd) {
			childNodes.add(child);
		}
	}

	public void addId(String id) {
		idSet.add(id);
	}

	public void addIds(Set<String> ids) {
		for (String id : ids) {
			idSet.add(id);
		}
	}

	public Boolean containsChildNodes() {
		if (childNodes.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

}
