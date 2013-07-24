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
package com.librethinking.simmodsys;

import com.librethinking.simmodsys.exceptions.business.FunctionalException;
import com.librethinking.simmodsys.exceptions.business.TechnicalException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.reflections.Reflections;
import java.lang.reflect.Modifier;

/**
 * This class creates SIMParameters depending on a String
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class SIMParameterFactory {
    
    /**
     * This method creates a parameter depending on a String.
     * 
     * @param name Parameter name to be created.
     * @return <code>SIMParameter</code> instance or null if didn't find a possible match.
     * @throws ReflectiveOperationException if something goes wrong determining the model by reflection.
     * @throws FunctionalException if the type provided is null.
     * @throws TechnicalException if there are any unexpected errors.
     */
    public SIMParameter createSIMParameter(String name) throws ReflectiveOperationException, FunctionalException{
        try {
            Reflections reflections = new Reflections(this.getClass().getPackage().getName());

            Set<Class<? extends SIMParameter>> allClasses = reflections.getSubTypesOf(SIMParameter.class);

            for(Iterator<Class<? extends SIMParameter>> i = allClasses.iterator(); i.hasNext();){
                Class<? extends SIMParameter> currParam = i.next();
                
                
                    if(!Modifier.isAbstract(currParam.getModifiers())){
                        SIMParameter newParam = currParam.newInstance();
                        String paramType = newParam.getName();
                        if(name.equals(paramType)){
                            return newParam;
                        }
                    }

            }
        } catch (IllegalAccessException|SecurityException|InstantiationException ex) {
            Logger.getLogger(SIMModelFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new ReflectiveOperationException(ex);
        }
        catch (NullPointerException e){
            if(name==null){throw new FunctionalException("Provided type was null.");}
            else{throw new TechnicalException(e);}
        }
        catch (Exception e){
            throw new TechnicalException(e);
        }
        
       
       
       return null;
        
    }
    
}
