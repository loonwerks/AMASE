package edu.umn.cs.crisys.safety.analysis;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.Subcomponent;
import org.osate.annexsupport.AnnexUtil;

import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeUtils;

import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.util.RecordIdPathElement;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.RecordAccessExpr;
/*
 * This class provides some utility methods:
 * typeContainsSafetyAnnex(Subcomponent)
 * containsSafetyAnnex(Subcomponent)
 */
public class SafetyUtils {

	/* containsSafetyAnnex
	 * @param subComp Subcomponent: is the subcomponent to check for associated safety annex
	 * @return boolean: true if safety annex found, else false
	 * This is found by calling typeContainsSafetyAnnex(Subcomponent) 
	 * 
	 */
    public static boolean containsSafetyAnnex(ComponentImplementation compImpl) {
        
        if (compImpl != null) {
        	EList<AnnexSubclause> annexes = AnnexUtil.getAllAnnexSubclauses(compImpl, SafetyPackage.eINSTANCE.getSafetyContractSubclause());
            for(AnnexSubclause annex : annexes){
                EObject container = AgreeUtils.getClosestContainerOfType(annex, ComponentImplementation.class);
                if(compImpl.getName().equals(((ComponentImplementation)container).getName())){
                	return true;
                }
            }
        }
        return typeContainsSafetyAnnex(compImpl);
    }
    
    
	/* typeContainsSafetyAnnex
	 * @param subComp Subcomponent: is the subcomponent to check for associated safety annex
	 * @return boolean true if annex found, else false
	 * 
	 */
    public static boolean typeContainsSafetyAnnex(ComponentImplementation compImpl) {
    	
    	
        ComponentType compType = compImpl.getType();
        if (compType != null) {
            EList<AnnexSubclause> annexes = AnnexUtil.getAllAnnexSubclauses(compType, SafetyPackage.eINSTANCE.getSafetyContractSubclause());
            for(AnnexSubclause annex : annexes){
            	EObject container = AgreeUtils.getClosestContainerOfType(annex, ComponentType.class);
                if(compType.getName().equals(((ComponentType)container).getName())){
                	return true;
                }
            }
        }
        return false;
    }
    
    public static List<RecordIdPathElement> getExprPath(List<RecordIdPathElement> path, Expr expr) {
    	
    	if(expr instanceof IdExpr) {
    		return path;
    	}
    	if(!(expr instanceof RecordAccessExpr)){
    		throw new AgreeException("Cannot get expression path in AgreeUtils.");
    	}
    	
    	RecordAccessExpr record = (RecordAccessExpr) expr;
    	getExprPath(path, record.record);
    	path.add(new RecordIdPathElement(record.field));
    	
    	return path;
    }
	
}
