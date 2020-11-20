package edu.umn.cs.crisys.safety.analysis.generators;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.ModelUnit;
import org.osate.aadl2.NamedElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTToJsonVisitor;
import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.util.Filesystem;

public class CTToJsonGenerator {

	static public URI makeJsonFile(URI dan) throws CoreException, IOException {

		URI folder = dan.trimSegments(1);
		String base = Filesystem.getBase(dan);

		URI writeFolder = Filesystem.createFolder(folder, new String[] { "json-generated" });
		URI json = writeFolder.appendSegment(base).appendFileExtension("json");

		return json;
	}

	static private void printJson(URI json, String whatToPrint) throws CoreException, IOException {

		IFile print = Filesystem.getFile(json);
		Filesystem.writeFile(print, whatToPrint.getBytes());
	}

	static public URI createJson(URI compURI, CT ct) throws Exception {


		JsonElement je = toJson(ct);

		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();

		URI jsonURI = null;
		try {

			jsonURI = makeJsonFile(compURI);
			printJson(jsonURI, gson.toJson(je));

		} catch (CoreException | IOException e) {
			System.err.println("Trouble writing Json representation to filesystem.");
			e.printStackTrace();
		}

		return jsonURI;
	}

	public static ModelUnit getContainingModelUnit(NamedElement ne) {

		EObject o = ne.eContainer();
		while (o != null && !(o instanceof ModelUnit)) {
			o = o.eContainer();
		}

		return (ModelUnit) o;
	}

	public static JsonElement toJson(CT ct) {
		JsonArray modelsJson = new JsonArray();
		CTToJsonVisitor ctToJsonVisitor = new CTToJsonVisitor();
		// convert ct to modelsJson, supporting the following:
		// 1) directed acyclic graph (DAG) (tree with multiple inheritance)
		// 2) expansion and contraction of subtree upon user interaction
		// 3) show and hide detailed node description/information

		modelsJson.addAll(ctToJsonVisitor.visit(ct));
		return modelsJson;
	}

}
