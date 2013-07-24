/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librethinking.simmodsys;

import com.librethinking.simmodsys.exceptions.SIMModelException;
import java.util.Collection;
import java.util.Set;

/**
 * SIMModel is the main unit of the system. It represents an executable simulation
 * or mathematical model. It is composed by States that saves the value of its parameters
 * along its life cycle. 
 * 
 * 
 * @author cvielma
 */
public interface SIMModel {
        
    /** This method executes the SIMModel until a final SIMState is reached */
    public void execute() throws SIMModelException;
    
    /** This method tells if the SIMModel can be executed (satisfies certain requirements)
     */
    public boolean isExecutable();
    
    /** This method obtains the first SIMState established for the model.
     * If no SIMState has been set, this will return null.
     */
    public SIMState getInitialState();
    
    /** This method sets the initial SIMState of the model.
     * It can return runtime exceptions as result of the operation. */
    public void setInitialState(SIMState st);
    
    /** This method obtains the last (final) SIMState established for the model, if
     * any. If no final state is set, this will return null.*/
    public SIMState getFinalState();
    
    /** This method sets the final SIMState for the model. It should remove the last
     * SIMState in the model (if there's one) and change it for this new SIMState.
     * It can return runtime exceptions as result of the operation.
     */
    public void setFinalState(SIMState st);
    
    
    /** This method sets a Set of States to the SIMModel. The first state will
     * be the initial, and the last will be the final. 
     * It can return runtime exceptions depending on the result.
     */
    public void setStates(Set<SIMState> sts);
    
    /** This method returns all the States associated to the model. The first one
     * (if any) will be the initial state, and the last one will be the final.
     * It can return null.
     */
    public Collection<SIMState> getStates();
    
    /** This method adds States to the model.
     * It can return runtime exceptions.
     */
    public void addState(SIMState st);
    
    /** This method sets the SIMUser that is executing the model in order to review 
     * its permissions (when used to save models).
     * It can return runtime exceptions.
     */
    public void setUser(SIMUser u);
    
    /** This method gets the SIMUser associated to the SIMModel, if any.
     * It can return null.
     */
    public SIMUser getUser();
    
    /** This method gives a name to the model to be associated by the user **/
    public void setName(String name);
    
    /** This method returns the name of the model to be associated by the user **/
    public String getName();
    
    /** This method returns the models type */
    public String getType();
    
    /** This method creates a new instance of the defining state used by this model**/
    public SIMState newDefStateInstance();
    
    /** This method returns a skeleton of an initial state for this model **/
    public SIMState newInitialStateMock();
    
    /** This method returns the skeleton of a final state for this model**/
    public SIMState newFinalStateMock();
    
    
    
    
}
