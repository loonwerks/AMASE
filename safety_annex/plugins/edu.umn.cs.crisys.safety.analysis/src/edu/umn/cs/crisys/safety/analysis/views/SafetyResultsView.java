package edu.umn.cs.crisys.safety.analysis.views;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Composite;

import com.rockwellcollins.atc.agree.analysis.views.AgreeMenuListener;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsView;

import jkind.api.results.AnalysisResult;

public class SafetyResultsView extends AgreeResultsView {

	public static final String ID = "edu.umn.cs.crisys.safety.analysis.views.safetyResultsView";

	private SafetyAnalysisResultTree safetyResultTree;
	private AgreeMenuListener safetyMenuListener;

	@Override
	public void createPartControl(Composite parent) {
		safetyResultTree = new SafetyAnalysisResultTree(parent);
		safetyResultTree.getViewer().setAutoExpandLevel(2);

		safetyMenuListener = new AgreeMenuListener(getViewSite().getWorkbenchWindow(), safetyResultTree);
		MenuManager manager = new MenuManager();
		manager.setRemoveAllWhenShown(true);
		manager.addMenuListener(safetyMenuListener);
		safetyResultTree.getControl().setMenu(manager.createContextMenu(safetyResultTree.getViewer().getTree()));
	}

	@Override
	public void setFocus() {
		safetyResultTree.getControl().setFocus();
	}

	@Override
	public void setInput(AnalysisResult result, AgreeResultsLinker linker) {
		safetyResultTree.setInput(result);
		safetyMenuListener.setLinker(linker);
	}

}
