package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.List;

public interface BaseFault {
	public void setPath(List<String> path);

	public String getID();
}
