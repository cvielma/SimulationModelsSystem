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
import com.librethinking.simmodsys.models.pesm.PESMModel;
import com.librethinking.simmodsys.models.pesm.PESMState;
import com.librethinking.simmodsys.models.pesm.PESMUtil;
import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.exceptions.StateValidationException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMModelDataTest {
    
    static PESMState testState;
    
    public PESMModelDataTest() {
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
            
            Set<Set<SIMParameter>> params = new HashSet<>();
            HashSet<SIMParameter> myparam;
            SIMParameter currParam;
//AssetAPR                                            
            myparam = new HashSet<>();
            currParam = new AssetAPR();
            ((AssetAPR) currParam).setPercentage(0.15);
            myparam.add(currParam); params.add(myparam);       

//AssetAmount            
            myparam = new HashSet<>();
            currParam = new AssetAmount();
            ((AssetAmount) currParam).setAmount(10000.00);
            myparam.add(currParam); params.add(myparam);       
            
//AssetMinSavings
            myparam = new HashSet<>();
            currParam = new AssetMinSavings();
            ((AssetMinSavings) currParam).setPercentage(0.1);
            myparam.add(currParam); params.add(myparam);                    

//ExpenseDesired            
            myparam = new HashSet<>();
            currParam = new ExpenseDesiredExpenses();
            currParam.setValue(Arrays.asList(new Object[]{"Default", "Travel to Venezuela", 12000.00, 1}));
            myparam.add(currParam);             
            
            currParam = new ExpenseDesiredExpenses();
            currParam.setValue(Arrays.asList(new Object[]{"Default", "Car initial", 20000.00, 2}));
            myparam.add(currParam);             
            
            currParam = new ExpenseDesiredExpenses();
            currParam.setValue(Arrays.asList(new Object[]{"Default", "Concert", 500.00, 3}));
            myparam.add(currParam); 
            
            params.add(myparam); 
            
//ExpenseInflation
            myparam = new HashSet<>();
            currParam = new ExpenseInflation();
            currParam.setValue(Arrays.asList(new Object[]{"Groceries", 0.3, 0.5, 0.35}));
            myparam.add(currParam); 
            
            currParam = new ExpenseInflation();
            currParam.setValue(Arrays.asList(new Object[]{"Insurance", 0.1, 0.2, 0.1}));
            myparam.add(currParam);
            
            currParam = new ExpenseInflation();
            currParam.setValue(Arrays.asList(new Object[]{"Default", 0.1, 0.3, 0.15}));
            myparam.add(currParam);
                        
            params.add(myparam); 

//ExpenseFixed            
            myparam = new HashSet<>();
            currParam = new ExpenseFixed();
            currParam.setValue(Arrays.asList(new Object[]{"Groceries", 2000.00}));
            myparam.add(currParam);             
            
            currParam = new ExpenseFixed();
            currParam.setValue(Arrays.asList(new Object[]{"Insurance", 500.00}));
            myparam.add(currParam);             
            
            currParam = new ExpenseFixed();
            currParam.setValue(Arrays.asList(new Object[]{"Default", 300.00}));
            myparam.add(currParam); 
            
            params.add(myparam); 

//ExpenseVariable
            myparam = new HashSet<>();
            currParam = new ExpenseVariable();
            currParam.setValue(Arrays.asList(new Object[]{"Default", 0.9, 500.00}));
            myparam.add(currParam);             
            
            currParam = new ExpenseVariable();
            currParam.setValue(Arrays.asList(new Object[]{"Default", 0.05, 5000.00}));
            myparam.add(currParam);             
            
            params.add(myparam); 

//AnnualRise
            myparam = new HashSet<>();
            
            currParam = new IncomeAnnualRise();
            ((IncomeAnnualRise) currParam).setValue(Arrays.asList(new Object[]{"Salary", 0.2, 0.25, 0.23}));
            myparam.add(currParam); 
            
            currParam = new IncomeAnnualRise();
            ((IncomeAnnualRise) currParam).setValue(Arrays.asList(new Object[]{"Phone", 0.01, 0.02, 0.02}));
            myparam.add(currParam); 
            
            currParam = new IncomeAnnualRise();
            ((IncomeAnnualRise) currParam).setValue(Arrays.asList(new Object[]{"Default", 0.1, 0.2, 0.15}));
            myparam.add(currParam); 
            
            params.add(myparam);                    
//FixedEarnings
            
            myparam = new HashSet<>();
            currParam = new IncomeFixedEarnings();
            
            ((IncomeFixedEarnings) currParam).setValue(Arrays.asList(new Object[]{"Salary", 4150.00}));
            myparam.add(currParam);             
            
            currParam = new IncomeFixedEarnings();
            ((IncomeFixedEarnings) currParam).setValue(Arrays.asList(new Object[]{"Phone", 400.00}));
            myparam.add(currParam);             
            
            params.add(myparam); 

//VariableEarnings            
            myparam = new HashSet<>();
            currParam = new IncomeVariableEarnings();
            
            ((IncomeVariableEarnings) currParam).setValue(Arrays.asList(new Object[]{"Default", 0.1, 1000.00}));
            myparam.add(currParam);             
            
            currParam = new IncomeVariableEarnings();
            ((IncomeVariableEarnings) currParam).setValue(Arrays.asList(new Object[]{"Default", 0.25, 100.00}));
            myparam.add(currParam);             
            
            params.add(myparam); 

//LiabilitiesAPR
            myparam = new HashSet<>();
            currParam =new LiabilitiesAPR();
            ((LiabilitiesAPR)currParam).setPercentage(0.25);
            myparam.add(currParam); params.add(myparam); 

//LiabilitiesAmount            
            myparam = new HashSet<>();
            currParam = new LiabilitiesAmount();
            ((LiabilitiesAmount) currParam).setAmount(0.00);
            myparam.add(currParam); params.add(myparam);                    
            
//LiabilitiesMinPayment
            myparam = new HashSet<>();
            currParam = new LiabilitiesMinPayment();
            ((LiabilitiesMinPayment) currParam).setPercentage(0.01);
            myparam.add(currParam); params.add(myparam); 

//LiabilitiesDelayFee            
            myparam = new HashSet<>();
            currParam = new LiabilitiesDelayFee();
            ((LiabilitiesDelayFee) currParam).setAmount(300);
            myparam.add(currParam); params.add(myparam);

//Period            
            myparam = new HashSet<>();
            myparam.add(new Period()); params.add(myparam);  
        
//IncomeMonthlyWI            
            myparam = new HashSet<>();
            currParam = new IncomeMonthlyWI();
            myparam.add(currParam); params.add(myparam);  
            
//IncomeMonthly
            myparam = new HashSet<>();
            myparam.add(new IncomeMonthly()); params.add(myparam); 

//ExpenseMonthly            
            myparam = new HashSet<>();
            currParam = new ExpenseMonthly();
            myparam.add(currParam); params.add(myparam); 

//AssetMonthAPR            
            myparam = new HashSet<>();
            myparam.add(new AssetMonthAPR()); params.add(myparam); 

//AssetAvDesExp            
            myparam = new HashSet<>();
            myparam.add(new AssetAvDesExp()); params.add(myparam); 

//IncomeYearly            
            myparam = new HashSet<>();
            currParam = new IncomeYearly();
            myparam.add(currParam); params.add(myparam); 
            
//ExpenseYearly
            myparam = new HashSet<>();
            currParam = new ExpenseYearly();
            myparam.add(currParam); params.add(myparam);

//LiabilitiesMonthly Expenses            
            myparam = new HashSet<>();
            currParam = new LiabilitiesMonthly();
            myparam.add(currParam); params.add(myparam); 
                    
            testState.setParameters(params);
        
        
    }
    
    @After
    public void tearDown() {
    }
    
    
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testToString() throws StateValidationException {
         PESMModel myModel = new PESMModel();
         myModel.addState(testState);
         assertTrue(myModel.isExecutable());
      //   System.out.println(myModel.toString());
         
         myModel.execute();
         
        // System.out.println(myModel.toString());
         assertFalse(myModel.isExecutable());     
     }
     
     @Test
     public void testCorrectExecution() throws StateValidationException {
         PESMModel myModel = new PESMModel();
         
         PESMState finalSt = new PESMState();
         Period myPeriod = new Period();
         myPeriod.setMonth(24);
         finalSt.setParameter(Period.NAME, PESMUtil.paramWrapper(myPeriod));
         
         myModel.addState(testState);
         myModel.addState(finalSt);
         
         assertTrue(myModel.isExecutable());
         //System.out.println(myModel.toString());
         
         myModel.execute();
         
         System.out.println(myModel.toString());
         assertFalse(myModel.isExecutable());     
     }
     
     
}
