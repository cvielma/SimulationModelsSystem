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

import com.librethinking.simmodsys.exceptions.InvalidParameterException;
import com.librethinking.simmodsys.exceptions.InvalidParameterNumberException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * SIMState represents a particular moment in the life cycle of the model.
 * Each SIMState should have a set of Parameters with values defined. 
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public interface SIMState {
    
    /** Gets the parameter (Collection of 1 parameter)
     * that matches with the name the given SIMParameter. 
     * If the SIMParameter is not unique, then it returns a Collection of Parameters.
     * @param paramName the name of the SIMParameter requested
     * @return Parameters with the given name
     */
    public Set<SIMParameter> getParameter(String paramName);
    
    /** Returns all the Parameters associated to the SIMState. Similar to 
     * {@link getParameter}, for unique Parameters the Collection<Parameter> insider
     * of the larger Collection will be one or more parameters. 
     */
    public Set<Set<SIMParameter>> getParameters();
    
    /** Sets all the Parameters that this SIMState holds.
     * This method can throw exceptions.
     * @throws InvalidParameterException if parameter is not of correct type, accepted by the state.
     * @throws InvalidParameterNumberException if number of parameters is not acceptable by the state
     */
    public void setParameters(Set<Set<SIMParameter>> params);
    
    /** Sets a SIMParameter that the current SIMState have given the name of the passed 
     * SIMParameter. This method can throw exceptions.
     * @param paramName This is the name of the parameter to be set.
     * @param param This the Collection of values that the parameter holds (if compound or not unique).
     * If the parameter is unique, then it will be a collection of size 1.
     * @throws InvalidParameterException if parameter is not of correct type, accepted by the state.
     */
    public void setParameter(String paramName, Set<SIMParameter> param);
    
    /** Returns a numeric ID for the current State, mostly used to order and database operations.
     * @returns int with numeric ID.
     */
    public int getNumericID();
    
    /** This method will return a list of category strings each one with a list of 
     * SIMParameters for that category that belong to the SIMState.
     * @return a list of category strings each one with a list of 
     * SIMParameters for that category that belong to the SIMState.
     */
    public Map<String,Collection<SIMParameter>> describe();
    
    /** This method will return a Set with the list with the parameters'
     * names expected by this State */
    public Set<String> getExpectedParametersSet();
    
}
