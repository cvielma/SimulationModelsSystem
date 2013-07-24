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

import com.librethinking.simmodsys.models.pesm.parameters.AssetAmount;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesAmount;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseMonthly;
import com.librethinking.simmodsys.models.pesm.parameters.AssetMonthAPR;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeMonthlyWI;
import com.librethinking.simmodsys.models.pesm.parameters.AssetAvDesExp;
import com.librethinking.simmodsys.models.pesm.parameters.AssetMinSavings;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeYearly;
import com.librethinking.simmodsys.models.pesm.parameters.AssetAPR;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseYearly;
import com.librethinking.simmodsys.SIMParameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * This class is a Decorator to a PESMState to implement the rules associated 
 * to the Assets parameters.
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMStateAssetsDecorator extends PESMStateDecorator {
      
    
    /** String that represents the rule associated to I.1 (Monthly Income)*/
    private String RULEI1 ="I1";
    
    /** String that represents the rule associated to E.1 (Monthly Expenses)*/
    private String RULEE1 ="E1";
    
    
    
  /********************** BEGIN METHODS *******************************/  
    
    
    
    /**
     * Associates the State that will be decorated.
     * 
     * @param state State to be decorated.
     */
    public PESMStateAssetsDecorator (PESMState state){
        internalState = state;
    }  
    
    /**
     * This method applies the rule number A.1, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * ASSET.AMOUNT = ASSET.AMOUNT + (Monthly Incomes I.1) – (Monthly Expenses E.1) 
     * 
     */
    protected void executeRuleA1(){
        AssetAmount currAmountParam = PESMUtil.getUniqueParam(this, new AssetAmount());
        double monthInc = ((IncomeMonthlyWI) (internalState.getParameter(IncomeMonthlyWI.NAME)).toArray()[0]).getAmount();
        double monthExp = ((ExpenseMonthly) (internalState.getParameter(ExpenseMonthly.NAME)).toArray()[0]).getAmount();

        double currAmount = currAmountParam.getAmount();        
        
        currAmount += monthInc - monthExp;
        
        currAmountParam.setAmount(currAmount);        
    }
    
    /**
     * This method applies the rule number A.2, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * (Interests Earned) = ASSET.AMOUNT*(APR/12)
     */
    protected void executeRuleA2(){
        AssetMonthAPR intEarned = new AssetMonthAPR();        
        double currAmount = ((AssetAmount) (internalState.getParameter(AssetAmount.NAME)).toArray()[0]).getAmount();
        double currAPR = ((AssetAPR) (internalState.getParameter(AssetAPR.NAME)).toArray()[0]).getPercentage();
        
        intEarned.setAmount(currAmount*(currAPR/12));
        
        Set<SIMParameter> interestsEarned = new HashSet<SIMParameter>();
        interestsEarned.add(intEarned);
        
        internalState.setParameter(intEarned.getName(), interestsEarned);        
    }
    
    /**
     * This method applies the rule number A.3, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * ASSET.AMOUNT= ASSET.AMOUNT + (Interests Earned)
     */
    protected void executeRuleA3(){
        AssetAmount currAmountParam = (AssetAmount) (internalState.getParameter(AssetAmount.NAME)).toArray()[0];
        AssetMonthAPR currIntEarned = (AssetMonthAPR) (internalState.getParameter(AssetMonthAPR.NAME)).toArray()[0];
        
        currAmountParam.setAmount(currAmountParam.getAmount()+currIntEarned.getAmount());
    }
    
    
    /**
     * This method applies the rule number A.4, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     *  IF( PERIOD.MONTH%12 = 0 AND 
     *        (year total incomes)*ASSET.MINSAVING lessThan 
     *        ((year total incomes) – (year total expenses))){
     *    
     *     (Desired expenses av. Amount) =  ((year total incomes) – (year total expenses)) – (year total incomes)*ASSET.MINSAVING
     *   }
     * NOTE: Validation of month is managed by <code>PESMModel</code>.
     * @see {@link PESMModel}
     */
    protected void executeRuleA4(){
     // Period month = PESMUtil.getUniqueParam(this, new Period());
      IncomeYearly incYear = PESMUtil.getUniqueParam(this, new IncomeYearly());
      ExpenseYearly expYear = PESMUtil.getUniqueParam(this, new ExpenseYearly());
      AssetMinSavings minSav = PESMUtil.getUniqueParam(this, new AssetMinSavings());
      
      double desiredSaved = incYear.getAmount()*minSav.getPercentage();
      double reallySaved = incYear.getAmount() - expYear.getAmount();
      
      //month.getMonth()%12 == 0 && 
      if(desiredSaved <= reallySaved){
          AssetAvDesExp desExpAv = PESMUtil.getUniqueParam(this, new AssetAvDesExp());
          desExpAv.setAmount(reallySaved-desiredSaved);
      }
    }
    
    /**
     * This method applies the rule number A.5, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     *  IF (ASSET.AMOUNT > LIABILITIES.AMOUNT) {
     *     ASSET.AMOUNT = ASSET.AMOUNT – LIABILITIES.AMOUNT
     *    LIABILITIES.AMOUNT=0
     *  }
     */
    protected void executeRuleA5(){
        AssetAmount currAmount = PESMUtil.getUniqueParam(this, new AssetAmount());
        LiabilitiesAmount currLia = PESMUtil.getUniqueParam(this, new LiabilitiesAmount());
        
        //TODO: Test bound case
        if(currAmount.getAmount() >= currLia.getAmount()){
            double amount = currAmount.getAmount();
            
            currAmount.setAmount(amount - currLia.getAmount());
            currLia.setAmount(0);
            
        }
    }    
    
    
    
    
    
    
   
   
}
