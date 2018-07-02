package edu.umn.cs.crisys.safety.analysis.handlers;

import com.rockwellcollins.atc.agree.analysis.AgreeException;

import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaComp;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaCompLib;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaModel;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;

public class IvcToSoteriaGenerator {
	SoteriaCompLib soteriaCompLib = new SoteriaCompLib();

	public SoteriaModel generateModel(AnalysisResult result) {
		walkthroughResults(result);
		return new SoteriaModel(soteriaCompLib);
	}

	private void walkthroughResults(AnalysisResult result) {
		// get current verification result
		AnalysisResult curResult = ((CompositeAnalysisResult) result).getChildren().get(0);
		// if one layer, the curResult is JKindResult for the current component verified
		if (curResult instanceof JKindResult) {
			// build Soteria component for the current component
			// get current component name
			SoteriaComp curComp = new SoteriaComp("cur comp name");
			soteriaCompLib.addComp(curComp);
		}
		// if multiple layers
		// the first is JKindResult for the current layer
		// since we skip consistency check in safety analysis
		// the rest ones are CompositeAnanalysisResult, one for each subcomponent verified
		else if (curResult instanceof CompositeAnalysisResult) {
			// recursively call walkthroughResults
			walkthroughResults(curResult);

		} else {
			throw new AgreeException("Not JKindResult or CompositeAnalysisResult");
		}
	}

}
