package edu.umn.cs.crisys.safety.analysis.handlers;

import com.rockwellcollins.atc.agree.analysis.AgreeException;

import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaComp;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaCompLib;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaModel;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;

public class IvcToSoteriaGenerator {
	SoteriaCompLib soteriaCompLib = new SoteriaCompLib();

	public SoteriaModel generateModel(AnalysisResult result) {
		// get current verification result
		AnalysisResult curResult = ((CompositeAnalysisResult) result).getChildren().get(0);
		// get component name
		String compName = curResult.getName().replaceFirst("Verification for ", "").replaceFirst(".impl", "");
		// build Soteria component for the current component
		// get current component name
		SoteriaComp curComp = new SoteriaComp(compName);
		walkthroughResults(curResult, curComp);
		soteriaCompLib.addComp(curComp);
		return new SoteriaModel(soteriaCompLib);
	}

	private void walkthroughResults(AnalysisResult result, SoteriaComp comp) {
		// if one layer, the curResult is JKindResult for the current component verified
		if (result instanceof JKindResult) {
			for (PropertyResult propertyResult : ((JKindResult) result).getPropertyResults()) {
				System.out.println("property name: "+propertyResult.getName());
//				if(propertyResult.getName().contains("guarantee")){
//					system.
//				}
//				else if(propertyResult.getName().contains("guarantee")) {
//
//				}
			}
		}
		// if multiple layers
		// the first is JKindResult for the current layer
		// since we skip consistency check in safety analysis
		// the rest ones are CompositeAnanalysisResult, one for each subcomponent verified
		else if (result instanceof CompositeAnalysisResult) {
			for (AnalysisResult curResult : ((CompositeAnalysisResult) result).getChildren()) {
				// recursively call walkthroughResults
				walkthroughResults(curResult, comp);
			}

		} else {
			throw new AgreeException("Not JKindResult or CompositeAnalysisResult");
		}
	}

}
