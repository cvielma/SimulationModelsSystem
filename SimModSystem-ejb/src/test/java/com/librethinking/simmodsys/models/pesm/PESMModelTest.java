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

import com.librethinking.simmodsys.models.pesm.PESMModel;
import com.librethinking.simmodsys.models.pesm.PESMState;
import com.librethinking.simmodsys.models.pesm.PESMUtil;
import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.SIMState;
import com.librethinking.simmodsys.SIMUser;
import com.librethinking.simmodsys.exceptions.InvalidParameterException;
import com.librethinking.simmodsys.exceptions.NullOrInvalidStateException;
import com.librethinking.simmodsys.exceptions.StateValidationException;
import com.librethinking.simmodsys.models.pesm.parameters.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMModelTest {
    static PESMState testState;
       
    public PESMModelTest() {
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

        /**
     * Test of wasExecuted method, of class PESMModel.
     */
    @Test
    public void testExecuteIncorrectly() throws StateValidationException {
        System.out.println("wasExecuted");
        PESMModel instance = new PESMModel();
        try {instance.execute();}
        catch(NullOrInvalidStateException e){ 
            assertTrue("Correclty threw exception.", true);
        }                      
    }
    
    /**
     * Test of wasExecuted method, of class PESMModel.
     */
    @Test
    public void testWasExecutedFalse() {
        System.out.println("wasExecuted");
        PESMModel instance = new PESMModel();
        
        assertFalse(instance.isExecutable());
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("Was executed, when shouldn't.");
    }
    
     /**
     * Test of wasExecuted method, of class PESMModel.
     */
    @Test
    public void testWasExecutedTrue() throws StateValidationException {
        System.out.println("wasExecuted");
        PESMModel instance = new PESMModel();
        PESMState mySt = new PESMState();
        Period period = new Period();
        period.setMonth(12);
        mySt.setParameter(period.getName(), PESMUtil.paramWrapper(period));
        instance.addState(testState);
        instance.addState(mySt);
        assertTrue(instance.isExecutable());
        instance.execute();
        assertFalse(instance.isExecutable());
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("Was executed, when shouldn't.");
    }
    /**
     * Test of getInitialState method, of class PESMModel.
     */
    @Test
    public void testGetInitialState() {
        System.out.println("getInitialState");
        PESMModel instance = new PESMModel();
        //PESMState mystate= new PESMState();
        instance.addState(testState);
        
        SIMState expResult = testState;
        SIMState result = instance.getInitialState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("Failed getting Initial SIMState.");
    }

    /**
     * Test of setInitialState method, of class PESMModel.
     */
    @Test
    public void testSetInitialState() throws InvalidParameterException {
        System.out.println("setInitialState");
        PESMState mystate = testState;
        SIMParameter myParam = new AssetAPR();
        HashSet<SIMParameter> myParamCollection = new HashSet<SIMParameter>();
        myParamCollection.add(myParam);
        
        mystate.setParameter(myParam.getName(), myParamCollection);
        SIMState st = testState;
        
        SIMParameter myParam2 = new AssetAPR();
        HashSet<SIMParameter> myParamCollection2 = new HashSet<SIMParameter>();
        myParamCollection2.add(myParam2);
        
        st.setParameter(myParam2.getName(), myParamCollection2);
        PESMModel instance = new PESMModel();
        
        instance.addState(mystate);
        instance.setInitialState(st);
        
        assertEquals(instance.getInitialState(), st);

    }

    /**
     * Test of getFinalState method, of class PESMModel.
     */
    @Test
    public void testGetFinalState() {
        System.out.println("getFinalState");
        PESMModel instance = new PESMModel();
        PESMState myInitState = testState;
        PESMState mystate= new PESMState();
        instance.addState(myInitState);
        instance.addState(mystate);
        
        SIMState expResult = mystate;
        SIMState result = instance.getFinalState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of setFinalState method, of class PESMModel.
     */
    @Test
    public void testSetFinalState() throws InvalidParameterException {
        System.out.println("setFinalState");
        PESMState mystate = testState;
        SIMParameter myParam = new AssetAPR();
        HashSet<SIMParameter> myParamCollection = new HashSet<SIMParameter>();
        myParamCollection.add(myParam);
        
        mystate.setParameter(myParam.getName(), myParamCollection);
        SIMState st = new PESMState();
        SIMParameter myParam2 = new AssetAPR();
        HashSet<SIMParameter> myParamCollection2 = new HashSet<SIMParameter>();
        myParamCollection2.add(myParam2);
        
        st.setParameter(myParam2.getName(), myParamCollection2);
        PESMModel instance = new PESMModel();
        
        instance.addState(mystate);
        instance.setFinalState(st);
        
        assertEquals(instance.getFinalState(), st);
        // TODO review the generated test code and remove the default call to fail.
       // fail("Final SIMState set or got incorrectly.");
    }

    /**
     * Test of get and setStates method, getInitial and getFinal, of class PESMModel.
     */
    @Test
    public void testGetSetStates() {
        System.out.println("setStates");
        Set<SIMState> sts = new TreeSet<>();
        SIMState st1 = new PESMState();
        SIMState st2 = new PESMState();
        Period p1 = new Period();
        Period p2 = new Period();
        p1.setMonth(1);
        p2.setMonth(2);
        st1.setParameter(Period.NAME, PESMUtil.paramWrapper(p1));
        st2.setParameter(Period.NAME, PESMUtil.paramWrapper(p2));
        //State st3 = new PESMState();
        
        sts.add(st1); sts.add(st2);
        
        PESMModel instance = new PESMModel();
        instance.setStates(sts);
        //TODO: test getStates
        //Collection<State> stres = instance.getStates();
        //assertEquals(stres.size(), sts.size());
        
        assertEquals(instance.getInitialState(), st1);
        assertEquals(instance.getFinalState(), st2);
        
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("GetState, GetInitial, GetFinal or SetStates failed.");
    }

    /**
     * Test of getStates method, of class PESMModel.
     */
    @Test
    public void testGetStates() {
        System.out.println("getStates");
        PESMModel instance = new PESMModel();
        Collection expResult = null;
        Collection result = instance.getStates();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setUser method, of class PESMModel.
     
    @Test
    public void testGetSetUser() {
        System.out.println("setUser");
        SIMUser u = new SIMUser() {
            @Override
            public StringBuilder getID() {
                return new StringBuilder("MyUser");
            }
        }; //TODO: Validate when implementing SIMUser.
        PESMModel instance = new PESMModel();
        instance.setUser(u);
        
        SIMUser result = instance.getUser();
        assertEquals(u, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("Failed to return same user");
    }*/

}
