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
package com.librethinking.simmodsys.exceptions;

/**
 * This Exception represents a problem with the type of Parameters to
 * be assigned to States. 
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class InvalidParameterException extends RuntimeException{

    public InvalidParameterException(){
        super("Tried to assign invalid Parameter. Check Reference.");
    }
    
    public InvalidParameterException(String err) {
        super(err);
    }
    
}
