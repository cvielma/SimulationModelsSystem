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

import com.librethinking.simmodsys.*;
import com.librethinking.simmodsys.exceptions.StateValidationException;
import com.librethinking.simmodsys.exceptions.ValueOutOfBoundsException;
import com.librethinking.simmodsys.exceptions.business.FunctionalException;
import com.librethinking.simmodsys.exceptions.business.TechnicalException;
import com.librethinking.simmodsys.models.pesm.parameters.AssetMonthAPR;
import com.librethinking.simmodsys.models.pesm.parameters.PESMParameter;
import com.librethinking.simmodsys.models.pesm.parameters.Period;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the Simulation SIMModel that executes the Personal Economy model (PESM).
 * It has to calculate monthly the changes in the parameters and store it in states. 
 * Also have to determine if it should continue of it has reached a stopping state.
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMModel implements SIMModel {
    /** This field defines the type of this model */
    public static final String MODELTYPE = "MT001";
    
    /** This field stores the name of the model */
    private String name;
    
    /**This field determines if the model can be executed or not. */
    private boolean executable = false;

     /**This field establishes the initial state of the model. */
    private PESMState initialState = null;
    
    /**This field establishes the final state of the model. By default has the default values*/
    private PESMState finalState = null;
    
    /**This field is a utility field to keep a pointer to the current state of the model. */
    private PESMState currentState = null;
    
    /**This field stores all the states of this model. */
    private SortedSet<PESMState> states = null; 
    
    /**This represents the user associated to the model*/
    private SIMUser user = null;
    
    /** This method executes the SIMModel until a final SIMState is reached.
     * If no final state has been set, it will consider a default state stop. 
     * 
     * @throws StateValidationException if there is an error determining if the state has 
     * reached the final state.
     */
    @Override
    public void execute() throws StateValidationException{
        if(isExecutable()){
                this.initializeStates();
                PESMModelStopRulesValidator validator = PESMModelStopRulesValidator.getInstance();
                PESMStateDecorator rulesDecorator; 
                this.cloneIncrementState(this.initialState);
                
                String[] monthRules = {"I1", "L2", "L1","E2", "A1", "L3", "A2",
                                       "A3", "A5", "I5", "I4", "E5"};
                String[] beforeMonthRules = {"E1"};
                String[] endYearRules = {"A4", "E3", "I2"};
                String[] startYearRules = {"I3","E4"};
                
                rulesDecorator =  completeDecoration(this.currentState);                                            
                
                while(!validator.finalReached(initialState, currentState, finalState)){
                  rulesDecorator = (PESMStateDecorator) this.applyAllRules(rulesDecorator, 
                                                                    beforeMonthRules, 
                                                                    monthRules, 
                                                                    startYearRules, 
                                                                    endYearRules);
                }
        
        }
    }

    /** This method tells if the SIMModel can be executed (satisfies certain requirements).
     */
    @Override
    public boolean isExecutable() {
        boolean initSet = this.initialState!=null && this.initialState.getParameters().size()>0;        
        boolean currNotSet = this.currentState==null;
        boolean statesNotSet = this.states==null;
        
        if(initSet && currNotSet && statesNotSet){
            this.executable = true;
        }
        else{
            this.executable = false;
        }
            
        return executable;
    }
    
    public void setExecutable(boolean executable) {
        this.executable = executable;
    }

    /** This method obtains the first SIMState established for the model.
     * If no SIMState has been set, this will return null.
     */
    @Override
    public SIMState getInitialState() {
        return this.initialState;
    }

    
    /** This method sets the initial SIMState of the model.
     * It can return runtime exceptions as result of the operation. 
     * @param st <code>SIMState</code> with that will be the initial 
     * <code>SIMState</code> for this model.
     * 
     * This method is threadsafe. //TODO: make this method threadsafe.
     * 
     * If the passed state is not complete it completes the rest of the parameters
     * with default values.
     * 
     * @throws ClassCastException if <code>st</code> is not <code>PESMState</code>.     
     * 
     */
    @Override
    public void setInitialState(SIMState st) {
        synchronized(this){
                if(this.initialState==null){
                    if( st.getParameters().size() < ((PESMState) st).getExpectedParameters().length){
                        this.initialState = this.completeState((PESMState)st);
                    }
                    else{
                        this.initialState =(PESMState) st;
                    }
                }                
        }
    }

    /** This method obtains the last (final) SIMState established for the model, if
     * any. If no final state is set, this will return null.*/
    @Override
    public SIMState getFinalState() {
        return this.finalState;
    }

    /** This method sets the final SIMState for the model. It should remove the last
     * SIMState in the model (if there's one) and change it for this new SIMState. 
     * 
     * This method is threadsafe.
     * 
     * @param st <code>SIMState</code> with that will be the final
     * <code>SIMState</code> for this model.
     * 
     * @throws ClassCastException if <code>st</code> is not <code>PESMState</code>.
     */
    @Override
    public void setFinalState(SIMState st) {
        synchronized(this){
                this.finalState = (PESMState) st;
        }
        
    }

    /** This method sets a Collection of States to the SIMModel. The first state will
     * be the initial, and the last will be the final. 
     * The size of <code>sts</sts> should be 2. Doesn't matter the order in which 
     * the States are passed, this method will order the States.
     * 
     * This method is threadsafe.
     * 
     * @param sts <code>Set</code> with the initial and final states for this model.
     * @throws ValueOutOfBoundsException if <code>sts</code> is not of size two.    
     * @throws ClassCastException if <code>sts</code> is not a <code>set</code> of <code>PESMState</code>.
     */
    @Override
    public void setStates(Set<SIMState> sts) {
        if (sts.size()!=2){
            throw new ValueOutOfBoundsException("List of states to be set is: "+sts.size()+" and should be 2.");
        }
        else{
            synchronized(this){
                SortedSet<PESMState> mySet = new TreeSet<>();
                for(SIMState currSt : sts){
                    mySet.add((PESMState) currSt);
                }
                this.initialState = mySet.first();
                this.finalState = mySet.last();
                this.currentState = this.initialState;
            }
        }
    }

    /** This method returns all the States associated to the model. The first one
     * (if any) will be the initial state, and the last one will be the final.
     * It can return null.
     */
    @Override
    public Collection<SIMState> getStates() {
        Collection<SIMState> myCol = new ArrayList<>();
        try{
        if(this.states.size()>0){
            myCol.addAll(this.states);
            //TODO: is this necessary? myCol.add(this.finalState);
            return myCol;
        }
        else{
            myCol.add(this.initialState);
            return myCol;
        }
        }
        catch(NullPointerException e){return null;}
        
    }

    /** This method adds States to the model. If no initial state is set this
     * will set the initial state. Also if no final state is set, but the initial
     * state was set, this will set final state.
     * 
     * After the second state is added, any additional state added will make this 
     * model no executable and will add all the states ordered by their natural order.
     *      
     * For this model, this method can call <code>setInitialState</code>
     * or <code>setFinalState</code>
     * It can return runtime exceptions.
     * @see {@link setInitialState}
     * @see {@link setFinalState}
     */
    @Override
    public void addState(SIMState st) {
        if(this.initialState==null){
            this.setInitialState(st);
        }
        else if (this.finalState==null) {
            this.setFinalState(st);
        }
        else{
            if(this.states==null){
                this.initializeStates();
                this.states.add(initialState);
                this.states.add(finalState);
                this.states.add((PESMState)st);
            }
            else{
                this.states.add((PESMState)st);
            }
        }
    }

    /** This method sets the SIMUser that is executing the model in order to review 
     * its permissions (when used to save models).
     * It can return runtime exceptions.
     */
    @Override
    public void setUser(SIMUser u) {
        user = u;
    }

    /** This method gets the SIMUser associated to the SIMModel, if any.
     * It can return null.
     */
    @Override
    public SIMUser getUser() {
        return user;       
    }
    
    /**
     * This is an utility method to wrap a state with all the decorators
     * @param st PESMState to be wrapped.
     * @return A PESMState decorated with Asset, Liabilities, Expenses and Income
     * decorators.
     */
    private static PESMStateDecorator completeDecoration(PESMState st){
        PESMStateDecorator rulesDecorator;
        rulesDecorator= new PESMStateAssetsDecorator(st);
        rulesDecorator = new PESMStateExpensesDecorator(rulesDecorator);
        rulesDecorator = new PESMStateIncomesDecorator(rulesDecorator);
        rulesDecorator = new PESMStateLiabilitiesDecorator(rulesDecorator); 
        return rulesDecorator;
    }    
    
    /** 
     * This method initializes correctly the set of states this model will hold
     * and the final state (if not set).
     */
    private void initializeStates(){
        if(this.finalState==null){
                    this.finalState = PESMUtil.newDefaultPESMState(PESMModelStopRulesValidator.FINALPARAMETERS);  
                    Period maxPeriod = new Period();
                    maxPeriod.setMonth(Period.DEFAULTVALUE);//Period is the only that starts with MINVALUE
                    this.finalState.setParameter(maxPeriod.getName(), PESMUtil.paramWrapper(maxPeriod));
                }
                this.states = new TreeSet<>();
                this.states.add(this.initialState);
    }
    
    /**
     * This method clones a SIMState to currentState and increments the 
     * period of current state.     
     * @param st SIMState to be cloned.
     */
    private void cloneIncrementState(PESMState st){
        try {
            this.currentState = (PESMState) st.clone();
            this.currentState.incrementPeriod();
        } catch (CloneNotSupportedException ex) {
            //THIS SHOULD NEVER HAPPEN
            Logger.getLogger(PESMModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method applies all the rules of PESM passed on a given state.
     * 
     * @return PESMState with the rules applied.
     */
    private PESMState applyAllRules(PESMStateDecorator rulesDecorator, 
                                     String[] beforeMonthRules, 
                                     String[] monthRules,
                                     String[] startYearRules,
                                     String[] endYearRules){

                rulesDecorator.applyRules(monthRules);

                rulesDecorator.applyRules(beforeMonthRules);
                
                byte lastPeriod = (byte) rulesDecorator.getPeriod();
                byte lastMonth = (byte) ((byte) lastPeriod % 12);
                
                this.states.add(this.currentState);
                this.cloneIncrementState(this.currentState);

                rulesDecorator =  completeDecoration(this.currentState);
                                
                if(lastMonth == 0 && lastPeriod !=0){
                    rulesDecorator.applyRules(endYearRules);
                    rulesDecorator.applyRules(startYearRules);
                }
                
                
     
           return rulesDecorator;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n****************************************************");
        sb.append("\n*********---------- MODEL START --------**************");
        sb.append("\n****************************************************\n");
        if(this.isExecutable()){
            sb.append("------------- INITIAL STATE ----------------\n");
            sb.append(this.modelStateToString(this.initialState));
            sb.append("\n******************************************");
            sb.append("\n------------- FINAL STATE ----------------");
            sb.append(this.modelStateToString(this.finalState));
        }
        else{
            sb.append("------------- INITIAL STATE ----------------\n");
            sb.append(this.modelStateToString(this.initialState));
            for(PESMState st : this.states){
                sb.append("\n----------------PERIOD: ");
                sb.append(st.getPeriod());                
                sb.append(" ----------------------");
                sb.append(this.modelStateToString(st));
            }
            sb.append("\n******************************************");
            sb.append("\n------------- FINAL STATE ----------------");
            sb.append(this.modelStateToString(this.finalState));
            sb.append("\n******************************************");
            sb.append("\n------------- CURRENT STATE ----------------");
            sb.append(this.modelStateToString(this.currentState));
        
        }
        sb.append("\n****************************************************");
        sb.append("\n*********---------- MODEL END --------**************");
        sb.append("\n****************************************************\n");
        
        return sb.toString();
    }
    
    /** This method returns the String representation of a PESMState 
     * without all the details given by PESMState.toString method.
     * 
     * @param st PESMState to be printed.
     * @returns String representation of the SIMState.
     */
    protected String modelStateToString(PESMState st){
        StringBuilder sb = new StringBuilder();
        if(st == null){return "\nSTATE IS NULL";}
        
        for(Set<SIMParameter> currParam : st.getParameters()){
            if(currParam.isEmpty()){sb.append("PARAMETER VALUE WAS NULL.");}
            else {
                boolean firstTime=true;
                for(Iterator<SIMParameter> i = currParam.iterator(); i.hasNext();){
                    SIMParameter myParam = i.next();
                    sb.append("\n");
                    if(firstTime){
                        sb.append(myParam.getName());
                        sb.append("\t| ");
                        firstTime = false;
                    }
                    sb.append("[");
                    for(Iterator j = (myParam.getValue()).iterator(); j.hasNext();){
                        Object o = j.next();
                        sb.append(o);
                        if(j.hasNext()){
                            sb.append(" | ");
                        }
                    }
                    sb.append("]\t");                    
                }
            }
            
        }
        return sb.toString();
    }

    /** Sets the name for this model.
     * 
     * @param name Name to be assigned to this model
     * 
     */
    @Override
    public void setName(String name) {
        this.name =name;
    }

    /** Returns the name of this model. If the name hasn't been assigned it
     * will generate one.
     * 
     * @return Name of the model.
     */
    @Override
    public String getName() {
        if(this.name == null){
            StringBuilder sb = new StringBuilder();
            sb.append(this.MODELTYPE);
            sb.append("-");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            sb.append(sdf.format(now));            
            return sb.toString();
        }
        return this.name;
    }

    @Override
    public String getType() {
        return MODELTYPE;
    }

    /**This method returns a new instance of a PESMState, that is the 
     * defining state of this model.     
     */
    @Override
    public SIMState newDefStateInstance() {
        return new PESMState();
    }

    /** This method returns the skeleton of a final state for this model**/
    @Override
    public SIMState newFinalStateMock() {
        return PESMUtil.newDefaultPESMState(PESMModelStopRulesValidator.FINALPARAMETERS);  
    }

    /** This method returns a skeleton of an initial state for this model **/
    @Override
    public SIMState newInitialStateMock() {
        return PESMUtil.newDefaultPESMState(PESMState.USERPARAMETERS);  
    }
    
    /** 
     * This is an internal method to complete a PESMState that has missing parameters
     * with new default parameters. This is done because in the client side there
     * are parameters that must not be set but the model need to execute.
     * 
     * This method modifies the passed PESMState.
     * 
     * @param incomplete State with incomplete parameters.
     * @return A new State with all the parameters.
     * 
     * @throws TechnicalException if there's a configuration problem in the Parameter
     * Factory configuration.     
     */
     private PESMState completeState(PESMState incomplete){
         String[] params = incomplete.getExpectedParameters();
         SIMParameterFactory paramFactory = new SIMParameterFactory();
         for(String currParam : params){
             Set<SIMParameter> setParam = incomplete.getParameter(currParam);
             if(setParam == null){
                 Set<SIMParameter> newSet = new HashSet<>();
                 try{
                     SIMParameter newParam = paramFactory.createSIMParameter(currParam);
                     newSet.add(newParam);
                     incomplete.setParameter(currParam, newSet);
                 }
                 catch(FunctionalException|ReflectiveOperationException ex){
                     throw new TechnicalException(ex);
                 }
             }
         }
      return incomplete;
     }
    
}
