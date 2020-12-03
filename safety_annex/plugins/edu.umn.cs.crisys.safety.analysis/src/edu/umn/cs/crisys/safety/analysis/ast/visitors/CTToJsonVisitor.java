package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAndNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAst;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBinaryIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTConstantNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTUnaryIdNode;

public class CTToJsonVisitor implements CTAstVisitor<JsonArray> {

	public JsonArray visit(CTAst ast) {
		return ast.accept(this);
	}

	@Override
	public JsonArray visit(CT ct) {
		JsonArray resultArray = new JsonArray();
		JsonObject rootObj = new JsonObject();
//		rootObj.add("name", new JsonPrimitive(ct.rootNode.nodeName));
//		rootObj.add("node_type", new JsonPrimitive("ROOT Node"));
//
//		for (CTNode child : ct.rootNode.childNodes.values()) {
//			visit(child);
//		}
		resultArray.add(rootObj);
		return resultArray;
	}

	@Override
	public JsonArray visit(CTAndNode node) {
		JsonArray resultArray = new JsonArray();
		JsonObject rootObj = new JsonObject();
//		rootObj.add("node_name", new JsonPrimitive(node.nodeName));
//		rootObj.add("node_type", new JsonPrimitive("AND Node"));
//
//		for (CTNode child : node.childNodes.values()) {
//			visit(child);
//		}
		resultArray.add(rootObj);
		return null;
	}

	@Override
	public JsonArray visit(CTOrNode node) {
		JsonArray resultArray = new JsonArray();
		JsonObject rootObj = new JsonObject();
//		rootObj.add("node_name", new JsonPrimitive(node.nodeName));
//		rootObj.add("node_type", new JsonPrimitive("AND Node"));
//
//		for (CTNode child : node.childNodes.values()) {
//			visit(child);
//		}
		resultArray.add(rootObj);
		return null;
	}

	@Override
	public JsonArray visit(CTBinaryIdNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTUnaryIdNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTConstantNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray visit(CTIdNode node) {
		// TODO Auto-generated method stub
		return null;
	}

}
