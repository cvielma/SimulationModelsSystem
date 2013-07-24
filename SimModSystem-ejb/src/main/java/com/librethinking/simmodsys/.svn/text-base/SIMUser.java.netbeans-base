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
 * 
 * SIMUser represents an actual user of the System. SIMUser is used to manage permissions
 * (like saving into database), and resources usage.
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public interface SIMUser {
    
    public String getEmail();

    public void setEmail(String email);

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public boolean isLocked();

    public void setLocked(boolean locked);
    
    /** This method must return the SIMUser ID. 
     * @return The SIMUser ID
     */
    public String getID();
    
    
}
