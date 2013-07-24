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

import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesMinPayment;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseInflation;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeMonthlyWI;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseDesiredExpenses;
import com.librethinking.simmodsys.models.pesm.parameters.AssetAPR;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeMonthly;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseFixed;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeYearly;
import com.librethinking.simmodsys.models.pesm.parameters.AssetMinSavings;
import com.librethinking.simmodsys.models.pesm.parameters.AssetAvDesExp;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesAPR;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeVariableEarnings;
import com.librethinking.simmodsys.models.pesm.parameters.Period;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeAnnualRise;
import com.librethinking.simmodsys.models.pesm.parameters.AssetAmount;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseMonthly;
import com.librethinking.simmodsys.models.pesm.parameters.AssetMonthAPR;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesMonthly;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesAmount;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeFixedEarnings;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseVariable;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesDelayFee;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseYearly;
import com.librethinking.simmodsys.models.pesm.PESMStateExpensesDecorator;
import com.librethinking.simmodsys.models.pesm.PESMUtil;
import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.exceptions.InvalidParameterException;
import org.junit.*;
import static org.junit.Assert.*;
import com.librethinking.simmodsys.models.pesm.PESMState;
import java.util.*;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMStateExpensesDecoratorTest {
    
    static PESMState testState;
    static PESMState testState2;
    String ASSETAMOUNT = "ASSET.AMOUNT";
    String INCOMEMONTHLY = "INCOME.MONTHLY";
    String EXPENSEMONTHLY = "EXPENSE.MONTHLY";
    String EXPENSEFIXED = ExpenseFixed.NAME;
    String EXPENSEVAR = ExpenseVariable.NAME;
    String ASSETAPR = "ASSET.APR";
    String ASSETMINSAVINGS = "ASSET.MINSAVING";
    String INCOMEYEARLY = "INCOME.YEARLY";
    String EXPENSEYEARLY = "EXPENSE.YEARLY";
    String PERIODMONTH = Period.NAME;
    String DESIREDAV = "ASSET.AVDESEXP";
    String LIABILITIESAMOUNT ="LIABILITIES.AMOUNT";
    String EXPENSEINFLATION = "EXPENSE.INFLATION";
    private String LIABILITIESMINPAY = "LIABILITIES.MINPAY";
    private String LIABILITIESDELAY ="LIABILITIES.DELAYEDFEE";
    private String EXPENSEDESIRED ="EXPENSE.DESIREDEXP";
    private String ASSETAVDESEXP= "ASSET.AVDESEXP";
    
    public PESMStateExpensesDecoratorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {    
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
            testState = new PESMState();//TODO: initialize correctly
            
            Set<Set<SIMParameter>> params = new HashSet<Set<SIMParameter>>();
            HashSet<SIMParameter> myparam;
            SIMParameter currParam;
                                            
            myparam = new HashSet<SIMParameter>();
            currParam = new AssetAPR();
            ((AssetAPR) currParam).setPercentage(0.15);
            myparam.add(currParam); params.add(myparam);       

            myparam = new HashSet<SIMParameter>();
            currParam = new AssetAmount();
            ((AssetAmount) currParam).setAmount(3000);
            myparam.add(currParam); params.add(myparam);       
            

            myparam = new HashSet<SIMParameter>();
            currParam = new AssetMinSavings();
            ((AssetMinSavings) currParam).setPercentage(0.05);
            myparam.add(currParam); params.add(myparam);                    

//ExpenseDesired            
            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseDesiredExpenses();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria1", "Carro", 100.00, 1}));
            myparam.add(currParam);             
            
            currParam = new ExpenseDesiredExpenses();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria2", "Moto", 10000.00, 2}));
            myparam.add(currParam);             
            
            currParam = new ExpenseDesiredExpenses();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria3", "Viaje", 300.00, 3}));
            myparam.add(currParam); 
            
            params.add(myparam); 
            
//ExpenseInflation
            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseInflation();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria1", 0.1, 0.4, 0.2}));
            myparam.add(currParam); 
            
            currParam = new ExpenseInflation();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria2", 0.05, 0.1, 0.07}));
            myparam.add(currParam);
                        
            params.add(myparam); 

//ExpenseFixed            
            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseFixed();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria1", 100.00}));
            myparam.add(currParam);             
            
            currParam = new ExpenseFixed();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria2", 200.00}));
            myparam.add(currParam);             
            
            currParam = new ExpenseFixed();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria3", 300.00}));
            myparam.add(currParam); 
            
            params.add(myparam); 

//ExpenseVariable
            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseVariable();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria1", 0.1, 110.00}));
            myparam.add(currParam);             
            
            currParam = new ExpenseVariable();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria2", 0.3, 250.00}));
            myparam.add(currParam);             
            
            currParam = new ExpenseVariable();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria3", 0.8, 70.00}));
            myparam.add(currParam); 
            
            params.add(myparam); 

//AnnualRise
            myparam = new HashSet<SIMParameter>();
            currParam = new IncomeAnnualRise();
            
            currParam = new IncomeAnnualRise();
            ((IncomeAnnualRise) currParam).setValue(Arrays.asList(new Object[]{"Categoria1", 0.1, 0.4, 0.2}));
            myparam.add(currParam); 
            
            currParam = new IncomeAnnualRise();
            ((IncomeAnnualRise) currParam).setValue(Arrays.asList(new Object[]{"Categoria2", 0.05, 0.1, 0.07}));
            myparam.add(currParam); 
            
            params.add(myparam);                    
//FixedEarnings
            
            myparam = new HashSet<SIMParameter>();
            currParam = new IncomeFixedEarnings();
            
            ((IncomeFixedEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria1", 200.00}));
            myparam.add(currParam);             
            
            currParam = new IncomeFixedEarnings();
            ((IncomeFixedEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria2", 100.00}));
            myparam.add(currParam);             
            
            currParam = new IncomeFixedEarnings();
            ((IncomeFixedEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria3", 300.00}));
            myparam.add(currParam);             
            
            params.add(myparam); 

//VariableEarnings            
            myparam = new HashSet<SIMParameter>();
            currParam = new IncomeVariableEarnings();
            
            ((IncomeVariableEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria1", 0.1, 200.00}));
            myparam.add(currParam);             
            
            currParam = new IncomeVariableEarnings();
            ((IncomeVariableEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria2", 0.8, 50.00}));
            myparam.add(currParam);             
            
            currParam = new IncomeVariableEarnings();
            ((IncomeVariableEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria3", 0.5, 120.00}));
            myparam.add(currParam);             
            
            params.add(myparam); 

//LiabilitiesAPR
            myparam = new HashSet<SIMParameter>();
            myparam.add(new LiabilitiesAPR()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new LiabilitiesAmount();
            ((LiabilitiesAmount) currParam).setAmount(1100);
            myparam.add(currParam); params.add(myparam);                    
            

            myparam = new HashSet<SIMParameter>();
            myparam.add(new LiabilitiesMinPayment()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new LiabilitiesDelayFee()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new Period();
            ((Period) currParam).setMonth(12);
            myparam.add(currParam); params.add(myparam);  
            
            myparam = new HashSet<SIMParameter>();
            currParam = new IncomeMonthlyWI();
            //((IncomeMonthlyWI) currParam).setAmount(500);
            myparam.add(currParam); params.add(myparam);  
            

            myparam = new HashSet<SIMParameter>();
            myparam.add(new IncomeMonthly()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseMonthly();
            ((ExpenseMonthly) currParam).setAmount(800);
            myparam.add(currParam); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new AssetMonthAPR()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new AssetAvDesExp()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new IncomeYearly();
            //((IncomeYearly) currParam).setAmount(5000);
            myparam.add(currParam); params.add(myparam); 
            

            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseYearly();
            //((ExpenseYearly) currParam).setAmount(3000);
            myparam.add(currParam); params.add(myparam);

//LiabilitiesMonthly Expenses            
            myparam = new HashSet<SIMParameter>();
             currParam = new LiabilitiesMonthly();
            ((LiabilitiesMonthly) currParam).setAmount(200);
            myparam.add(currParam); params.add(myparam); 
                    
         
            testState.setParameters(params);
            
            params = new HashSet<Set<SIMParameter>>();
            
            myparam = new HashSet<SIMParameter>();
            currParam = new AssetAPR();
            ((AssetAPR) currParam).setPercentage(0.15);
            myparam.add(currParam); params.add(myparam);       

            myparam = new HashSet<SIMParameter>();
            currParam = new AssetAmount();
            ((AssetAmount) currParam).setAmount(3000);
            myparam.add(currParam); params.add(myparam);       
            

            myparam = new HashSet<SIMParameter>();
            currParam = new AssetMinSavings();
            ((AssetMinSavings) currParam).setPercentage(0.05);
            myparam.add(currParam); params.add(myparam);                    

//ExpenseDesired            
            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseDesiredExpenses();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria1", "Carro", 100.00, 1}));
            myparam.add(currParam);             
            
            currParam = new ExpenseDesiredExpenses();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria2", "Moto", 10000.00, 2}));
            myparam.add(currParam);             
            
            currParam = new ExpenseDesiredExpenses();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria3", "Viaje", 300.00, 3}));
            myparam.add(currParam); 
            
            params.add(myparam); 
            
//ExpenseInflation
            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseInflation();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria1", 0.1, 0.4, 0.2}));
            myparam.add(currParam); 
            
            currParam = new ExpenseInflation();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria2", 0.05, 0.1, 0.07}));
            myparam.add(currParam);
                        
            params.add(myparam); 

//ExpenseFixed            
            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseFixed();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria1", 100.00}));
            myparam.add(currParam);             
            
            currParam = new ExpenseFixed();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria2", 200.00}));
            myparam.add(currParam);             
            
            currParam = new ExpenseFixed();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria3", 300.00}));
            myparam.add(currParam); 
            
            params.add(myparam); 

//ExpenseVariable
            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseVariable();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria1", 0.1, 110.00}));
            myparam.add(currParam);             
            
            currParam = new ExpenseVariable();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria2", 0.3, 250.00}));
            myparam.add(currParam);             
            
            currParam = new ExpenseVariable();
            currParam.setValue(Arrays.asList(new Object[]{"Categoria3", 0.8, 70.00}));
            myparam.add(currParam); 
            
            params.add(myparam); 

//AnnualRise
            myparam = new HashSet<SIMParameter>();
            currParam = new IncomeAnnualRise();
            
            currParam = new IncomeAnnualRise();
            ((IncomeAnnualRise) currParam).setValue(Arrays.asList(new Object[]{"Categoria1", 0.1, 0.4, 0.2}));
            myparam.add(currParam); 
            
            currParam = new IncomeAnnualRise();
            ((IncomeAnnualRise) currParam).setValue(Arrays.asList(new Object[]{"Categoria2", 0.05, 0.1, 0.07}));
            myparam.add(currParam); 
            
            params.add(myparam);                    
//FixedEarnings
            
            myparam = new HashSet<SIMParameter>();
            currParam = new IncomeFixedEarnings();
            
            ((IncomeFixedEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria1", 200.00}));
            myparam.add(currParam);             
            
            currParam = new IncomeFixedEarnings();
            ((IncomeFixedEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria2", 100.00}));
            myparam.add(currParam);             
            
            currParam = new IncomeFixedEarnings();
            ((IncomeFixedEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria3", 300.00}));
            myparam.add(currParam);             
            
            params.add(myparam); 

//VariableEarnings            
            myparam = new HashSet<SIMParameter>();
            currParam = new IncomeVariableEarnings();
            
            ((IncomeVariableEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria1", 0.1, 200.00}));
            myparam.add(currParam);             
            
            currParam = new IncomeVariableEarnings();
            ((IncomeVariableEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria2", 0.8, 50.00}));
            myparam.add(currParam);             
            
            currParam = new IncomeVariableEarnings();
            ((IncomeVariableEarnings) currParam).setValue(Arrays.asList(new Object[]{"Categoria3", 0.5, 120.00}));
            myparam.add(currParam);             
            
            params.add(myparam); 

//LiabilitiesAPR
            myparam = new HashSet<SIMParameter>();
            myparam.add(new LiabilitiesAPR()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new LiabilitiesAmount();
            ((LiabilitiesAmount) currParam).setAmount(1100);
            myparam.add(currParam); params.add(myparam);                    
            

            myparam = new HashSet<SIMParameter>();
            myparam.add(new LiabilitiesMinPayment()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new LiabilitiesDelayFee()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new Period();
            ((Period) currParam).setMonth(12);
            myparam.add(currParam); params.add(myparam);  
            
            myparam = new HashSet<SIMParameter>();
            currParam = new IncomeMonthlyWI();
            //((IncomeMonthlyWI) currParam).setAmount(500);
            myparam.add(currParam); params.add(myparam);  
            

            myparam = new HashSet<SIMParameter>();
            myparam.add(new IncomeMonthly()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseMonthly();
            ((ExpenseMonthly) currParam).setAmount(800);
            myparam.add(currParam); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new AssetMonthAPR()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new AssetAvDesExp()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new IncomeYearly();
            //((IncomeYearly) currParam).setAmount(5000);
            myparam.add(currParam); params.add(myparam); 
            

            myparam = new HashSet<SIMParameter>();
            currParam = new ExpenseYearly();
            //((ExpenseYearly) currParam).setAmount(3000);
            myparam.add(currParam); params.add(myparam);

//LiabilitiesMonthly Expenses            
            myparam = new HashSet<SIMParameter>();
             currParam = new LiabilitiesMonthly();
            ((LiabilitiesMonthly) currParam).setAmount(200);
            myparam.add(currParam); params.add(myparam); 
            
            
            testState2 = new PESMState();//TODO: initialize correctly
            
            testState2.setParameters(params);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of applyRuleTo method, of class PESMStateExpensesDecorator.
     */
    @Test
    public void testApplyRuleTo() {
        System.out.println("applyRuleTo");
        PESMState subject = new PESMState();
        String rule = "E1";
        PESMStateExpensesDecorator instance = new PESMStateExpensesDecorator(testState);
        PESMState result=null;
        try {result = instance.applyRuleTo(subject, rule);}
        catch (UnsupportedOperationException e){
            assertTrue("UnsupportedOperation, it should always use internal state", true);            
        }
        catch (Exception e){fail("Failed to throw UnsupportedOperation, when rule applied to state. ");
        }
        
        if (result!=null){fail("Failed to throw UnsupportedOperation, when rule applied to state. ");}
        
        
        // TODO review the generated test code and remove the default call to fail.
      //  fail("Failed to throw UnsupportedOperation, when rule applied to state. ");
    }

    /**
     * Test of applyRule method for rule E.1
     */
    @Test
    public void testApplyRuleE1() {
        //instance1 and instance2 should be equals
        System.out.println("applyRule E.1");
        String rule = "E1";
        PESMStateExpensesDecorator instance = new PESMStateExpensesDecorator(testState);
        PESMStateExpensesDecorator instance2 = new PESMStateExpensesDecorator(testState2);
        
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
            Set<SIMParameter> oldFExpenses = (instance2.getParameter(EXPENSEFIXED));
            Set<SIMParameter> oldVExpenses = (instance2.getParameter(EXPENSEVAR));
            Set<SIMParameter> oldDExpenses = (instance2.getParameter(EXPENSEDESIRED));
            Set<SIMParameter> inflation = instance2.getParameter(EXPENSEINFLATION);
         
            for(Iterator i = oldFExpenses.iterator(); i.hasNext();){
                ExpenseFixed currExp= (ExpenseFixed) i.next();
                String currExpCat = currExp.getCategory();
                boolean hasCategory = false;
                for(Iterator j = inflation.iterator(); j.hasNext();){
                    ExpenseInflation currInfl = (ExpenseInflation) j.next();
                    String currInflCat = currInfl.getCategory();
                    if(currInflCat.equals(currExpCat)){
                         double initialValue = currExp.getAmount() / (1 + (currInfl.getCurrentValue()/12)*(instance.getPeriod()-1));
                         double updatedValue = initialValue*(1 + (currInfl.getCurrentValue()/12)*(instance.getPeriod()));
                        
                        currExp.setAmount(updatedValue);
                        hasCategory = true;
                        break;
                    }
                }
                if (!hasCategory){
                    double defaultInflation = (Double) (((SIMParameter) inflation.toArray()[0]).getDefaultValue().toArray()[3]);
                    double initialValue = currExp.getAmount() / (1 + (defaultInflation/12)*(instance.getPeriod()-1));
                    double updatedValue = initialValue*(1 + (defaultInflation/12)*(instance.getPeriod()));
                    currExp.setAmount(updatedValue);
                }
            }
            
            for(Iterator i = oldVExpenses.iterator(); i.hasNext();){
                ExpenseVariable currExp= (ExpenseVariable) i.next();
                String currExpCat = currExp.getCategory();
                boolean hasCategory = false;
                
                for(Iterator j = inflation.iterator(); j.hasNext();){
                    ExpenseInflation currInfl = (ExpenseInflation) j.next();
                    String currInflCat = currInfl.getCategory();
                    if(currInflCat.equals(currExpCat)){
                        double initialValue = currExp.getAmount() / (1 + (currInfl.getCurrentValue()/12)*(instance.getPeriod()-1));
                        double updatedValue = initialValue*(1 + (currInfl.getCurrentValue()/12)*(instance.getPeriod()));
                        
                        currExp.setAmount(updatedValue);
                        hasCategory = true;
                        break;
                    }
                }
                if (!hasCategory){
                     double defaultInflation = (Double) (((SIMParameter) inflation.toArray()[0]).getDefaultValue().toArray()[3]);
                    double initialValue = currExp.getAmount() / (1 + (defaultInflation/12)*(instance.getPeriod()-1));
                    double updatedValue = initialValue*(1 + (defaultInflation/12)*(instance.getPeriod()));
                    currExp.setAmount(updatedValue);
                }
            } 
            
            for(Iterator i = oldDExpenses.iterator(); i.hasNext();){
                ExpenseDesiredExpenses currExp= (ExpenseDesiredExpenses) i.next();
                String currExpCat = currExp.getCategory();
                boolean hasCategory = false;
                
                for(Iterator j = inflation.iterator(); j.hasNext();){
                    ExpenseInflation currInfl = (ExpenseInflation) j.next();
                    String currInflCat = currInfl.getCategory();
                    if(currInflCat.equals(currExpCat)){
                        double initialValue = currExp.getAmount() / (1 + (currInfl.getCurrentValue()/12)*(instance.getPeriod()-1));
                        double updatedValue = initialValue*(1 + (currInfl.getCurrentValue()/12)*(instance.getPeriod()));
                        
                        currExp.setAmount(updatedValue);
                        hasCategory = true;
                        break;
                    }
                }
                if (!hasCategory){
                     double defaultInflation = (Double) (((SIMParameter) inflation.toArray()[0]).getDefaultValue().toArray()[3]);
                    double initialValue = currExp.getAmount() / (1 + (defaultInflation/12)*(instance.getPeriod()-1));
                    double updatedValue = initialValue*(1 + (defaultInflation/12)*(instance.getPeriod()));
                    currExp.setAmount(updatedValue);
                }
            }  
        
                
            instance.applyRule(rule);
        
        if(instance.equals(instance2)){
            assertTrue("Calculation of "+EXPENSEFIXED+", and "+EXPENSEVAR+", was successful.", true);
        }
        else{
        // TODO review the generated test code and remove the default call to fail.
        fail("Failed to calculate expenses inflation correctly.");
        }
        }
    }
    
    
    /**
     * Test of applyRule method for rule E.2
     */
    @Test
    public void testApplyRuleE2() {
        System.out.println("applyRule E.2");
        String rule = "E2";
        PESMStateExpensesDecorator instance = new PESMStateExpensesDecorator(testState);
        PESMStateExpensesDecorator instance2 = new PESMStateExpensesDecorator(testState2);
         
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
        Set<SIMParameter> oldFExpenses = (instance2.getParameter(EXPENSEFIXED));
        Set<SIMParameter> oldVExpenses = (instance2.getParameter(EXPENSEVAR));
        LiabilitiesMinPayment liaMinPay = (LiabilitiesMinPayment) (instance2.getParameter(LIABILITIESMINPAY)).toArray()[0];
        LiabilitiesAmount liaAmount = (LiabilitiesAmount) (instance2.getParameter(LIABILITIESAMOUNT)).toArray()[0];
        LiabilitiesDelayFee liaDelay = (LiabilitiesDelayFee) (instance2.getParameter(LIABILITIESDELAY)).toArray()[0];
        AssetAmount assetAmount = (AssetAmount) (instance2.getParameter(ASSETAMOUNT)).toArray()[0];
        
        double maxExpense = 0.0; //with all variable expenses included
        double minExpense = 0.0; // without any variable expense included
        
        //Liabilities expenses
        LiabilitiesMonthly liabMonth = PESMUtil.getUniqueParam(instance2, new LiabilitiesMonthly());
        maxExpense+= liabMonth.getAmount();
        minExpense+= liabMonth.getAmount();
        /**OLD
        if(liaMinPay.getPercentage()*liaAmount.getAmount() > assetAmount.getAmount()){
            minExpense = liaMinPay.getPercentage()*liaAmount.getAmount() + liaDelay.getAmount();
        }
        else{
            minExpense =liaMinPay.getPercentage()*liaAmount.getAmount();
        }
        * */
        
        //Fixed expenses
        for(Iterator i = oldFExpenses.iterator(); i.hasNext();){
            ExpenseFixed currFExp = (ExpenseFixed) i.next();
            minExpense += currFExp.getAmount();
        }
        
        maxExpense+= minExpense;
        //Variable expenses (if all ocurred)
        for(Iterator i = oldVExpenses.iterator(); i.hasNext();){
            ExpenseVariable currFExp = (ExpenseVariable) i.next();
            maxExpense += currFExp.getAmount();
        }
        
               
        instance.applyRule(rule);
        
        double monthlyExp = ((ExpenseMonthly)(instance.getParameter(EXPENSEMONTHLY)).toArray()[0]).getAmount();
        
        if(monthlyExp >= minExpense && monthlyExp <=maxExpense){
            assertTrue("Calculation of "+EXPENSEMONTHLY+" is the expected.", true);
        }       
        else{
        // TODO review the generated test code and remove the default call to fail.
        fail("Failed to calculate "+EXPENSEMONTHLY+".");
        }
        }
    }
    
    /**
     * Test of applyRule method for rule E.3
     */
    @Test
    public void testApplyRuleE3() throws InvalidParameterException {
        System.out.println("applyRule E.3");
        String rule = "E3";
        PESMStateExpensesDecorator instance = new PESMStateExpensesDecorator(testState);
        PESMStateExpensesDecorator instance2 = new PESMStateExpensesDecorator(testState2);
         
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
        
           Set<SIMParameter> oldDExpenses = (instance2.getParameter(EXPENSEDESIRED));
           AssetAvDesExp avDesExp = (AssetAvDesExp) (instance2.getParameter(ASSETAVDESEXP)).toArray()[0];
           AssetAmount assetAmount = (AssetAmount) (instance2.getParameter(ASSETAMOUNT)).toArray()[0];
           int period = (Integer) ((SIMParameter)(instance2.getParameter(PERIODMONTH)).toArray()[0]).getValue().toArray()[0];
           double expending = 0.0;
           Set<SIMParameter> orderedExpenses = new HashSet<SIMParameter>();
           Set<ExpenseDesiredExpenses> selectedExpenses = new HashSet<ExpenseDesiredExpenses>();
           
           if(period%12==0){
               if(avDesExp.getAmount() >0.0){
                   //ordering the expenses
                   int lastPriority = Integer.MAX_VALUE;
                   while(orderedExpenses.size()<oldDExpenses.size()){
                       int currentMaxPrior = 0;
                       SIMParameter currentMax = null;
                       for(Iterator i= oldDExpenses.iterator(); i.hasNext();){
                           ExpenseDesiredExpenses currExp = (ExpenseDesiredExpenses) i.next();
                           if(currExp.getPriority() >= currentMaxPrior && currExp.getPriority()<= lastPriority){
                               currentMaxPrior = currExp.getPriority();
                               currentMax = currExp;
                           }
                       }
                       lastPriority = currentMaxPrior;
                       if (currentMax == null){throw new RuntimeException("There is a problem in the code");}
                       else{
                           orderedExpenses.add(currentMax);
                       }                      
                   }
                   
                   expending = avDesExp.getAmount();
                   //Iterating over ordered expenses to select the expenses to be charged
                   for(Iterator i = orderedExpenses.iterator(); i.hasNext();){
                       ExpenseDesiredExpenses currExp = (ExpenseDesiredExpenses) i.next();
                         if(currExp.getAmount() <= expending){ //TODO: Validate negative amounts
                             if(currExp.getAmount()<0.0 || expending <0.0){throw new RuntimeException("There is a serious trouble here with the expending amounts.");}
                             selectedExpenses.add(currExp);
                             expending = expending - currExp.getAmount();
                         }
                   }
                   
                   //Removing expenses selected
                   for (Iterator i = selectedExpenses.iterator(); i.hasNext();){
                       ExpenseDesiredExpenses currExp = (ExpenseDesiredExpenses) i.next();
                       if(orderedExpenses.contains(currExp)){
                           orderedExpenses.remove(currExp);
                       }
                   }
                   
                   //Assigning again the expenses
                   instance2.setParameter(EXPENSEDESIRED, (Set<SIMParameter>)orderedExpenses);
                   //Adjusting amount
                  double finalAsset = assetAmount.getAmount()-(avDesExp.getAmount() - expending);
                  assetAmount.setAmount(finalAsset);
                  Set<SIMParameter> fAsset = new HashSet<SIMParameter>();
                  fAsset.add(assetAmount);
                  instance2.setParameter(ASSETAMOUNT, fAsset);                 
               }
           }
                
        instance.applyRule(rule);
              if(instance.equals(instance2)){
                 assertTrue("Calculation of "+EXPENSEDESIRED+" is the expected.", true);
              }
              else{
        
                // TODO review the generated test code and remove the default call to fail.
                fail("Failed to calculate "+EXPENSEDESIRED+".");
                
              }
        
        }
        
    }
    
    
   /**
     * Test of applyRule method for rule E.4
     */
    @Test
    public void testApplyRuleE4() {
        System.out.println("applyRule E.4");
        String rule = "E4";
        PESMStateExpensesDecorator instance = new PESMStateExpensesDecorator(testState);
        PESMStateExpensesDecorator instance2 = new PESMStateExpensesDecorator(testState2);
        
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
            
            instance.applyRule(rule);
            
            Set<SIMParameter> inflation1 = (instance.getParameter(EXPENSEINFLATION));
            Set<SIMParameter> inflation2 = (instance2.getParameter(EXPENSEINFLATION));
            int period = (Integer) ((SIMParameter)(instance2.getParameter(PERIODMONTH)).toArray()[0]).getValue().toArray()[0];
            
            //THIS CHANGED, NOW IT'S MANAGED BY MODEL INSTEAD OF DECORATOR
            //if(period%12 == 1){
                for (Iterator i = inflation2.iterator(); i.hasNext();){
                    ExpenseInflation currInf2 = (ExpenseInflation) i.next();
                    
                    for(Iterator j = inflation1.iterator(); j.hasNext();){
                        ExpenseInflation currInf1 = (ExpenseInflation) j.next();
                        
                        if(currInf1.getCategory().equals(currInf2.getCategory())){
                            assertTrue(currInf1.getCurrentValue()>= currInf2.getMinPercentage());
                            assertTrue(currInf1.getCurrentValue()<= currInf2.getMaxPercentage());                            
                        }
                    }
                }
            //}
            //else{
              //  assertTrue("The period is not 12, but "+period+", so the instances are the same.",instance.equals(instance2));
            //}      
        
        }
    }
    
     /**
     * Test of applyRule method for rule E.5
     */
    @Test
    public void testApplyRuleE5() {
        System.out.println("applyRule E.5");
        String rule = "E5";
        PESMStateExpensesDecorator instance = new PESMStateExpensesDecorator(testState);
        PESMStateExpensesDecorator instance2 = new PESMStateExpensesDecorator(testState2);
        
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
            ExpenseYearly expYearly = (ExpenseYearly) (instance2.getParameter(EXPENSEYEARLY)).toArray()[0];
            ExpenseMonthly expMonthly = (ExpenseMonthly) (instance2.getParameter(EXPENSEMONTHLY)).toArray()[0];
            int period = (Integer) ((SIMParameter)(instance2.getParameter(PERIODMONTH)).toArray()[0]).getValue().toArray()[0];
            double totalExpense =0.0;

            if(period%12!=1){
                totalExpense = expYearly.getAmount() + expMonthly.getAmount();
            }
            
            instance.applyRule(rule);

            ExpenseYearly expYearlyFinal = (ExpenseYearly) (instance.getParameter(EXPENSEYEARLY)).toArray()[0];
            
            if(expYearlyFinal.getAmount() == totalExpense){
                assertTrue("Calculation of "+EXPENSEYEARLY+" is the expected.", true);
            }       
            else{
            // TODO review the generated test code and remove the default call to fail.
            fail("Failed to calculate "+EXPENSEYEARLY+".");
            }
        }
    }
    
    /**
     * Test of applyRules method, of class PESMStateExpensesDecorator.
     * TODO: Complete this test case
     */
    @Test
    public void testApplyRules() {
        System.out.println("applyRules");
        String[] rules = new String[3];
        rules[0]= "E1";
       // rules[1]= "E2"; this returns variable results
        rules[1]= "E3";
        //rules[3]= "E4"; this returns variable results
        rules[2]= "E5";
        PESMStateExpensesDecorator instance1 =  new PESMStateExpensesDecorator(testState);
        PESMStateExpensesDecorator instance2 =  new PESMStateExpensesDecorator(testState2);
        
        instance1.applyRules(rules);
        instance2.applyRule("E1");
        //instance2.applyRule("E2");
        instance2.applyRule("E3");
        //instance2.applyRule("E4");
        instance2.applyRule("E5");
        
        if(instance1.equals(instance2)){
            assertTrue("Rules applied correctly.", true);
        }
        else{
            // TODO review the generated test code and remove the default call to fail.
            fail("Failed applying rules.");
        }
    }
}
