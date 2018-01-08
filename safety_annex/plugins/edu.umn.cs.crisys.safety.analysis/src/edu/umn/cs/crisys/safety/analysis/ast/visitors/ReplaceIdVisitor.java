package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.Map;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.RecordAccessExpr;


// Note: this is only designed for sub-parts of a node: 
// Statements or equations.  If you apply to a node, it will
// race through all the subnodes, likely escaping scope.

public class ReplaceIdVisitor extends AgreeASTMapVisitor {

	Map<String, String> idMap;
	
	public ReplaceIdVisitor(Map<String, String> idMap) {
		super(new jkind.lustre.visitors.TypeMapVisitor());
		this.idMap = idMap;
	}
	
	//@Override
	public Expr visit(Expr expr) {
		if(expr instanceof IdExpr) {
		  if (idMap.containsKey(((IdExpr)expr).id)) {
			return new IdExpr(expr.location,((IdExpr)expr).id);
		  } else {
			return expr;
		  }
		} else if(expr instanceof RecordAccessExpr) {
			RecordAccessExpr recordId = (RecordAccessExpr) expr;
			if(idMap.containsKey(recordId.record.toString())) {
				return new RecordAccessExpr(expr.location, recordId.record, recordId.field);
			}else {
				return expr;
			}
		} else {
			throw new SafetyException("Error visiting node: must have IdExpression or " +	
				"Record Access Expression.");
		}
	}
	
}
