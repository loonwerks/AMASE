Regression Testing Examples
developed by Danielle Stewart and Janet Liu

These are used to occasionally test the Safety Annex when larger changes
are made to the implementation. 

(1) Toy_Example_Safety: This simple example is used in the Users Guide to illustrate
	grammatical structures and show how to run the tool. 
	
	There are three components in the model (A, B, C) and a top level (top_level)
	all located in the file Integer_Toy.aadl. 
	---------------------
	RESULTS 
	run on top_level.Impl
	---------------------
	- Verify single layer : passes all contracts
	- Verify in the presence of faults: 
		- max 1+ fault: System output range contract fails
		- probability: 
			fault comp A = 5E-5
			fault comp B = 5E-9
			top level threshold = 5E-7
			--------------------------- ALL PROPERTIES PASS
			fault comp A = 5E-9
			fault comp B = 5E-7
			top level threshold = 5E-7
			--------------------------- TOP LEVEL PROPERTY FAILS
	- Generate MCSs
		- max n fault (n = 2): 1 cut set, cardinality 1 (stuck_at_fault_B)
		- probabilistic (5E-7, fault probs shown in case 2 above):  1 cut set, cardinality 1 (stuck_at_fault_B)

(2) ARP4761_annex: The first simplified WBS version has two wheel

(3) Sensor_Example: The reactor system is composed of three subsystems: temperature
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
	unnecessary. 
	
	---------------------
	RESULTS 
	(a) run on Reactor_Ctrl.No_Voting 
	---------------------
	- Verify single layer : passes all contracts
	- Verify in the presence of faults: 
		- max 1+ fault (compositional or monolithic): "Shut down when and only when we should" contract fails
			(when compositional, failure is at the subsystem level). 
		- probability (monolithic): 
			all sensor faults = 1E-5
			top level threshold = 1E-10
			--------------------------- MONOLITHIC: PROPERTY FAILS (temp or pressure faults)
	
	- Generate MCSs probability threshold 1E-10 (all faults 1E-5)	: 9 MCSs
		- All single faults, one for each sensor failing high.
		
	- Generate MCSs max 3 faults : 12 MCSs 
		- All single faults, one for each sensor failing high (9 total).
		- Combination of three sensors all failing low (3 cut sets, one for each subsystem). 
	
	---------------------
	RESULTS 
	(b) run on Reactor_Ctrl.W_Voting 
	---------------------
	- Verify single layer : passes all contracts
	- Verify in the presence of faults: 
		- max 1+ fault (compositional or monolithic): "Shut down when and only when we should" contract fails
			at the subsystem level. 
		- probability (monolithic): 
			all sensor faults = 1E-5
			top level threshold = 1E-10
			--------------------------- MONOLITHIC: PROPERTY FAILS (temp or pressure combination of two faults)
	- Generate MCSs max 3 faults : 16 MCSs
		- Pressure sensors fail low: 
			Any 2 pressure sensors are low when pressure is actually high, 
			we do not shut down when we should. 
			(3 cut sets of these combinations)
		- Pressure sensors fail high: 
			Any 2 pressure sensors are high when pressure is actually low, 
			we shut down when we shouldn’t. 
			(3 cut sets of these combinations)
		- Temp sensors fail low: 
			Any 2 temp sensors are low when temp is actually high, 
			we do not shut down when we should. 
			(3 cut sets of these combinations)
		- Temp sensors fail high: 
			Any 2 temp sensors are high when temp is actually low, 
			we shut down when we shouldn’t. 
			(3 cut sets of these combinations)
		- Radiation sensor is low:
			Since all 3 must agree to send shut down command, one low (when radiation is actually high), 
			then we do not shut down when we should. 
			(3 cut sets, one for each sensor)
		- Radiation sensors are all high:
			If all are high (when radiation is actually low), then we shut down when we shouldn’t. 
			(1 cut set with all three sensors)

	- Generate MCSs probability threshold 1E-10 (all faults 1E-5)	: 15 MCSs
		- Pressure sensors fail low: 
			Any 2 pressure sensors are low when pressure is actually high, 
			we do not shut down when we should. 
			(3 cut sets of these combinations)
		- Pressure sensors fail high: 
			Any 2 pressure sensors are high when pressure is actually low, 
			we shut down when we shouldn’t. 
			(3 cut sets of these combinations)
		- Temp sensors fail low: 
			Any 2 temp sensors are low when temp is actually high, 
			we do not shut down when we should. 
			(3 cut sets of these combinations)
		- Temp sensors fail high: 
			Any 2 temp sensors are high when temp is actually low, 
			we shut down when we shouldn’t. 
			(3 cut sets of these combinations)
		- Radiation sensor is low:
			Since all 3 must agree to send shut down command, one low (when radiation is actually high), 
			then we do not shut down when we should. 
			(3 cut sets, one for each sensor) 
	
