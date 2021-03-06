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

import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.exceptions.business.FunctionalException;
import com.librethinking.simmodsys.exceptions.business.TechnicalException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
 
     
     
     /**
 * This Class is an utility class that converts a SIMParameter to a HTML
 * representation.
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class SIMParameterAdapter {
    /** These strings define the styles for the the blocks */
    static private final String PARBLOCKST = ".parameter.block.style";
    static private final String PARNAMEST = ".parameter.name.style";
    static private final String PARVALUEST = ".parameter.value.style";
    static private final String PARMFIELDGROUPST = ".parameter.field.group.style";
    static private final String PARMFIELDST = ".parameter.field.style";
    static private final String PARMFIELDTST = ".parameter.field.title.style";
    static private final String PARMFIELDIST = ".parameter.field.input.style";
    static private final String PARMFIELDRST = ".parameter.field.range.style";
    static private final String FIELDERROR = "fieldError";
    
    /** JS **/
    static private final String VALIDATEFIELDJS = "validateField";
    
    
    /** These strings are used to define input fields that will be validated*/
    static private final String MANDATORY="mand";
    static private final String FIELDV="fieldv.";
        
    /** These are suffixes used for getting ids of parameters and parameters' fields */
    static private final String PARNAMESUF = ".name";
    static private final String PARFIELDSUF = ".param";
    
    //TODO: these have no sense if you are going to create a string for each number
    /** These represent bundle keys for parameter's values*/
    static private final String INTEGERZERO = "pt001.0";
    static private final String INTEGER12 = "pt001.12";
    static private final String INTEGER36 = "pt001.36";
    static private final String INTEGER60 = "pt001.60";
    static private final String INTEGER120 = "pt001.120";
    static private final String INTEGERMAXPOS = "pt001.posinfinite";
    static private final String INTEGERONE = "pt001.1";
    static private final String DOUBLEZERO = "pt002.0";
    static private final String DOUBLEMAXNEG = "pt002.neginfinite";
    static private final String DOUBLEMAXPOS = "pt002.posinfinite";
    static private final String DOUBLEONE = "pt002.1";
    static private final String STRINGEMPTY = "pt003.empty";
    static private final String STRINGMAXLENGTH = "pt003.maxlength.posinfinite";
    static private final String VALUENOTFOUND = "error.valuenotfound";
    
    static private final String ERRORPREF = "error-";
    static private final String ERRORRANGE = "errorRange-";
    static private final String ERRORMANDATORY = "error.field.mandatory";
    static private final String ERROROUTOFRANGE = "error.field.outofrange";
    
    /** These are keys to incorporate in the input field id in order to use to parse*/
    static private final String VALUEINIT = "|*";
    static private final String VALUEEND = "*|";
    
    
    
    
    
    /**This method converts a SIMParameter to an HTML representation
     *
     * @param param Parameter to be transformed.
     * @param fieldprefix This prefix is used to identify the field
     * @param bundleprefix The prefix used in the bundle to uniquely translate the param.
     * Usually the type of model to which <code>param</code> is associated.. 
     * @param bundle ResourceBundle to be used in the transformation.
     * @param updatable indicates if the user can or can't modify the values.
     * @param formName Name of the form that renders the SIMParameter. By Default is "".
     * @param isMand Boolean to identify if the parameter is mandatory or not. By Default is true.
     * 
     * @return A String representing the HTML code associated to the param. If
     * a key cannot be found in the bundle, the key will be shown instead of its
     * value. 
     */
    public static String SIMParameterToHTML
            (SIMParameter param, String fieldprefix, String bundleprefix, ResourceBundle bundle,
            boolean updatable, String formName, boolean isMand){
        StringBuilder sb = new StringBuilder();
        String paramkey = bundleprefix.toLowerCase()+"."+param.getName().toLowerCase();
        //Parameter Block
        sb.append("<div ");
        sb.append(" class=\"");//sb.append("\" style=\"");
        sb.append(bundle.getString(bundleprefix.toLowerCase()+PARBLOCKST));
        sb.append("\">\n");
        
        //Parameter Name
        sb.append("\t<div class=\"");
        sb.append(bundle.getString(bundleprefix.toLowerCase()+PARNAMEST));
        sb.append("\">");
        sb.append(bundle.getString(paramkey+PARNAMESUF));
        sb.append("</div>\n");
        
        //Parameter's fields
        int i =0;
        Object[] defaults = param.getDefaultValue().toArray();
        Object[] maxs = param.getMaxValue().toArray();
        Object[] mins = param.getMinValue().toArray();
        Object[] currents =param.getValue().toArray();
        
        sb.append("\t<div id=\"");
        sb.append(paramkey);
        sb.append("\" class=\"");
        sb.append(bundle.getString(bundleprefix.toLowerCase()+PARMFIELDGROUPST));
        sb.append("\">\n");
        for(Object o : defaults){
            sb.append("\t<div class=\"");
            sb.append(bundle.getString(bundleprefix.toLowerCase()+PARMFIELDST));
            sb.append("\">\n");
            sb.append("\t<div class=\"");
            sb.append(bundle.getString(bundleprefix.toLowerCase()+PARMFIELDTST));
            sb.append("\">\n");
            sb.append(bundle.getString(paramkey+PARFIELDSUF+i));
            String posValues="";
            if(mins[i].equals(maxs[i])){
                    String bundlest = bundle.getString(translateValue(mins[i]));
                    if(bundlest.length()>0){posValues=" ["+bundlest+"] ";} 
                }
                else{ 
                    String bundlestmin = bundle.getString(translateValue(mins[i]));
                    String bundlestmax = bundle.getString(translateValue(maxs[i]));
                    if(bundlestmin.length() >0 || bundlestmax.length()>0){
                        posValues= " ["+bundlestmin+" - "+bundlestmax+"] ";
                }
            }   
            sb.append("\t<span class=\"");
            sb.append(bundle.getString(bundleprefix.toLowerCase()+PARMFIELDRST));
            sb.append("\">\n");
            sb.append(posValues);
            sb.append("</span>\n");
            
            sb.append("</div>\n");
            sb.append("\t<div class=\"");
            sb.append(bundle.getString(bundleprefix.toLowerCase()+PARMFIELDIST));
            sb.append("\">\n");
            sb.append("<table><tr><td>");
            sb.append("<input type=\"text\" value=\"");
            String value="";
            if(!updatable){
                value = (param.getValue().toArray()[i]).toString();
            }
            else{
                value=bundle.getString(translateValue(defaults[i]));
            }
            
            sb.append(value);             
            sb.append("\" id=\"");
            String mand =""; 
            if(isMand){
                    mand = MANDATORY +".";
            }
            String jsPrefix =  mand + VALUEINIT + bundle.getString(FIELDV+translateValue(mins[i])) + VALUEEND
                        + VALUEINIT+bundle.getString(FIELDV+translateValue(maxs[i]))+VALUEEND +".";
            String paramString = fieldprefix+param.getName().toLowerCase() + PARFIELDSUF +i;            
            
            sb.append(jsPrefix);             
            sb.append(paramString);           
            sb.append("\" name=\"");
            sb.append(paramString);           
            sb.append("\" ");
            if(!updatable){ sb.append(" disabled=\"disabled\" ");}
            sb.append("onblur=\"");
            sb.append(VALIDATEFIELDJS);sb.append("("); sb.append("'"); 
            sb.append(paramString); sb.append("',");
            sb.append("'");sb.append(formName); sb.append("');\"/>");
            
            sb.append("</td>\n<td>");
            sb.append("<div style=\"display:none;\" id=\"");
            sb.append(ERRORPREF); sb.append(paramString); sb.append("\" class=\""+FIELDERROR+"\">");
            sb.append(bundle.getString(ERRORMANDATORY));
            sb.append("</div>\n");
            
            sb.append("<div style=\"display:none;\" id=\"");
            sb.append(ERRORRANGE); sb.append(paramString); sb.append("\" class=\""+FIELDERROR+"\">");
            sb.append(bundle.getString(ERROROUTOFRANGE));
            sb.append("</div>\n");
            sb.append("</td>\n</tr>\n</table>\n");
            
            sb.append("</div>\n");
            sb.append("</div>\n");
                        
                i++;
        }
        if(!param.isUnique()&&updatable){
            sb.append("<button type=\"button\" id=\"");
            sb.append(paramkey);
            sb.append("\" onClick=\"addParameterBlock('");
            sb.append(paramkey);
            sb.append("');\">+</button>\n");
        }
        sb.append("</div>\n");
        
        sb.append("</div>\n"); 
        
        return sb.toString();
    }
    
    
    /** 
     * Alternative methods with default values.
     */
      public static String SIMParameterToHTML
            (SIMParameter param, String fieldprefix, String bundleprefix, ResourceBundle bundle,
            boolean updatable){
        return SIMParameterToHTML(param, fieldprefix, bundleprefix, bundle, updatable, "", true);
      }
      
      /** 
     * Alternative methods with default values.
     */
      public static String SIMParameterToHTML
            (SIMParameter param, String fieldprefix, String bundleprefix, ResourceBundle bundle,
            boolean updatable,  String formName){
        return SIMParameterToHTML(param, fieldprefix, bundleprefix, bundle, updatable, formName, true);
      }
    
    /** 
     *  This method translate the parameters values to bundle strings
     *  @param target Object that represents the parameter value.
     *  @returns String that is a bundle key.
     */
    private static String translateValue(Object target){
        String result;
         
            if(target.equals(0.0)){result = DOUBLEZERO;}
            else if(target.equals(-Double.MAX_VALUE)){result = DOUBLEMAXNEG;}
            else if(target.equals(Double.MAX_VALUE)){result = DOUBLEMAXPOS;}
            else if(target.equals(1.0)){result = DOUBLEONE;}
            else if(target.equals(1)){result = INTEGERONE;}
            else if(target.equals(0)){result = INTEGERZERO;}
            else if(target.equals(Integer.MAX_VALUE)){result = INTEGERMAXPOS;}
            else if(target.equals(12)){result = INTEGER12;}
            else if(target.equals(36)){result = INTEGER36;}
            else if(target.equals(60)){result = INTEGER60;}
            else if(target.equals(120)){result = INTEGER120;}
            else if(target.equals("")){result = STRINGEMPTY;}
            //TODO implement else if(target.equals("pt003.MAXLENGTH.POSINFINITE")){ 
            else{result = VALUENOTFOUND;}
            
            return result;
            
    }
    
    /** This method sets a given parameter with the values found in the
     *  Map passed. This map is assumed to be the result of request.getParameterMap(), 
     *  so it will contain keys in the form: 
     *  <code>
     *          "initial.asset.amount.param0" => ["1000.00"]
     *          "final.desired.expense.param0" => ["DesExp1", "DesExp2", "DesExp3"]
     *          "final.desired.expense.param1" => ["Desc1", "Desc2", "Desc3"]
     *          "final.desired.expense.param2" => ["10.0", "2500.00", "1000.00"]
     *  </code>
     * 
     * @param param parameter to be set.
     * @param paramPrefix prefix used to determine the specific parameter in the request
     * @param requestMap Map with the result of request.getParameterMap().
     *
     * @return A set with a new parameter of the same type of <code>param</code>
     * with the values set. If it is unique the set will have size=1, if not, the set 
     * will contain all the params of the same type that could be found in the request.
     * If all the values for the given parameter are empty, the set will be empty (instead of
     * a set with one parameter with empty values).
     * 
     * @throws FunctionalException if the given parameter doesn't have a value set in 
     * the request. Also can throw FunctionalException if the parameters passed do not match 
     * with the expected <code>param</code> parameter.
     * 
     * @throws TechnicalException if there is a problem reflecting the parameter or any
     * other unexpected runtime error.
     */
     public static Set<SIMParameter> MapToSIMParameter(SIMParameter param, String paramPrefix, 
             Map<String, String[]> requestMap) throws FunctionalException {
         try{  
            String key = (paramPrefix + param.getName() + PARFIELDSUF).toLowerCase();
            Object[] paramTypes = param.getDefaultValue().toArray();
            int paramNumber =paramTypes.length;

            Object[][] paramsValues = null;
            Set<SIMParameter> result = new HashSet<>();

            //Extract params values in request and store it in a matrix. Rows will be 
            //each SIMParameter and columns each value of the parameters.
            for(int i=0; i<paramNumber; i++){
                String[] values = requestMap.get(key+i);
                if(paramsValues==null){paramsValues = new Object[values.length][paramNumber];}

                for(int j=0; j<values.length; j++){
                    try{paramsValues[j][i] = convert(values[j], paramTypes[i]);}
                    catch(FunctionalException|TechnicalException ex){
                       throw new FunctionalException("Incorrect type for parameter '"+param.getName()+"': "+ex);
                    }
                }             
            }

            //Setting the SIMParameters
            if(paramsValues!=null){
                for(int i = 0; i<paramsValues.length; i++){
                    //validate if all the values are empty or not.
                    int emptyCount =0;
                    for(int j = 0; j<paramsValues[i].length; j++){
                        if(paramsValues[i][j]==null || paramsValues[i][j].equals("")){emptyCount++;}
                    }
                    //Validating completeness
                    if(emptyCount == paramsValues[i].length){continue;}
                    else if(emptyCount>0 && emptyCount < paramsValues[i].length){
                        throw new FunctionalException("Some values for parameter '"+key+"' were not set.");
                    }
                    //Setting the new parameter
                    SIMParameter newParam = param.getClass().newInstance();
                    newParam.setValue((new ArrayList(Arrays.asList(paramsValues[i]))));
                    result.add(newParam);
                }
            }
            else{throw new FunctionalException("Parameter '"+param.getName()+"' was not set.");}
            
                return result;
          
          }
          catch (InstantiationException|IllegalAccessException  ex) {
              throw new TechnicalException("Error reflecting the parameter: " +ex, ex);
          }
          
          
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
    
    
}
