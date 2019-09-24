Other Examples
--------------
(1) Modes_of_Operation
	Explores the modes of a system like WBS to see when it switches
	from normal to alternate or alternate to emergency modes. Which contracts must 
	be in place and how many faults it is resilient to. 
	
	In this model, it is resilient to 2 active faults. These faults can shut down the 
	normal system (which switches it to alternate) and then a second fault in 
	alternate forces the system to use emergency mode. Anything beyond 2 faults will
	cause all top level contracts in the system to fail. 
	
	A complete write-up of this exploration is found in the example 
	directory of GitHub under the folder name: 2_Other_model_descriptions/Modes_of_Operation
	
(2) ContractScope
	ContractScope_v2
	Explores how scoping is done in AADL models and used in contracts. 
	If an upper level component has output b and it's child also has an output
	called b, then a contract in the lower level referring to b refers to child b. 
	A contract in the upper level referring to b refers to parent b. 
	
	We just made these examples to be sure our idea of scoping was accurate. 
	A complete write-up of this exploration is found in the example 
	directory of GitHub under the folder name: 2_Other_model_descriptions/Scoping_in_Agree
	
(3) QFCS_V3
	The original QFCS was made by John Backes and this is a simplified form used
	to explore byzantine fault modeling. 
	
(4) ThreeMileIsland
	This model was based on the NuSMV model of the same name that is referred to in the book
	Design and Safety Assessment of Critical Systems by Bozzano and Villafiorita. The idea was to create 
	a model that we can use for comparison with xSAP. The timing of the project did not allow for this
	comparison to be completed, but the model is possibly worth saving for later use. 