(4) ColorByzantineAgreement: This model is used to illustrate the mitigation strategy using	
	AGREE contracts. The architecture is of 4 nodes. One is the leader node and sends its color
	(represented as boolean) to 3 other nodes. The other nodes must reach agreement on the color.
	Contracts used in mitigation are found in LeaderNode.aadl (color sent in first time step and 
	always remains the same) and in Nodes.aadl (send other nodes what was seen in step 1). 
	
	The faults can occur on the LeaderNode output and the Nodes output (since all are 1-n connections.)
	The top level contracts state that all nodes agree on the color after one time step.
	Property regarding non-failed nodes pass with 1 active fault and fail with anything more than 1.
	
	---------------------
	RESULTS 
	run on Top_Level.impl 
	---------------------
	- Verify single layer : passes all contracts
	- Verify in the presence of faults: 
		- max 1 fault (compositional or monolithic): ALL NODES AGREE PROPERTY FAILS
													  ALL NON-FAILED NODES AGREE PASSES
		- max 2+ fault (compositional or monolithic): ALL NODES AGREE PROPERTY FAILS
													  ALL NON-FAILED NODES AGREE FAILS
		- probability (monolithic): 
			all node faults = 1E-5
			top level threshold = 1E-10
			--------------------------- MONOLITHIC: ALL NODES AGREE PROPERTY FAILS
													ALL NON-FAILED NODES AGREE PASSES
	- Generate MCSs max 1 faults : ALL NODES AGREE 1 MCS
		- LeaderNode failure, single cut set of cardinality 1
	
	- Generate MCSs max 2+ faults : ALL NODES AGREE 4 MCSs
		- LeaderNode failure single cut set with cardinality 1
		- Combinations of two node faults, 3 cut sets with cardinality 2
		
	- Generate MCSs max 2+ faults : ALL NON-FAILED NODES AGREE 6 MCSs
		- Combinations of any 2 faults on any 2 nodes, 6 cut sets of cardinality 2
	
	- Generate MCSs probability : 
		- If the threshold is 1.0E-10, then this matches max 1 fault cut sets.
		- If the threshold is 1.0E-9, then this matches max 2 fault cut sets.
	
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
	For a complete run, go to top_level_v6.aadl and select the top level implementation. 
	
	---------------------
	RESULTS 
	run on top_level_v6.aadl, TopLevel.impl 
	---------------------
	- Verify single layer : passes all contracts
	- Verify in the presence of faults: 
		- max 1 fault (compositional or monolithic): ALL NODES AGREE PROPERTIES FAIL
													  ALL NON-FAILED NODES AGREE PROPERTIES PASS
		- max 2+ fault (compositional or monolithic): ALL NODES AGREE PROPERTIES FAIL
													  ALL NON-FAILED NODES AGREE PROPERTIES FAIL
		- probability (monolithic): 
			all node faults = 1E-5
			top level threshold = 1E-10
			--------------------------- MONOLITHIC: ALL NODES AGREE PROPERTIES FAIL
													ALL NON-FAILED NODES AGREE PROPERTIES FAIL
			NOTE: In order to make properties pass with probabilistic, the top level threshold must 
			be changed from 1.0E-10. This value allows combinations of 2 faults to be active together. 
			If it is changed to 1.0E-9, the non-failed node agreement properties will pass. 
			
	- Generate MCSs max 2+ faults : ALL NODES AGREE PROPERTIES 5 MCSs each
		- Node failure corresponding to the PID of the contract, single cut set of cardinality 1
		- Combinations of two nodes failures, 4 cut sets of cardinality 2
	
	- Generate MCSs max 2+ faults : ALL NON-FAILED NODES AGREE 6 MCSs
		- Combinations of any 2 faults on any 2 nodes, 6 cut sets of cardinality 2
	
	- Generate MCSs probability : 
		- If the threshold is 1.0E-10, then this matches max 1 fault cut sets.
		- If the threshold is 1.0E-9, then this matches max 2 fault cut sets.
	
