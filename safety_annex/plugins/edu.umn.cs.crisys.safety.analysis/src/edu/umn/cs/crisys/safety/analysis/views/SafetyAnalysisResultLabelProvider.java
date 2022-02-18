package edu.umn.cs.crisys.safety.analysis.views;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.viewers.ColumnViewer;

import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddPairwiseFaultDriverWitnesses;
import edu.umn.cs.crisys.safety.analysis.results.SafetyPropertyResult;
import jkind.api.ui.results.AnalysisResultColumnViewer.Column;
import jkind.api.ui.results.AnalysisResultLabelProvider;

public class SafetyAnalysisResultLabelProvider extends AnalysisResultLabelProvider {

	public SafetyAnalysisResultLabelProvider(Column column) {
		super(column);
	}

	public SafetyAnalysisResultLabelProvider(Column column, ColumnViewer viewer) {
		super(column, viewer);
	}

	// CAUTION: this string needs to match that in
	// com.rockwellcollins.atc.agree.analysis.translation.LustreAstBuilder#guarSuffix
	public final static String GUARANTEE_BASENAME = "__GUARANTEE";

	public final static int TRUNCATION_LENGTH = 60;

	@Override
	public String getText(Object element) {
		if (element instanceof SafetyPropertyResult) {
			SafetyPropertyResult pr = (SafetyPropertyResult) element;
			switch (column) {
			case PROPERTY:
				if (AddPairwiseFaultDriverWitnesses.FAULT_DRIVER_PAIR_WITNESS_PATTERN.matcher(pr.getName()).find()) {
					List<String> guarantees = new ArrayList<>();
					AgreeRenaming renaming = (AgreeRenaming) pr.getRenaming();
					final Pattern GUARANTEE_PATTERN = Pattern
							.compile(renaming.forceRename(GUARANTEE_BASENAME).replaceAll(".", "\\.") + "(\\d+)");
					Matcher matcher = GUARANTEE_PATTERN.matcher(pr.getName());
					while (matcher.find()) {
						String varName = GUARANTEE_BASENAME + matcher.group(1);
						String guaranteeName = renaming.rename(varName);
						if (guaranteeName.length() > TRUNCATION_LENGTH) {
							guaranteeName = guaranteeName.substring(0, TRUNCATION_LENGTH) + " ...";
						}
						guarantees.add("\"" + guaranteeName + "\"");
					}
					return String.join(" and ", guarantees) + " cannot fail together";
				}
			default:
				/* Fall through to return */
			}
		}
		return super.getText(element);
	}

}
