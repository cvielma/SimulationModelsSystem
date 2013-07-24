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
import com.librethinking.simmodsys.models.pesm.PESMStateIncomesDecorator;
import com.librethinking.simmodsys.models.pesm.PESMState;
import com.librethinking.simmodsys.models.pesm.PESMUtil;
import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.exceptions.InvalidParameterException;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;
import java.util.Iterator;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMStateIncomesDecoratorTest {
    
    static PESMState testState;
    static PESMState testState2;
    
    String INCOMEFIXED = "INCOME.FIXEDEARN";
    String INCOMEVAR = "INCOME.VAREARN";
    String INCOMEWI = "INCOME.MONTHLYWI";
    private String INCOMEANNUALRISE = "INCOME.ANNUALRISE";
    private String PERIODMONTH = "PERIOD.MONTHS";
    private String INCOMEYEARLY = "INCOME.YEARLY";
    private String INCOMEMONTHLY = "INCOME.MONTHLY";
    private String ASSETMONTHAPR = "ASSET.MONTHAPR";
    
    
    public PESMStateIncomesDecoratorTest() {
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

            myparam = new HashSet<SIMParameter>();
            myparam.add(new ExpenseDesiredExpenses()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new ExpenseFixed()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new ExpenseInflation()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new ExpenseVariable()); params.add(myparam); 
//AnnualRise
            myparam = new HashSet<SIMParameter>();
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
            ((ExpenseYearly) currParam).setAmount(3000);
            myparam.add(currParam); params.add(myparam);
            
            myparam = new HashSet<SIMParameter>();
            myparam.add(new LiabilitiesMonthly()); params.add(myparam); 
                    
         
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

            myparam = new HashSet<SIMParameter>();
            myparam.add(new ExpenseDesiredExpenses()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new ExpenseFixed()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new ExpenseInflation()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new ExpenseVariable()); params.add(myparam); 
//AnnualRise
            myparam = new HashSet<SIMParameter>();
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
            ((ExpenseYearly) currParam).setAmount(3000);
            myparam.add(currParam); params.add(myparam);
            
            myparam = new HashSet<SIMParameter>();
            myparam.add(new LiabilitiesMonthly()); params.add(myparam);  
            
            
            testState2 = new PESMState();//TODO: initialize correctly
            
            testState2.setParameters(params);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of applyRuleTo method, of class PESMStateIncomesDecorator.
     */
    @Test
    public void testApplyRuleTo() {
        System.out.println("applyRuleTo");
        PESMState subject = new PESMState();
        String rule = "I1";
        PESMStateIncomesDecorator instance = new PESMStateIncomesDecorator(testState);
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
     * Test of applyRule method for rule I.1
     */
    @Test
    public void testApplyRuleI1() throws InvalidParameterException {
        System.out.println("applyRule I.1");
        String rule = "I1";
        PESMStateIncomesDecorator instance = new PESMStateIncomesDecorator(testState);
        PESMStateIncomesDecorator instance2 = new PESMStateIncomesDecorator(testState2);
               
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
            Set<SIMParameter> incomeFix = (instance2.getParameter(INCOMEFIXED));
            Set<SIMParameter> incomeVar = (instance2.getParameter(INCOMEVAR));
            double totalWI1 = 0.0;
            double totalWI2 = 0.0;
            
            for(Iterator i = incomeFix.iterator(); i.hasNext();){
                IncomeFixedEarnings currFix = (IncomeFixedEarnings) i.next();
                totalWI1 += currFix.getAmount();
            }
            
            for(Iterator i = incomeVar.iterator(); i.hasNext();){
                IncomeVariableEarnings currFix = (IncomeVariableEarnings) i.next();
                totalWI2 += currFix.getAmount();
            }
            
            instance.applyRule(rule);
            
            IncomeMonthlyWI finalWI = PESMUtil.getUniqueParam(instance, new IncomeMonthlyWI());
            
            if(finalWI.getAmount() >= totalWI1 && finalWI.getAmount() <= (totalWI2 +totalWI1)){
                assertTrue("Calculation of "+INCOMEWI+" is the expected.", true);
            }       
            else{
            // TODO review the generated test code and remove the default call to fail.
            fail("Failed to calculate "+INCOMEWI+" or updating the model.");
            }
        }
    }
    
      /**
     * Test of applyRule method for rule I.2
     */
    @Test
    public void testApplyRuleI2() {
        System.out.println("applyRule I.2");
        String rule = "I2";
        PESMStateIncomesDecorator instance = new PESMStateIncomesDecorator(testState);
        PESMStateIncomesDecorator instance2 = new PESMStateIncomesDecorator(testState2);
        
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
            Set<SIMParameter> incomeFix = (instance2.getParameter(INCOMEFIXED));
            Set<SIMParameter> incomeVar = (instance2.getParameter(INCOMEVAR));
            Set<SIMParameter> annualRise = instance2.getParameter(INCOMEANNUALRISE);
            int period = (Integer) ((SIMParameter)(instance2.getParameter(PERIODMONTH)).toArray()[0]).getValue().toArray()[0];
            
            if(period%12 ==0){
                //Calculation of fixed income rises
                for(Iterator i = incomeFix.iterator(); i.hasNext();){
                    IncomeFixedEarnings currExp= (IncomeFixedEarnings) i.next();
                    String currExpCat = currExp.getCategory();
                    boolean hasCategory = false;

                    for(Iterator j = annualRise.iterator(); j.hasNext();){
                        IncomeAnnualRise currRise = (IncomeAnnualRise) j.next();
                        String currInflCat = currRise.getCategory();
                        if(currInflCat.equals(currExpCat)){
                            currExp.setAmount(currExp.getAmount()*(1+currRise.getCurrentValue()));
                            hasCategory = true;
                            break;
                        }
                    }
                    if (!hasCategory){
                        double defaultRise = (Double) (((SIMParameter) annualRise.toArray()[0]).getDefaultValue().toArray()[1]);
                        currExp.setAmount(currExp.getAmount()*(1+defaultRise));
                    }
                } 
                
                //Calculation of variable income rises
                for(Iterator i = incomeVar.iterator(); i.hasNext();){
                    IncomeVariableEarnings currExp= (IncomeVariableEarnings) i.next();
                    String currExpCat = currExp.getCategory();
                    boolean hasCategory = false;

                    for(Iterator j = annualRise.iterator(); j.hasNext();){
                        IncomeAnnualRise currRise = (IncomeAnnualRise) j.next();
                        String currInflCat = currRise.getCategory();
                        if(currInflCat.equals(currExpCat)){
                            currExp.setAmount(currExp.getAmount()*(1+currRise.getCurrentValue()));
                            hasCategory = true;
                            break;
                        }
                    }
                    if (!hasCategory){
                        double defaultRise = (Double) (((SIMParameter) annualRise.toArray()[0]).getDefaultValue().toArray()[1]);
                        currExp.setAmount(currExp.getAmount()*(1+defaultRise));
                    }
                } 
            }
            
            instance.applyRule(rule);
            
            if(instance.equals(instance2)){
                assertTrue("Calculation of rises is the expected, and States modified equally.", true);
            }       
            else{
            // TODO review the generated test code and remove the default call to fail.
            fail("Failed to calculate rises.");
            }
        }
    }
    
      /**
     * Test of applyRule method for rule I.3
     */
    @Test
    public void testApplyRuleI3() {
        System.out.println("applyRule I.3");
        String rule = "I3";
        PESMStateIncomesDecorator instance = new PESMStateIncomesDecorator(testState);
        PESMStateIncomesDecorator instance2 = new PESMStateIncomesDecorator(testState2);
        
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
            instance.applyRule(rule);
            Set<SIMParameter> annualRise1 = instance.getParameter(INCOMEANNUALRISE);
            Set<SIMParameter> annualRise2 = instance2.getParameter(INCOMEANNUALRISE);
            int period = (Integer) ((SIMParameter)(instance2.getParameter(PERIODMONTH)).toArray()[0]).getValue().toArray()[0];
            
            if(period%12 ==0){
                for (Iterator i = annualRise2.iterator(); i.hasNext();){
                    IncomeAnnualRise currRise2 = (IncomeAnnualRise) i.next();
                    
                    for(Iterator j = annualRise1.iterator(); j.hasNext();){
                        IncomeAnnualRise currRise1 = (IncomeAnnualRise) j.next();                        
                        
                        if(currRise1.getCategory().equals(currRise2.getCategory())){
                            assertTrue(currRise1.getCurrentValue()>= currRise2.getMinPercentage());
                            assertTrue(currRise1.getCurrentValue()<= currRise2.getMaxPercentage());                            
                        }
                    }
                }
            
            }
            else{
                assertTrue("The period is not 12, but "+period+", so the instances are the same.", instance.equals(instance2));
            }
            
        }
    }
    
      /**
     * Test of applyRule method for rule I.4
     */
    @Test
    public void testApplyRuleI4() {
        System.out.println("applyRule I.4");
        String rule = "I4";
        PESMStateIncomesDecorator instance = new PESMStateIncomesDecorator(testState);
        PESMStateIncomesDecorator instance2 = new PESMStateIncomesDecorator(testState2);
        
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
            IncomeYearly incYearly = (IncomeYearly) (instance2.getParameter(INCOMEYEARLY)).toArray()[0];
            IncomeMonthly incMonthly = (IncomeMonthly) (instance2.getParameter(INCOMEMONTHLY)).toArray()[0];
            int period = (Integer) ((SIMParameter)(instance2.getParameter(PERIODMONTH)).toArray()[0]).getValue().toArray()[0];
            double totalIncome =0.0;

            if(period%12!=1){
                totalIncome = incYearly.getAmount() + incMonthly.getAmount();
            }
            
            instance.applyRule(rule);

            IncomeYearly incYearlyFinal = (IncomeYearly) (instance.getParameter(INCOMEYEARLY)).toArray()[0];
            
            if(incYearlyFinal.getAmount() == totalIncome){
                assertTrue("Calculation of "+INCOMEYEARLY+" is the expected.", true);
            }       
            else{
            // TODO review the generated test code and remove the default call to fail.
            fail("Failed to calculate "+INCOMEYEARLY+".");
            }
        }
    }
    
      /**
     * Test of applyRule method for rule I.5
     */
    @Test
    public void testApplyRuleI5() {
        System.out.println("applyRule I.5");
        String rule = "I5";
        PESMStateIncomesDecorator instance = new PESMStateIncomesDecorator(testState);
        PESMStateIncomesDecorator instance2 = new PESMStateIncomesDecorator(testState2);
        
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
            IncomeMonthlyWI incMonthlyWI = (IncomeMonthlyWI) (instance2.getParameter(INCOMEWI)).toArray()[0];
            AssetMonthAPR assetMonth = (AssetMonthAPR) (instance2.getParameter(ASSETMONTHAPR)).toArray()[0];
            double totalIncome =incMonthlyWI.getAmount() + assetMonth.getAmount();

            instance.applyRule(rule);

            IncomeMonthly incMonthly = (IncomeMonthly) (instance.getParameter(INCOMEMONTHLY)).toArray()[0];            
            
            if(incMonthly.getAmount() == totalIncome){
                assertTrue("Calculation of "+INCOMEMONTHLY+" is the expected.", true);
            }       
            else{
            // TODO review the generated test code and remove the default call to fail.
            fail("Failed to calculate "+INCOMEMONTHLY+".");
            }
        }
    }

   
    /**
     * Test of applyRules method, of class PESMStateExpensesDecorator.
     */
    @Test
    public void testApplyRules() {
        System.out.println("applyRules");
        String[] rules = new String[3];
        //rules[0]= "I1"; //I1 generaties variable results
        rules[0]= "I2"; 
        //rules[2]= "I3";  //I3 generates variable results 
        rules[1]= "I4";
        rules[2]= "I5";
        PESMStateIncomesDecorator instance1 = new PESMStateIncomesDecorator(testState);
        PESMStateIncomesDecorator instance2 = new PESMStateIncomesDecorator(testState2);
        
        instance1.applyRules(rules);
        //instance2.applyRule("I1");
        instance2.applyRule("I2");
        //instance2.applyRule("I3");
        instance2.applyRule("I4");
        instance2.applyRule("I5");
        
        if(instance1.equals(instance2)){
            assertTrue("Rules applied correctly.", true);
        }
        else{
            // TODO review the generated test code and remove the default call to fail.
            fail("Failed applying rules.");
        }
    }
}
