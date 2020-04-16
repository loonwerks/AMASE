package edu.umn.cs.crisys.safety.analysis.handlers;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.impl.ComponentTypeImpl;
import org.osate.aadl2.impl.DefaultAnnexSubclauseImpl;

import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl;
import edu.umn.cs.crisys.safety.safety.impl.SafetyContractSubclauseImpl;

public class FaultDisableHandler extends VerifyHandler {

	private HashMap<String, List<String>> mapCompNameToFaults = new HashMap<String, List<String>>();
	static private HashMap<String, Boolean> mapFaultToSelected = new HashMap<String, Boolean>();

	@Override
	public Object execute(ExecutionEvent event) {
		makeMenu();
		mapCompNameToFaults.clear();
		return event;

	}

	/**
	 * Gather the AadlPackage, send classifiers to method that creates menu
	 * pop-up, save faults that have been selected.
	 * @return bool: menu made without issue
	 */
	private boolean makeMenu() {
		XtextEditor xtextEditor = EditorUtils.getActiveXtextEditor();
		if (xtextEditor == null) {
			return false;
		}
		EObject original = xtextEditor.getDocument().readOnly(resource -> resource.getContents().get(0));
		AadlPackage aadlPackage = null;
		if (original instanceof AadlPackage) {
			aadlPackage = (AadlPackage) original;
		}

		if (aadlPackage == null) {
			return false;
		}

		makeMap(aadlPackage.getOwnedPublicSection().getOwnedClassifiers());
		makeDialog();
		saveSelectedFaults();
		return true;
	}

	/**
	 * Checks classifiers in AadlPackage for any ComponentTypeImpl
	 * and then collects safety annex spec statements to gather
	 * fault names specific to that component.
	 *
	 * @param classifiers Classifiers in this AadlPackage
	 */
	private void makeMap(List<Classifier> classifiers) {

		for (Classifier cl : classifiers) {
			if (cl instanceof ComponentTypeImpl) {
				String compName = cl.getName();
				List<String> faultNameList = new ArrayList<String>();
				List<AnnexSubclause> asList = cl.getOwnedAnnexSubclauses();
				for (AnnexSubclause as : asList) {
					if (as.getName().equalsIgnoreCase("safety")) {
						if (as instanceof DefaultAnnexSubclauseImpl) {
							DefaultAnnexSubclauseImpl defaultAnnex = (DefaultAnnexSubclauseImpl) as;
							SafetyContractSubclauseImpl safetyAnnex = (SafetyContractSubclauseImpl) defaultAnnex
									.getParsedAnnexSubclause();
							List<SpecStatement> specs = ((SafetyContract) safetyAnnex.getContract()).getSpecs();
							for (SpecStatement spec : specs) {
								if (spec instanceof FaultStatementImpl) {
									FaultStatementImpl fs = (FaultStatementImpl) spec;
									faultNameList.add(fs.getName());
								}
							}

						}
					}
				}
				// If list nonempty, add to map for this component
				if (!faultNameList.isEmpty()) {
					mapCompNameToFaults.put(compName, faultNameList);
				}
			}
		}
	}

	/**
	 * Create java frame, put all the gui stuff inside.
	 */
	private void makeDialog() {

		JFrame mainFrame = new JFrame("Disable Faults Menu");
		mainFrame.setSize(700, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.setLocationRelativeTo(null);

		JLabel headerLabel = new JLabel(
				"<html>Select faults that you wish to disable." + " The default (unselected) state is enabled."
						+ " To disable faults in other packages, navigate to that"
						+ " package in the editor and reselect this menu item.</html>",
				SwingConstants.CENTER);
		headerLabel.setSize(300, 100);
		headerLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		JPanel checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

		fillCheckboxPanel(checkboxPanel);
		JScrollPane scrollPane = new JScrollPane(checkboxPanel);

		mainFrame.add(headerLabel);
		mainFrame.add(scrollPane);
		mainFrame.setVisible(true);
	}

	/**
	 * Fill up the panel with component names and faults (checkboxes)
	 * @param p The panel to fill with comp names/faults
	 * @return filled panel
	 */
	private JPanel fillCheckboxPanel(JPanel p) {

		for (String compName : mapCompNameToFaults.keySet()) {
			JLabel name = new JLabel(compName);
			List<JCheckBox> faultList = new ArrayList<JCheckBox>();
			for (String faultName : mapCompNameToFaults.get(compName)) {
				JCheckBox jc = new JCheckBox(faultName);
				jc.addActionListener(new CheckboxAction("Selected"));
				if (mapFaultToSelected.containsKey(faultName)) {
					jc.setSelected(true);
				}
//				jc.addActionListener(e -> mapFaultToSelected.put(faultName, jc.isSelected()));
				faultList.add(new JCheckBox(faultName));
			}
			p.add(name);
			for (JCheckBox jc : faultList) {
				p.add(jc);
			}
			faultList.clear();
		}

		return p;
	}

	/**
	 *
	 */
	private void saveSelectedFaults() {
		// save faults that have been selected so that when map is
		// repopulated next time menu runs, those same faults are
		// still selected.
	}

	@Override
	protected String getJobName() {
		return "Fault disable menu";
	}

	class CheckboxAction extends AbstractAction {
		public CheckboxAction(String text) {
			super(text);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JCheckBox cbLog = (JCheckBox) e.getSource();
			if (cbLog.isSelected()) {
				mapFaultToSelected.put(cbLog.getName(), cbLog.isSelected());
			}
		}
	}

}
