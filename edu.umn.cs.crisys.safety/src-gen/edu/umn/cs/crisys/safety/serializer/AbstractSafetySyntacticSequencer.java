/*
 * generated by Xtext
 */
package edu.umn.cs.crisys.safety.serializer;

import com.google.inject.Inject;
import edu.umn.cs.crisys.safety.services.SafetyGrammarAccess;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AlternativeAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public abstract class AbstractSafetySyntacticSequencer extends AbstractSyntacticSequencer {

	protected SafetyGrammarAccess grammarAccess;
	protected AbstractElementAlias match_TermExpr_LeftParenthesisKeyword_14_0_a;
	protected AbstractElementAlias match_TermExpr_LeftParenthesisKeyword_14_0_p;
	protected AbstractElementAlias match_TriggerCondition___EnablerKeyword_1_0_LeftCurlyBracketKeyword_1_1_RightCurlyBracketKeyword_1_3___or___MustKeyword_0_0_LeftCurlyBracketKeyword_0_1_RightCurlyBracketKeyword_0_3__;
	protected AbstractElementAlias match_WheneverStatement_OccurKeyword_0_0_0_6_0_or_OccursKeyword_0_0_0_6_1;
	protected AbstractElementAlias match_WheneverStatement_OccurKeyword_1_0_0_7_0_or_OccursKeyword_1_0_0_7_1;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (SafetyGrammarAccess) access;
		match_TermExpr_LeftParenthesisKeyword_14_0_a = new TokenAlias(true, true, grammarAccess.getTermExprAccess().getLeftParenthesisKeyword_14_0());
		match_TermExpr_LeftParenthesisKeyword_14_0_p = new TokenAlias(true, false, grammarAccess.getTermExprAccess().getLeftParenthesisKeyword_14_0());
		match_TriggerCondition___EnablerKeyword_1_0_LeftCurlyBracketKeyword_1_1_RightCurlyBracketKeyword_1_3___or___MustKeyword_0_0_LeftCurlyBracketKeyword_0_1_RightCurlyBracketKeyword_0_3__ = new AlternativeAlias(false, false, new GroupAlias(false, false, new TokenAlias(false, false, grammarAccess.getTriggerConditionAccess().getEnablerKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getTriggerConditionAccess().getLeftCurlyBracketKeyword_1_1()), new TokenAlias(false, false, grammarAccess.getTriggerConditionAccess().getRightCurlyBracketKeyword_1_3())), new GroupAlias(false, false, new TokenAlias(false, false, grammarAccess.getTriggerConditionAccess().getMustKeyword_0_0()), new TokenAlias(false, false, grammarAccess.getTriggerConditionAccess().getLeftCurlyBracketKeyword_0_1()), new TokenAlias(false, false, grammarAccess.getTriggerConditionAccess().getRightCurlyBracketKeyword_0_3())));
		match_WheneverStatement_OccurKeyword_0_0_0_6_0_or_OccursKeyword_0_0_0_6_1 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getWheneverStatementAccess().getOccurKeyword_0_0_0_6_0()), new TokenAlias(false, false, grammarAccess.getWheneverStatementAccess().getOccursKeyword_0_0_0_6_1()));
		match_WheneverStatement_OccurKeyword_1_0_0_7_0_or_OccursKeyword_1_0_0_7_1 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getWheneverStatementAccess().getOccurKeyword_1_0_0_7_0()), new TokenAlias(false, false, grammarAccess.getWheneverStatementAccess().getOccursKeyword_1_0_0_7_1()));
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}
	
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_TermExpr_LeftParenthesisKeyword_14_0_a.equals(syntax))
				emit_TermExpr_LeftParenthesisKeyword_14_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TermExpr_LeftParenthesisKeyword_14_0_p.equals(syntax))
				emit_TermExpr_LeftParenthesisKeyword_14_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TriggerCondition___EnablerKeyword_1_0_LeftCurlyBracketKeyword_1_1_RightCurlyBracketKeyword_1_3___or___MustKeyword_0_0_LeftCurlyBracketKeyword_0_1_RightCurlyBracketKeyword_0_3__.equals(syntax))
				emit_TriggerCondition___EnablerKeyword_1_0_LeftCurlyBracketKeyword_1_1_RightCurlyBracketKeyword_1_3___or___MustKeyword_0_0_LeftCurlyBracketKeyword_0_1_RightCurlyBracketKeyword_0_3__(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_WheneverStatement_OccurKeyword_0_0_0_6_0_or_OccursKeyword_0_0_0_6_1.equals(syntax))
				emit_WheneverStatement_OccurKeyword_0_0_0_6_0_or_OccursKeyword_0_0_0_6_1(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_WheneverStatement_OccurKeyword_1_0_0_7_0_or_OccursKeyword_1_0_0_7_1.equals(syntax))
				emit_WheneverStatement_OccurKeyword_1_0_0_7_0_or_OccursKeyword_1_0_0_7_1(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     '('*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) 'Get_Property' '(' component=Expr
	 *     (rule start) (ambiguity) 'event' '(' id=NestedDotID
	 *     (rule start) (ambiguity) 'floor' '(' expr=Expr
	 *     (rule start) (ambiguity) 'if' a=Expr
	 *     (rule start) (ambiguity) 'latched' '(' expr=Expr
	 *     (rule start) (ambiguity) 'pre' '(' expr=Expr
	 *     (rule start) (ambiguity) 'prev' '(' delay=Expr
	 *     (rule start) (ambiguity) 'real' '(' expr=Expr
	 *     (rule start) (ambiguity) 'this' '.' subThis=NestedDotID
	 *     (rule start) (ambiguity) 'this' (rule start)
	 *     (rule start) (ambiguity) 'time' (rule start)
	 *     (rule start) (ambiguity) 'timefall' '(' id=NestedDotID
	 *     (rule start) (ambiguity) 'timeof' '(' id=NestedDotID
	 *     (rule start) (ambiguity) 'timerise' '(' id=NestedDotID
	 *     (rule start) (ambiguity) base=[NamedElement|QCPREF]
	 *     (rule start) (ambiguity) op='-'
	 *     (rule start) (ambiguity) op='not'
	 *     (rule start) (ambiguity) val=BooleanLiteral
	 *     (rule start) (ambiguity) val=INTEGER_LIT
	 *     (rule start) (ambiguity) val=REAL_LIT
	 *     (rule start) (ambiguity) {BinaryExpr.left=}
	 *     (rule start) (ambiguity) {FnCallExpr.fn=}
	 *     (rule start) (ambiguity) {RecordExpr.record=}
	 *     (rule start) (ambiguity) {RecordUpdateExpr.record=}
	 */
	protected void emit_TermExpr_LeftParenthesisKeyword_14_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) 'Get_Property' '(' component=Expr
	 *     (rule start) (ambiguity) 'if' a=Expr
	 *     (rule start) (ambiguity) 'prev' '(' delay=Expr
	 *     (rule start) (ambiguity) op='-'
	 *     (rule start) (ambiguity) op='not'
	 *     (rule start) (ambiguity) {BinaryExpr.left=}
	 *     (rule start) (ambiguity) {RecordUpdateExpr.record=}
	 */
	protected void emit_TermExpr_LeftParenthesisKeyword_14_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ('must' '{' '}') | ('enabler' '{' '}')
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) (rule start)
	 */
	protected void emit_TriggerCondition___EnablerKeyword_1_0_LeftCurlyBracketKeyword_1_1_RightCurlyBracketKeyword_1_3___or___MustKeyword_0_0_LeftCurlyBracketKeyword_0_1_RightCurlyBracketKeyword_0_3__(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'occurs' | 'occur'
	 *
	 * This ambiguous syntax occurs at:
	 *     effect=Expr (ambiguity) 'during' interval=TimeInterval
	 *     effect=Expr (ambiguity) (rule end)
	 *     excl='exclusively' (ambiguity) 'during' interval=TimeInterval
	 *     excl='exclusively' (ambiguity) (rule end)
	 */
	protected void emit_WheneverStatement_OccurKeyword_0_0_0_6_0_or_OccursKeyword_0_0_0_6_1(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'occurs' | 'occur'
	 *
	 * This ambiguous syntax occurs at:
	 *     effect=Expr (ambiguity) 'during' interval=TimeInterval
	 *     effect=Expr (ambiguity) (rule end)
	 *     excl='exclusively' (ambiguity) 'during' interval=TimeInterval
	 *     excl='exclusively' (ambiguity) (rule end)
	 */
	protected void emit_WheneverStatement_OccurKeyword_1_0_0_7_0_or_OccursKeyword_1_0_0_7_1(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
