Users Guide Examples
developed by Danielle Stewart and Janet Liu

(1) Toy_Example_Safety: This simple example is used in the Users Guide to illustrate
	grammatical structures and running the tool. 
	
	There are three components in the model (A, B, C) and a top level (top_level)
	all located in the file Integer_Toy.aadl. To run the model, select top_level.Impl
	and click "Verify in the presence of faults" and then select "Verify all layers."
	
(2) Sensor_Example: The reactor system is composed of three subsystems: temperature
	sensors, radiation sensors, and pressure sensors. Each of these subsystems contain
	three related sensors which monitor the environmental inputs (to the top level reactor
	system) and send a shutdown command when necessary. 
	
	There are two versions of the system: No_Voting and W_Voting. 
	(a) No_Voting: The shutdown command is an OR of all sensors. If any of them report high, 
		shutdown command is issued.
	(b) W_Voting: The temperature and pressure sensors have a majority voting scheme. If the majority
		reports high, then shutdown command is sent. 
		The radiation sensors have an All-must-agree voting scheme. In order to issue the shutdown 
		command, all sensors must report high. 
		
	Due to the variety of voting schemes in the model, the cut sets generated show various results 
	for each sensor subsystem and is an interesting example of how these agreement schemes affect 
	the overall behavior in terms of shutting down when we should vs. shutting down when it is 
	unnecessary. More information about the results can be found in the Users Guide.
	
(3) ColorByzantineAgreement: This model is used to illustrate the mitigation strategy using	
	AGREE contracts. The architecture is of 4 nodes. One is the leader node and sends its color
	(represented as boolean) to 3 other nodes. The other nodes must reach agreement on the color.
	Contracts used in mitigation are found in LeaderNode.aadl (color sent in first time step and 
	always remains the same) and in Nodes.aadl (send other nodes what was seen in step 1). 
	
	The faults can occur on the LeaderNode output and the Nodes output (since all are 1-n connections.)
	The top level contracts state that all nodes agree on the color after one time step.
	Property regarding non-failed nodes pass with 1 active fault and fail with anything more than 1.
	
(4) PIDByzantineAgreement: Four nodes share with each other their Process IDs (PIDs) in the first time step. 
	They share with each other what they have seen from everyone else in the second time step. 
	An agreement strategy of majority voting is implemented to determine PIDs of each node. 
	If majority does not work, then a default PID of 0 is assigned. 
	
	Each node can have a fault on its PID output (first time step) or its sharing output (second time step).
	Contracts implementing the agreement strategy are found in all nodes. Top level properties regarding 
	two cases: 
		(a) No faults: all nodes agree on the correct PID for each node.
		(b) Faults: all non-failed nodes agree on PIDs. 
	
	This model was developed in stages and the most comprehensive version is version 6. 
	It is seen in the packages directory that top levels and nodes have versions (top_level_v6, etc.). 
	For a complete run, go to top_level_v6.aadl and select the implementation and then continue with
	the analysis desired.  