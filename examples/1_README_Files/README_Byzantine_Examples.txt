Byzantine Examples:
developed by Danielle Stewart and Janet Liu

This directory contains a variety of Byzantine examples used in testing, 
debugging, and mitigation strategizing. Many are simple and used to test 
multiple aspects of the implementation of asymmetric faults, but others are
more complex and show a mitigation strategy through the AGREE contracts. 

Examples:
--------
The first 3 examples are very simple and were used to test various datatypes
for the implementation; boolean, nested, and real. The architecture is the same. 
Other tests included combinations of faults on the same output. These combinations are
still in place for some examples. 

A sender component sends a message (output of an OR gate) to three other components.
The top level contract states that all three components see the same thing. The sender
component has an asymmetric fault defined

(1) ByzantineExampleBoolean: Connections are boolean.
(2) ByzantineExampleReal: Connections are real.
(3) ByzantineExampleNestedTypes: Connections are nested data types in AADL. There are two
	ways to represent the fault, both of which are defined in the Safety Annex of Sender
	component. The fault definitions in Common_Faults.aadl also reflect these ways of using
	nested datatypes in fault definitions.

(4) ColorByzantineAgreement: This model is used to illustrate the mitigation strategy using	
	AGREE contracts. The architecture is of 4 nodes. One is the leader node and sends its color
	(represented as boolean) to 3 other nodes. The other nodes must reach agreement on the color.
	Contracts used in mitigation are found in LeaderNode.aadl (color sent in first time step and 
	always remains the same) and in Nodes.aadl (send other nodes what was seen in step 1). 
	
	The faults can occur on the LeaderNode output and the Nodes output (since all are 1-n connections.)
	The top level contracts state that all nodes agree on the color after one time step.
	Property regarding non-failed nodes pass with 1 active fault and fail with anything more than 1. 
	
(5) PIDByzantineAgreement: This model is described in detail in the Users Guide and briefly here.
	Four nodes share with each other their Process IDs (PIDs) in the first time step. They share with each 
	other what they have seen from everyone else in the second time step. An agreement strategy of majority 
	voting is implemented to determine PIDs of each node. If majority does not work, then a default PID of 0
	is assigned. 
	
	Each node can have a fault on its PID output (first time step) or its sharing output (second time step).
	Contracts implementing the agreement strategy are found in all nodes. Top level properties regarding 
	two cases: 
		(a) No faults: all nodes agree on the correct PID for each node.
		(b) Faults: all non-failed nodes agree on PIDs. 
	
	This model was developed in stages and the most comprehensive version is version 6. 
	It is seen in the packages directory that top levels and nodes have versions (top_level_v6, etc.). 
	For a complete run, go to top_level_v6.aadl and select the implementation. 
	
(6) ByzantineTest: This model was a hand-wired byzantine like model based on the QFCS project.
	This model was not used for the implementation of byzantine faults, but only to see how one 
	can model such faults in the Safety Annex. 