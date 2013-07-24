/*
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

import com.librethinking.simmodsys.models.pesm.parameters.AssetAmount;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesAmount;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseDesiredExpenses;
import com.librethinking.simmodsys.models.pesm.parameters.Period;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeFixedEarnings;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseFixed;
import com.librethinking.simmodsys.models.pesm.parameters.AssetMinSavings;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeYearly;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseYearly;
import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.exceptions.NullOrInvalidStateException;
import com.librethinking.simmodsys.exceptions.StateValidationException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is an utility class to validate the stop rules of a model.
 * This class is used by <code>PESMModel</code> to determine if a model have 
 * reached its final <code>State</code>. This class implements Singleton pattern.
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
class PESMModelStopRulesValidator {
    
    /**To reflect validation methods*/
    private static String REACHPREFIX = "reached"; 
    
    private static volatile PESMModelStopRulesValidator instance=null;
    
    private PESMModelStopRulesValidator() {}
    
    /**This determines which parameters can be validated.*/
    protected final static Class[] FINALPARAMETERS = {AssetAmount.class, ExpenseFixed.class,
                                              IncomeFixedEarnings.class, 
                                              AssetMinSavings.class, 
                                              ExpenseDesiredExpenses.class,
                                              LiabilitiesAmount.class,
                                              Period.class};
    /** This specifies some parameters that need others to get validated */
    private final static Class[] SPECIALPARAMETERS = {AssetMinSavings.class};
    
            
    protected static PESMModelStopRulesValidator getInstance(){
        synchronized (PESMModelStopRulesValidator.class){
            if(PESMModelStopRulesValidator.instance == null){
                PESMModelStopRulesValidator.instance = new PESMModelStopRulesValidator();
            }
        }
        return instance;
    }
    
    /** This method is used to determine if a model have reached its final state.
     * 
     * Depending on how is the difference between <code>initalS</code> and
     * <code>finalS</code>, this method will determine if <code>currentS</code>
     * has reached the final State (i.e.: if initial amount is less than final amount
     * the model should stop when current amount is greater or equal to final amount).  
     * 
     * A state is reached when any of its parameters is reached. 
     * 
     * @param initialS Initial state of the model.
     * @param currentS Current state to be compared.
     * @param finalS Final state of the model.
     * 
     * @returns <code>true</code> if <code>finalS</code> was reached, false otherwise.
     * 
     * @throws StateValidationException if an unexpected runtime error occurs during the validation or
     * this class have problems resolving the validation method for a specified parameter.
     * @throws RuntimeException for some errors regarding the reflection of methods. This should never happen.
     *
     */
    public boolean finalReached(PESMState initialS, PESMState currentS, PESMState finalS) throws StateValidationException{
        boolean reached = false;
        Set<Set<SIMParameter>> iParameters = initialS.getParameters();
        List<Class> myParameters = Arrays.asList(FINALPARAMETERS);
        List<Class> specialParams = Arrays.asList(SPECIALPARAMETERS);
        
        
        for(Set<SIMParameter> currParamSet : iParameters){
            SIMParameter firstInSet = (SIMParameter) currParamSet.toArray()[0];
            
            if(myParameters.contains(firstInSet.getClass())){
                StringBuilder sb = new StringBuilder(REACHPREFIX);
                sb.append(firstInSet.getClass().getSimpleName());
                
                try {
                    //If parameter requires other parameter to get validated
                    if(specialParams.contains(firstInSet.getClass())){ 
                        reached = (Boolean) this.getClass()
                                                .getDeclaredMethod(sb.toString(), 
                                                           PESMState.class, 
                                                           PESMState.class, 
                                                           PESMState.class)
                                                .invoke(this, initialS, currentS, finalS); 
                    }
                    //if parameter is unique, then parameters are reflected
                    else if(firstInSet.isUnique() && !specialParams.contains(firstInSet.getClass())){
                        SIMParameter currentP = PESMUtil.getUniqueParam(currentS, firstInSet);
                        SIMParameter finalP = PESMUtil.getUniqueParam(finalS, firstInSet);
                        reached = (Boolean) this.getClass()
                                               .getDeclaredMethod(sb.toString(), 
                                                           SIMParameter.class, 
                                                           SIMParameter.class, 
                                                           SIMParameter.class)
                                               .invoke(this, firstInSet, currentP, finalP);                                          
                    }
                    //if the parameter is not unique, it needs the set of values
                    else if(!firstInSet.isUnique() && !specialParams.contains(firstInSet.getClass())){
                         reached = (Boolean) this.getClass()
                                                .getDeclaredMethod(sb.toString(), 
                                                           Set.class, 
                                                           Set.class, 
                                                           Set.class)
                                                .invoke(this, currParamSet, 
                                                     currentS.getParameter(firstInSet.getName()), 
                                                     finalS.getParameter(firstInSet.getName()));                        
                    }
                    else{throw new RuntimeException("THIS SHOULD NEVER HAPPEN");}
                } 
                catch (NoSuchMethodException|SecurityException|IllegalAccessException|
                            IllegalArgumentException ex) {
                        throw new StateValidationException("Error finding or "
                                + "calling method for validation of: '"
                                +firstInSet.getClass().getSimpleName()+"'.", ex);
                    }                 
                catch(InvocationTargetException ex){
                    throw new StateValidationException("Unexpected error inside method: '"
                                +  sb.toString()+"'.", ex.getCause());
                }
                catch (RuntimeException ex) {
                        throw new StateValidationException("Unexpected error validating "
                                + "parameter: '"
                                +firstInSet.getClass().getSimpleName()+"'.", ex);
                } 
                if(reached){return true;}
            }
        }
        
        return false;
    }
    
    
    /** This method defines specific rules to determine parameter's stop conditions.
     * @see Project Documentation.
     */
    private static boolean reachedAssetAmount(SIMParameter initialP, SIMParameter currentP, SIMParameter finalP){
        if(finalP==null){return false;} //in case it wasn't stablished in final state, it means that is not a stop condition
        double ip= ((AssetAmount) initialP).getAmount();
        double cp= ((AssetAmount) currentP).getAmount();
        double fp= ((AssetAmount) finalP).getAmount();
        
        if(ip < fp && cp >= fp){return true;}
        else if(ip >= fp && cp <=fp){return true;}
        else{return false;}        
    }
    
    /** This method defines specific rules to determine parameter's stop conditions.
     * @see Project Documentation.
     */
    private static boolean reachedExpenseFixed(Set<SIMParameter> initialS, Set<SIMParameter> currentS, Set<SIMParameter> finalS){
        if(finalS==null){return false;} //in case it wasn't stablished in final state, it means that is not a stop condition
        
        for(SIMParameter initialP : initialS){
            for(SIMParameter currentP : currentS){
                for(SIMParameter finalP : finalS){
                    double ip= ((ExpenseFixed) initialP).getAmount();
                    double cp= ((ExpenseFixed) currentP).getAmount();
                    double fp= ((ExpenseFixed) finalP).getAmount();
        
                    if(ip < fp && cp >= fp){return true;}
                    else if(ip >= fp && cp <=fp){return true;}
                }
            }
        }
        
        return false;        
    }
    
    /** This method defines specific rules to determine parameter's stop conditions.
     * @see Project Documentation.
     */
    private static boolean reachedIncomeFixedEarnings(Set<SIMParameter> initialS, Set<SIMParameter> currentS, Set<SIMParameter> finalS){
        if(finalS==null){return false;} //in case it wasn't stablished in final state, it means that is not a stop condition
        
        for(SIMParameter initialP : initialS){
            for(SIMParameter currentP : currentS){
                for(SIMParameter finalP : finalS){
                    double ip= ((ExpenseFixed) initialP).getAmount();
                    double cp= ((ExpenseFixed) currentP).getAmount();
                    double fp= ((ExpenseFixed) finalP).getAmount();
        
                    if(ip < fp && cp >= fp){return true;}
                    else if(ip >= fp && cp <=fp){return true;}
                }
            }
        }
        
        return false;               
    }
    
    /** This method defines specific rules to determine parameter's stop conditions.
     * @see Project Documentation.
     */
    private static boolean reachedLiabilitiesAmount(SIMParameter initialP, SIMParameter currentP, SIMParameter finalP){
        if(finalP==null){return false;} //in case it wasn't stablished in final state, it means that is not a stop condition
                
        double ip= ((LiabilitiesAmount) initialP).getAmount();
        double cp= ((LiabilitiesAmount) currentP).getAmount();
        double fp= ((LiabilitiesAmount) finalP).getAmount();
        
        if(ip < fp && cp >= fp){return true;}
        else if(ip >= fp && cp <=fp){return true;}
        else{return false;}        
    }
    
    /** This method defines specific rules to determine parameter's stop conditions.
     * @see Project Documentation.
     */
    private static boolean reachedPeriod(SIMParameter initialP, SIMParameter currentP, SIMParameter finalP){
               
        if(((Period)currentP).getMonth() >= ((Period)finalP).getMonth()+1 || 
                ((Period)currentP).getMonth() == ((Period)currentP).MAXVALUE+1 ){return true;}
        else{return false;}        
    }
    
    /** This method defines specific rules to determine parameter's stop conditions.
     * @see Project Documentation.
     */
    private static boolean reachedExpenseDesiredExpenses(Set<SIMParameter> initialP, Set<SIMParameter> currentP, Set<SIMParameter> finalP){
        /**finalP has all the desired expenses that should be made. In other words, 
        to reach the final, currentP must NOT have ANY of the finalP expenses in it.**/
        
        if(finalP==null){return false;} //in case it wasn't stablished in final state, it means that is not a stop condition
        
        //final desired expenses are more than initial
        if(initialP.size() < finalP.size() || initialP.size() < currentP.size()){//this should never happen
            throw new NullOrInvalidStateException("Final or current state shouldn't have more "
                    + " desired expenses than initial state ["+initialP.size()+"].");}
        //initial and final expenses are the same
        else if(initialP.size()==finalP.size()){return true;}
       //at least the number of expenses in finalP has been selected
        else if(currentP.size() <= initialP.size()-finalP.size()){
            boolean allExpended = false;
            
            for(SIMParameter curr:finalP){
                allExpended = allExpended && !currentP.contains(curr);
            }
            if (allExpended){return true;}
        }
        
        return false;
        
            
     }
    
    /** This method defines specific rules to determine parameter's stop conditions.
     * @see Project Documentation.
     */
    private static boolean reachedAssetMinSavings(PESMState initialS, PESMState currentS, PESMState finalS){
        if(currentS.getPeriod()%12!=0){return false;}
        
        AssetMinSavings finalSavings = PESMUtil.getUniqueParam(finalS, new AssetMinSavings());
        if(finalSavings==null){return false;}
        
        double expectedSavings = finalSavings.getPercentage();
        double currYearIncome = PESMUtil.getUniqueParam(currentS, new IncomeYearly()).getAmount();
        double currYearExpense = PESMUtil.getUniqueParam(currentS, new ExpenseYearly()).getAmount();
        
        if((currYearIncome*expectedSavings )<= (currYearIncome-currYearExpense)){
            return true;
        }
        else{return false;}               
    
    }
        
        
    
    
}
