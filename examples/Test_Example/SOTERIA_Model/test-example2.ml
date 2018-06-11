#use "top.ml";;
(* ----- COMPONENT LIBRARY ----- *)
let test_library =
  [
   {name = "C123_w_fault";
    faults = ["contract violation"];
    input_flows = [];
    basic_events = ["C1 out1 negation fault"; "C2 out2 fail to zero fault"];
    event_info   = [(1.0e-6, 1.0); (1.0e-5, 1.0)];
    output_flows = ["C123 out3 range";"C123 out3 value"];
    formulas = [
		(["C123 out3 range"; "contract violation"], 
                 F["C1 out1 negation fault"]
                );
	        (["C123 out3 value"; "contract violation"], 
                Or [F["C1 out1 negation fault"]; 
                    F["C2 out2 fail to zero fault"]]
                )
              ]
    };

   {name = "C0123";
    faults = ["contract violation"];
    input_flows = ["C123 out3 range"; "C123 out3 value"];
    basic_events = ["C0 out0 fail to one fault"];
    event_info = [(1.0e-7, 1.0)];
    output_flows = ["C0123 out0 range"; "C0123 out0 value"];
    formulas	 = [
		    (["C0123 out0 range"; "contract violation"],
	             F["C123 out3 range"; "contract violation"]
		    );
		    (["C0123 out0 value"; "contract violation"], 
		    Or[F["C123 out3 value"; "contract violation"]; 
 		       F["C0 out0 fail to one fault"]]
		    )
		   ]
    }
];;


(* ----- CHECK LIBRARY ----- *)
checkLibrary_componentUnique test_library;;
checkLibrary_nonEmptyFaults test_library;;
checkLibrary_disjointInputFlowsandBasicEvents test_library;;
checkLibrary_listsAreConsistentLengths test_library;;
checkLibrary_allOutputFaultsHaveFormulas test_library;;
checkLibrary_formulasMakeSense test_library;;

(* ----- COMPONENT INSTANCES, CONNECTIONS, OUT RANGE TOP LEVEL FAULT ----- *)
let test_model_out_range =
  {instances =
      [makeInstance "C0123" "C0123" ();
       makeInstance "C123_w_fault" "C123_w_fault" ();
      ];
   connections =
      [ (("C0123", "C123 out3 range"), ("C123_w_fault", "C123 out3 range"));
	(("C0123", "C123 out3 value"), ("C123_w_fault", "C123 out3 value"));
      ];
   top_fault =("C0123", F["C0123 out0 range"; "contract violation"])
  } ;;

(* ----- CHECK MODEL ----- *)
checkModel_instanceNameUnique test_model_out_range;;
checkModel_cnameInstanceIsDefinedInLibrary test_model_out_range test_library;;
checkModel_exposureOfBasicIsDefinedInLibrary test_model_out_range test_library;;
checkModel_validConnections test_model_out_range test_library;;
checkModel_inputFlowUnique test_model_out_range;;

(* ----- COMPONENT INSTANCES, CONNECTIONS, OUT VALUE TOP LEVEL FAULT ----- *)
let test_model_out_value =
  {instances=test_model_out_range.instances;
   connections=test_model_out_range.connections;
   top_fault =("C0123", F["C0123 out0 value"; "contract violation"])} ;;

(* ----- CHECK MODEL ----- *)
checkModel_instanceNameUnique test_model_out_value;;
checkModel_cnameInstanceIsDefinedInLibrary test_model_out_value test_library;;
checkModel_exposureOfBasicIsDefinedInLibrary test_model_out_value test_library;;
checkModel_validConnections test_model_out_value test_library;;
checkModel_inputFlowUnique test_model_out_value;;

(* ----- MODEL VISUALIZATIONS ----- *)
dot_gen_show_ph_file ~rend:"pdf" test_model_out_range "test_model_range_ph.gv";;
dot_gen_show_funct_file ~rend:"pdf" test_library test_model_out_range "test_model_range_fn.gv";;
dot_gen_show_fault_file ~rend:"pdf" test_library test_model_out_range "test_model_range_fa.gv";;

dot_gen_show_ph_file ~rend:"pdf" test_model_out_value "test_model_value_ph.gv";;
dot_gen_show_funct_file ~rend:"pdf" test_library test_model_out_value "test_model_value_fn.gv";;
dot_gen_show_fault_file ~rend:"pdf" test_library test_model_out_value "test_model_value_fa.gv";;

(* ----- ANALYSES ----- *)
let test_model_out_range_ftree = model_to_ftree test_library test_model_out_range ;;
let test_model_out_value_ftree = model_to_ftree test_library test_model_out_value ;;
probErrorCutImp test_model_out_range_ftree;;
probErrorCut test_model_out_range_ftree;;
probErrorCutImp test_model_out_value_ftree;;
probErrorCut test_model_out_value_ftree;;

(* ---- POST ANALYSES VISUALIZATIONS --- *)
dot_gen_show_direct_tree_file "test_model_range_dftree.gv" test_model_out_range_ftree;;
dot_gen_show_tree_file "test_model_range_ftree.gv" test_model_out_range_ftree ;;

dot_gen_show_direct_tree_file "test_model_value_dftree.gv" test_model_out_value_ftree;;
dot_gen_show_tree_file "test_model_value_ftree.gv" test_model_out_value_ftree ;;


