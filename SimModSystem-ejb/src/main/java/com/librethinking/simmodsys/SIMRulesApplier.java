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
package com.librethinking.simmodsys;

/**
 * This interface is used to give a common entry point to interact with models. 
 * When some rules need to be applied to a Model data, this Interface should be implemented.
 *  
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public interface SIMRulesApplier {
   
    /** This method allows to dynamically apply a rule to an object. 
     * 
     * @param subject Object to be modified.
     * @param rule Name of the rule to be applied .
     * @return The modified object after been applied the rule "rule".
     */
    public <T extends Object> T applyRuleTo (T subject, String rule);
    
    /** This method assume the implementing class knows to which object apply the rule
     * 
     * @param rule Name of the rule to be applied.
     */
    public void applyRule(String rule);
    
    /** This method allows the application of many rules in the same order they're passed.
     * 
     * @param rules Each rule to be be applied
     */
    public void applyRules(String ...rules);
}
