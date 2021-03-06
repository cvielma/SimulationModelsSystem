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

import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.SIMRulesApplier;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides some methods to decorate and implement rules
 * decorators for PESMState.
 * 
 * This decorator provides a transparent way to access its decorated state through
 * the inherited methods from State. 
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public abstract class PESMStateDecorator extends PESMState implements SIMRulesApplier {
     /** String used to determine method's names by reflection when applying rules **/
    protected String METHODPREFIX ="executeRule";
    
    /**This is the internal decorated state **/
    protected PESMState internalState = null; 
    
    /**
     * This method provides a way to apply a rule to a given State. 
     * 
     * This method is not implemented for the PESM model. It will always throw 
     * <code>UnsupportedOperationException</code>. 
     *      
     * @throws UnsupportedOperationException
     */
    @Override
    public final <T> T applyRuleTo(T subject, String rule) {
        throw new UnsupportedOperationException("It should always use internal state.");
    }

    /**
     * This method applies a rule dynamically. This method uses Reflection to
     * determine the processing of the rule. If no method is found for the rule, it
     * will throw <code>UnsupportedOperationException</code>.
     * 
     * @param rule String with the rule to be applied, as for example "A1".
     * 
     * @throws UnsupportedOperationException when invalid rule is supplied.
     */
    @Override
    public final void applyRule(String rule) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.METHODPREFIX);
        sb.append(rule);
        try {
            this.getClass().getDeclaredMethod(sb.toString(), null).invoke(this, null);                                   
        } 
        catch (NoSuchMethodException ex) {
            try { //This allows multiple decorators attached in chain in internalState
                if(internalState instanceof PESMStateDecorator){
                    ((PESMStateDecorator) internalState).applyRule(rule);
                }
                else{throw ex;}
            } catch (NoSuchMethodException e) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append("Rule: ").append(rule).append(", is invalid.");
               // Logger.getLogger(PESMStateAssetsDecorator.class.getName()).log(Level.SEVERE, null, ex);
                throw new UnsupportedOperationException(sb1.toString());
            }
             catch(Exception e){
                StringBuilder sb1 = new StringBuilder();
                sb1.append("Rule: ").append(rule).append(", caused error: ").append(e);
                //Logger.getLogger(PESMStateAssetsDecorator.class.getName()).log(Level.SEVERE, null, sb1.toString());
                throw new RuntimeException(sb1.toString(), e);
             }
        }
        catch(Exception e){
           StringBuilder sb1 = new StringBuilder();
           sb1.append("Rule: ").append(rule).append(", caused error: ").append(e);
           //Logger.getLogger(PESMStateAssetsDecorator.class.getName()).log(Level.SEVERE, null, sb1.toString());
           throw new RuntimeException(sb1.toString(), e);
        }       
    }

    /**
     * This method applies a sequence of rules dynamically. 
     * If a given rule is not found  will throw 
     * <code>UnsupportedOperationException</code> and the returned state will be 
     * the initial state without rules processing.
     * 
     * @param rules Strings with the rules to be applied, as for example "A1", "A2".
     * 
     * @throws UnsupportedOperationException when invalid rule is supplied.
     */
    @Override
    public final void applyRules(String... rules) {
        for(String i : rules){
            this.applyRule(i);
        }
    }   
    
    /****** METHODS FROM STATE *****/
    
    /** 
     * Calls the same the method of the decorated State.
     * @see PESMState
     *
     */
    @Override
    public final Set<SIMParameter> getParameter(String paramName) {
       return internalState.getParameter(paramName);
    }
    
    
    /**  
     * Calls the same the method of the decorated State.
     * @see PESMState
     */
    @Override
    public final Set<Set<SIMParameter>> getParameters(){
        return internalState.getParameters();
    }
    
    /** 
     * Calls the same the method of the decorated State.
     * @see PESMState
     */
    @Override
    public final void setParameters(Set<Set<SIMParameter>> params){
        internalState.setParameters(params);
    }
    
    /** 
     * Calls the same the method of the decorated State.
     * @see PESMState
     */
    @Override
    public final void setParameter(String paramName, Set<SIMParameter> param){
        internalState.setParameter(paramName, param);
    }
    
    @Override
    public Set<String> getExpectedParametersSet(){
        return internalState.getExpectedParametersSet();
    }
    
    @Override
    public boolean equals(Object obj){
        if (getClass() != this.getClass()){
                Logger.getLogger(getClass().getName()).log(Level.FINEST, "Class to be compared is not PESMState"); 
                return false;}
        else{
            return internalState.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return internalState.hashCode();
    }
    
    /** @throws RuntimeException if reflected class cannot be instanced. */
    @Override
    public Object clone() throws CloneNotSupportedException{
        super.clone();
        try {
            PESMStateDecorator cloned = this.getClass().newInstance();
            cloned.internalState = (PESMState) this.internalState.clone();
            return cloned;
            
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        
        
        
    }
}
