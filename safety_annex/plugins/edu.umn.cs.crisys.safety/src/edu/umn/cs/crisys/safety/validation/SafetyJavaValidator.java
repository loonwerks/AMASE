/*
 * generated by Xtext
 */
package edu.umn.cs.crisys.safety.validation;

import static com.rockwellcollins.atc.agree.validation.AgreeType.BOOL;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.osate.aadl2.AadlInteger;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.Feature;
import org.osate.aadl2.NamedElement;

import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.Expr;
import com.rockwellcollins.atc.agree.agree.IntLitExpr;
import com.rockwellcollins.atc.agree.agree.NamedID;
import com.rockwellcollins.atc.agree.agree.NestedDotID;
import com.rockwellcollins.atc.agree.agree.NodeDefExpr;
import com.rockwellcollins.atc.agree.agree.RealLitExpr;
import com.rockwellcollins.atc.agree.agree.UnaryExpr;
import com.rockwellcollins.atc.agree.validation.AgreeType;

import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.EqValue;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.InputStatement;
import edu.umn.cs.crisys.safety.safety.Interval;
import edu.umn.cs.crisys.safety.safety.IntervalEq;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.RangeEq;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.SetEq;
import edu.umn.cs.crisys.safety.safety.TemporalConstraint;
import edu.umn.cs.crisys.safety.safety.TransientConstraint;
import edu.umn.cs.crisys.safety.safety.TriggerCondition;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class SafetyJavaValidator extends AbstractSafetyJavaValidator {
	
	/*
	 * (non-Javadoc)
	 * @see com.rockwellcollins.atc.agree.validation.AgreeJavaValidator#isResponsible(java.util.Map, org.eclipse.emf.ecore.EObject)
	 */
	protected boolean isResponsible(Map<Object, Object> context, EObject eObject) {
		return (eObject.eClass().getEPackage() == SafetyPackage.eINSTANCE) || eObject instanceof AadlPackage;
	}

	@Check
	/*
	 * Puts out a warning if the fault description is an empty string. 
	 * The description is optional, but shouldn't be ""
	 * 
	 * Checks that the nested dot id used as the fault name is a valid
	 * node definition. 
	 */
	public void checkFaultSpecStmt(FaultStatement specStmt){
		
		NestedDotID nodeName = specStmt.getFaultDefName();
		
		// Check on fault description
		if(specStmt.getStr().isEmpty()) {
			warning(specStmt, "Fault description is optional, but should "
					+ "not be an empty string.");
		}
		
		// Check that the nested dot id (fault node name) is a valid node definition
		NamedElement finalNodeName = getFinalNestId(nodeName);
		if(!(finalNodeName instanceof NodeDefExpr)){
			error(nodeName, "The fault name must be a valid node definition.");
		}
		
		// The check done on node arg list and input list is done in checkInput
	}
	
	
	/* Input Statements
	 * (1): Get container - check to see if it is a fault statement.
	 * 		Then gather the deepest sub of the fault definition name
	 * 		and get it's base. For instance: fault.fail_to
	 * 		The base of that deepest sub should be "fail_to."
	 * 		Make sure this is a valid NodeDefExpr and then collect the arguments.
	 * 		These arguments must match all input fault values (by name). 
	 * (2): Make sure the types of arguments match the types of expressions
	 * 		passed in as fault inputs (connections or otherwise). 
	 * (3) Type check Expr (right side) with arguments to node (left side)
	 */
	@Check(CheckType.FAST)
	public void checkInput(InputStatement inputs){
		
		// Get container of inputs (FaultSpecStmt)
		EObject container = inputs.eContainer();
		NamedElement defNameSub;
		NestedDotID defName;
		
		// Gather expression list from rhs of '<-'
		EList<Expr> exprList = inputs.getNom_conn();
		
		// (1) : if the container is a fault statement:
		// Grab the nested dot id (fault node name: faults.fail_to) and 
		// get the base of the deepest sub (fail_to)
		if (container instanceof FaultStatement) {
			FaultStatement faultStatement = (FaultStatement) container;
			defName = faultStatement.getFaultDefName();
			
			while(defName.getSub() != null){
				defName = defName.getSub();
			}
			
			// This should be fail_to (NodeDefExpr)
			defNameSub = defName.getBase();
			
			// Make sure we have a NodeDefExpr
			if(defNameSub instanceof NodeDefExpr){
				
				// Cast to NodeDefExpr
				NodeDefExpr nodeDef = (NodeDefExpr) defNameSub;
				// Gather the arguments from node def
				EList<Arg> args = nodeDef.getArgs();
				EList<NamedID> inputList = inputs.getFault_in();
				
				// Make an easy string list to access that contains the argument names 
				// from the node defintion
				ArrayList<String> argNames = new ArrayList<String>();
				
				for(Arg arg : args){
					argNames.add(arg.getFullName());
				}
				
				// (2) : If the sizes are accurate, make sure names match
				if(args.size()-1 == (inputList.size())){
					
					// Go through input list and make sure each name is in the arg list
					for(NamedID input : inputList){
			    	
			    		String inputName = input.getFullName();
			    		
			    		//Check to see if the input name is in the arg list
			    		if(!argNames.contains(inputName)){
			    			error(inputs, "Input names must match fault node definition names. "
			    					+"The input name "+inputName+" is not an input in the node definition. "
			    					+"All possible input names are: "+argNames.toString());
			    		}
			    	}
				}else{
			    	// Wrong number of arguments/inputs
					// To print list of inputs, I need to remove "trigger" from the list
					ArrayList<String> noTrigger = new ArrayList<String>();
					for(String item : argNames){
						if(!(item.equals("trigger"))){
							noTrigger.add(item);
						}
					}
					error(inputs, "With this fault definition, you must have "+(argNames.size()-1)+" inputs."
							+ " These are called: "+noTrigger.toString());
				}
				
				
				// (3) : Type check arguments to node with Expr on rhs
				// Go through expression list
				for(int i = 0; i < exprList.size(); i++){
					
					// Save expr and arg
					Expr expr = exprList.get(i);
					Arg arg = args.get(i);
					
					// Get agree types of each
					AgreeType typeExpr = getAgreeType(expr);
					AgreeType typeArg = getAgreeType(arg);
					
					// See if they match using agree "matches" method
					if(!(matches(typeExpr, typeArg))){
						error(expr, "Left side ("+arg.getName()+") is of type "+typeArg.toString()
						+" but right side is of type "+typeExpr.toString());
					}
				}
				
			}else{
				// Not a node def expr
				error(defName, "Fault definition name must be an instance of NodeDefExpr."
						+" It is: "+defNameSub.getFullName()+".");
			}
		}else{
			// Not in fault statement
			error(inputs, "Fault inputs must be in a fault statement, not a "+container.toString()+".");
		}
	}
	
	/* Output Statements
	 * (1): Get container of the output in order to check the fault node for
	 * 		list of return values. This is compared with the list of fault outputs.
	 * 		If the sizes of the lists do not match, the names of the return values 
	 * 		do not match, or the output statement is not in the fault spec, we
	 * 		send out an error.
	 * (2): Make sure we have valid nominal connections as our output connections.
	 * (3): Make sure the nominal connection types match return value types. 
	 */
	@Check
	public void checkOutput(OutputStatement outputs){
		// (1) 
		// Get container of inputs (FaultSpecStmt)
		EObject container = outputs.eContainer();
		// defName: faults.fail_to
		NestedDotID defName;
		// defNameSub: fail_to
		NamedElement defNameSub;
		// Make an easy string list to access that contains the return names 
		// from the node definition
		ArrayList<String> retNames = new ArrayList<String>();
		// List of return values
		EList<Arg> retvals = null;
				
		// if the container is a fault statement:
		// Grab the nested dot id (fault node name: defName: faults.fail_to) and 
		// get the base of the deepest sub (defNameSub: fail_to)
		if (container instanceof FaultStatement) {
			FaultStatement faultStatement = (FaultStatement) container;
			defName = faultStatement.getFaultDefName();
					
			while(defName.getSub() != null){
				defName = defName.getSub();
			}
					
			// This should be fail_to (NodeDefExpr)
			defNameSub = defName.getBase();
					
			// Make sure we have a NodeDefExpr
			if(defNameSub instanceof NodeDefExpr){
						
				// Cast to NodeDefExpr
				NodeDefExpr nodeDef = (NodeDefExpr) defNameSub;
				// Gather the return values from node def
				retvals = nodeDef.getRets();
				EList<NamedID> outputList = outputs.getFault_out();
				
				for(Arg arg : retvals){
					retNames.add(arg.getFullName());
					
				}
					
				// If the sizes are accurate, make sure names match
				if(retvals.size() == (outputList.size())){
						
					// Go through output list and make sure each name is in the arg list
					for(NamedID output : outputList){
			    		String outputName = output.getFullName();
				    		
			    		//Check to see if the input name is in the arg list
				   		if(!retNames.contains(outputName)){
				   			error(outputs, "Output names must match fault node definition return value names. "
				   					+"The output name "+outputName+" is not an return value in the node definition. "
				   					+"All possible output names are: "+retNames.toString());
				   		}
				   	}
				}else{
				   	// Wrong number of arguments/inputs
					error(outputs, "The number of outputs must match the number of return values in the node definition."
							+" With the fault"+defNameSub.getName()+", this value must be "+retNames.size()+".");
				}
			} else{
				// Not a node def expr
				error(defName, "Fault definition name must be an instance of NodeDefExpr."
						+" It is: "+defNameSub.getFullName()+".");
			}
		}else{
			// Not a fault statement
			error(outputs, "Fault outputs must be in a fault statement, not a "+container.toString()+".");
		}
		

		
		// ============================HERE++++++++++++++++++++++++++++++++++++++++++
		
		
		
		
		// (2) Make sure connections are valid component connections in aadl
		// Event ports, data ports, buses, etc. 
		
		// List of nominal connections
		EList<NestedDotID> nomConns = outputs.getNom_conn();
		// The sub of the nominal connection
		NestedDotID nomSub = null;
		// The base of the sub of the nominal connection
		NamedElement baseSubNom;
		
		// Make sure that the connection is a valid component connection
		for(NestedDotID nom : nomConns){
			nomSub = nom.getSub();
			if(nomSub != null){
				baseSubNom = nomSub.getBase();
				
				System.out.println();
				
				if(!(baseSubNom instanceof Feature)){
					error(nom, "This connection must be a component connection (Feature). "
				            +"Possible features are "
							+"Port, BusAccess, DataAccess, SubprogramAccess, EventPort, EventDataPort.");
				}
				break;
			}else{
				error(nom, "The connection "+nomSub.toString()+" is null.");
			}
		}
		
		// (3) Type check between nominal connections and return values
		
		// Iterate through the list of nominal connections (nomConns)
//		for(int i = 0; i < nomConns.size(); i++){
//			
//			// Get the nominal connection
//			NestedDotID nom = nomConns.get(i);
//			// Return value from the list of all return values
//			Arg returnArg = null; 
//			// There is no reason why retvals should still be null.
//			// If it is, there are other errors that would be shown to the user.
//			if(retvals != null){
//				returnArg = retvals.get(i);
//			}else{
//				error(outputs, "Return value list is empty.");
//			}
//			
//			// get agree type of return value
//			AgreeType typeReturnArg = getAgreeType(returnArg);
//			// Get the final nested id of the nominal connection
//			NamedElement nestedNom = getFinalNestId(nom);
//			// Get agree type of that nested id
//			AgreeType typeNom = getAgreeType(nestedNom);
//			
//			System.out.println();
//			
//			// Use agrees "matches" method to check types
//			if(!matches(typeNom, typeReturnArg)){
//				error(nom, "Left side (nominal connection) is of type "+typeNom.toString()
//				+" but right side (return value) is of type "+typeReturnArg.toString());
//			}
//		}
	}
	
	/*
	 * Check Duration: 
	 * (1) : If transient, check for interval (there must be one). 
	 * 		 Checks for valid integer interval, 
	 * 		 that the lower and upper integers in the interval are not constants.
	 * (2) : If permanent, there cannot be an interval associated with it. 
	 */
	@Check
	public void checkDuration(DurationStatement durationStmt){
		
		TemporalConstraint tc = durationStmt.getTc();
		
		// (1): See if we have a transient duration
		// If so, there must be a duration interval associated with it. 
		if(tc instanceof TransientConstraint){
			// Check for valid integer interval
			Interval interval = durationStmt.getInterv();
			if(interval != null){
				Expr lower = interval.getLow();
			    Expr higher = interval.getHigh();
			    
			    // Lower value is integer valued literal and not a const
			    if(!(lower instanceof IntLitExpr || isConst(lower))){
			        error(lower, "Lower interval must be an integer valued literal.");
			    }
			    
			    // Higher value is integer valued literal
			    if(!(higher instanceof IntLitExpr || isConst(higher))){
		            error(higher, "Higher interval must be an integer valued literal.");
		        }
			}else{
				error(tc, "There must be a duration interval associated with transient faults.");
			}
			
		// (2): Else we have a permanent fault and hence should have no duration interval. 	
		}else{
			Interval interval = durationStmt.getInterv();
			if(interval != null){
				error(tc, "It makes no sense to have a duration interval on a permanent fault.");
			}
		}
	}
	
	
	/*
	 *  Trigger Statements:
	 *  Calls helper function to check trigger condition.
	 *  
	 *  Make sure probability statement is a valid probability 
	 *  (real number between 0 and 1 inclusive)
	 */
	@Check
	public void checkTriggerStatement(TriggerStatement triggerStmt){
		
		// First check the trigger condition
		checkTriggerCondition(triggerStmt.getCond());
		
		// Check the optional probability expression
		if(triggerStmt.getProbability() != null ){
			
			// Check for non-real valued probability
			// Try casting string to double, catch exceptions to print out error
			double result = 0;
			try{
				result = Double.parseDouble(triggerStmt.getProbability());
			} catch(NullPointerException npe){
				error(triggerStmt, "Valid real number required");
			} catch(NumberFormatException nfe){
				error(triggerStmt, "Valid real number required");
			}
			
			// Now check to make sure it's a valid probability (btwn 0 and 1 inclusive)
			if((result < 0) || (result > 1)){
				error(triggerStmt, "Probability must be between 0 and 1 inclusive");
			}	
		}
	}
	
	/*
	 * Trigger Condition: 
	 * Checks nonempty list and only boolean values in expression list.
	 * Expr validity is done primarily through agree
	 */
	@Check
	public void checkTriggerCondition(TriggerCondition tc){
		if(tc != null){
			
			// Make sure expression list for trigger conditions is nonempty
			EList<Expr> exprList = tc.getExprList();
			if(exprList.isEmpty()) {
				error(tc, "Trigger condition list cannot be empty.");
			}
			
			// For each expression in the list, make sure they are all of type boolean
			for(Expr expr : exprList){
				if (expr != null) {
		            AgreeType exprType = getAgreeType(expr);
		            if (!matches(BOOL, exprType)) {
		                error(tc, "Expression for trigger condition is of type '" + exprType.toString()
		                        + "' but must be of type 'bool'");
		            }
		        }
			}
		}
	}
	
	
	/*
	 *  EqStatements: 
	 *  Call agrees checkArg routine for each arg in the statement. 
	 *  The expressions on the rhs are validated through agree.
	 */
	@Check
	public void checkEqStatement(EqValue eqStmt){
		
		// For each arg in the list, call agree 'checkArg' method for validation
		EList<Arg> args = eqStmt.getLhs();
		for(Arg arg : args){
			checkArg(arg);
		}
	}
	
	/*
	 * IntervalEqStatements:
	 * Check the time interval consists of both integer or both real literal values. 
	 * 
	 */
	@Check
	public void checkIntervalEqStatement(IntervalEq intervalEq){
		
		// Check valid real OR integer interval
		
		Interval interval = intervalEq.getInterv();
		Expr lower = interval.getLow();
	    Expr higher = interval.getHigh();
	    
	    // Both must be real or both must be integer
	    if(!((lower instanceof IntLitExpr && higher instanceof IntLitExpr)|| (lower instanceof RealLitExpr && higher instanceof RealLitExpr))){
	    	error(intervalEq, "Lower and higher interval values must be both real or both integer.");
	    }
	    
	    // Neither can be constants
	    if(isConst(lower) || isConst(higher)){
	    	error(intervalEq, "Lower and higher interval values must be real or integer valued literals.");
	    }
	}
	
	/*
	 * SetEqStatements:
	 * Check the set eq statements for empty set or non-integer values
	 */
	@Check
	public void checkSetEqStatement(SetEq setEq){
		
		// Check for empty list
		if((setEq.getList().isEmpty()) && (setEq.getL1() == null)){
			error(setEq, "Set cannot be empty.");
		}
		
		// Get the expr and get Agree type from it
		Expr lhsExpr = setEq.getL1();
		AgreeType lhsType = getAgreeType(lhsExpr);
		
		// Make sure types match (int)
		if(!matches(AgreeType.INT, lhsType)){
			error(lhsExpr, "Valid integer required in set");
		}
		
		// Get the list and do the same to expressions in the list
		EList<Expr> exprList = setEq.getList();
		
		if(!exprList.isEmpty()){
			for(Expr exp : exprList){
				AgreeType expType = getAgreeType(exp);
				if(!matches(AgreeType.INT, expType)){
					error(exp, "Valid integer required in set");
				}
			}
		}
	}
	
	/*
	 * RangeEqStatments:
	 * (1) Make sure expressions are constants
	 * (2) Check for UnaryExpr (negative integers)
	 * 	   If Unary:
	 * 		cast to unary expr
	 * 		call testNegativeInteger
	 * 		
	 * (3) Check for IntLitExpr
	 * 	   If IntLit:
	 * 		cast to IntLit
	 * 		call testPositiveInteger
	 * 
	 * (4) Else error 
	 * 
	 * (5) If integer results are non-null:
	 * 		check for strict inequality lhs < rhs
	 */
	@Check
	public void checkRangeEqStatement(RangeEq range){
		
		Expr lhs = range.getL1();
		Expr rhs = range.getL2();
		
		Integer intlhs = null;
		Integer intrhs = null;

		// (1) Check for constants
		if(!exprIsConst(lhs)){
			error(lhs, "Range values must be valid integer constants.");
		}
		if(!exprIsConst(rhs)){
			error(rhs, "Range values must be valid integer constants.");
		}
		
		// LHS TEST
		// (2), (3): If lhs UnaryExpr: get op, get val, concat strings, parse int
		if(lhs instanceof UnaryExpr){
			
			UnaryExpr lhsUnary = (UnaryExpr) lhs;
			intlhs = testNegativeInteger(lhsUnary);
			
			// If return value is null, not a valid negative integer
			if(intlhs == null){
				error(lhs, "Must have valid negative or positive integer in range.");
			}
			
		} else if(lhs instanceof IntLitExpr){
			// Else check if lhs IntLitExpr: cast, get val, parse int
			IntLitExpr lhsIntLit = (IntLitExpr) lhs;
			
			intlhs = testPositiveInteger(lhsIntLit);
			
			// If return value null, put out error message
			if(intlhs == null){
				error(lhs, "Must have valid positive integer in range.");
			}
			
		} else{
			// else throw validation error
			error(lhs, "Range values must be valid integers");
		}
		
		
		
		// RHS TEST:
		// (2), (3): If rhs UnaryExpr: call testNegativeInteger on unary expr
		if(rhs instanceof UnaryExpr){
			
			UnaryExpr rhsUnary = (UnaryExpr) rhs;
			intrhs = testNegativeInteger(rhsUnary);
			
			// If null int returned, put out error message
			if(intrhs == null){
				error(rhs, "Must have valid negative or positive integer in range.");
			}
			
		} else if(rhs instanceof IntLitExpr){
			// Else check if rhs IntLitExpr: testPositiveInteger
			IntLitExpr rhsIntLit = (IntLitExpr) rhs;
			intrhs = testPositiveInteger(rhsIntLit);
			
			if(intrhs == null){
				error(rhs, "Must have valid positive integer in range");
			}
			
		} else{
			// else throw validation error
			error(rhs, "Range values must be valid integers");
		}
		
		// Test for strict inequality lhs<rhs
		if((intrhs != null) && (intlhs != null)){
			if(intlhs >= intrhs){
				error(range, "There must be strict inequality between lhs and rhs range values: lhs < rhs.");
			}
		}
		
	
		
	}
	
	
	/*
	 * testNegativeInteger:
	 * parameter: UnaryExpr
	 * returns: Integer
	 * (1): Gets op : should be negative sign
	 *		If op not '-', return null
	 * (2): Get Expr which should be IntLitExpr
	 * 		If not, return null
	 * (3): Get value (string) from IntLitExpr
	 * 		Concat '-' with string value
	 * (4): Parse int: if exception, return null
	 * (5): Return int
	 */
	private Integer testNegativeInteger(UnaryExpr unary){
	
		Integer intResult = null;
		String op = unary.getOp();
		
		if(!op.equals("-")){
			return null;
		}
		
		Expr rhsDeepExpr = unary.getExpr();
		
		// This expression has to be an IntLit... else error
		if(rhsDeepExpr instanceof IntLitExpr){
			
			IntLitExpr rhsDeepInt = (IntLitExpr) rhsDeepExpr;
			String rhsDeepIntVal = rhsDeepInt.getVal();
			String totalInt = op.concat(rhsDeepIntVal);
			
			// Parse int
			try {
				intResult = Integer.parseInt(totalInt);
			      
			} catch (NumberFormatException e) {
			      return null;
			}
			
		} else {
			return null;
		}
		
		return intResult;
	}
	
	
	/*
	 * testPositiveInteger:
	 * parameter: IntLitExpr
	 * returns: Integer
	 * (1): Gets val : string
	 * (2): Parse int: if exception, return null
	 * (5): Return int
	 */
	private Integer testPositiveInteger(IntLitExpr intLit){
		String lhsIntVal = intLit.getVal();
		Integer intResult = null;
		
		// Try to parse int
		try {
			intResult = Integer.parseInt(lhsIntVal);
		      
		} catch (NumberFormatException e) {
		      return null;
		}
		
		return intResult;
	}
}
