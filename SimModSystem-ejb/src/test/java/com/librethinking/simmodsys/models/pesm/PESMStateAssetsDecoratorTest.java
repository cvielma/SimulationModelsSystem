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
import com.librethinking.simmodsys.models.pesm.PESMStateAssetsDecorator;
import com.librethinking.simmodsys.SIMParameter;
import org.junit.*;
import static org.junit.Assert.*;
import com.librethinking.simmodsys.models.pesm.PESMState;
import java.util.HashSet;
import java.util.Set;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for PESMStateASsetsDecorator
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMStateAssetsDecoratorTest {
    
    static PESMState testState;
    static PESMState testState2;
    String ASSETAMOUNT = "ASSET.AMOUNT";
    String INCOMEMONTHLYWI = "INCOME.MONTHLYWI";
    String EXPENSEMONTHLY = "EXPENSE.MONTHLY";
    String ASSETAPR = "ASSET.APR";
    String ASSETMINSAVINGS = "ASSET.MINSAVING";
    String INCOMEYEARLY = "INCOME.YEARLY";
    String EXPENSEYEARLY = "EXPENSE.YEARLY";
    String PERIODMONTH = "PERIOD.MONTHS";
    String DESIREDAV = "ASSET.AVDESEXP";
    String LIABILITIESAMOUNT ="LIABILITIES.AMOUNT";
    
    public PESMStateAssetsDecoratorTest() {
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

            myparam = new HashSet<SIMParameter>();
            myparam.add(new IncomeAnnualRise()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new IncomeFixedEarnings()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new IncomeVariableEarnings()); params.add(myparam); 

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
            ((IncomeMonthlyWI) currParam).setAmount(500);
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
            ((IncomeYearly) currParam).setAmount(5000);
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

            myparam = new HashSet<SIMParameter>();
            myparam.add(new IncomeAnnualRise()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new IncomeFixedEarnings()); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            myparam.add(new IncomeVariableEarnings()); params.add(myparam); 

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
            ((IncomeMonthlyWI) currParam).setAmount(500);
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
            ((IncomeYearly) currParam).setAmount(5000);
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
     * Test of applyRuleTo method, of class PESMStateAssetsDecorator.
     */
    @Test
    public void testApplyRuleTo() {
        System.out.println("applyRuleTo");
        PESMState subject = new PESMState();
        String rule = "A1";
        PESMStateAssetsDecorator instance = new PESMStateAssetsDecorator(testState);
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
     * Test of applyRule method for rule A.1
     */
    @Test
    public void testApplyRuleA1() {
        System.out.println("applyRule A.1");
        String rule = "A1";
        PESMStateAssetsDecorator instance = new PESMStateAssetsDecorator(testState);
        
        double oldAsset = (Double)((SIMParameter)(instance.getParameter(ASSETAMOUNT)).toArray()[0]).getValue().toArray()[0];
        double monthIncomes = (Double)((SIMParameter)(instance.getParameter(INCOMEMONTHLYWI)).toArray()[0]).getValue().toArray()[0]; 
        double monthExpenses = (Double) ((SIMParameter)(instance.getParameter(EXPENSEMONTHLY)).toArray()[0]).getValue().toArray()[0]; 
        
                
        instance.applyRule(rule);
        
        double newAsset = (Double) ((SIMParameter)(instance.getParameter(ASSETAMOUNT)).toArray()[0]).getValue().toArray()[0];
        
        if(newAsset == oldAsset+monthIncomes-monthExpenses){
            assertTrue("Calculation of "+ASSETAMOUNT+" is the expected.", true);
        }       
        else{
        // TODO review the generated test code and remove the default call to fail.
        fail("Failed to calculate "+ASSETAMOUNT+".");
        }
    }
    
    
    /**
     * Test of applyRule method for rule A2 and A.3
     */
    @Test
    public void testApplyRuleA2A3() {
        System.out.println("applyRule A2 and A.3");
        String rule = "A3";
        String rule1 = "A2";
        PESMStateAssetsDecorator instance = new PESMStateAssetsDecorator(testState);
        
        double oldAsset = (Double) ((SIMParameter)(instance.getParameter(ASSETAMOUNT)).toArray()[0]).getValue().toArray()[0];
        double assetAPR = (Double) ((SIMParameter)(instance.getParameter(ASSETAPR)).toArray()[0]).getValue().toArray()[0]; 
        
        instance.applyRule(rule1);
        instance.applyRule(rule);
        
        double newAsset = (Double) ((SIMParameter)(instance.getParameter(ASSETAMOUNT)).toArray()[0]).getValue().toArray()[0];
        
        if(newAsset == oldAsset+oldAsset*(assetAPR/12)){
            assertTrue("Calculation of "+ASSETAMOUNT+" is the expected.", true);
        }       
        else{
        // TODO review the generated test code and remove the default call to fail.
        fail("Failed to calculate "+ASSETAMOUNT+".");
        }
    }
    
    /**
     * Test of applyRule method for rule A.4
     */
    @Test
    public void testApplyRuleA4() {
        System.out.println("applyRule A.4");
        String rule = "A4";
        PESMStateAssetsDecorator instance = new PESMStateAssetsDecorator(testState);
        
        double minSavings =(Double) ((SIMParameter)(instance.getParameter(ASSETMINSAVINGS)).toArray()[0]).getValue().toArray()[0]; 
        double yearlyIncome = (Double) ((SIMParameter)(instance.getParameter(INCOMEYEARLY)).toArray()[0]).getValue().toArray()[0];
        double yearlyExpense = (Double) ((SIMParameter)(instance.getParameter(EXPENSEYEARLY)).toArray()[0]).getValue().toArray()[0]; 
        int period = (Integer) ((SIMParameter)(instance.getParameter(PERIODMONTH)).toArray()[0]).getValue().toArray()[0];
        double savedMin = (yearlyIncome - yearlyExpense) - (yearlyIncome*minSavings);
        double desiredAvailable = period % 12 == 0 && savedMin>0? savedMin : 0;
        
        instance.applyRule(rule);
        
        double newDesiredAvailable = (Double) ((SIMParameter)(instance.getParameter(DESIREDAV)).toArray()[0]).getValue().toArray()[0]; 
        
        if(newDesiredAvailable == desiredAvailable){
            assertTrue("Calculation of "+DESIREDAV+" is the expected, when period is"+period, true);
        }
        else{
        
        // TODO review the generated test code and remove the default call to fail.
        fail("Failed to calculate "+DESIREDAV+".");
        }
    }
    
    
   /**
     * Test of applyRule method for rule A.5
     */
    @Test
    public void testApplyRuleA5() {
        System.out.println("applyRule A.5");
        String rule = "A5";
        PESMStateAssetsDecorator instance = new PESMStateAssetsDecorator(testState);
        
        double oldAssets =(Double) ((SIMParameter)(instance.getParameter(ASSETAMOUNT)).toArray()[0]).getValue().toArray()[0];
        double oldLiabilities = (Double) ((SIMParameter)(instance.getParameter(LIABILITIESAMOUNT)).toArray()[0]).getValue().toArray()[0];
        double expectedAssets = -1.0; 
        double expectedLiab = -1.0;
        if(oldAssets > oldLiabilities){
            expectedAssets = oldAssets - oldLiabilities;
            expectedLiab = 0.0;
        }
        
        instance.applyRule(rule);
        
        if(expectedAssets == -1.0 || expectedLiab == -1.0){
            expectedAssets = oldAssets;
            expectedLiab = oldLiabilities;
        }
        
        double newAssets = (Double) ((SIMParameter)(instance.getParameter(ASSETAMOUNT)).toArray()[0]).getValue().toArray()[0];
        double newLiabilities = (Double) ((SIMParameter)(instance.getParameter(LIABILITIESAMOUNT)).toArray()[0]).getValue().toArray()[0];
        
        if(newAssets == expectedAssets && newLiabilities == expectedLiab){
            assertTrue("Calculation of "+ASSETAMOUNT+" and "+LIABILITIESAMOUNT+" is the expected.", true);
        }       
        else{
        // TODO review the generated test code and remove the default call to fail.
        fail("Failed to calculate "+ASSETAMOUNT+" ("+newAssets+", expected: "+expectedAssets+") and "+LIABILITIESAMOUNT+" ("+newLiabilities+", expected: "+expectedLiab+").");
        }
    }
    
    /**
     * Test of applyRule method for fake rule
     */
    @Test
    public void testFakeRule() {
        System.out.println("applyFakeRule A.5");
        String rule = "FakeA5";
        PESMStateAssetsDecorator instance = new PESMStateAssetsDecorator(testState);
        
        try{instance.applyRule(rule);}
        catch (UnsupportedOperationException e){
            assertTrue("Correct! Fake rules should return Unsupported Exception.", true);
        }       
    }
    
    /**
     * Test of applyRules method, of class PESMStateAssetsDecorator.
     * TODO: Complete this test case
     */
    @Test
    public void testApplyRules() {
        System.out.println("applyRules");
        String[] rules = new String[5];
        rules[0]= "A1";
        rules[1]= "A2";
        rules[2]= "A3";
        rules[3]= "A4";
        rules[4]= "A5";
        PESMStateAssetsDecorator instance1 =  new PESMStateAssetsDecorator(testState);
        PESMStateAssetsDecorator instance2 =  new PESMStateAssetsDecorator(testState2);
        
        instance1.applyRules(rules);
        instance2.applyRule("A1");
        instance2.applyRule("A2");
        instance2.applyRule("A3");
        instance2.applyRule("A4");
        instance2.applyRule("A5");
        
        if(instance1.equals(instance2)){
            assertTrue("Rules applied correctly.", true);
        }
        else{
            // TODO review the generated test code and remove the default call to fail.
            fail("Failed applying rules.");
        }
    }
    
    /**
     * Test of applyRules method, of class PESMStateAssetsDecorator.
     * This will show the behavior of applyRules when a given rule is invalid
     */
    @Test
    public void testApplyFakeRules() {
        System.out.println("applyFakeRules");
        String[] rules = new String[4];
        rules[0]= "A1";
        rules[1]= "A3";
        rules[2]= "A4";
        rules[3]= "A5";
        rules[3]= "A6";
        PESMStateAssetsDecorator instance1 =  new PESMStateAssetsDecorator(testState);
               
        try{instance1.applyRules(rules);}
        catch(UnsupportedOperationException e){
            assertTrue("UnsupportedOperationException thrown correclty.",true);
        }       
        
    }
    
    /**
     * Test of equals and hashcode     
     */
    @Test
    public void testEqualsAndHashcode() {
        System.out.println("applyEqualsHashCode");
        String[] rules = new String[5];
        rules[0]= "A1";
        rules[1]= "A2";
        rules[2]= "A3";
        rules[3]= "A4";
        rules[4]= "A5";
        PESMStateAssetsDecorator instance1 =  new PESMStateAssetsDecorator(testState);
        PESMStateAssetsDecorator instance2 =  new PESMStateAssetsDecorator(testState2);
        
        
        assertTrue("Yes, they're equal.", instance1.equals(instance2));
        assertTrue("Hashcode equal too.", instance1.hashCode() == instance2.hashCode());
        
        
        instance1.applyRules(rules);
        instance2.applyRule("A1");
        instance2.applyRule("A2");
        
        assertTrue("They are not equal.", !instance1.equals(instance2));
        assertTrue("Hashcode neither.", instance1.hashCode() != instance2.hashCode());
        
        
        instance2.applyRule("A3");
        instance2.applyRule("A4");
        instance2.applyRule("A5");
        
        assertTrue("Yes, they're equal now.", instance1.equals(instance2));
        assertTrue("Hashcode equal too.", instance1.hashCode() == instance2.hashCode());      
        
    }
    
    /**
     * Test of equals and hashcode  Against state
     */
    @Test
    public void testEqualsAndHashcodeState() {
        System.out.println("applyEqualsHaschCodeState");
        String[] rules = new String[5];
        rules[0]= "A1";
        rules[1]= "A2";
        rules[2]= "A3";
        rules[3]= "A4";
        rules[4]= "A5";
        PESMStateAssetsDecorator instance1 =  new PESMStateAssetsDecorator(testState);
        PESMStateAssetsDecorator instance2 =  new PESMStateAssetsDecorator(testState2);
        
        
        assertTrue("Yes, they're equal.", instance1.equals(testState2));
        assertTrue("Hashcode equal too.", instance1.hashCode() == testState2.hashCode());
        
        
        instance1.applyRules(rules);
        instance2.applyRule("A1");
        instance2.applyRule("A2");
        
        assertTrue("They are not equal.", !instance1.equals(testState2));
        assertTrue("Hashcode neither.", instance1.hashCode() != testState2.hashCode());
        
        
        instance2.applyRule("A3");
        instance2.applyRule("A4");
        instance2.applyRule("A5");
        
        assertTrue("Yes, they're equal now.", instance1.equals(testState2));
        assertTrue("Hashcode equal too.", instance1.hashCode() == testState2.hashCode());      
        
    }
}
