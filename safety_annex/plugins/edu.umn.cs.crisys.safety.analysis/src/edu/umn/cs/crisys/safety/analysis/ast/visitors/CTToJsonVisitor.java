package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAndNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAst;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBinaryIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTConstantNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTUnaryIdNode;

public class CTToJsonVisitor implements CTAstVisitor<JsonObject> {

	public JsonObject visit(CTAst ast) {
		return ast.accept(this);
	}

	@Override
	public JsonObject visit(CT ct) {
		JsonObject resultObj = new JsonObject();
		resultObj.add("tree", visit(ct.rootNode));
		return resultObj;
	}

	@Override
	public JsonObject visit(CTAndNode node) {
		// For non bottom node: add op, is failure or not, child nodes
		JsonObject resultObj = new JsonObject();
		JsonArray linkArray = new JsonArray();

		resultObj.add("op", new JsonPrimitive(node.op.toString()));
		if (node.isFailure) {
			resultObj.add("type", new JsonPrimitive("failure"));
		} else {
			if (!node.isFeasible) {
				resultObj.add("type", new JsonPrimitive("not-feasible"));
			} else {
				resultObj.add("type", new JsonPrimitive("non-failure"));
			}
		}
		resultObj.add("expr", new JsonPrimitive(""));
		resultObj.add("description", new JsonPrimitive(node.op.toString()));
		resultObj.add("link", linkArray);
		JsonArray childArray = new JsonArray();
		for (CTNode child : node.childNodes) {
			childArray.add(visit(child));
		}
		resultObj.add("children", childArray);

		return resultObj;

	}

	@Override
	public JsonObject visit(CTOrNode node) {
		// For non bottom node: add op, is failure or not, child nodes
		JsonObject resultObj = new JsonObject();
		JsonArray linkArray = new JsonArray();

		resultObj.add("op", new JsonPrimitive(node.op.toString()));
		if (node.isFailure) {
			resultObj.add("type", new JsonPrimitive("failure"));
		} else {
			if (!node.isFeasible) {
				resultObj.add("type", new JsonPrimitive("not-feasible"));
			} else {
				resultObj.add("type", new JsonPrimitive("non-failure"));
			}
		}
		resultObj.add("expr", new JsonPrimitive(""));
		resultObj.add("description", new JsonPrimitive(node.op.toString()));
		resultObj.add("link", linkArray);
		JsonArray childArray = new JsonArray();
		for (CTNode child : node.childNodes) {
			childArray.add(visit(child));
		}
		resultObj.add("children", childArray);

		return resultObj;
	}

	@Override
	public JsonObject visit(CTBinaryIdNode node) {
		// For bottom node: show expr, is failure or not, child nodes
		JsonObject resultObj = new JsonObject();
		JsonArray linkArray = new JsonArray();

		resultObj.add("op", new JsonPrimitive(""));
		if (node.isFailure) {
			resultObj.add("type", new JsonPrimitive("failure"));
		} else {
			if (!node.isFeasible) {
				resultObj.add("type", new JsonPrimitive("not-feasible"));
			} else {
				resultObj.add("type", new JsonPrimitive("non-failure"));
			}
		}
		resultObj.add("expr", new JsonPrimitive(node.expr.toString()));
		resultObj.add("description", new JsonPrimitive(node.expr.toString()));
		resultObj.add("link", linkArray);
		JsonArray childArray = new JsonArray();
		for (CTNode child : node.childNodes) {
			childArray.add(visit(child));
		}
		resultObj.add("children", childArray);

		return resultObj;

	}

	@Override
	public JsonObject visit(CTUnaryIdNode node) {
		// For bottom node: show expr, is failure or not, child nodes
		JsonObject resultObj = new JsonObject();
		JsonArray linkArray = new JsonArray();

		resultObj.add("op", new JsonPrimitive(""));
		if (node.isFailure) {
			resultObj.add("type", new JsonPrimitive("failure"));
		} else {
			if (!node.isFeasible) {
				resultObj.add("type", new JsonPrimitive("not-feasible"));
			} else {
				resultObj.add("type", new JsonPrimitive("non-failure"));
			}
		}
		resultObj.add("expr", new JsonPrimitive(node.expr.toString()));
		resultObj.add("description", new JsonPrimitive(node.expr.toString()));
		resultObj.add("link", linkArray);
		JsonArray childArray = new JsonArray();
		for (CTNode child : node.childNodes) {
			childArray.add(visit(child));
		}
		resultObj.add("children", childArray);

		return resultObj;

	}

	@Override
	public JsonObject visit(CTConstantNode node) {
		// For bottom node: show expr, is failure or not, child nodes
		JsonObject resultObj = new JsonObject();
		JsonArray linkArray = new JsonArray();

		resultObj.add("op", new JsonPrimitive(""));
		if (node.isFailure) {
			resultObj.add("type", new JsonPrimitive("failure"));
		} else {
			if (!node.isFeasible) {
				resultObj.add("type", new JsonPrimitive("not-feasible"));
			} else {
				resultObj.add("type", new JsonPrimitive("non-failure"));
			}
		}
		resultObj.add("expr", new JsonPrimitive(node.expr.toString()));
		resultObj.add("description", new JsonPrimitive(node.expr.toString()));
		resultObj.add("link", linkArray);
		JsonArray childArray = new JsonArray();
		for (CTNode child : node.childNodes) {
			childArray.add(visit(child));
		}
		resultObj.add("children", childArray);

		return resultObj;

	}

	@Override
	public JsonObject visit(CTIdNode node) {
		// For bottom node: show expr, is failure or not, child nodes
		JsonObject resultObj = new JsonObject();
		JsonArray linkArray = new JsonArray();

		resultObj.add("op", new JsonPrimitive(""));
		if (node.isFailure) {
			resultObj.add("type", new JsonPrimitive("failure"));
		} else {
			if (!node.isFeasible) {
				resultObj.add("type", new JsonPrimitive("not-feasible"));
			} else {
				resultObj.add("type", new JsonPrimitive("non-failure"));
			}
		}
		resultObj.add("expr", new JsonPrimitive(node.expr.toString()));

		// if fault associated with node, add fault description and probability info
		String description = node.expr.toString();
		if (node.fault != null) {
			description = "Fault: " + node.fault.explanitoryText + ", Probability: " + node.fault.probability;
		}

		resultObj.add("description", new JsonPrimitive(description));
		resultObj.add("link", linkArray);
		JsonArray childArray = new JsonArray();
		for (CTNode child : node.childNodes) {
			childArray.add(visit(child));
		}
		resultObj.add("children", childArray);

		return resultObj;

	}

}
