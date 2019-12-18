package edu.umn.cs.crisys.safety.parsing;

import org.osate.aadl2.AnnexLibrary;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.modelsupport.errorreporting.ParseErrorReporter;
import org.osate.annexsupport.AnnexParseUtil;
import org.osate.annexsupport.AnnexParser;

import com.google.inject.Injector;

import edu.umn.cs.crisys.safety.parser.antlr.SafetyParser;
import edu.umn.cs.crisys.safety.services.SafetyGrammarAccess;
import edu.umn.cs.crisys.safety.ui.internal.SafetyActivator;

public class SafetyAnnexParser implements AnnexParser{


	private SafetyParser parser;

    protected SafetyParser getParser() {
        if (parser == null) {
            Injector injector = SafetyActivator.getInstance().getInjector(
                    SafetyActivator.EDU_UMN_CS_CRISYS_SAFETY_SAFETY);
            parser = injector.getInstance(SafetyParser.class);
        }
        return parser;
    }

    protected SafetyGrammarAccess getGrammarAccess() {
        return getParser().getGrammarAccess();
    }

	@Override
	public AnnexLibrary parseAnnexLibrary(String annexName, String source, String filename, int line, int column,
			ParseErrorReporter errReporter) {

		return (AnnexLibrary) AnnexParseUtil.parse(getParser(),source, getGrammarAccess().getSafetyLibraryRule(), filename, line,
                column, errReporter);
	}

	@Override
	public AnnexSubclause parseAnnexSubclause(String annexName, String source, String filename, int line, int column,
			ParseErrorReporter errReporter) {
		return (AnnexSubclause) AnnexParseUtil.parse(getParser(),source,getGrammarAccess().getSafetySubclauseRule(),filename,line,column, errReporter);
	}



}
