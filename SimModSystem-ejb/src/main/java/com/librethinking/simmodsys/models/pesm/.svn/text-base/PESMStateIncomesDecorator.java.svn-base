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

import com.librethinking.simmodsys.models.pesm.parameters.IncomeMonthly;
import com.librethinking.simmodsys.models.pesm.parameters.AssetMonthAPR;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeMonthlyWI;
import com.librethinking.simmodsys.models.pesm.parameters.Period;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeAnnualRise;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeFixedEarnings;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeYearly;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeVariableEarnings;
import com.librethinking.simmodsys.SIMParameter;
import java.util.Collection;

/**
 * This class is a Decorator to a PESMState to implement the rules associated 
 * to the Incomes parameters.
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMStateIncomesDecorator extends PESMStateDecorator{
    
    
    public PESMStateIncomesDecorator (PESMState state){
        internalState = state;
    }

    /**
     * This method applies the rule number I.1, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * (Monthly Income WI) = SumAll(INCOME.FIXEDEARN) + SumAll(Variator*INCOME.VAREARN[earning])
     */
    protected void executeRuleI1(){
        double monthIncWI = 0.0;
        Collection<SIMParameter> paramCol = internalState.getParameter(IncomeFixedEarnings.NAME);
        
        for(SIMParameter currParam : paramCol){
            monthIncWI+= ((IncomeFixedEarnings) currParam).getAmount();
        }
        
        paramCol = internalState.getParameter(IncomeVariableEarnings.NAME);
        for(SIMParameter currParam : paramCol){
            IncomeVariableEarnings currParam2 = (IncomeVariableEarnings) currParam;
            
            monthIncWI+= PESMUtil.variator(currParam2.getPercentage())*currParam2.getAmount();
        }
        
        IncomeMonthlyWI incParam = new IncomeMonthlyWI();
        incParam.setAmount(monthIncWI);
        
        internalState.setParameter(incParam.getName(), PESMUtil.paramWrapper(incParam));

    }
    
    /**
     * This method applies the rule number I.2, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * IF(PERIOD.MONTH%12 = 0){
     *   FOR EACH INCOME.FIXEDEARN DO {
     *   HasCategory = false;
     *   FOR EACH INCOME.ANNUALRISE DO {
     *       IF (INCOME.FIXEDEARN[category] = INCOME.ANNUALRISE[category]){ 
     *           INCOME.FIXEDEARN[value] =INCOME.FIXEDEARN[value]*(1+INCOME.ANNUALRISE[value])
     *           HasCategory=true;
     *       }
     *       }
     *       IF (HasCategory = false) {INCOME.FIXEDEARN*DefaultAnnualRise}
     *   (the same for INCOME.VAREARN)
     *   }
     * NOTE: Validation of month is managed by <code>PESMModel</code>.
     * @see {@link PESMModel}
     */
    protected void executeRuleI2(){
        Collection<SIMParameter> fixeds = internalState.getParameter(IncomeFixedEarnings.NAME);
        Collection<SIMParameter> rises = internalState.getParameter(IncomeAnnualRise.NAME);
        boolean hasCateg = false;
        for(SIMParameter itParam: fixeds){
            hasCateg = PESMUtil.updateParamByPerc(rises, itParam, 
                                                    IncomeAnnualRise.CURRVALINDEX, 
                                                    IncomeFixedEarnings.CATEGORYINDEX,
                                                    IncomeFixedEarnings.AMOUNTINDEX, (byte) 1, (byte)0);
            if(!hasCateg){                    
                IncomeFixedEarnings currParam = ((IncomeFixedEarnings) itParam);
                double defaultRise = (Double) IncomeAnnualRise.DEFAULTVALUE[IncomeAnnualRise.CURRVALINDEX];
                currParam.setAmount(currParam.getAmount()*(1+defaultRise));
            }
        }

        Collection<SIMParameter> vars = internalState.getParameter(IncomeVariableEarnings.NAME);
        for(SIMParameter itParam: vars){
            hasCateg = PESMUtil.updateParamByPerc(rises, itParam, 
                                                    IncomeAnnualRise.CURRVALINDEX, 
                                                    IncomeVariableEarnings.CATEGORYINDEX,
                                                    IncomeVariableEarnings.AMOUNTINDEX, (byte) 1, (byte)0);
            if(!hasCateg){
                IncomeVariableEarnings currParam = ((IncomeVariableEarnings) itParam);
                double defaultRise = (Double) IncomeAnnualRise.DEFAULTVALUE[IncomeAnnualRise.CURRVALINDEX];
                currParam.setAmount(currParam.getAmount()*(1+defaultRise));
            }
        }
    }    
    
    
    /**
     * This method applies the rule number I.3, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * IF(PERIOD.MONTH/12 = 1) {
     *   FOR EACH INCOME.ANNUALRISE DO{
     *       INCOME.ANNUALRISE[category-value] = Variator(INCOME.ANNUALRISE[minvalue], INCOME.ANNUALRISE[maxvalue])
     *   }
     * }
     * NOTE: Validation of month is managed by <code>PESMModel</code>.
     * @see {@link PESMModel}
     */
    protected void executeRuleI3(){
        Collection<SIMParameter> rises = internalState.getParameter(IncomeAnnualRise.NAME);
        for(SIMParameter currRise: rises){
            IncomeAnnualRise tempRise = ((IncomeAnnualRise)currRise);
            double rise = PESMUtil.variator(tempRise.getMinPercentage(), tempRise.getMaxPercentage());
            tempRise.setCurrentValue(rise);
        }
    }
    
     /**
     * This method applies the rule number I.4, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * IF (PERIOD.MONTH%12 !=1){
     *   (Year Total Income) = (Year Total Income) + (Monthly Income)
     * }
     *   ELSE { (Year Total Income) = 0}
     * }
     */
    protected void executeRuleI4(){
        byte period = (byte) (PESMUtil.getUniqueParam(this, new Period())).getMonth();
        IncomeYearly incYear = PESMUtil.getUniqueParam(this, new IncomeYearly());
        if(period%12 !=1){            
            IncomeMonthly incMonth = PESMUtil.getUniqueParam(this, new IncomeMonthly());
            
            incYear.setAmount(incYear.getAmount() + incMonth.getAmount());
        }
        else{
            incYear.setAmount(0.0);
        }
    }
    
    /**
     * This method applies the rule number I.5, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * (Monthly Income) = (Monthly Income WI) + (Interests Earned)
     */
    protected void executeRuleI5(){
        IncomeMonthlyWI incMonthWI = PESMUtil.getUniqueParam(this, new IncomeMonthlyWI());
        IncomeMonthly incMonth = PESMUtil.getUniqueParam(this, new IncomeMonthly());
        AssetMonthAPR intEarn = PESMUtil.getUniqueParam(this, new AssetMonthAPR());
        
        incMonth.setAmount(incMonthWI.getAmount() + intEarn.getAmount());
    }
    
    
}
