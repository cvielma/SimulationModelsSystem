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
package com.librethinking.simmodsys.jasper.reports.pesm;

import com.librethinking.simmodsys.SIMState;
import com.librethinking.simmodsys.models.pesm.PESMModel;
import com.librethinking.simmodsys.models.pesm.PESMState;
import com.librethinking.simmodsys.models.pesm.parameters.AssetAmount;
import com.librethinking.simmodsys.models.pesm.parameters.ExpenseMonthly;
import com.librethinking.simmodsys.models.pesm.parameters.IncomeMonthly;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesAmount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to get the information from a PESM Model
 * and publish it to JasperReport.
 *
 * The methods in this class are just facade method to obtain the
 * information required by the report from the state. 
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMStateReportFacade {
        
    /** Each Facade includes the internal PESMState with the values to print*/
    private PESMState internalState;
    
    /** Configuration associated to this report that should be static (and final for a given report)
     */
    private PESMStateReportFacadeConfigurator configurator;
       
    //TODO: javadoc
    public void setConfiguration (PESMStateReportFacadeConfigurator config){
        configurator = config;
    }
    
    private void setInternalState(PESMState state){
        internalState = state;
    }
    
    public String getAssetSeries() {
        return configurator.getAssetSeries();
    }

    public String getExpensesSeries() {
        return configurator.getExpensesSeries();
    }

    public String getIncomeSeries() {
        return configurator.getIncomeSeries();
    }

    public String getLiabilitySeries() {
        return configurator.getLiabilitySeries();
    }

    public String getMainTitle() {
        return configurator.getMainTitle();
    }    
    
    public double getAssetAmount(){
        return ((AssetAmount) internalState.getParameter(AssetAmount.NAME).toArray()[0]).getAmount();
    }
    
    public double getLiabilitiesAmount(){
        return ((LiabilitiesAmount) internalState.getParameter(LiabilitiesAmount.NAME).toArray()[0]).getAmount();
    }
    
    public double getMonthlyExpensesAmount(){
        return ((ExpenseMonthly) internalState.getParameter(ExpenseMonthly.NAME).toArray()[0]).getAmount();
    }
    
    public double getMonthlyIncomesAmount(){
        return ((IncomeMonthly) internalState.getParameter(IncomeMonthly.NAME).toArray()[0]).getAmount();
    }
    
    public int getMonth(){
        return internalState.getPeriod();
    }
    
    public static Collection<PESMStateReportFacade> getStatesList(PESMStateReportFacadeConfigurator config) {
        ArrayList<PESMStateReportFacade> states = new ArrayList<>();
        
        if(config!=null){
            PESMModel model = config.getModel();
            for(SIMState currSimState : model.getStates()){
                PESMState currState = (PESMState) currSimState;
                PESMStateReportFacade fac = new PESMStateReportFacade();
                fac.setConfiguration(config);
                fac.setInternalState(currState);
                states.add(fac);
            }
        }        
                
        return states;
    }
    
    /**
     * This method returns Mock Data to test the report in development
     * @return A Collection of PESMStateReportFacade to make the report.
     * @see MockData
     */
    public static Collection<PESMStateReportFacade> getMockStatesList(){
       try{
            PESMModel model = MockData.getMockModel();        
            PESMStateReportFacadeConfigurator config = new PESMStateReportFacadeConfigurator();
            config.setTexts("Title", "Expenses", "Incomes", "Assets", "Liabilities");           
            config.setModel(model);
            return getStatesList(config);
        }catch(Exception e){
            Logger.getLogger(PESMStateReportFacade.class.getName()).log(Level.SEVERE, null, e);               
        }
       return null;
    }
    
}
