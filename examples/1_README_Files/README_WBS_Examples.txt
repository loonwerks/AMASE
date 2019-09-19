Wheel Brake System Examples
Developed by Danielle Stewart and Michael Whalen

ARP4761_annex :
-------------
This was the initial model used in development of the Safety Annex and 
can only be run using Single Layer Analysis. The lower layers of the model
do not yet have AGREE contracts defined and thus the top level will not prove
using an all layer approach. 

wbs.federated is the implementation that has been used in research and is the 
implementation that should be selected before running Verify in the Presence of Faults
with Single Layer Analysis. 

 

WBS_arch4:
---------
The AADL model is based on the NuSMV model created by FBK in the AIR6110 case study 
(Formal Design and Safety Analysis of AIR6110 Wheel Brake System). The fault model is
based on the xSAP fault model of the same project. 

An overall description of the behavior and architecture of the model can be found in 
this repository directory examples/WBS/docs/WBS_description.

Version differences:
Version 1 (WBS_arch4): This was the closest match to the NuSMV model in terms of the top 
		  level contracts. This meant (for AADL/AGREE) that numerous physical system contracts
		  were repeated on the top level. Since this is unnecessary in terms of compositional
		  analysis, in version 2 these were removed from the top level. 
Version 2 (WBS_arch4_v2): All top level contracts relate specifically to AIR6110 and this version
		  and its analysis results are cited in any research papers or case study reports.
Version 3 (WBS_arch4_v3): A sensor system was added to the top level. Instead of having
		  a single sensor per brake pedal, three sensors were added along with a majority
		  voting strategy. This was shown to eliminate the pedal sensor faults from being
		  single points of failure. This can be seen by running the analysis (any of the  
		  4 forms of analysis) and checking single points of failure for the top level 
		  properties.