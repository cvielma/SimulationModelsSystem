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

import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesAmount;
import com.librethinking.simmodsys.models.pesm.parameters.AssetAmount;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesMinPayment;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesMonthly;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesDelayFee;
import com.librethinking.simmodsys.models.pesm.parameters.LiabilitiesAPR;
import com.librethinking.simmodsys.SIMRulesApplier;

/**
 * This class is a Decorator to a PESMState to implement the rules associated 
 * to the Liabilities parameters.
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class PESMStateLiabilitiesDecorator extends PESMStateDecorator{
    
    
    public PESMStateLiabilitiesDecorator (PESMState state){
        internalState = state;
    }

    /**
     * This method applies the rule number L.1, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * IF( LIABILITIES.MINPAY*LIABILITIES.AMOUNT > ASSET.AMOUNT) {
     *   (Monthly Liabilities Expenses) = LIABILITIES.MINPAY*LIABILITIES.AMOUNT + LIABILITIES.DELAYEDFEE
     *   }
     *   ELSE {
     *   (Monthly Liabilities Expenses) = LIABILITIES.MINPAY*LIABILITIES.AMOUNT
     *   }
     */
    protected void executeRuleL1(){
        LiabilitiesMinPayment liabMin = PESMUtil.getUniqueParam(this, new LiabilitiesMinPayment());
        LiabilitiesAmount liabAmount = PESMUtil.getUniqueParam(this, new LiabilitiesAmount());
        AssetAmount assetAmount = PESMUtil.getUniqueParam(this, new AssetAmount());
        LiabilitiesMonthly liabMonth = PESMUtil.getUniqueParam(this, new LiabilitiesMonthly());
        
        double monthExp = monthExp = liabMin.getPercentage()*liabAmount.getAmount();

        if(liabMin.getPercentage()*liabAmount.getAmount() > assetAmount.getAmount()){
            LiabilitiesDelayFee liabDelay = PESMUtil.getUniqueParam(this, new LiabilitiesDelayFee());
            
            monthExp += liabDelay.getAmount();                        
        }        
        
        liabMonth.setAmount(monthExp);
        
    }
    
    /**
     * This method applies the rule number L.2, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * LIABILITIES.AMOUNT= LIABILITIES.AMOUNT*(1+LIABILITIES.APR/12)
     */
    protected void executeRuleL2(){
        LiabilitiesAmount liabAmount = PESMUtil.getUniqueParam(this, new LiabilitiesAmount());
        LiabilitiesAPR liabApr = PESMUtil.getUniqueParam(this, new LiabilitiesAPR());
        
        liabAmount.setAmount(liabAmount.getAmount()*(1+(liabApr.getPercentage()/12)));
    }
    
    /**
     * This method applies the rule number L.3, updating the parameters of the 
     * decorated State associated accordingly. 
     * 
     * IF (ASSET.AMOUNT lessThan 0) {
     *   LIABILITIES.AMOUNT = LIABILITIES.AMOUNT + ABS(ASSET.AMOUNT) 
     *   ASSET.AMOUNT = 0}
     */
    protected void executeRuleL3(){
        LiabilitiesAmount liabAmount = PESMUtil.getUniqueParam(this, new LiabilitiesAmount());
        AssetAmount assetAmount = PESMUtil.getUniqueParam(this, new AssetAmount());
        
        if(assetAmount.getAmount() < 0){
            liabAmount.setAmount(liabAmount.getAmount() + Math.abs(assetAmount.getAmount()));
            assetAmount.setAmount(0.0);
        }
        
    }
    
    
    
    
}
