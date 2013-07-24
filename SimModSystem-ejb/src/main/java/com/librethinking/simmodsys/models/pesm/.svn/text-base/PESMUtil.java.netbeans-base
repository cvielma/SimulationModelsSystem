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

import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.SIMState;
import com.librethinking.simmodsys.exceptions.InvalidParameterException;
import com.librethinking.simmodsys.exceptions.ValueOutOfBoundsException;
import com.librethinking.simmodsys.models.pesm.parameters.PESMParameter;
import java.util.*;
import java.util.logging.Level;
import org.reflections.Reflections;

/**
 * This class provides some utility functions to ease the development of the
 * PESM.
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMUtil {
    
    /** This method returns a parameter when the parameter is unique.
     *  If the given parameter is not unique it will throw 
     * <code>InvalidParameterException</code>
     * 
     * @param state SIMState from which it will look the given parameter.
     * @param param SIMParameter type desired.
     * 
     * @throws InvalidParameterException when parameter is not unique.     
     */
    protected static <T extends SIMParameter> T getUniqueParam(SIMState state, T param){
        Collection<SIMParameter> paramCol = state.getParameter(param.getName());
        if(paramCol == null)
            return null;
                
        T myParam =  (T) paramCol.toArray()[0];
        if(paramCol.size()==1 && myParam.isUnique()){
            return myParam;
        }
        else{
            throw new InvalidParameterException("Parameter is not unique or values"
                    + " assigned are larger than expected.");
        }
    }
    
    /** This method wraps a SIMParameter as a Set of SIMParameter.
     * 
     * @param param SIMParameter to be wrapped
     * 
     * @returns Set<Parameter> of size 1.
     */
    protected static Set<SIMParameter> paramWrapper(SIMParameter param){
        Set<SIMParameter> paramCol = new HashSet<SIMParameter>();
        paramCol.add(param);
        
        return paramCol;
    }
    
    /** 
     * This method is used to know if a amount should be included.
     * It takes a number and if the random generation is less than or equal to
     * the number, then it return 1, else it will return 0. 
     * 
     * @param max The number that should be the max to be compared.
     * @return 1 or 0 depending if the random generation is less or equal,
     * greater than <code>maxrange</code>
     * @throws ValueOutOfBoundException if max is > 1.      * 
     */
    protected static byte variator(double max){
        if(max>1.0){
            throw new ValueOutOfBoundsException("Max value ['"+max+"'] is greater than random range.");
        }
        
        return (byte) (Math.random() > max? 0: 1);
    }
    
    /** 
     * This method is used to determine the annual percentage of a given
     * account (i.e.: rise or inflation).
     * It takes two numbers a return a number between both.
     * 
     * @param min The number with minimum bound.
     * @param max The number with the maximum bound.
     * @return a number between <code>min</code> and <code>max</code>,
     */
    protected static double variator(double min, double max){
        return min + (Math.random()*(max-min));//TODO: here is a restriction: this can't never return 1.0
    }
    
    
    /**
     * This is an utility method to update the current amount of a parameter
     * based on a list of categories and percentages. This is used for
     * annual rises and for monthly inflation. If <code>period</code> is passed
     * then it will do a back calculation like if it applied the increment against an
     * original amount. This is for monthly inflation to be calculated accurately.
     * 
     * @param updater Collection with the percentages and categories 
     * @param param SIMParameter to be updated depending on its category.
     * @param percentIndex Index of the updater percentage to be used. 
     * @param categIndex Index of the parameter category.
     * @param amountIndex Index of the parameter amount to be updated. 
     * @param factor Factor by which the percentage will be divided before updating the parameter.
     * @param period Current month. This is used to calculate monthly inflation.
     * @returns <code>true</code> if the parameter was updated, <code>false</code> otherwise.
     * 
     * Example: 
     * <code>
     * (AnnualRise) 
     *  Collection<Parameter> myAnnualRise = state.getParameter(IncomeAnnualRise.NAME);
     *  IncomeFixedEarnings myParam = PESMUtil.getUniqueParam(state, new IncomeFixedEarnings());
     *       PESMUtil.updateParamByPerc(myAnnualrise, myParam, 
     *                                                IncomeAnnualRise.CURRVALINDEX, 
     *                                                IncomeFixedEarnings.CATEGORYINDEX,
     *                                                IncomeFixedEarnings.AMOUNTINDEX, (byte) 1, (byte) 0);
     *</code>
     *  
     */
    public static boolean updateParamByPerc(Collection<SIMParameter> updater, 
                                            SIMParameter param, byte percentIndex, 
                                            byte categIndex, byte amountIndex,
                                            byte factor, byte period){
           for(SIMParameter currUp : updater){
               Object[] paramData = param.getValue().toArray();
               String paramCateg = (String) paramData[categIndex];
               String currCateg = (String) currUp.getValue().toArray()[categIndex];
              if(paramCateg.equals(currCateg)){
                    double paramAmount =((Double) paramData[amountIndex]);
                    double currAmount = ((Double) currUp.getValue().toArray()[percentIndex]);
                    
                    if(period == 0){//rise or other percentages that don't depend on the month
                        double updatedValue = paramAmount*(1+currAmount/factor);
                        paramData[amountIndex] = updatedValue; 
                    }
                    else{//inflation or percentages that depend on the month
                        double initialValue = paramAmount / (1 + (currAmount/factor)*(period-1));
                        double updatedValue = initialValue*(1+(currAmount/factor)*period);
                        paramData[amountIndex] = updatedValue; 
                    }
                    
                    param.setValue(Arrays.asList(paramData));
                    
                    return true;
                }
            }       
                
        return false;
    }
    
    /**
     * This method creates a PESMState with all the PESMParameters passed 
     * establishing them with their defaultValue.
     * 
     * @param parameters List with the parameters to be initialized.
     * 
     * @returns A <code>PESMState</code> with all the PESMParameters set with the default values.  
     * 
     */
    protected static PESMState newDefaultPESMState(Class[] parameters){
        PESMState mySt = new PESMState();
        
        Reflections reflections = new Reflections(mySt.getClass().getPackage().getName());

        for(Class currParam : parameters){
            try {
               Set<SIMParameter> mySet = new HashSet<>();
               PESMParameter myParam = ((Class<? extends PESMParameter>) currParam).newInstance();
               mySet.add(myParam);
               mySt.setParameter(myParam.getName(), mySet);
               
            } catch (Exception ex) {                 
                 throw new RuntimeException(ex);                 
            }
        }
        
        return mySt;
    }
    
    
}
