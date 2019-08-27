

For each AgreeProgram:
	 * (1) AgreeASTBuilder contains the extension point for the program.
	 * It will call the class AddFaultsToAgree.transform(program).
	 * If the transformFlag (set by the menu item selection) is true, then changes are made 
	 * to the program that adds in the fault information for safety analysis. 
	 * If the flag is false (safety analysis is not selected), the program is returned unchanged. 
	 * 
	 *
	 * (2) AddFaultsToAgree.transform calls AddFaultsToNodeVisitor.visit(program) and the program
	 * is traversed from the top down. Each node is visited (visit(node)) and fault information 
	 * added based on the safety annex located within each node. 
	 *
	 * The class AddFaultsToNodeVisitor is complex in that it handles a variety of fault types 
	 * and inserts them into the program in specific ways. To better understand this class and related
	 * classes, this document attempts to outline the important aspects. 
	 * Related classes include FaultASTBuilder, AsymFaultASTBuilder, and the set of compositional analysis
	 * classes. 
-----------------------------------------------------------------------------------------------------------	 
AddFaultsToNodeVisitor overview: 
-----------------------------------------------------------------------------------------------------------
visit(program) begins the changes by passing the top node to visit(node). 
The program is changed by creation of new nodes with additional fault information. 
This new program is returned for completion of analysis. 

visit(node) method behavior depends on whether we are in a top node or not. 

The initial steps are the same in either case: 
- Save fault information in local maps for later reference
- Create a node builder object which builds new lustre node for insertion into the program
- Traverse through children nodes
- Add locals to the node builder based on the faults present in the annex
- Create fault objects for these fault definitions in the spec statements of the grammar 
  This is done by creating a FaultASTBuilder object which builds a fault based on the information
  within the spec statement. When the faults of the spec statement are multiple asymmetric faults,
  an AsymFaultASTBuilder object is created and the program changed accordingly.
- Create fault nodes for the program
  These are the fault node calls that will dictate the behavior of each active fault. 

Top node: 
	topNodeVisit(nodeBuilder, topNode)
	When we visit the top node, this corresponds to main node in lustre. The structure of this node
	must take into account the other nodes of the program (and call them appropriately), the inputs of 
	all other nodes, and any assert statements that dictate the behavior of the program. 
	This method inserts the main fault information such as:
	- Adding independent/dependent fault activation vars and their linking
	- Adding fault count/probability information based on the analysis statements at the top level AADL program
	- Passing this fault information into corresponding nodes
	- Inserting constraints on dependent faults
	- Latching permanent faults
	- Changing connections in the asymmetric fault case to account for communication nodes 
	
visit(idExpr) 
At the lowest level, we visit the id expression corresponding to the output that a fault is attached to.
Since the fault node output will override the AADL component output, this is renamed as __fault__nominal__output 
and referred to as such in the new lustre program. 

----------------------------------------------------------------------------------------------
Given the rough outline of the class and how it works, we now go through more specifics regarding the 
behavior of the class with respect to the types of analysis that must be performed. 

Symmetric faults
------------------------
All new fault information is added to the current node (being visited) and the top node (main). 
The only new node created is the fault node which is called in the current node. 
FaultASTBuilder class creates the faults and when visiting the top node, fault constraints are 
added based on whether we are performing max no fault analysis or probabilistic analysis.

To avoid outlining all method calls for each procedure, I will outline the more important methods that
will show the basic function of the class. 
 
The main outline of method calls is: 
visit(program)
	visit(node)
		- Upon visiting the node, we must create Fault objects through FaultASTBuilder. This is done
		in the call to gatherFaults method. 
		gatherFaults() separates the spec statements into types of faults they define. Based on this, 
		it is known whether to use FaultASTBuilder or AsymFaultASTBuilder to process the faults. 
		In this case, FaultASTBuilder is used to create all faults. 
		A number of class maps are populated here based on information gathered in the ASTBuilder classes.
		This method returns a list of faults defined for the current node. 
		
		- gatherHWFaults() is a similar method that deals with dependent faults. The faults are created
		through the use of FaultASTBuilder class), but information about dependency relations are saved 
		for later addition into the main node. 
		
		- For each fault in the list, locals and fault node calls are added to the current node.
		
		- Mutually exclusive faults are collected for insertion into main node assert stmts.
		  
		visit(idExpr) changes output to correspond to a fault nominal output.

