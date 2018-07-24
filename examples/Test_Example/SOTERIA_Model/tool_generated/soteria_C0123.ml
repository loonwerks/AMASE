#use "top.ml";;
(* ----- COMPONENT LIBRARY ----- *)

let comp_library = 
  [
{name = "C123";
faults = ["contract violation"];
input_flows = [];
basic_events = ["C2 out2 fail to zero fault (C2__fault_1)"; "C1 out1 negation fault (C1__fault_1)"];
event_info = [(1.0E-5, 1.0); (1.0E-6, 1.0)];
output_flows = ["C123 out3 range"; "C123 out3 value"];
formulas = [
(["C123 out3 range"; "contract violation"],
Or[F["C1 out1 negation fault (C1__fault_1)"]; 
F["C2 out2 fail to zero fault (C2__fault_1)"]]); 
(["C123 out3 value"; "contract violation"],
Or[F["C1 out1 negation fault (C1__fault_1)"]; 
F["C2 out2 fail to zero fault (C2__fault_1)"]])]
}; 

{name = "C0123";
faults = ["contract violation"];
input_flows = ["C123 out3 value"];
basic_events = ["C0 out0 fail to one fault (C0__fault_1)"];
event_info = [(1.0E-7, 1.0)];
output_flows = ["C0123 out0 range"; "C0123 out0 value"];
formulas = [
(["C0123 out0 range"; "contract violation"],
F["C123 out3 value"; "contract violation"]); 
(["C0123 out0 value"; "contract violation"],
Or[F["C123 out3 value"; "contract violation"]; 
F["C0 out0 fail to one fault (C0__fault_1)"]])]
}];;

(* ----- CHECK LIBRARY ----- *)
checkLibrary_componentUnique comp_library;;
checkLibrary_nonEmptyFaults comp_library;;
checkLibrary_disjointInputFlowsandBasicEvents comp_library;;
checkLibrary_listsAreConsistentLengths comp_library;;
checkLibrary_allOutputFaultsHaveFormulas comp_library;;
checkLibrary_formulasMakeSense comp_library;;


(* ----- COMPONENT INSTANCES, CONNECTIONS, OUT RANGE TOP LEVEL FAULT ----- *)
let model_C0123_out0_range = 
{instances = 
[makeInstance "C123" "C123"();
makeInstance "C0123" "C0123"();
];
connections = 
[(("C0123", "C123 out3 value"),("C123", "C123 out3 value")); ];
top_fault = ("C0123", F["C0123 out0 range"; "contract violation"])} ;;

(* ----- CHECK MODEL ----- *)
checkModel_instanceNameUnique model_C0123_out0_range;;
checkModel_cnameInstanceIsDefinedInLibrary model_C0123_out0_range comp_library;;
checkModel_exposureOfBasicIsDefinedInLibrary model_C0123_out0_range comp_library;;
checkModel_validConnections model_C0123_out0_range comp_library;;
checkModel_inputFlowUnique model_C0123_out0_range;;

(* ----- PRE ANALYSES MODEL VISUALIZATIONS ----- *)
dot_gen_show_ph_file ~rend:"pdf" model_C0123_out0_range "model_C0123_out0_range_ph.gv";;
dot_gen_show_funct_file ~rend:"pdf" comp_library model_C0123_out0_range "model_C0123_out0_range_fn.gv";;
dot_gen_show_fault_file ~rend:"pdf" comp_library model_C0123_out0_range "model_C0123_out0_range_fa.gv";;

let model_C0123_out0_range_ftree = model_to_ftree comp_library model_C0123_out0_range;;
probErrorCutImp model_C0123_out0_range_ftree;;
probErrorCut model_C0123_out0_range_ftree;;
dot_gen_show_direct_tree_file "model_C0123_out0_range_dftree.gv" model_C0123_out0_range_ftree ;;
dot_gen_show_tree_file "model_C0123_out0_range_ftree.gv" model_C0123_out0_range_ftree ;;

(* ----- COMPONENT INSTANCES, CONNECTIONS, OUT RANGE TOP LEVEL FAULT ----- *)
let model_C0123_out0_value = 
{instances = model_C0123_out0_range.instances;
connections=model_C0123_out0_range.connections;
top_fault = ("C0123", F["C0123 out0 value"; "contract violation"])} ;;

(* ----- CHECK MODEL ----- *)
checkModel_instanceNameUnique model_C0123_out0_value;;
checkModel_cnameInstanceIsDefinedInLibrary model_C0123_out0_value comp_library;;
checkModel_exposureOfBasicIsDefinedInLibrary model_C0123_out0_value comp_library;;
checkModel_validConnections model_C0123_out0_value comp_library;;
checkModel_inputFlowUnique model_C0123_out0_value;;

(* ----- PRE ANALYSES MODEL VISUALIZATIONS ----- *)
dot_gen_show_ph_file ~rend:"pdf" model_C0123_out0_value "model_C0123_out0_value_ph.gv";;
dot_gen_show_funct_file ~rend:"pdf" comp_library model_C0123_out0_value "model_C0123_out0_value_fn.gv";;
dot_gen_show_fault_file ~rend:"pdf" comp_library model_C0123_out0_value "model_C0123_out0_value_fa.gv";;

let model_C0123_out0_value_ftree = model_to_ftree comp_library model_C0123_out0_value;;
probErrorCutImp model_C0123_out0_value_ftree;;
probErrorCut model_C0123_out0_value_ftree;;
dot_gen_show_direct_tree_file "model_C0123_out0_value_dftree.gv" model_C0123_out0_value_ftree ;;
dot_gen_show_tree_file "model_C0123_out0_value_ftree.gv" model_C0123_out0_value_ftree ;;

