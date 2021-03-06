/**
 * Copyright 2012 Christian Vielma <cvielma@librethinking.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.librethinking.simmodsys.models.pesm;

import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.SIMState;
import com.librethinking.simmodsys.exceptions.InvalidParameterException;
import com.librethinking.simmodsys.exceptions.InvalidParameterNumberException;
import com.librethinking.simmodsys.exceptions.ValueOutOfBoundsException;
import com.librethinking.simmodsys.models.pesm.parameters.PESMParameter;
import com.librethinking.simmodsys.models.pesm.parameters.Period;
import com.librethinking.simmodsys.models.pesm.parameters.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.reflections.Reflections;

/**
 * This SIMState is used as a Reference to the PESMModel, to indicate
 * to callers what are the parameters that the PESMModel expects. 
 * It seem that this class shouldn't be abstract because it'll be needed
 * in the model to represent the history of the parameters.
 *  
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMState implements SIMState, Cloneable, Comparable {

    //TODO: Document errors
    
    /** Number of parameters that will be managed by this SIMState Type*/
    static final int NUMBEROFPARAMS = 23;
    
    /** This will store the list of parameters of the model */
    private HashMap<String, Set<SIMParameter>> parameters = new HashMap<String, Set<SIMParameter>>(NUMBEROFPARAMS);
    
    /**This describes the types of Parameters accepted by this model. */
    private HashSet<String> expectedParams;
    
    /** This variable identifies the parameters that the user can modify */
    static final Class[] USERPARAMETERS = {AssetAPR.class, AssetAmount.class, AssetMinSavings.class,
        ExpenseDesiredExpenses.class, ExpenseFixed.class, ExpenseInflation.class, ExpenseVariable.class,
        IncomeAnnualRise.class, IncomeFixedEarnings.class, IncomeVariableEarnings.class,
        LiabilitiesAPR.class, LiabilitiesAmount.class, LiabilitiesDelayFee.class, 
        LiabilitiesMinPayment.class, Period.class};
    
    /** 
     * Initializes the types of Parameters accepted by this model.
     * 
     */
    public PESMState(){
        this.expectedParams = new HashSet<String>();
        
        Reflections reflections = new Reflections(this.getClass().getPackage().getName());

        Set<Class<? extends PESMParameter>> allClasses = reflections.getSubTypesOf(PESMParameter.class);
        
        for(Iterator<Class<? extends PESMParameter>> i = allClasses.iterator(); i.hasNext();){
            Class<? extends PESMParameter> currParam = i.next();
            try {
                this.expectedParams.add(currParam.newInstance().getName());
            } catch (Exception ex) {
                 Logger.getLogger(PESMState.class.getName()).log(Level.FINE, "Generic Error: {0}.", ex.getMessage());
                 throw new RuntimeException(ex);                 
            }
        }
        
        if(this.expectedParams.size()!= NUMBEROFPARAMS){
            String msg = "Sync is out of sync. PESM Parameters are: '"+ this.expectedParams.toString()+
                    "' ("+this.expectedParams.size()+" in total), but Number of Expected Params is: '"+NUMBEROFPARAMS+"'. ";
            Logger.getLogger(PESMState.class.getName()).log(Level.FINE, msg);
            throw new RuntimeException(msg);
        }

    }
    
    /** This method will return the parameter indicated by <code>paramName</code>.
     * 
     * @param paramName Name of the SIMParameter to be searched.
     * 
     * @return If the parameter is unique, then it will return a Collection of one SIMParameter, 
     * if not, then it will return a Collection with many Parameters (all of the same type).
     * If no SIMParameter is found by that name, it will return <code>null</code>.
     * 
     * @throws NoSuchElementException If <code>paramName</code> is invalid.
     * 
     */ 
    @Override
    public Set<SIMParameter> getParameter(String paramName) {
        if(!this.expectedParams.contains(paramName)){
            String msg = "Parameter: '"+paramName+"' is not valid. "
                    + "Possible parameters are: '"+this.expectedParams.toString()+"'.";
            Logger.getLogger(PESMState.class.getName()).log(Level.FINE, msg);
            throw new NoSuchElementException(msg);
        }
       return this.parameters.get(paramName);
    }

    /** Returns all the Parameters associated to the SIMState. Similar to 
     * {@link getParameter}, for unique Parameters the Collection<Parameter> inside
     * of the larger Collection will be one or more parameters. 
     * 
     * @return A Collection with every SIMParameter in the model. Each SIMParameter will be 
     * in a Collection (unique Parameters will be Collection of 1, and non-unique will be
     * Collection of more). If the SIMState doesn't have a SIMParameter it will be return
     * an empty Collection. 
     */
    @Override
    public Set<Set<SIMParameter>> getParameters() {
        Set myparams = new HashSet<Set<SIMParameter>>();
        for(Iterator i = this.parameters.entrySet().iterator(); i.hasNext();){
            Map.Entry current = (Map.Entry) i.next();
            myparams.add((Set<SIMParameter>)current.getValue());
        }
        return myparams;
    }

    /**
     * This method will set all the Parameters that this SIMState holds, this will 
     * not update the value of the parameters but replace all the parameters. 
     * 
     * @param params <code>Collection</code> of <code>Collection</code> of 
     * <code>Parameters</code> to be assigned. The <code>Collection</code> will 
     * have a <code>Collection</code> of one <code>SIMParameter</code> for unique 
     * parameters and a bigger <code>Collection</code> if the 
     * <code>SIMParameter</code> is not unique. All parameters passed should be of 
     * type <code>PESMParameter</code>.
     * 
     * @throws InvalidParameterException if parameter is not of type 
     * <code>PESMParameter</code> or a<code>Collection<Parameter></code> has 
     * different types of Parameters in it.
     * 
     * @throws InvalidParameterNumberException if number of parameters is not 
     * acceptable by the state, as indicated by <code>NUMBEROFPARAMS</code>
     */
    @Override
    public void setParameters(Set<Set<SIMParameter>> params){
        HashMap<String, Set<SIMParameter>> tempParams = new HashMap<String, Set<SIMParameter>>(NUMBEROFPARAMS);
        HashSet<String> passedParams = new HashSet<String>();
        
        if (params.size()!=NUMBEROFPARAMS){
            String msg = "Error in number of parameters "
                    + "passed. Parameter passed: '"+params.size()+"', expected: '"+NUMBEROFPARAMS+"'.";
            Logger.getLogger(PESMState.class.getName()).log(Level.FINE, msg);
            throw new InvalidParameterNumberException(msg);
        }
        
        for(Iterator i = params.iterator(); i.hasNext();){
            Set<SIMParameter> currParamCol = (Set<SIMParameter>) i.next();
            SIMParameter currParam = (SIMParameter)currParamCol.toArray()[0];

            if(currParam.isUnique() && currParamCol.size()>1){
                String msg = "Error in number of parameters of type: '"+
                        currParam.getName()+"'. The parameter is unique but '"+currParamCol.size()+"' were passed.";
                Logger.getLogger(PESMState.class.getName()).log(Level.FINE,msg);                        
                throw new InvalidParameterNumberException(msg);
            }
            
            //validates that all the parameters value are of same type
            for(Iterator<SIMParameter> j = currParamCol.iterator(); j.hasNext();){
                SIMParameter valParam = j.next();
                if(!(currParam.getName().equals(valParam.getName()))){
                    String msg = "Parameter of type: '"+currParam.getName()+"', "+
                    "has a value of type: '"+valParam.getName()+"'.";
                    Logger.getLogger(PESMState.class.getName()).log(Level.FINE, msg);
                    throw new InvalidParameterException(msg);
                }
            }
            
            passedParams = validateParameter(passedParams, currParam); //validates type is of correct type
            tempParams.put(currParam.getName(), currParamCol);            
        }
        
        //validates completeness of the passed parameters
        for(Iterator<String> i = this.expectedParams.iterator(); i.hasNext();){
            if(!(passedParams.contains(i.next()))){
                String msg = "Passed parameters are wrong. Passed parameters are: '"+
                        passedParams.toString()+"', but expected parameters are: '"+this.expectedParams.toString()+"'.";
                throw new InvalidParameterException(msg);                       
            }
        }
        
        parameters = tempParams;
    }

    /** Sets a SIMParameter that the current SIMState have given the name of the passed 
     * SIMParameter. This method can throw exceptions.
     * @param paramName This is the name of the parameter to be set.
     * @param param This the Collection of values that the parameter holds (if
     * compound or not unique).
     * If the parameter is unique, then it will be a collection of size 1.
     * 
     * @throws InvalidParameterException if parameter is not of 
     * <code>PESMParameter</code> type, or at least one of the values of the 
     * parameters passed in <code>param</code> is not equal to <code>paramName</code>.
     * @throws InvalidParameterNumberException if parameter is unique and was 
     * tried to set more than one value, or if <code>param</code> set is null or empty.
     */
    @Override
    public void setParameter(String paramName, Set<SIMParameter> param) throws InvalidParameterException {
        if(!this.expectedParams.contains(paramName)){
            String msg = "Parameter of type: '"+paramName+"' is invalid. "
                    + "Accepted parameters are: '"+this.expectedParams.toString()+"'.";
            //Logger.getLogger(PESMState.class.getName()).log(Level.FINE, msg);
            throw new InvalidParameterException(msg);
        }
        else if(param==null || param.isEmpty()){
            String msg = "Tried to assign to parameter: "+paramName+", null value.";
            throw new InvalidParameterException(msg);
        }
       
        //validates that all the parameters value are of same type
       for(Iterator<SIMParameter> i = param.iterator(); i.hasNext();){
                SIMParameter valParam = i.next();
                if(!(paramName.equals(valParam.getName()))){
                    String msg = "Parameter of type: '"+paramName+"', "+
                    "has a value of type: '"+valParam.getName()+"'.";
                    Logger.getLogger(PESMState.class.getName()).log(Level.FINE, msg);
                    throw new InvalidParameterException(msg);
                }
       }
        
        SIMParameter currParam = (SIMParameter)param.toArray()[0];
        
        if(currParam.isUnique() && param.size()>1){
                String msg = "Error in number of parameters of type: '"+
                        currParam.getName()+"'. The parameter is unique but '"+param.size()+"' were passed.";
                Logger.getLogger(PESMState.class.getName()).log(Level.FINE, msg);
                throw new InvalidParameterNumberException(msg);
            }
        
        this.parameters.put(paramName, param);
        
    }

    /** This method is used to validate whether or not a SIMParameter is valid for the model.
     * If it is valid, it'll return a <code>HashSet</code> with the <code>passedParams</code> 
     * input parameter updated (it will add the parameter class name to the list of passedParams).
     * 
     * @param passedParams Set with current valid passed Parameters. 
     * @param currParam Current SIMParameter to be evaluated if valid or not.
     *
     * @return <code>passedParams</code> with <code>currParam</code> class name added.
     * 
     * @throws InvalidParameterException if <code>currParam</code> is not of expected type.
     */
    private HashSet<String> validateParameter(HashSet<String> passedParams, SIMParameter currParam) {
        boolean validType = this.expectedParams.contains(currParam.getName());
        if(!validType){
            String msg = "Invalid Parameter Type. Passed type is: '"+
                    currParam.getName()+"', possible types are: '"+ this.expectedParams.toString()+"'.";
            Logger.getLogger(PESMState.class.getName()).log(Level.FINE, msg);
            throw new InvalidParameterException(msg);
        }
        
        passedParams.add(currParam.getName());
        
        return passedParams;
    }    
    
    @Override
    public boolean equals(Object obj){
        if (getClass() != this.getClass() || obj == null){
                //Logger.getLogger(PESMState.class.getName()).log(Level.FINEST, "Class to be compared is not PESMState"); 
                return false;}
        
        Set<Set<SIMParameter>> objParams = ((PESMState)obj).getParameters();
        
        //both states must have same number of params
        if(!(objParams.size()==this.parameters.size())){
            Logger.getLogger(PESMState.class.getName()).log(Level.FINEST, "Number of parameters is different."); 
            return false;}
        
        for(Iterator<Set<SIMParameter>> i = objParams.iterator(); i.hasNext();){
            Collection<SIMParameter> currCol = i.next();
            
            //all the parameters in obj should be in this object too
            if(this.parameters.containsKey(((SIMParameter)currCol.toArray()[0]).getName())){
                Set<SIMParameter> myCurrCol = this.parameters.get(((SIMParameter)currCol.toArray()[0]).getName());
                
                //for a given parameter, the number of values must be the same
                if(myCurrCol.size() == currCol.size()){
                
                     for(Iterator<SIMParameter> j = myCurrCol.iterator(); j.hasNext();){
                        SIMParameter currParam = j.next();
                        
                        boolean atLeastOne = false;
                        //for each parameter in this object, there should be at least
                        //one parameter in obj that is equal
                        for(Iterator<SIMParameter> k = currCol.iterator(); k.hasNext();){
                            SIMParameter currEval = k.next();
                           if(currEval.equals(currParam)){atLeastOne = true; break;}                                        
                        }
                        
                        if(!atLeastOne){
                            Logger.getLogger(PESMState.class.getName()).log(Level.FINEST, "Could not find any parameter value equal."); 
                            return false;}
                    }
                }
                else{
                    Logger.getLogger(PESMState.class.getName()).log(Level.FINEST, "Number of values for parameter '{0}' is distinct.", ((SIMParameter)currCol.toArray()[0]).getName()); 
                    return false;}
            }
            else{
                Logger.getLogger(PESMState.class.getName()).log(Level.FINEST, "This State doesn't contain parameter named '{0}'.", ((SIMParameter)currCol.toArray()[0]).getName()); 
                return false;}
        }
        
        return true;
      
    }
    
    @Override
    public int hashCode(){
        int accHash =0;
        Collection<Set<SIMParameter>> myParams = this.parameters.values();
        
        for(Iterator<Set<SIMParameter>> i = myParams.iterator(); i.hasNext();){
            Set<SIMParameter> curr = i.next();
            for(Iterator<SIMParameter> j = curr.iterator(); j.hasNext();){
                SIMParameter currParam = j.next();
                accHash += currParam.hashCode();
            }
        }
        
        return accHash;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{[PESMSTATE]");
        sb.append(" --- NUMBEROFPARAMS: ").append(PESMState.NUMBEROFPARAMS);
        sb.append(", --- EXPECTEDPARAMS: ").append(this.expectedParams.toString());
        sb.append(", --- PARAMETERS: ").append(this.parameters.toString());
        sb.append(" ---. ");
        return sb.toString();
        
    }

    /**
     * @throws InvalidParameterException if Period parameter is not unique. 
     */
    @Override
    public int compareTo(Object arg0) {
        Period myPeriod;
        Period itsPeriod;
        
        try{
            myPeriod = PESMUtil.getUniqueParam(this, new Period());
        }
        catch(InvalidParameterException e){
            if(this.getParameter(Period.NAME).size()>1){//period parameter is corrupted
                throw e;
            }
            else{ //period parameter is not assigned in this state
                return Integer.MAX_VALUE;
            }
        }
        try{
            itsPeriod = PESMUtil.getUniqueParam((PESMState) arg0, new Period());
            }
        catch(InvalidParameterException e){
            if(this.getParameter(Period.NAME).size()>1){//period parameter is corrupted
                throw e;
            }
            else{ //period parameter is not assigned in passed state
                return Integer.MIN_VALUE;
            }
        }
        
        if(myPeriod == null && itsPeriod==null)
            return 0;
        else if(myPeriod == null)
            return -1;
        else if(itsPeriod == null)
            return 1;
        
        return myPeriod.compareTo(itsPeriod);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        PESMState cloned = new PESMState();
        
        for(Set<SIMParameter> curr : this.parameters.values()){
            String currName = ((SIMParameter)curr.toArray()[0]).getName();
            Set<SIMParameter> clonedCurr = new HashSet<SIMParameter>();
            
            for(SIMParameter par: curr){
                clonedCurr.add((SIMParameter)((PESMParameter)par).clone());
            }
            
            cloned.setParameter(currName, clonedCurr);
        }
        
        return cloned;
        
    }
    
    /**
     * This method sums one to the period parameter of this state to facilitate 
     * ordering.
     * 
     * @throws ValueOutOfBoundsException If the period parameter currently has the maximum value.
     * 
     */
    public void incrementPeriod(){
        Period myPeriod = PESMUtil.getUniqueParam(this, new Period());
        
        myPeriod.setMonth(myPeriod.getMonth()+1);
    }
    
    /** 
     * This method returns the current period month.
     * 
     * @returns The number of the current period month.
     */
    public int getPeriod(){
        return PESMUtil.getUniqueParam(this, new Period()).getMonth();
    }

    /** This method is the same than @link{getPeriod()}. */
    @Override
    public int getNumericID() {
        return this.getPeriod();
    }

    @Override
    public Map<String,Collection<SIMParameter>> describe() {
        Map<String, Collection<SIMParameter>> categories = new HashMap<>();
        Collection<SIMParameter> AssetParams = new ArrayList<>();
        Collection<SIMParameter> IncomeParams = new ArrayList<>();
        Collection<SIMParameter> ExpenseParams = new ArrayList<>();
        Collection<SIMParameter> LiabParams = new ArrayList<>();
        Collection<SIMParameter> OtherParams = new ArrayList<>();
        
        for(String s : this.parameters.keySet()){
                Set<SIMParameter> currentSet = this.parameters.get(s);
                if(currentSet.size()>0){
                
                    PESMParameter param = (PESMParameter) currentSet.toArray()[0];
                    if(param.getName().startsWith("ASSET")){
                        AssetParams.add(param);
                    }
                    else if(param.getName().startsWith("INCOME")){
                        IncomeParams.add(param);
                    }
                    else if(param.getName().startsWith("EXPENSE")){
                        ExpenseParams.add(param);
                    }
                    else if(param.getName().startsWith("LIABILIT")){
                        LiabParams.add(param);
                    }
                    else {OtherParams.add(param);}
                }
                else{throw new RuntimeException("Parameter set but size is 0.");}
        }
        categories.put("ASSET", AssetParams); //TODO: use bundle
        categories.put("INCOME", IncomeParams);
        categories.put("EXPENSE", ExpenseParams);
        categories.put("LIABILITY", LiabParams);
        categories.put("OTHER", OtherParams);
        
        return categories;
    }
    
    /** 
     * This method returns the expected parameters by this State.
     * 
     */
    protected String[] getExpectedParameters(){
        return this.expectedParams.toArray(new String[0]);
    }

    @Override
    public Set<String> getExpectedParametersSet() {
        return  this.expectedParams;
    }
    
}
