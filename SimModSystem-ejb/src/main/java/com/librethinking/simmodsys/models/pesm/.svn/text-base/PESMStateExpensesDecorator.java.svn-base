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
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseMonthly;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseDesiredExpenses;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseInflation;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesMonthly;
import com.librethinking.simmodsys.models.pesm.parameters.Period;
import com.librethinking.simmodsys.models.pesm.parameters.AssetAvDesExp;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseFixed;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseVariable;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseYearly;
import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.SIMRulesApplier;
import java.util.*;

/**
 * This class is a Decorator to a PESMState to implement the rules associated 
 * to the Expenses parameters.
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMStateExpensesDecorator extends PESMStateDecorator{
    
    /** This should be the name of the field in ExpenseFixed and ExpenseVariable to get category */
    private final String CATEGINDLABEL = "CATEGORYINDEX"; 
    
    /** This should be the name of the field in ExpenseFixed and ExpenseVariable to get amount */
    private final String AMNTINDLABEL = "AMOUNTINDEX"; 
    
    public PESMStateExpensesDecorator (PESMState state){
        internalState = state;
    }

   /**
     * This method applies the rule number E.1, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * This method uses reflection to update the expenses. Any of the exceptions
     * thrown are relative to the use of the CATEGORYINDEX and AMOUNTINDEX fields
     * of the classes ExpenseFixed or ExpenseVariable.
     * 
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * 
     * (Monthly Inflation) = EXPENSE.INFLATION/12
     * 
     *   FOR EACH EXPENSE.FIXEDEXP DO {
     *   HasCategory = false;
     *   FOR EACH (Monthly Inflation) DO {
     *       IF (EXPENSE.FIXEDEXP[category] = (Monthly Inflation)[category]){ 
     *           EXPENSE.FIXEDEXP[value] =EXPENSE.FIXEDEXP[value]*(1+(Monthly Inflation)[value])
     *           HasCategory=true;
     *       }
     *       }
     *       IF (HasCategory = false) {EXPENSE.FIXEDEXP*(DefaultAnnualInflation/12)}
     *   (the same for EXPENSE.VAREARN and EXPENSE.DESIREDEXP)
     *   }
     * 
     * NOTE: The above formula is only a guidance but it is incorrect. To accomplish
     * the annual inflation, each month should increment the expense (annualInflation/12)*currMonth
     * against the expense at the beginning of the year.
     * 
     */
    protected void executeRuleE1() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        Collection<SIMParameter> fixed = internalState.getParameter(ExpenseFixed.NAME);
        Collection<SIMParameter> var = internalState.getParameter(ExpenseVariable.NAME);
        Collection<SIMParameter> des = internalState.getParameter(ExpenseDesiredExpenses.NAME);
        Collection<SIMParameter> inflation = internalState.getParameter(ExpenseInflation.NAME);
        Collection<SIMParameter> all = new HashSet<SIMParameter>();
        all.addAll(fixed);
        all.addAll(var);
        all.addAll(des);
        
        boolean hasCateg = false;
        
        for(SIMParameter currParm : all){
            hasCateg = PESMUtil.updateParamByPerc(inflation, currParm, ExpenseInflation.CURRVALINDEX,
                    (byte) currParm.getClass().getField(CATEGINDLABEL).getByte(currParm), 
                    (byte) currParm.getClass().getField(AMNTINDLABEL).getByte(currParm), (byte) 12, 
                    (byte)internalState.getPeriod());
            if(!hasCateg){
                Object[] values = currParm.getValue().toArray();
                byte currAmInd = currParm.getClass().getField(AMNTINDLABEL).getByte(currParm);
                double defaultInflation = (Double) ExpenseInflation.DEFAULTVALUE[ExpenseInflation.CURRVALINDEX];
                double initialValue = ((Double) values[currAmInd]) / (1 + (defaultInflation/12)*(internalState.getPeriod()-1));
                double updatedValue = initialValue*(1 + (defaultInflation/12)*(internalState.getPeriod()));
                values[currAmInd] = updatedValue;
                
                currParm.setValue(Arrays.asList(values));
            }
        }
    }
    
    /**
     * This method applies the rule number E.2, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * (Monthly Expenses) = SumAll(EXPENSE.FIXEDEXP) + SumAll(Variator*EXPENSE.VAREXP[earning]) + (Monthly Liabilities Expenses)
     */
    protected void executeRuleE2(){
        double monthExp= 0.0;
        Collection<SIMParameter> paramCol = internalState.getParameter(ExpenseFixed.NAME);
       //fixed
        for(SIMParameter currParam : paramCol){
            monthExp+= ((ExpenseFixed) currParam).getAmount();
        }
        //variable
        paramCol = internalState.getParameter(ExpenseVariable.NAME);
        for(SIMParameter currParam : paramCol){
            ExpenseVariable currParam2 = (ExpenseVariable) currParam;
            
            monthExp+= PESMUtil.variator(currParam2.getPercentage())*currParam2.getAmount();
        }
        //liabilities
        paramCol = internalState.getParameter(LiabilitiesMonthly.NAME);
        for(SIMParameter currParam : paramCol){
            monthExp+= ((LiabilitiesMonthly) currParam).getAmount();
        }
        
        ExpenseMonthly expParam = new ExpenseMonthly();
        expParam.setAmount(monthExp);
        
        internalState.setParameter(expParam.getName(), PESMUtil.paramWrapper(expParam));
    }
    
    /**
     * This method applies the rule number E.3, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * IF(PERIOD.MONTH%12 = 0){
     *      Desired[]
     *      IF((Desired expenses av. Amount)>0){
     *      (Expending amount) = (Desired expenses av.Amount)
     *      FOR EACH (ORDERED BY PRIORITY) EXPENSE.DESIREDEXP DO{
     *          IF(EXPENSE.DESIREDEXP[value]lessThan or equalTo (Expending Amount)){ 
     *               desired.add(EXPENSE.DESIREDEXP[title])
     *               RemoveTheAddedExpense.
     *               (Expending Amount) = (Expending Amount) - EXPENSE.DESIREDEXP[value]
     *           } 
     *       }
     *       ASSET.AMOUNT = ASSET.AMOUNT – ((Desired expenses av. Amount) – (Expending Amount))
     *       }
     *   }
     * NOTE: Validation of month is managed by <code>PESMModel</code>.
     * @see {@link PESMModel}
     */
    protected void executeRuleE3(){        

        Collection<SIMParameter> temp = internalState.getParameter(ExpenseDesiredExpenses.NAME);
        Set<SIMParameter> desired = new TreeSet<SIMParameter>();
        for(SIMParameter currExp : temp){
            desired.add(currExp);
        }
        List<ExpenseDesiredExpenses> desiredSelected = new ArrayList<ExpenseDesiredExpenses>();

        double available = PESMUtil.getUniqueParam(this, new AssetAvDesExp()).getAmount();
        double expending = available;
        //double expended = 0.0;//TODO: clean code

        //Collections.sort(desired);  //Supossedly desired is already ordered

        for(SIMParameter currExp : desired){
            if(((ExpenseDesiredExpenses) currExp).getAmount() <= available){
                desiredSelected.add( (ExpenseDesiredExpenses) currExp);
                expending = expending - ((ExpenseDesiredExpenses)currExp).getAmount();
            }
        }

        //removing selected expenses from desired expenses
        for(ExpenseDesiredExpenses currExp : desiredSelected){
            desired.remove(currExp);
        }


        AssetAmount assetAmount = PESMUtil.getUniqueParam(this, new AssetAmount());
        assetAmount.setAmount(assetAmount.getAmount() - (available - expending));

        if(desired.isEmpty()){
            desired.add(new ExpenseDesiredExpenses());
        }
        internalState.setParameter(ExpenseDesiredExpenses.NAME, desired);       
        
    }
    
    /**
     * This method applies the rule number E.4, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * IF(PERIOD.MONTH%12=1) {
     *   FOR EACH EXPENSE.INFLATION DO{
     *       EXPENSE.INFLATION[category-value] = Variator(EXPENSE.INFLATION[minvalue], EXPENSE.INFLATION[maxvalue])
     *   }
     *  }
     * NOTE: Validation of month is managed by <code>PESMModel</code>.
     * @see {@link PESMModel}
     */
    protected void executeRuleE4(){

        Collection<SIMParameter> inflation = internalState.getParameter(ExpenseInflation.NAME);
        for(SIMParameter currInfl: inflation){
            ExpenseInflation tempInfl = ((ExpenseInflation)currInfl);
            double newInfl = PESMUtil.variator(tempInfl.getMinPercentage(), tempInfl.getMaxPercentage());
            tempInfl.setCurrentValue(newInfl);
        }        
        
    }
    
     /**
     * This method applies the rule number E.5, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * IF (PERIOD.MONTH%12!=1){
     *   (Year Total Expense) = (Year Total Expense) + (Monthly Expense)
     *   }
     *   ELSE { (Year Total Expense) = 0}
     */
    protected void executeRuleE5(){
       byte period = (byte) (PESMUtil.getUniqueParam(this, new Period())).getMonth();
        ExpenseYearly expYear = PESMUtil.getUniqueParam(this, new ExpenseYearly());
        if(period%12 !=1){            
            ExpenseMonthly expMonth = PESMUtil.getUniqueParam(this, new ExpenseMonthly());
            
            expYear.setAmount(expYear.getAmount() + expMonth.getAmount());
        }
        else{
            expYear.setAmount(0.0);
        }  
    }
    
    
    
}
    