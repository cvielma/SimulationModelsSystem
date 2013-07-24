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
package com.librethinking.simmodsys.web.adapters;

import java.util.Enumeration;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;

/**
 * This class is a helper class to treat a HTTPRequest in order to obtain the 
 * information from it.
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class RequestParser {
    
    private TreeMap<String, TreeMap<String, String>> parser;
    
    /** 
     * This method initializes the RequestParser putting all the parameters that
     * start with <code>prefix</code> at the root (not shown), a second level with the parameters
     * name, and a third level with the <code>suffix</code>. 
     * 
     * For the root the parsing will take all the string from the beginning of the parameter's
     * name to <code>prefix</code> and for the third level, it will contain all the string
     * since the <code>suffix</code> to the end.
     * 
     * e.g.: 
     * prefix = "initial.";
     * suffix = ".param";
     * //request has a parameter with name initial.asset.amount.param0
     * //caling this method will return a RequestParser with a level with "asset.amount" 
     * and a second level with ".param0".
     * 
     * @param request With the HTTPRequest object.
     * @param prefix String representing the prefix of the parameter.
     * @param suffix String representing the suffix of the parameter.
     * 
     */
    public RequestParser(HttpServletRequest request, String prefix, String suffix){
        Enumeration<String> parameters = request.getParameterNames(); 
        TreeMap<String, TreeMap<String, String>> paramFields = new TreeMap<>();
                
        while(parameters.hasMoreElements()){
            String s = parameters.nextElement();
            if(s.contains(prefix) && s.contains(suffix)){
                
                int start = s.indexOf(prefix) + prefix.length();
                int end = s.indexOf(suffix) + suffix.length()+1;
                String param = s.substring(start, s.indexOf(suffix));
                String field = s.substring(s.indexOf(suffix), end);
                String value = request.getParameter(s);
                
                TreeMap<String, String> fieldValue = paramFields.get(param);
                if(fieldValue!=null){fieldValue.put(field, value);}
                else{
                    fieldValue = new TreeMap<>();
                    fieldValue.put(field, value);
                }
                
                paramFields.put(param, fieldValue);                
            }
            this.parser = paramFields;            
        }
    }
    
    /** This method returns a TreeMap with the params and values corresponding to
     * the given String.
     * @param name String with the name of the key to be searched.
     * @returns <code>null</code> if key not found, and a TreeMap<String,String> otherwise.
     */
    public TreeMap<String, String> get(String name){
        return this.parser.get(name);
    }
    
    /** This method returns the complete TreeMap representing the RequestParser
     * @return TreeMap with the parameters and values.
     */
    public TreeMap<String, TreeMap<String, String>> getParams(){
        return this.parser;
    }
    
}
