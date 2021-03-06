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
import com.librethinking.simmodsys.models.pesm.PESMStateLiabilitiesDecorator;
import com.librethinking.simmodsys.models.pesm.PESMState;
import com.librethinking.simmodsys.SIMParameter;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMStateLiabilitiesDecoratorTest {
    static PESMState testState;
    static PESMState testState2;
    private String LIABILITIESMINPAY = "LIABILITIES.MINPAY";
    private String LIABILITIESAMOUNT = "LIABILITIES.AMOUNT";
    private String LIABILITIESDELAY = "LIABILITIES.DELAYEDFEE";
    private String ASSETAMOUNT = "ASSET.AMOUNT";
    private String EXPENSEMONTHLY = "EXPENSE.MONTHLY";
    private String LIABILITIESAPR = "LIABILITIES.APR";
    
    
    public PESMStateLiabilitiesDecoratorTest() {
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
            ((AssetAmount) currParam).setAmount(-3000.00);
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
            currParam =new LiabilitiesAPR();
            ((LiabilitiesAPR)currParam).setPercentage(0.24);
            myparam.add(currParam); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new LiabilitiesAmount();
            ((LiabilitiesAmount) currParam).setAmount(1100);
            myparam.add(currParam); params.add(myparam);                    
            

            myparam = new HashSet<SIMParameter>();
            currParam = new LiabilitiesMinPayment();
            ((LiabilitiesMinPayment) currParam).setPercentage(0.05);
            myparam.add(currParam); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new LiabilitiesDelayFee();
            ((LiabilitiesDelayFee) currParam).setAmount(50);
            myparam.add(currParam); params.add(myparam);

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
            //((LiabilitiesMonthly) currParam).setAmount(200);
            myparam.add(currParam); params.add(myparam); 
                    
/****************************/                   
/****************************/                   
  /****************************/       
            testState.setParameters(params);
            
            params = new HashSet<Set<SIMParameter>>();
            
            myparam = new HashSet<SIMParameter>();
            myparam = new HashSet<SIMParameter>();
            currParam = new AssetAPR();
            ((AssetAPR) currParam).setPercentage(0.15);
            myparam.add(currParam); params.add(myparam);       

            myparam = new HashSet<SIMParameter>();
            currParam = new AssetAmount();
            ((AssetAmount) currParam).setAmount(-3000.00);
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
            currParam =new LiabilitiesAPR();
            ((LiabilitiesAPR)currParam).setPercentage(0.24);
            myparam.add(currParam); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new LiabilitiesAmount();
            ((LiabilitiesAmount) currParam).setAmount(1100);
            myparam.add(currParam); params.add(myparam);                    
            

            myparam = new HashSet<SIMParameter>();
            currParam = new LiabilitiesMinPayment();
            ((LiabilitiesMinPayment) currParam).setPercentage(0.05);
            myparam.add(currParam); params.add(myparam); 

            myparam = new HashSet<SIMParameter>();
            currParam = new LiabilitiesDelayFee();
            ((LiabilitiesDelayFee) currParam).setAmount(50);
            myparam.add(currParam); params.add(myparam);

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
            //((LiabilitiesMonthly) currParam).setAmount(200);
            myparam.add(currParam); params.add(myparam); 
            
            
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
        String rule = "L1";
        PESMStateLiabilitiesDecorator instance = new PESMStateLiabilitiesDecorator(testState);
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
     * Test of applyRule method for rule L.1
     */
    @Test
    public void testApplyRuleL1() {
        System.out.println("applyRule L.1");
        String rule = "L1";
        PESMStateLiabilitiesDecorator instance = new PESMStateLiabilitiesDecorator(testState);
        PESMStateLiabilitiesDecorator instance2 = new PESMStateLiabilitiesDecorator(testState2);
        
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
            LiabilitiesMinPayment liaMinPay = (LiabilitiesMinPayment) (instance2.getParameter(LIABILITIESMINPAY)).toArray()[0];
            LiabilitiesAmount liaAmount = (LiabilitiesAmount) (instance2.getParameter(LIABILITIESAMOUNT)).toArray()[0];
            LiabilitiesDelayFee liaDelay = (LiabilitiesDelayFee) (instance2.getParameter(LIABILITIESDELAY)).toArray()[0];
            AssetAmount assetAmount = (AssetAmount) (instance2.getParameter(ASSETAMOUNT)).toArray()[0];
            
            double totalLiab;
            
            if(liaMinPay.getPercentage()*liaAmount.getAmount() > assetAmount.getAmount()){
                totalLiab = liaMinPay.getPercentage()*liaAmount.getAmount() + liaDelay.getAmount();
            }
            else{
                totalLiab = liaMinPay.getPercentage()*liaAmount.getAmount();
            }
            
            instance.applyRule(rule);
            instance2.applyRule(rule);

            double monthlyExp1 = ((ExpenseMonthly)(instance.getParameter(EXPENSEMONTHLY)).toArray()[0]).getAmount();
            double monthlyExp2 = ((ExpenseMonthly)(instance2.getParameter(EXPENSEMONTHLY)).toArray()[0]).getAmount();
            
            if(monthlyExp2 - totalLiab == monthlyExp1 - totalLiab){
                assertTrue("Calculation of monthly liabilities expenses is the expected.", true);
            }       
            else{
            // TODO review the generated test code and remove the default call to fail.
            fail("Failed to calculate monthly liabilities expenses.");
            }
        }
    }
    
      /**
     * Test of applyRule method for rule L.2
     */
    @Test
    public void testApplyRuleL2() {
        System.out.println("applyRule L.2");
        String rule = "L2";
        PESMStateLiabilitiesDecorator instance = new PESMStateLiabilitiesDecorator(testState);
        PESMStateLiabilitiesDecorator instance2 = new PESMStateLiabilitiesDecorator(testState2);
        
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
            LiabilitiesAPR liaAPR = (LiabilitiesAPR) (instance2.getParameter(LIABILITIESAPR)).toArray()[0];
            LiabilitiesAmount liaAmount = (LiabilitiesAmount) (instance2.getParameter(LIABILITIESAMOUNT)).toArray()[0];
            
            double totalLiaAmount = liaAmount.getAmount()*(1+liaAPR.getPercentage()/12.0);
                        
            instance.applyRule(rule);

            LiabilitiesAmount liaAmountFinal = (LiabilitiesAmount) (instance.getParameter(LIABILITIESAMOUNT)).toArray()[0];
            
            if(liaAmountFinal.getAmount() == totalLiaAmount){
                assertTrue("Calculation of "+LIABILITIESAMOUNT+" is the expected.", true);
            }       
            else{
            // TODO review the generated test code and remove the default call to fail.
            fail("Failed to calculate "+LIABILITIESAMOUNT+".");
            }
        }
    }
    
      /**
     * Test of applyRule method for rule L.3
     */
    @Test
    public void testApplyRuleL3() {
        System.out.println("applyRule L.3");
        String rule = "L3";
        PESMStateLiabilitiesDecorator instance = new PESMStateLiabilitiesDecorator(testState);
        PESMStateLiabilitiesDecorator instance2 = new PESMStateLiabilitiesDecorator(testState2);
        
        if(!instance.equals(instance2)){
            fail("Test instances are not equal.");
        }
        else{
           LiabilitiesAmount liaAmount = (LiabilitiesAmount) (instance2.getParameter(LIABILITIESAMOUNT)).toArray()[0];
           AssetAmount assetAmount = (AssetAmount) (instance2.getParameter(ASSETAMOUNT)).toArray()[0];
           double totalLia = liaAmount.getAmount();
           
           instance.applyRule(rule);
           AssetAmount assetAmountFinal = (AssetAmount) (instance.getParameter(ASSETAMOUNT)).toArray()[0];
           LiabilitiesAmount liaAmountFinal = (LiabilitiesAmount) (instance.getParameter(LIABILITIESAMOUNT)).toArray()[0];
           
           if(assetAmount.getAmount()<0.0){
               totalLia = liaAmount.getAmount() + Math.abs(assetAmount.getAmount());
               assertTrue("If Assets were <0, now should be 0",assetAmountFinal.getAmount() == 0);
               assertTrue("Liabilities were increased then. ",liaAmountFinal.getAmount() == totalLia);               
           }
           else{
               assertTrue("If Assets were not <0, then they're kept the same. ",assetAmountFinal.getAmount() == assetAmount.getAmount());
               assertTrue("Liabilities were kept the same then. ",liaAmountFinal.getAmount() == totalLia);               
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
        rules[0]= "L1";
        rules[1]= "L2";
        rules[2]= "L3";
        
        PESMStateLiabilitiesDecorator instance1 = new PESMStateLiabilitiesDecorator(testState);
        PESMStateLiabilitiesDecorator instance2 = new PESMStateLiabilitiesDecorator(testState2);
        
        instance1.applyRules(rules);
        instance2.applyRule("L1");
        instance2.applyRule("L2");
        instance2.applyRule("L3");
        
        
        if(instance1.equals(instance2)){
            assertTrue("Rules applied correctly.", true);
        }
        else{
            // TODO review the generated test code and remove the default call to fail.
            fail("Failed applying rules.");
        }
    }
}
