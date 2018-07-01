package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;
import java.util.List;

public class SoteriaCompLib {
	public List<SoteriaComp> comps = new ArrayList<SoteriaComp>();

	public SoteriaCompLib() {
	}

	public void addComp(SoteriaComp comp) {
		comps.add(comp);
	}

}