Asymmetric faults
------------------------------------------
The main outline of visit methods is the same for any fault combination, but in this case, the main 
changes take place in the gatherFaults method. Since the spec statements are divided according to type, 
we can treat these faults differently. 

Asymmetric faults (fanned output) will have new communication nodes inserted between the original output
and the receiving connections. These connections are changed in the main lustre node. As an example, a sender
component has a fanned output to three receivers. Thus, three communication nodes are created: asym0, 1, 2. 
The connections are defined in main lustre node originally 
as: 
	 * 	   Sender_out = reciever1.in
	 * 	   Sender_out = reciever2.in
	 * 	   Sender_out = reciever3.in
These old connections are removed and new ones added: 
	 * 	   Sender_out = asym0.in
	 * 	   Sender_out = asym1.in
	 * 	   Sender_out = asym2.in
	 * 	   asym0.out = reciever1.in
	 * 	   asym1.out = reciever2.in
	 * 	   asym2.out = reciever3.in
	 
Each of these comm nodes has the fault information added (fault node call, 
mutual exclusion assertions, etc) and NOT the original sender node. Thus, any asymmetric faults are handled 
differently as evidenced in the class by checks (isAsymmetric) on the faults and spec statements. 

If a single asym fault is present, a FaultASTBuilder object is still created to handle this. 
If multiple asym faults are present, an AsymFaultASTBuilder object is created to handle the differences. 
These differences are evident in specific types of assert stmts that are added to lustre. 
The communication nodes have multiple fault node calls and the event of one specific fault triggers
a corresponding fault node call in all communication nodes. 		

The calls specific to asym faults that are made in this class are: 
changeTopNodeAsymConnections -- changes connections as described above (done in top node).
addTopLevelAsymFaultDeclarations -- adds information about the comm nodes to top level. 

The rest of the asym specific information is added in FaultASTBuilder class. 
	
	
-----------------------------------------------------------------------------------------------------------	 
FaultASTBuilder overview: 
-----------------------------------------------------------------------------------------------------------
Upon creation of the FaultASTBuilder object (in AddFaultsToNodeVisitor.gatherFaults), each spec statement is sent
to a method in FaultASTBuilder class called: processFaults. This returns a Fault object based on the spec statement
that was sent in. 

processFaults: 
--------------
if isSymmetric: 
	buildSymmetricFault method: 
		Creates new fault object
		Creates fault node
		Processes fault subcomponents (i.e. safety eq stmts, inputs, outputs)
if isAsymmetric: 
	buildAsymmetricFault method: 
		Create sender fault - this is the main asym fault on the sender subcomponent
		Find sender output - used to determine where the comm nodes will be situated in the program and 
							 what output the fault node calls will override.
		Create communication nodes - one created per connection (from sender to receiver)

		
-----------------------------------------------------------------------------------------------------------	 
AsymFaultASTBuilder overview: 
-----------------------------------------------------------------------------------------------------------		
When there are multiple asym faults found in the spec statements of AddFaultsToNodeVisitor.gatherFaults, 
an AsymFaultASTBuilder object is created to make the associated Fault objects. 
This class extends FaultASTBuilder and mirrors it (as well as uses some of its methods). 

processFaults: 
--------------
This takes in a list of fault statements and returns a list of faults. They must be processed together
because each of the communication nodes have all fault node calls and mutual exclusion added to them. 

The behavior of this method is similar to buildAsymmetricFault in all cases, but it processes all faults together
when creating the comm nodes. 


