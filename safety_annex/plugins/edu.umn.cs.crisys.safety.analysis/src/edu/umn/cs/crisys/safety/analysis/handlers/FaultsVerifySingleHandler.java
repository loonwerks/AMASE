package edu.umn.cs.crisys.safety.analysis.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;

import com.rockwellcollins.atc.agree.analysis.handlers.VerifySingleHandler;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.transform.AddFaultsToAgree;

public class FaultsVerifySingleHandler extends VerifySingleHandler {

	private static MenuItem item;

	@Override
	public Object execute(ExecutionEvent event) {
		Event selEvent = (Event) event.getTrigger();
		item = (MenuItem) selEvent.widget;
		AddFaultsToAgree.setTransformFlag(item);
		// clear static variables before each run
		AddFaultsToNodeVisitor.init();
		return super.execute(event);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see edu.umn.cs.crisys.safety.analysis.handlers.AadlHandler#getJobName()
	 *
	 * Output job name (will see it at the bottom of Osate2 after selected)
	 */
	@Override
	protected String getJobName() {
		return "Fault analysis: single layer";
	}
}
