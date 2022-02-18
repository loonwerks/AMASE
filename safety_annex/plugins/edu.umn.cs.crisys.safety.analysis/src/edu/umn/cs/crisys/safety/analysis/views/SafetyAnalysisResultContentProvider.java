package edu.umn.cs.crisys.safety.analysis.views;

import java.beans.PropertyChangeEvent;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddPairwiseFaultDriverWitnesses;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.api.results.JRealizabilityResult;
import jkind.api.results.PropertyResult;
import jkind.api.results.Status;
import jkind.api.ui.results.AnalysisResultColumnViewer;
import jkind.api.ui.results.AnalysisResultContentProvider;

public class SafetyAnalysisResultContentProvider extends AnalysisResultContentProvider {

	public SafetyAnalysisResultContentProvider(AnalysisResultColumnViewer viewer) {
		super(viewer);
	}

	private final static List<Status> goodStatuses = Collections
			.unmodifiableList(Lists.newArrayList(Status.VALID, Status.VALID_REFINED, Status.WAITING, Status.WORKING));

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof JRealizabilityResult) {
			JRealizabilityResult result = (JRealizabilityResult) inputElement;
			return result.getPropertyResults().toArray();
		} else if (inputElement instanceof JKindResult) {
			JKindResult result = (JKindResult) inputElement;
			final boolean allParentResultsValid = result.getPropertyResults()
					.stream()
					.allMatch(p -> goodStatuses.contains(p.getStatus()));
			return result.getPropertyResults()
					.stream()
					.filter(p -> !AddPairwiseFaultDriverWitnesses.FAULT_DRIVER_PAIR_WITNESS_PATTERN.matcher(p.getName())
							.find() || !allParentResultsValid)
					.collect(Collectors.toList())
					.toArray();
		} else {
			return getChildren(inputElement);
		}
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof JRealizabilityResult) {
			return new Object[0];
		} else if (parentElement instanceof JKindResult) {
			JKindResult result = (JKindResult) parentElement;
			final boolean allParentResultsValid = result.getPropertyResults()
					.stream()
					.allMatch(p -> goodStatuses.contains(p.getStatus()));
			return result.getPropertyResults()
					.stream()
					.filter(p -> !AddPairwiseFaultDriverWitnesses.FAULT_DRIVER_PAIR_WITNESS_PATTERN.matcher(p.getName())
							.find() || !allParentResultsValid)
					.collect(Collectors.toList())
					.toArray();
		} else if (parentElement instanceof CompositeAnalysisResult) {
			CompositeAnalysisResult result = (CompositeAnalysisResult) parentElement;
			return result.getChildren().toArray();
		} else {
			return new Object[0];
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		Object newValue = event.getNewValue();
		if (newValue instanceof PropertyResult && AddPairwiseFaultDriverWitnesses.FAULT_DRIVER_PAIR_WITNESS_PATTERN
				.matcher(((PropertyResult) newValue).getName())
				.find()) {
			if (!goodStatuses.contains(((PropertyResult) newValue).getStatus())) {
				super.propertyChange(event);
			}
		} else {
			super.propertyChange(event);
		}
	}

}
