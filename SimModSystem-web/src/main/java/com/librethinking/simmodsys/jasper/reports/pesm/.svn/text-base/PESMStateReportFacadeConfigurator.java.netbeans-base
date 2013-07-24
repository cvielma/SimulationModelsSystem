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

import com.librethinking.simmodsys.models.pesm.PESMModel;
import com.librethinking.simmodsys.models.pesm.PESMState;

/**
 * This class acts as a registry for the configuration of a 
 * PESMStateReportFacade. The reason is to avoid the multithreading issues
 * of using static methods.
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMStateReportFacadeConfigurator {
        
    /** This is the model used to extract each of the states to be
     *  printed in the report.
     */
    private PESMModel internalModel;;
    
     /**These are variables used to show some texts in the report*/
    private String expensesSeries;
    private String incomeSeries;
    private String assetSeries;    
    private String liabilitySeries;
    private String mainTitle;
    private static final int NUMBEROFTEXTS=5;
    
       
    public void setModel (PESMModel model){
        internalModel = model;
    }
    
    public PESMModel getModel () {
        return internalModel;
    }
    
    //TODO: complete javadoc and maybe convert into a interface.
    
    /**
     * This method sets the values of the labels of the report.
     * The inputs are the labels of each element.
     * @param title
     * @param expense
     * @param income
     * @param asset
     * @param liability 
     */
    public void setTexts(String title, String expense, String income, String asset, String liability){
        mainTitle = title;
        expensesSeries = expense;
        incomeSeries = income;
        assetSeries = asset;
        liabilitySeries = liability;
    }
    
    /**
     * 
     * @return 
     */
    public static int getNumberOfTexts(){
        return NUMBEROFTEXTS;
    }

    public String getAssetSeries() {
        return assetSeries;
    }

    public String getExpensesSeries() {
        return expensesSeries;
    }

    public String getIncomeSeries() {
        return incomeSeries;
    }

    public String getLiabilitySeries() {
        return liabilitySeries;
    }

    public String getMainTitle() {
        return mainTitle;
    }     
    
}
