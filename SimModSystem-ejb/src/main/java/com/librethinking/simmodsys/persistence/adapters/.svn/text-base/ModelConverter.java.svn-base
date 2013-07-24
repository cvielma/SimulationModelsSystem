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
package com.librethinking.simmodsys.persistence.adapters;

import com.librethinking.simmodsys.*;
import com.librethinking.simmodsys.exceptions.ValueOutOfBoundsException;
import com.librethinking.simmodsys.exceptions.business.FunctionalException;
import com.librethinking.simmodsys.exceptions.business.TechnicalException;
import com.librethinking.simmodsys.models.pesm.PESMState;
import com.librethinking.simmodsys.persistence.jpa.hibernate.*;
import com.librethinking.simmodsys.persistence.jpa.nativesql.postgres.PostgresStateParameterModelInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * This class is intended to transform objects from SIMModel to Model and
 * viceversa.
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class ModelConverter {
    
    private static int cvaluePk = 1; //used to set temporary primary key to c_value in state_parameter
                              //the database takes care of correctly updating this value
                              //TODO: find Hibernate way to do this
   
    
    /** This method converts a SIMModel to a Model that can be persisted
     * in database.
     *
     * @param simModel Desired <code>SIMModel</code> that should be converted to Model.
     * @param em EntityManager to obtain some values from db.
     * @returns Model that recreates the data from <code>simMode</code>. The model has 
     * already being persisted in DB when returned.
     * 
     */
    public static Model SIMModelToModel(EntityManager em, SIMModel simModel){
        Collection<SIMState> simStates = simModel.getStates();
        Collection<State> dbStates = new ArrayList<>();
        User user = (User) simModel.getUser();
        Model dbModel = new Model(user.getCUser(), simModel.getName());
        AdmTypes dbAdm = new AdmTypes(simModel.getType());
        dbModel.setAdmTypes(dbAdm);
                
        em.persist(dbModel);
        em.flush();
        //this next two lines replaces the em.refresh
        //It is necessary to refresh because c_model code is created with a trigger in the db before insert
       /** em.clear();
        TypedQuery<Model> query = em.createNamedQuery("Model.findByUnique", Model.class)
                .setParameter("cUser", dbModel.getUser().getCUser())
                .setParameter("nmModel", dbModel.getNmModel()); //TODO: parameterize this in other place
            dbModel = query.getSingleResult();
        //dbModel = em.find(Model.class, );*/
        em.refresh(dbModel);
        
        dbAdm = new AdmTypes(simModel.getType());
        
         for(SIMState st : simStates){
                State dbState =SIMStateToState(em, dbModel, st);
                dbState.setAdmTypes(dbAdm);                
                dbStates.add(dbState);     
                em.persist(dbState);
                em.flush();
            }
        
            dbModel.setStateCollection(dbStates);           
                    
        em.persist(dbModel);
        em.flush();        
        
        return dbModel;
            
    }

    
    /** 
     * This method converts a SIMState to a State that can be persisted.
     * 
     * @param em <code>EntityManage</code> to do some operations in database.
     * @param dbModel <code>Model</code> to which the state will be associated.
     * @param st <code>SIMState</code> to be converted.
     * @return <code>State</code> entity that can be persisted.
     */
    private static State SIMStateToState(EntityManager em, Model dbModel, SIMState st) {
        Collection<StateParameter> dbParams = new ArrayList<>();
        Set<Set<SIMParameter>> simParams = st.getParameters();
        State dbState = new State();
        dbState.setModel(dbModel);
        StatePK dbStatePk = new StatePK(dbModel.getcModel(), st.getNumericID());
        dbState.setStatePK(dbStatePk);
        dbState.setAdmTypes(dbModel.getAdmTypes());
        
        for(Set<SIMParameter> sp : simParams){
            for(SIMParameter param : sp){
                Collection<Object> paramValues = param.getValue();
                //TODO: check why some parameters like EXPENSE.INFLATION are being saved with 3 values instead of 4
                for(Object value : paramValues){                    
                    StateParameter sparam = SIMParameterToStateParameter(em, dbModel, dbState, param, value);
                    if(sparam.getStateParameterPK() != null){
                        dbParams.add(sparam);                    
                    }
                    
                }
            }
        }
        dbState.setStateParameterCollection(dbParams);
        
        
        return dbState;
        
    }

    /** This method sets a parameter value for a given parameter and value */
    //TODO:document
    private static StateParameter SIMParameterToStateParameter(EntityManager em, Model dbModel, State dbState, SIMParameter param, Object value) {
        StateParameter dbStParam = new StateParameter();
         //getting parameter
        TypedQuery<Parameter> query = em.createNamedQuery("Parameter.findByNmParam", Parameter.class);
        query.setParameter("nmParam", param.getName());
        List<Parameter> storedParams = query.getResultList();
        
        if(storedParams.size()>0){
            Parameter storedParam = storedParams.get(0);
            
            //setting state
            dbStParam.setState(dbState);
            StateParameterPK dbStParamPk = new StateParameterPK(dbModel.getcModel(), 
                                                                dbState.getStatePK().getCState(), 
                                                                storedParam.getCParam(), 
                                                                cvaluePk++);

            dbStParam.setStateParameterPK(dbStParamPk);
            dbStParam.setParameter(storedParam);
            dbStParam.setVlValue(value.toString());
          
            return dbStParam;
        }
        else{throw new TechnicalException("Parameter not found in database: Maybe model definition logic is out of sync.");} //TODO: to bundle
        
    }
    
    //TODO: Change this for something similar but less hardcode
    private static String typeConverter(Object value){
        if(value instanceof String) return "PT003";
        if(value instanceof Double) return "PT002";
        if(value instanceof Integer) return "PT001";
        
        return "";
    }
    
    
    /**
     * This method converts a Model to a SIMModel.
     * @param em <code>EntityManage</code> to do some operations in database.
     * @param model Model to be converted to a SIMModel.
     * @return SIMModel with the representation of the Model.
     * @throws TechnicalException if the Model cannot be converted to a SIMModel.
     */
    public static SIMModel ModelToSIMModel(EntityManager em, Model model) throws FunctionalException{
        try{
            SIMModelFactory factory = new SIMModelFactory();
            SIMModel simModel = factory.createSIMModel(model.getAdmTypes().getCType());
            Collection<State> states = model.getStateCollection();
            for(State state: states){
                simModel.addState(StateToSIMState(em, simModel.newDefStateInstance(), state));
            }
            
            return simModel;
                    
         } catch(FunctionalException e){
             throw e;
         } catch(Exception e){
            throw new TechnicalException(e); 
        }
    
    }
    
    /** 
     * This method converts a State to a SIMState.
     * @param em <code>EntityManage</code> to do some operations in database.
     * @param simState SIMState to be filled with the info from state.
     * @param state State to be converted to a SIMState.
     * @return SIMState with the representation of the State.
     * @throws RuntimeException if the State cannot be converted to a SIMState.
     */
    private static SIMState StateToSIMState(EntityManager em, SIMState simState, State state) throws FunctionalException{
        PostgresStateParameterModelInfo queryMaker = new PostgresStateParameterModelInfo(); //TODO: change for dynamic factory
        List<PostgresStateParameterModelInfo> listParams =  em.createNativeQuery(queryMaker.getQuery(), PostgresStateParameterModelInfo.class)
                                                    .setParameter("cModel", state.getStatePK().getCModel())
                                                    .setParameter("cState", state.getStatePK().getCState()).getResultList();
        
        Set<String> params = simState.getExpectedParametersSet();
        int paramsNum = params.size();
        
        for(int i = 0; i<paramsNum; i++){
            Set<SIMParameter> savedSameType = getCompleteParam(listParams);
            if(savedSameType.size()>0){
                SIMParameter savedParam = (SIMParameter) savedSameType.toArray()[0];
                if(params.contains(savedParam.getName())){
                    simState.setParameter(savedParam.getName(), savedSameType);
                }
                else{
                   throw new FunctionalException("Tried to load a model with different parameters than expected.");
                }
            }
            else{
                throw new TechnicalException("Impossible to get complete parameter"); //TODO:bundle
            }
        }
        
       return simState;
    }
    
    
    /** This method converts a String into an object of the type of <code>target</code>
     * 
     * @param input String that want to be parsed to object of the same type of <code>target</code>.
     * @param target Object of type to be converted.
     * @return A representation of <code>input</code> in the <code>target</code> type.
     * 
     * @throws FunctionalException if the input cannot be converted to target type due to 
     * input format.
     * @throws TechnicalException if the <code>target</code> cannot be found (hasn't been considered).
     */
    private static Object convert(String input, Object target) throws FunctionalException {
        Object result= null;
        try{
            if(input.equals("") && !(target instanceof String)){
             return null;   
            }
            
            if(target instanceof Double){                
                result = Double.parseDouble(input);            
            }
            else if(target instanceof Integer){
                result = Integer.parseInt(input);
            }
            else if(target instanceof String){
                result = input;
            }
            else{
                String error = "Cannot convert to target type: target "
                        + "class ("+target.getClass()+") not found.";
                //Logger.getLogger(SIMParameterAdapter.class.getName()).log(Level.SEVERE, null, error);
                throw new TechnicalException(error);
            }
        }catch(NumberFormatException e){
             //Logger.getLogger(SIMParameterAdapter.class.getName()).log(Level.SEVERE, null, e);
             throw new FunctionalException("Cannot convert '"+input+"' to '"+target.getClass()+"'.");
        }catch(TechnicalException e){throw e;}
        
        return result;
    }

    
    /**
     * This method returns a complete set with all the parameters of the
     * same type set in order to be directly included in a State.
     * 
     * It extracts this parameters from the list passed.
     * 
     * @param listParams The List with all the information saved about the parameters. 
     * This list will be modified in this method to remove the pararemeters already read.
     *
     * @return A Set with all the values of a same Parameter.
     * 
     * @throws TechnicalException if an error occurs while processing.
     * @theows FunctionalException if the parameter loaded doesn't meet the expected
     * SIMParameter conditions (uniqueness, range of values, etc).
     */
    private static Set<SIMParameter> getCompleteParam(List<PostgresStateParameterModelInfo> listParams) throws FunctionalException {
        List<PostgresStateParameterModelInfo> selectedParams = getNextParam(listParams);
        return stateParamToSIMParameterSet(selectedParams);
    }

    /**
     * This method extracts a List of parameters of the same type from a List
     * in order to be treated later. It also removes the extracted params from the listParams.
     * @param listParams Complete list with all the parameters.
     * @return Returns a List of parameters that matches with the type of the same parameter
     * of the list.
     * @throws TechnicalException if any other error arises.
     */
    private static List<PostgresStateParameterModelInfo> getNextParam(List<PostgresStateParameterModelInfo> listParams) {
        List<PostgresStateParameterModelInfo> result = new ArrayList<>();
        PostgresStateParameterModelInfo firstParam = listParams.get(0);
        PostgresStateParameterModelInfo currParam = firstParam;
        //NOTE: List used as Queue
        while(firstParam.getcParam()==currParam.getcParam() && listParams.size()>0){
            result.add(currParam);
            listParams.remove(0); //like pop
            if(listParams.size()>0){
                currParam = listParams.get(0);
            }
        }
        
        return result;
    }

    
    /**
     * This method creates a Set of SIMParameter given a List of Parameters from
     * the database. This method also validates the integrity of the loaded model to
     * guarantee it was saved correctly.
     * @param selectedParams is a List of saved stateParameters with all the Parameters
     * of the same type.
     * @return a Set of SimParameters with the values extracted from the database.
     * @throws TechnicalException if an error occurs while processing the set.
     * @theows FunctionalException if the parameter loaded doesn't meet the expected
     * SIMParameter conditions (uniqueness, range of values, etc).
     */
    private static Set<SIMParameter> stateParamToSIMParameterSet(List<PostgresStateParameterModelInfo> selectedParams)
    throws FunctionalException{
            Set<SIMParameter> result = new HashSet<>();
            try {
                if(selectedParams!=null && selectedParams.size()>0){
                    SIMParameterFactory factory = new SIMParameterFactory();

                    SIMParameter resultParam = factory.createSIMParameter(selectedParams.get(0).getNmParam());
                    if(resultParam.isUnique() && selectedParams.size()!=1){
                        throw new FunctionalException("Parameter is unique but was saved with multiple values.");//TODO: to bundle
                    } else if(resultParam.getDefaultValue().size() != selectedParams.get(0).getParamNum()){
                        throw new TechnicalException("Database and Param Login are out of sync: Number of parameters differs.");
                    } 
                    else{
                        List<PostgresStateParameterModelInfo> listValues = new ArrayList<>();
                        for(PostgresStateParameterModelInfo currVal : selectedParams){
                            
                            if(currVal.getParamOrd() == 0 && listValues.isEmpty()){
                                listValues.add(currVal);
                            }
                            else if(currVal.getParamOrd()!=0 && listValues.size()<currVal.getParamNum()){
                                listValues.add(currVal);
                            }
                            else{
                                throw new FunctionalException("Model was saved with different number of parameter's values than expected. Impossible to load."); //TODO:bundle
                            }
                            
                            if(listValues.size() == currVal.getParamNum()){
                                result.add(stateParamToSIMParam(listValues));
                                listValues = new ArrayList();
                            }
                        
                        }
                        return result;
                    }
                    
                }
                else{
                    throw new TechnicalException("Selected params is null or empty.");
                }
            } catch (Exception ex) {
                throw new TechnicalException(ex);
            }
            
    }

    /**
     * This method converts a StateParam to a SIMParam from a list of vales
     * @param listValues List with values to create a new parameter.
     * @return SIMParameter with the values passed set.
     * @throws TechnicalException if the Parameter cannot be converted to a SIMParameter
     * @throws FunctionalException if the listValues has values out of range of the parameter.
     */
       private static SIMParameter stateParamToSIMParam(List<PostgresStateParameterModelInfo> listValues) throws FunctionalException {
           try {
               //TODO: define if it is going to validate range of values
                if(listValues!=null && listValues.size()>0){
                        SIMParameterFactory factory = new SIMParameterFactory();
                        SIMParameter param = factory.createSIMParameter(listValues.get(0).getNmParam());
                        Collection paramNewValues = new ArrayList();
                        Object[] paramValueTypes = param.getDefaultValue().toArray();
                        if(listValues.size() == paramValueTypes.length){
                            for(int i=0; i<listValues.size(); i++){
                                
                                Object newValue = convert(listValues.get(i).getVlValue(),paramValueTypes[i]);
                                paramNewValues.add(newValue);                                
                            }
                            try{
                                param.setValue(paramNewValues);
                            }
                            catch(ValueOutOfBoundsException|ClassCastException e){
                                throw new FunctionalException("Error asigning values to parameter '"+param.getName()+"': "+e.getMessage());
                            }
                            return param;
                        }
                        else{
                            throw new TechnicalException("List of value passed differs from expected values."); //TODO:bundle
                        }
                }
                else{
                    throw new TechnicalException("List of values is null or empty.");
                }
            } catch (Exception ex) {
                throw new TechnicalException(ex);
            }
           
    }
    
}
