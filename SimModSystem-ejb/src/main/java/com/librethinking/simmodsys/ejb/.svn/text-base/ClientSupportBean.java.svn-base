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

import com.librethinking.simmodsys.exceptions.business.FunctionalException;
import com.librethinking.simmodsys.exceptions.business.TechnicalException;
import com.librethinking.simmodsys.persistence.jpa.hibernate.AdmTypes;
import com.librethinking.simmodsys.persistence.jpa.hibernate.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.*;

/**
 * This bean provides some common operations used by the clients to get
 * some support information.
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Stateless
@LocalBean
public class ClientSupportBean {
     @PersistenceContext 
    private EntityManager em;
    
    /** 
     * This operation returns a List of active parameters with all the information
     * given a parameter String. 
     * 
     * @param parameter Parameter to be searched. It can contain % as wildcard.
     *
     * @return A list with the parameters that match the given criteria, or an empty
     * list. 
     *
     * @throws TechnicalException if there are errors with transaction, timeouts or 
     * unexpected errors.
     */
    public List<AdmTypes> getActiveTypes(String parameter){
        try{
            TypedQuery<AdmTypes> query = em.createNamedQuery("AdmTypes.findByLikeCType", AdmTypes.class)
                .setParameter("cType", parameter)
                .setParameter("vlStatus", "ST001"); //TODO: parameterize this in other place
            List<AdmTypes> types  = query.getResultList();
        
            if(Logger.getLogger(ClientSupportBean.class.getName()).getLevel() == Level.INFO){
                    Logger.getLogger(ClientSupportBean.class.getName())
                            .log(Level.INFO, "GettingTypes - Query: {0}, Types: {1}. ", new Object[]{query.toString(), types.toString()});
                } 
            return types;
        }
         catch(IllegalArgumentException e){
            Logger.getLogger(ClientSupportBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new TechnicalException("Error in persistence integrity or definition.", e);
        }
        catch(QueryTimeoutException|LockTimeoutException e){
             Logger.getLogger(ClientSupportBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new TechnicalException("Procedure timed out.", e);
        }
        catch(Exception e){
             Logger.getLogger(ClientSupportBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new TechnicalException("Unexpected error.", e);
        }       
        
    }

    
    
}
