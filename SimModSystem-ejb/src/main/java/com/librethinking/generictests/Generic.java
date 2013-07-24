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
package com.librethinking.generictests;

import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.exceptions.business.FunctionalException;
import com.librethinking.simmodsys.exceptions.business.TechnicalException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class Generic {
    
    public static void main(String args[]){
        TreeMap<String, TreeMap<String,String>> myBigTree = new TreeMap<>();
        TreeMap<String, String> myTree = new TreeMap<>();
        
        
        myTree.put("param0", "1");
        myTree.put("param1", "2");
        myTree.put("param2", "3");
        myBigTree.put("test", myTree);
        
        myTree= new TreeMap<>();
        myTree.put("param0", "4");
        myTree.put("param1", "5");
        myTree.put("param2", "6");
        
        myBigTree.put("test", myTree);
        
        myTree = new TreeMap<>();
        myTree.put("param0", "1");  
        
        myBigTree.put("test1", myTree);
        
        System.out.println(myBigTree);
        
        
    }
    
}
