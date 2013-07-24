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
package com.librethinking.simmodsys.ejb;

import com.librethinking.simmodsys.SIMModel;
import com.librethinking.simmodsys.SIMModelFactory;
import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.SIMState;
import com.librethinking.simmodsys.exceptions.SIMModelException;
import com.librethinking.simmodsys.exceptions.business.FunctionalException;
import com.librethinking.simmodsys.exceptions.business.TechnicalException;
import com.librethinking.simmodsys.models.pesm.PESMModel;
import com.librethinking.simmodsys.models.pesm.PESMState;
import com.librethinking.simmodsys.models.pesm.parameters.Period;
import com.librethinking.simmodsys.persistence.adapters.ModelConverter;
import com.librethinking.simmodsys.persistence.jpa.hibernate.AdmTypes;
import com.librethinking.simmodsys.persistence.jpa.hibernate.Model;
import com.librethinking.simmodsys.persistence.jpa.hibernate.ModelPK;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.*;

/**
 * This bean encapsulates Model operations.
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Stateless
@LocalBean
public class ModelBean {
    @PersistenceContext 
    private EntityManager em;
    
    /** This method saves a SIMModel into the database.
     *  
     * @param model SIMModel to be saved.
     * 
     * @throws FunctionalException if there are errors that can be corrected by 
     * the user as:
     *              - The model already exist in database.
     *              - The model passed is invalid.
     * 
     * @throws TechnicalException if there are "big" errors as:
     *              - The transaction cannot commit.
     *              - If a <code>TransactionRequiredException</code> is thrown 
     *                inside the code.
     *              - If an unexpected error is found. 
     * 
     */
    public void saveSIMModel(SIMModel model) throws FunctionalException {
        //TODO:Validate if user has access to save
        try{
            Model result = ModelConverter.SIMModelToModel(em, model);
            em.persist(result);                       
        }
        catch(EntityExistsException e){            
            Logger.getLogger(ModelBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new FunctionalException("Model already exists in database.", e);
        }
        catch(IllegalArgumentException e){
            Logger.getLogger(ModelBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new FunctionalException("Model is invalid.", e);
        }
        catch(TransactionRequiredException|RollbackException e){
             Logger.getLogger(ModelBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new TechnicalException("Transaction or Commit failed.", e);
        }
        catch(Exception e){
            Logger.getLogger(ModelBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new TechnicalException("Something went terribly wrong.", e);
        }                
    }

    
    /** This method creates a new model given the type.
     * @param type String with the type of model to be created.
     * @return The model created or null if no model was found.
     * @throws FunctionalException if the type provided is null.
     * @throws TechnicalException if there are errors in execution.
     */
    public SIMModel createModel(String type) throws FunctionalException{
        SIMModelFactory factory = new SIMModelFactory();
        try{
            SIMModel model = factory.createSIMModel(type);
            return model;
        }
        catch (FunctionalException e){
            Logger.getLogger(ModelBean.class.getName()).log(Level.SEVERE, null, e);
            throw new FunctionalException("The type provided is not invalid.");            
        }
        catch (ReflectiveOperationException|TechnicalException e){
            Logger.getLogger(ModelBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new TechnicalException("Something went terribly wrong.", e);
        }
        
    }
    
    /** 
     * This method creates and executes a model given a model type and two states.
     * 
     * @param modelType Model type to be create.
     * @param initialSt Initial State for the model (can be null if the model accepts it).
     * @param finalSt Final State for the model (can be null if the model accepts it).
     * 
     * @return The executed model. 
     * 
     * @throws FunctionalException if the model generates a functional exception or an error
     * due to the input data.
     * @throws TechnicalException if the model generates a technical exception or any runtime
     * exception that is caused by an unexpected situation.
     * 
     */
    public SIMModel executeModel(String modelType, SIMState initialSt, SIMState finalSt) throws FunctionalException{
        SIMModelFactory modelFactory = new SIMModelFactory();
        SIMModel myModel = null;
        try {
            myModel = modelFactory.createSIMModel(modelType);
            Class modelClass = myModel.newDefStateInstance().getClass();
             
             
            if((initialSt.getClass().isAssignableFrom(modelClass) || initialSt==null)
               && (finalSt.getClass().isAssignableFrom(modelClass) || finalSt==null)){
                myModel.setInitialState(initialSt);
                myModel.setFinalState(finalSt);
                myModel.execute();
            }
            else{
                String msg = "At least one of the SIMStates passed are not of expected type. "
                        + " Expected type: '"+ modelClass.getSimpleName() +"' ."
                        + " Initial State's Type: '" + initialSt.getClass().getSimpleName() +"'. "
                        + " Final State's Type: '" + finalSt.getClass().getSimpleName() +"'. ";
                Logger.getLogger(ModelBean.class.getName()).log(Level.SEVERE, null, msg);
                throw new TechnicalException(msg);
            }
            
        }catch (SIMModelException ex) {
            Logger.getLogger(ModelBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new FunctionalException(ex);
        } catch (Exception ex) {
            Logger.getLogger(ModelBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new TechnicalException(ex);
        }
        
        
        return myModel;
        
    
    }
    
    /**
     * This method returns a list of Models with the matching user and name. 
     * If name is null then it will search only models with matching user.
     * 
     * @param user User Id of the models to be searched. 
     * @param name Name of the saved model to be searched. It can be null.
     * @return A List of Models with matching user and name (if provided), otherwise an empty list.
     * @throws FunctionalException if the user was not set (is null).
     * @throws TechnicalException if a runtime error is generated. 
     */
    public List<Model> getSavedModelsList(String user, String name) throws FunctionalException{
        List<Model> modelList = new ArrayList<>();
        
        if(user!=null){ 
            try{
                if(name!=null){
                    //ModelPK myModelPK = new ModelPK(user, name);
                    //Model myModel = em.find(Model.class, myModelPK);
                    TypedQuery<Model> query = em.createNamedQuery("Model.findByUnique", Model.class)
                                            .setParameter("cUser", user)
                                            .setParameter("nmModel", name); //TODO: parameterize this in other place
                    
                    Model myModel = query.getSingleResult();
                    if(myModel!=null){modelList.add(myModel);}
                }
                else{
                    TypedQuery<Model> query = em.createNamedQuery("Model.findByCUser", Model.class)
                    .setParameter("cUser", user);          
                    modelList  = query.getResultList();
                }
            } catch(Exception ex){
                Logger.getLogger(ModelBean.class.getName()).log(Level.SEVERE, null, ex);
                throw new TechnicalException(ex);
            }
            
        }
        else{
            throw new FunctionalException("User was not specified."); //TODO: set it in the bundle
        }
                
        return modelList;
    }
    
    /**
     * This method returns a list of Models with the matching user.
     * 
     * @param user User Id of the models to be searched.      
     * @return A List of Models with matching user, otherwise an empty list.
     * @throws FunctionalException if the user was not set (is null).
     * @throws TechnicalException if a runtime error is generated. 
     */
    public List<Model> getSavedModelsList(String user) throws FunctionalException{
        return getSavedModelsList(user, null);
    }
    
    /**
     * This method returns a model re-created from the database.
     * @param modelid name of the model.
     * @param user id of the user consulting the model.
     * @return the re-created SIMModel from the database saved model.
     * @throws FunctionalException if the user consulting doesn't have access to the model
     * @throws TechnicalException if any other error arises.
     */ 
    public SIMModel getSavedModel(int model, String user) throws FunctionalException {
        TypedQuery<Model> query = em.createNamedQuery("Model.findByCModelAndcUser", Model.class)
                                    .setParameter("cModel", model)
                                    .setParameter("cUser", user);
        List<Model> results = query.getResultList();
        
        if(results.size()==1){
            SIMModel simModel = ModelConverter.ModelToSIMModel(em, results.get(0));
            return simModel;
        }
        else{
            throw new FunctionalException("User not allowed to see this model. Please login with correct user."); //TODO: Exception with "security" problem
        }       
        
        
    }

    
    
    
}
