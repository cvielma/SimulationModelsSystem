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

/**
 * This class creates an specific model given the type.
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class SIMModelFactory {
    
    /** This method creates using reflection, the model which type is the provided.
     * If many models have the same type (SHOULD'NT HAPPEN), it will return the first one it finds.
     * If no model is found it will return null.
     * 
     * @param type a String with the type of the model
     * @return An instance of a <code>SIMModel</code>
     * @throws ReflectiveOperationException if something goes wrong determining the model by reflection.
     * @throws FunctionalException if the type provided is null.
     * @throws TechnicalException if there are any unexpected errors.
     */
    public SIMModel createSIMModel(String type) throws ReflectiveOperationException, FunctionalException {
       try {
            Reflections reflections = new Reflections(this.getClass().getPackage().getName());

            Set<Class<? extends SIMModel>> allClasses = reflections.getSubTypesOf(SIMModel.class);

            for(Iterator<Class<? extends SIMModel>> i = allClasses.iterator(); i.hasNext();){
                Class<? extends SIMModel> currModel = i.next();

                    String modelType = (String) currModel.getDeclaredField("MODELTYPE").get(null);
                    if(type.equals(modelType)){
                        return currModel.newInstance();
                    }

            }
        } catch (IllegalAccessException|NoSuchFieldException|SecurityException|InstantiationException ex) {
            Logger.getLogger(SIMModelFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new ReflectiveOperationException(ex);
        }
        catch (NullPointerException e){
            if(type==null){throw new FunctionalException("Provided type was null.");}
            else{throw new TechnicalException(e);}
        }
        catch (Exception e){
            throw new TechnicalException(e);
        }
        
       
       
       return null;
        
    }
    
}
