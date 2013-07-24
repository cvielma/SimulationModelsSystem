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

import com.librethinking.simmodsys.SIMUser;
import com.librethinking.simmodsys.exceptions.business.FunctionalException;
import com.librethinking.simmodsys.exceptions.business.TechnicalException;
import com.librethinking.simmodsys.persistence.jpa.hibernate.AdmTypes;
import com.librethinking.simmodsys.persistence.jpa.hibernate.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.*;

/**
 * This bean encapsulates operations over Users.
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Stateless
@LocalBean
public class UserBean {
     @PersistenceContext 
    private EntityManager em;

    /** 
     * This operation obtains a user from the persistence storage given
     * the ID of the user.
     * 
     * @param id String with the <code>User</code> id.
     * @param passwd String with the password of the <code>User</code>.
     * @return <code>null</code> if the user is not found, and the User if it is found.
     * @throws TechnicalException if there is an error querying the persistence storage as:
     *          - If id of the user is non unique.
     *          - If there is an error defining the query.
     *          - If there are timeouts.
     *          - Transaction issues. 
     *          - Any unexpected exception.
     * @throws FunctionalException if:
     *          - Id or Password provided don't match user information.
     *          - User is locked.
     */
    public User getUser(String id, String passwd) throws FunctionalException {
        try{
            TypedQuery<User> query = em.createNamedQuery("User.findByCUser", User.class).setParameter("cUser", id);
            User user = query.getSingleResult();
            
            if(Logger.getLogger(UserBean.class.getName()).getLevel() == Level.INFO){
                Logger.getLogger(UserBean.class.getName())
                        .log(Level.INFO, "GettingUser - Query: {0}, User: {1}. ", new Object[]{query.toString(), user.toString()});
            }            
            if(user.getVlLocked()==true){
                throw new FunctionalException("User: "+ user.getCUser() +" is locked.");
            }
            if(!user.getVlPassword().equals(passwd)){
                throw new FunctionalException("Invalid password.");
            }
            
            return user;
        }
        catch(NoResultException e){return null;}
        catch(NonUniqueResultException|IllegalArgumentException e){
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new TechnicalException("Error in persistence integrity or definition.", e);
        }
        catch(QueryTimeoutException|LockTimeoutException e){
             Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new TechnicalException("Procedure timed out.", e);
        }
        catch(FunctionalException|TechnicalException e){
            throw e;
        }
        catch(Exception e){
             Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new TechnicalException("Unexpected error.", e);
        }
    }
    
    /** 
     * This operation saves or update a given user in the persistence.
     * 
     * @param user User to be saved.
     * @throws FunctionalException if there is a error associated with the data provided.
     * @throws TechnicalException if there is a technical error such as timeouts, etc.
     */
    public void saveUser(User user) throws FunctionalException {
        try{
            em.merge(user);
        }
        catch(IllegalArgumentException e){
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new FunctionalException("Invalid user.", e);
        }
        catch(TransactionRequiredException e){
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new TechnicalException("Error obtaining Transaction.", e);
        }
        catch(Exception e){
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, e);
            //TODO: set a table with all error strings
            throw new TechnicalException("Unexpected error.", e);
        }
    }

    
    
    
}
