package edu.umn.cs.crisys.safety.analysis.handlers;

import javax.swing.JOptionPane;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.osate.aadl2.Element;

public abstract class AadlHandler extends AbstractHandler {
	
	private static Boolean analysis = false;

	protected static final String TERMINATE_ID = "safetyanalysis.commands.terminate";
	
	private IWorkbenchWindow window;

	abstract protected IStatus runJob(Element sel, IProgressMonitor monitor);

	abstract protected String getJobName();

	/*
	 * execute:
	 * @param ExecutionEvent: this is the SafetyAnalysis command that was pressed in the menu
	 * @return Object: URI execution
	 * 
	 * This method gets the node associated with the event (menu item pressed). 
	 * If that node is null, then no component was selected for analysis - will output
	 * dialog box showing error. 
	 * Else it will execute URI.
	 */
	@Override
	public Object execute(ExecutionEvent event) {
		
		// Get the eclipse context of the selection.
		// If no component is selected for analysis, this will return null. 
		EObjectNode node = getEObjectNode(HandlerUtil.getCurrentSelection(event));
		
		// If node is null, that means no aadl component was selected to perform analysis on. 
		// Output dialog box showing that error and return null. 
		if (node == null) {
			
			JOptionPane.showMessageDialog(null, "You must choose an AADL component implementation.",
					"Safety Analysis Error",
				    JOptionPane.ERROR_MESSAGE);
			
			return null;
		}
		
		// Get node uri
		final URI uri = node.getEObjectURI();

		// Check for active workbench window
		window = HandlerUtil.getActiveWorkbenchWindow(event);
		if (window == null) {
			return null;
		}

		// Since there was no issues with the node or the window, 
		// we change the boolean variable to true and continue with analysis
		analysis = true;
		
		// Execute URI (where the magic happens)
		return executeURI(uri);
	}

	public Object executeURI(final URI uri) {
		final XtextEditor xtextEditor = EditorUtils.getActiveXtextEditor();
		if (xtextEditor == null) {
			return null;
		}

		if (!saveChanges(window.getActivePage().getDirtyEditors())) {
			return null;
		}

		WorkspaceJob job = new WorkspaceJob(getJobName()) {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				return xtextEditor.getDocument().readOnly(new IUnitOfWork<IStatus, XtextResource>() {
					@Override
					public IStatus exec(XtextResource resource) throws Exception {
						EObject eobj = resource.getResourceSet().getEObject(uri, true);
						if (eobj instanceof Element) {
							return runJob((Element) eobj, monitor);
						} else {
							return Status.CANCEL_STATUS;
						}
					}
				});
			}
		};

		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
		return null;
	}

	private boolean saveChanges(IEditorPart[] dirtyEditors) {
		if (dirtyEditors.length == 0) {
			return true;
		}

		if (MessageDialog.openConfirm(window.getShell(), "Save editors", "Save editors and continue?")) {
			NullProgressMonitor monitor = new NullProgressMonitor();
			for (IEditorPart e : dirtyEditors) {
				e.doSave(monitor);
			}
			return true;
		} else {
			return false;
		}
	}

	private EObjectNode getEObjectNode(ISelection currentSelection) {
		if (currentSelection instanceof IStructuredSelection) {
			IStructuredSelection iss = (IStructuredSelection) currentSelection;
			if (iss.size() == 1) {
				return (EObjectNode) iss.getFirstElement();
			}
		}
		return null;
	}

	protected IWorkbenchWindow getWindow() {
		return window;
	}
	
	/*
	 * getAnalysisFlag:
	 * @param: none
	 * @return: returns analysis value
	 * 
	 * If the execute method is called, a component is selected, and the window
	 * is non-null, then analysis is set to true. 
	 * This is used in TransformAgree to check whether we should return the original program
	 * or if a transformation will be made. 
	 */
	public static Boolean getAnalysisFlag(){
		return analysis;
	}

}
