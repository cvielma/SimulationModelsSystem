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
import com.librethinking.simmodsys.models.pesm.PESMState;
import com.librethinking.simmodsys.models.pesm.PESMUtil;
import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.exceptions.InvalidParameterException;
import com.librethinking.simmodsys.exceptions.InvalidParameterNumberException;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * Unit Tests for PESMState.
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMStateTest {
    static PESMState testState;
    
    public PESMStateTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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
                    
            testState.setParameters(params);
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Sets and request a valid parameter
     */
    @Test
    public void testGetSetParameter() throws InvalidParameterException {
        System.out.println("testGetSetParameter");
        String param = "ASSET.AMOUNT";//Parameter suposed to be in the State
        PESMState instance = new PESMState();
        
        SIMParameter myParam = new AssetAmount();
        Set<SIMParameter> myParamCol = new HashSet<SIMParameter>();
        myParamCol.add(myParam);
        
        instance.setParameter(myParam.getName(), myParamCol);
        
        Set result = instance.getParameter(param);
           
        assertTrue(result.toArray()[0] instanceof AssetAmount);        
    }
    
    
    /**
     * Sets a parameter and request an 
     * inexistent parameter.
     */
    @Test
    public void testGetInexistentParameter() throws InvalidParameterException {
        System.out.println("testGetInexistentParameter");
        String param = "MYINEXISTENTPARAMETER";//Must not exist
        PESMState instance = new PESMState();
        
        SIMParameter myParam = new AssetAmount();
        HashSet<SIMParameter> myParamCol = new HashSet<SIMParameter>();
        myParamCol.add(myParam);
        
        instance.setParameter(myParam.getName(), myParamCol);
        boolean thrown = false;
        try{
            Set result = instance.getParameter(param);
        }
        catch(NoSuchElementException e){
            thrown = true;
            assertTrue("Exception trown correctly", true);
        }     
        
        assertTrue(thrown);
        
    }

    /**
     * Test of setParameters method, of class PESMState. 
     */
    @Test
    public void testGetSetParameters() throws InvalidParameterException, InvalidParameterNumberException {
        System.out.println("testGetSetParameters");
        Set<Set<SIMParameter>> params = new HashSet<Set<SIMParameter>>();
        HashSet<SIMParameter> myparam;
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetAPR()); params.add(myparam);       
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetAmount()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetMinSavings()); params.add(myparam); 
        
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
        myparam.add(new LiabilitiesAmount()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new LiabilitiesMinPayment()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new LiabilitiesDelayFee()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new Period()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new IncomeMonthly()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new IncomeMonthlyWI()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new ExpenseMonthly()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetMonthAPR()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetAvDesExp()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new IncomeYearly()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new ExpenseYearly()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new LiabilitiesMonthly()); params.add(myparam); 
        
         
        PESMState instance = new PESMState();
        instance.setParameters(params);
        Set result = instance.getParameters();
        
        byte kop = 0; //Kind of parameters. This will count how many different parameters the state return.
        boolean[] paramVal = new boolean[PESMState.NUMBEROFPARAMS]; //array to validate if each parameter has been counted
        
        for(Iterator i = result.iterator(); i.hasNext();){
            SIMParameter currParam = (SIMParameter) ((Set) i.next()).toArray()[0];
            
            if (currParam instanceof AssetAPR){ if(!paramVal[0]) kop++; paramVal[0]=true;}
            else if (currParam instanceof AssetAmount){ if(!paramVal[1]) kop++; paramVal[1]=true;}
            else if (currParam instanceof Period){ if(!paramVal[2]) kop++; paramVal[2]=true;}
            else if (currParam instanceof AssetMinSavings){ if(!paramVal[3]) kop++; paramVal[3]=true;}
            else if (currParam instanceof ExpenseDesiredExpenses){ if(!paramVal[4]) kop++; paramVal[4]=true;}
            else if (currParam instanceof ExpenseFixed){ if(!paramVal[5]) kop++; paramVal[5]=true;}
            else if (currParam instanceof ExpenseInflation){ if(!paramVal[6]) kop++; paramVal[6]=true;}
            else if (currParam instanceof ExpenseVariable){ if(!paramVal[7]) kop++; paramVal[7]=true;}
            else if (currParam instanceof IncomeAnnualRise){ if(!paramVal[8]) kop++; paramVal[8]=true;}
            else if (currParam instanceof IncomeFixedEarnings){ if(!paramVal[9]) kop++; paramVal[9]=true;}
            else if (currParam instanceof IncomeVariableEarnings){ if(!paramVal[10]) kop++; paramVal[10]=true;}
            else if (currParam instanceof LiabilitiesAPR){ if(!paramVal[11]) kop++; paramVal[11]=true;}
            else if (currParam instanceof LiabilitiesAmount){ if(!paramVal[12]) kop++; paramVal[12]=true;}
            else if (currParam instanceof LiabilitiesDelayFee){ if(!paramVal[13]) kop++; paramVal[13]=true;}
            else if (currParam instanceof LiabilitiesMinPayment){ if(!paramVal[14]) kop++; paramVal[14]=true;}
            else if (currParam instanceof IncomeMonthly){ if(!paramVal[15]) kop++; paramVal[15]=true;}
            else if (currParam instanceof IncomeYearly){ if(!paramVal[16]) kop++; paramVal[16]=true;}
            else if (currParam instanceof IncomeMonthlyWI){ if(!paramVal[17]) kop++; paramVal[17]=true;}
            else if (currParam instanceof ExpenseMonthly){ if(!paramVal[18]) kop++; paramVal[18]=true;}
            else if (currParam instanceof ExpenseYearly){ if(!paramVal[19]) kop++; paramVal[19]=true;}
            else if (currParam instanceof AssetMonthAPR){ if(!paramVal[20]) kop++; paramVal[20]=true;}
            else if (currParam instanceof AssetAvDesExp){ if(!paramVal[21]) kop++; paramVal[21]=true;}
            else if (currParam instanceof LiabilitiesMonthly){ if(!paramVal[22]) kop++; paramVal[22]=true;}
            else{fail("Returned parameter type incorrect: "+currParam.getName());}           
        }
        assertTrue(kop==PESMState.NUMBEROFPARAMS);  //at least one of each parameter  
        
    }
   
     /**
     * This try to set a wrong number of parameters.
     */
    @Test
    public void testBadNumberSetParameters() {
        System.out.println("testBadNumberSetParameters");
        Set<Set<SIMParameter>> params = new HashSet<Set<SIMParameter>>();
        HashSet<SIMParameter> myparam;
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetAPR()); params.add(myparam);       
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetAmount()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetMinSavings()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new ExpenseDesiredExpenses()); params.add(myparam); 
        
        
        PESMState instance = new PESMState();
        boolean thrown = false;
        try{
            instance.setParameters(params);
        }
        catch(InvalidParameterNumberException e){
            thrown = true;
            assertTrue("Failed correctly", true);
        }
        catch(Exception e){
            fail("Failed throwing the exception");
        }
        // TODO review the generated test code and remove the default call to fail.
        assertTrue(thrown);
    }
    
     /**
     * This try to set wrong type of Parameters.
     */
    @Test
    public void testBadTypeSetParameters() {
        System.out.println("testBadTypeSetParameters");
        Set<Set<SIMParameter>> params = new HashSet<Set<SIMParameter>>();
        HashSet<SIMParameter> myparam;
        
       myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetAPR()); params.add(myparam);       
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetAmount()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetMinSavings()); params.add(myparam); 
        
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
        myparam.add(new LiabilitiesAmount()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new LiabilitiesMinPayment()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new LiabilitiesDelayFee()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new Period()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new IncomeMonthly()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new IncomeMonthlyWI()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new ExpenseMonthly()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetMonthAPR()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetAvDesExp()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new IncomeYearly()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new LiabilitiesMonthly()); params.add(myparam); 
        
       SIMParameter myParam = new SIMParameter(){
                        public String getName(){return "MyParam";}
                        public Set<Object> getValue(){return new HashSet<Object>();}
                        public void setValue(Collection<Object> value){int i=0;};
                        public int getStatus(){return 1;}
                        public void setStatus(int status){int i=0;}
                        public Set<Object> getMaxValue(){return new HashSet<Object>();}
                        public Set<Object> getMinValue(){return new HashSet<Object>();}
                        public boolean isUnique(){return true; }
                        public Set<Object> getDefaultValue() {return new HashSet<Object>();}
           
        };     
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(myParam); params.add(myparam); 
        PESMState instance = new PESMState();
        boolean thrown = false;
        try{
            instance.setParameters(params);
        }
        catch(InvalidParameterException e){
            thrown = true;
            assertTrue("Failed correctly", true);
        }
        catch(Exception e){
            fail("Failed throwing the exception");
        }
        assertTrue(thrown);
    }
    
     /**
     * This try to compare if two States are equal.
     */
    @Test
    public void testEqualsOk() {
        System.out.println("testBadTypeSetParameters");
        Set<Set<SIMParameter>> params = new HashSet<Set<SIMParameter>>();
        HashSet<SIMParameter> myparam;
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetAPR()); params.add(myparam);       
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetAmount()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetMinSavings()); params.add(myparam); 
        
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
        myparam.add(new LiabilitiesAmount()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new LiabilitiesMinPayment()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new LiabilitiesDelayFee()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new Period()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new IncomeMonthly()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new IncomeMonthlyWI()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new ExpenseMonthly()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetMonthAPR()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new AssetAvDesExp()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new IncomeYearly()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new ExpenseYearly()); params.add(myparam); 
        
        myparam = new HashSet<SIMParameter>();
        myparam.add(new LiabilitiesMonthly()); params.add(myparam); 
        
        Set<Set<SIMParameter>> params2 = new HashSet<Set<SIMParameter>>();
        HashSet<SIMParameter> myparam2;
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new AssetAPR()); params2.add(myparam2);       
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new AssetAmount()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new AssetMinSavings()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new ExpenseDesiredExpenses()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new ExpenseFixed()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new ExpenseInflation()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new ExpenseVariable()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new IncomeAnnualRise()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new IncomeFixedEarnings()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new IncomeVariableEarnings()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new LiabilitiesAPR()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new LiabilitiesAmount()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new LiabilitiesMinPayment()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new LiabilitiesDelayFee()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new Period()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new IncomeMonthly()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new IncomeMonthlyWI()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new ExpenseMonthly()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new AssetMonthAPR()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new AssetAvDesExp()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new IncomeYearly()); params2.add(myparam2); 
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new ExpenseYearly()); params2.add(myparam2);
        
        myparam2 = new HashSet<SIMParameter>();
        myparam2.add(new LiabilitiesMonthly()); params2.add(myparam); 
        
        PESMState instance1 = new PESMState();
        instance1.setParameters(params);
        
        PESMState instance2 = new PESMState();
        instance2.setParameters(params2);
        
        //System.out.println(instance1.toString());
        //System.out.println(instance2.toString());
        
        assertTrue("Are equal by default.", instance1.equals(instance2));
        assertTrue("Hashcode too. ", instance1.hashCode()==instance2.hashCode());
        
        Set<SIMParameter> ad1 = new HashSet<SIMParameter>();
        SIMParameter adjust1 = new AssetAPR();
        ((AssetAPR)adjust1).setPercentage(0.15);
        ad1.add(adjust1);
        instance1.setParameter("ASSET.APR", ad1);
        ad1 = new HashSet<SIMParameter>();
        adjust1 = new ExpenseMonthly();
        ((ExpenseMonthly)adjust1).setAmount(20000.15);
        ad1.add(adjust1);
        instance1.setParameter("EXPENSE.MONTHLY", ad1);
        
        ad1 = new HashSet<SIMParameter>();
        adjust1 = new AssetAPR();
        ((AssetAPR)adjust1).setPercentage(0.15);
        ad1.add(adjust1);
        instance2.setParameter("ASSET.APR", ad1);
        
        ad1 = new HashSet<SIMParameter>();
        adjust1 = new ExpenseMonthly();
        ((ExpenseMonthly)adjust1).setAmount(20000.15);
        ad1.add(adjust1);
        instance2.setParameter("EXPENSE.MONTHLY", ad1);
        
        assertTrue("Are equal after modifying some parameters.", instance1.equals(instance2));
        assertTrue("Are equal the other way.", instance2.equals(instance1));
        assertTrue("Hashcode too. ", instance1.hashCode()==instance2.hashCode());
        
        ad1 = new HashSet<SIMParameter>();
        adjust1 = new IncomeMonthly();
        ((IncomeMonthly)adjust1).setAmount(1.25);
        ad1.add(adjust1);
        instance2.setParameter("INCOME.MONTHLY", ad1);
        
        assertTrue("They are no longer equal. ", !instance1.equals(instance2));   
        assertTrue("Hashcode too. ", instance1.hashCode()!=instance2.hashCode());
        
    }
    
    
    
    //TODO: tests with nulls and stuff
    
     /**
     * Clone test
     */
    @Test
    public void testClone() throws CloneNotSupportedException {
        System.out.println("testClone");
        PESMState myClonedState = (PESMState) testState.clone();
        
        
        assertTrue("Initially they're the same", myClonedState.equals(testState));
        
        AssetAmount assAm = PESMUtil.getUniqueParam(myClonedState, new AssetAmount());
        AssetAmount original = PESMUtil.getUniqueParam(testState, new AssetAmount());
        
                
        assAm.setAmount(assAm.getAmount()*5);
        
        assertTrue("Now they shouldn't be the same", !myClonedState.equals(testState));
        
        assertTrue("The amounts will be different", assAm.getAmount() != original.getAmount());
        assertTrue("Exactly 5 times different", assAm.getAmount() == original.getAmount()*5);
        
        assAm.setAmount(original.getAmount());
        
        assertTrue("Are the same again", myClonedState.equals(testState));
        
        Set<SIMParameter> newDesired = myClonedState.getParameter(ExpenseDesiredExpenses.NAME);
        for(SIMParameter currDesired : newDesired){
            ((ExpenseDesiredExpenses)currDesired).setPriority(((ExpenseDesiredExpenses)currDesired).getPriority()*40);
        }
        
        assertTrue("They are different", !myClonedState.equals(testState));
        
        Set<SIMParameter> oldDesired = testState.getParameter(ExpenseDesiredExpenses.NAME);
        for(SIMParameter curr : oldDesired){
            byte found = 0;
            
            for(SIMParameter clonedCurr : newDesired){
                if(((ExpenseDesiredExpenses)clonedCurr).getPriority() == ((ExpenseDesiredExpenses)curr).getPriority()*40){
                    found++;
                }
            }
            assertTrue("There is one for each priority", found>0);
        }
        
        
        
        
        
        
    }
}
