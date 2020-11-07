package edu.umn.cs.crisys.safety.analysis.causationTree;

import java.math.BigDecimal;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;

public class CTRealNode extends CTNode {

	public final BigDecimal value;

	public CTRealNode(String nodeName, BigDecimal value) {
		super(nodeName);
		this.value = value;
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(CTAstVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}

}
