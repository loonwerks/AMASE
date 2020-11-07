package edu.umn.cs.crisys.safety.analysis.causationTree;

import java.math.BigInteger;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;

public class CTIntNode extends CTNode {

	public final BigInteger value;

	public CTIntNode(String nodeName, BigInteger value) {
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