(6) WBS_arch4_v2: The AADL model is based on the NuSMV model created by FBK in the AIR6110 case study 
	(Formal Design and Safety Analysis of AIR6110 Wheel Brake System). The fault model is
	based on the xSAP fault model of the same project. 

	Descriptions of the behavior and architecture of the model can be found in 
	this repository directory examples/WBS/docs.
	
	---------------------
	RESULTS 
	run on WBS.inst
	---------------------
	Note: Probabilities found on faults are in accordance with AIR6110 and vary depending on component. 
	- Verify single layer : passes all contracts
	
	This is arranged differently given the number of top level properties. 
	The results are given per property.
	
	S18-WBS-0321: Loss of all wheel braking during landing or RTO shall be less than 5E-7 per flight.
		- Verify in the presence of faults
			- Max 1+ fault, compositional: PASSES
			
			- probabilistic threshold 1.0E-9, monolithic:
	
		- Generate MCS max n faults: c=1: 6
									 c=4: 1
		
		- Generate MCS probabilistic: none			

	S18-WBS-0322-L: Asymmetrical loss of wheel braking during landing shall be less than 5E-7 per flight.
		- Verify in the presence of faults
			- Max 1+ fault, compositional: PASSES
			
			- probabilistic threshold 1.0E-9, monolithic:
			
		- Generate MCS max n faults: c=1: 32
		
		- Generate MCS probabilistic: c=1: 32

	S18-WBS-0322-R: Asymmetrical loss of wheel braking during landing shall be less than 5E-7 per flight.
		- Verify in the presence of faults
			- Max 1+ fault, compositional: PASSES
			
			- probabilistic threshold 1.0E-9, monolithic:
			
		- Generate MCS max n faults: c=1: 32
		
		- Generate MCS probabilistic: c=1: 32
	
	S18-WBS-0323: Inadvertent wheel braking with all wheels locked during takeoff roll before V1 shall be less than 5E-7 per flight.
		- Verify in the presence of faults
			- Max 1+ fault, compositional: FAILS 
			
			- probabilistic threshold 1.0E-9, monolithic:
			
		- Generate MCS max n faults: c=1: 90
		
		- Generate MCS probabilistic: c=1: 90

	S18-WBS-0324: Inadvertent wheel braking of all wheels during takeoff roll after V1 shall be less than 1E-9 per takeoff.
		- Verify in the presence of faults
			- Max 1 fault, compositional: PASSES
			- Max 2+ faults, compositional: FAILS
			
			- probabilistic threshold 1.0E-9, monolithic:
			
		- Generate MCS max n faults: c=1: 8, c=2: 3401, c=3: 6800, c=4: 66472
		
		- Generate MCS probabilistic: c=2: 1

	S18-WBS-0325-WX: Undetected inadvertent wheel braking on one wheel w/o locking during takeoff shall be less than 1E-9 per takeoff.
		NOTE: property applies per wheel (i.e. there are 8 properties). Numbers are the same for each of the 8 properties
			  so they are combined here.
		- Verify in the presence of faults
			- Max 1+ fault, compositional: FAILS
			
			- probabilistic threshold 1.0E-9, monolithic:
		
		- Generate MCS max n faults: c=1: 20
		
		- Generate MCS probabilistic: c=1: 20


	
	
	