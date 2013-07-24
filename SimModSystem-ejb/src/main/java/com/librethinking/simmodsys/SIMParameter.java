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

import java.util.Collection;

/**
 * A SIMParameter is a single or compounded value assigned to a Model in a
 * given time. That's why Parameters are associated to States of a Model.
 * Each parameter is unique. The repetition or multiplicity of the parameter
 * is managed by the State. For example, ExpenseFixed has only a value, but in the 
 * State there could be a list of ExpenseFixed. 
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public interface SIMParameter extends Cloneable{
    public static final String MODELTYPE = null;
    
    /** This method returns the name of the parameter */
    public String getName();
    
    /** Returns the value stored in this parameter. If the parameter has an array
     * returns all its values, if not, returns a Collection with only value.
     * Clients of this method should know the type and meaning
     */
    public Collection<Object> getValue();
    
    /** Sets the value of this parameter. If the parameter value has only one value
     * then the input is a Collection of 1.     
     * Calling method should know the kind of Values this parameter stores.
     * @throws ClassCastException when passed value is wrong
     */
    public void setValue(Collection<Object> value);
    
    /** Returns the current Status of this SIMParameter (e.g.: Disabled or Enabled)*/
    public int getStatus();
    
    /** Sets the current Status of this SIMParameter.
     * This can throw runtime exceptions.
     */
    public void setStatus(int status);
    
    /** Sets the MaxValue acceptable for this SIMParameter.
     * It can throw runtime exceptions.
     */
   // public void setMaxValue(Object maxvalue);
    
    /** Sets the MinValue acceptable for this SIMParameter.
     * It can throw runtime exceptions.
     */
   // public void setMinValue(Object minvalue);
    
    /** Returns the MaxValue established for the SIMParameter.
     * If the parameter has an array
     * returns all its values, if not, returns a Collection with only value.
     * Caller should know how to interpret the object.
     */
    public Collection<Object> getMaxValue();
    
    /** Returns the MinValue established for the SIMParameter.
     * If the parameter has an array
     * returns all its values, if not, returns a Collection with only value.
     * Caller should know how to interpret the object.
     */
    public Collection<Object> getMinValue();
    
    /** Returns the DefaultValue established for the SIMParameter.
     * If the parameter has an array
     * returns all its values, if not, returns a Collection with only value.
     * Caller should know how to interpret the object.
     */
    public Collection<Object> getDefaultValue();
    
    /** Returns true if the SIMParameter is unique (no repeatable), false otherwise */
    public boolean isUnique();   
    
}
