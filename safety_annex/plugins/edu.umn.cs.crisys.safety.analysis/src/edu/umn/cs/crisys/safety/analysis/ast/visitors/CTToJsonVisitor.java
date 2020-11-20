package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAndNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAst;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTDivideNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTFalseNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTGreaterEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTGreaterNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTInitNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIntNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTLessEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTLessNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTMinusNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTMultiplyNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNonEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNonInitNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTPlusNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTPreNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTRealNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTRootNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTTrueNode;

public class CTToJsonVisitor implements CTAstVisitor<JsonArray> {

	public JsonArray visit(CTAst ast) {
		return ast.accept(this);
	}

	@Override
	public JsonArray visit(CT ct) {
		JsonArray resultArray = new JsonArray();
		JsonObject rootObj = new JsonObject();
		rootObj.add("name", new JsonPrimitive(ct.rootNode.nodeName));
		rootObj.add("node_type", new JsonPrimitive("ROOT Node"));

		for (CTNode child : ct.rootNode.childNodes.values()) {
			visit(child);
		}
		resultArray.add(rootObj);
		return resultArray;
	}

	@Override
	public JsonArray visit(CTAndNode node) {
		JsonArray resultArray = new JsonArray();
		JsonObject rootObj = new JsonObject();
		rootObj.add("node_name", new JsonPrimitive(node.nodeName));
		rootObj.add("node_type", new JsonPrimitive("AND Node"));

		for (CTNode child : node.childNodes.values()) {
			visit(child);
		}
		resultArray.add(rootObj);
		return null;
	}

	@Override
	public JsonArray visit(CTDivideNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTEqualNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTFalseNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTGreaterEqualNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTGreaterNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTIdNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTInitNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTIntNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTLessEqualNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTLessNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTMinusNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTMultiplyNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTNonEqualNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTNonInitNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTOrNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTPlusNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTPreNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTRealNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTRootNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTTrueNode node) {
		// TODO Auto-generated method stub
		return null;
	}

}
