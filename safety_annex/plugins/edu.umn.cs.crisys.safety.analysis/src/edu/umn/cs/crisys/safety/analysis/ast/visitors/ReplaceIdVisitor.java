package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.Map;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;

import jkind.lustre.Expr;
import jkind.lustre.IdExpr;


// Note: this is only designed for sub-parts of a node: 
// Statements or equations.  If you apply to a node, it will
// race through all the subnodes, likely escaping scope.

public class ReplaceIdVisitor extends AgreeASTMapVisitor {

	Map<String, String> idMap;
	
	public ReplaceIdVisitor(Map<String, String> idMap) {
		super(new jkind.lustre.visitors.TypeMapVisitor());
		this.idMap = idMap;
	}
	
	@Override
	public IdExpr visit(IdExpr e) {
		if (idMap.containsKey(e.id)) {
			return new IdExpr(e.location, idMap.get(e.id));
		} else {
			return e;
		}
	}
	
}
