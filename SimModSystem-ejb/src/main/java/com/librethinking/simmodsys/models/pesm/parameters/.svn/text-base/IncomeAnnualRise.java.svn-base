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
package com.librethinking.simmodsys.models.pesm.parameters;

import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.exceptions.ValueOutOfBoundsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * This parameter represents Annual Estimated Rise in the income. 
 * This is for the PESM (Personal Economy Simulation Model)
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class IncomeAnnualRise extends PESMParameter  {
    public static final String NAME = "INCOME.ANNUALRISE";
    public static final boolean UNIQUE = false;
    public static final Object[] MINVALUE = {"", 0.0, 0.0, 0.0};
    public static final Object[] MAXVALUE = {"", 1.0, 1.0, 1.0};
    public static final Object[] DEFAULTVALUE = {"", 0.0, 0.0, 0.0};
    public static final byte CATEGORYINDEX = 0;
    public static final byte MINPERCINDEX = 1;
    public static final byte MAXPERCINDEX = 2;
    public static final byte CURRVALINDEX = 3;
    
    private int status = 1;
    private String category = (String) DEFAULTVALUE[CATEGORYINDEX];
    private double minPercentage = (Double) DEFAULTVALUE[MINPERCINDEX];
    private double maxPercentage = (Double) DEFAULTVALUE[MAXPERCINDEX];
    private double currentValue = (Double) DEFAULTVALUE[CURRVALINDEX];

   /** This method returns the name of the SIMParameter.
     * 
     * @return The SIMParameter's name
     */
    @Override    
    public String getName() {
        return NAME;
    }

    /** Returns the value stored in this parameter, in this case,the category, minPercentage,
     * maxPercentage and currentValue.     
     * 
     * @return Collection of four objects (String category, Double minPercentage, Double 
     * maxPercentage, Double currentValue).
     */
    @Override
    public Collection<Object> getValue() {
        Collection<Object> returnArray = new ArrayList<Object>();
        returnArray.add(this.getCategory());
        returnArray.add(this.getMinPercentage());
        returnArray.add(this.getMaxPercentage());
        returnArray.add(this.getCurrentValue());              
        return returnArray;
    }

     /** Sets the value of the category, minpercentage, maxpercentage and currentvalue at once.
      * This is is similar to call independently {@link setCategory},  {@link setMinPercentage}
      * {@link setMaxPercentage} and {@link setCurrentValue}, but this is the general version to 
      * comply with the SIMParameter interface.
      * 
      * @param value shoud be a Collection of four Objects (String, Double, Double, Double)
      * 
      * @throws ClassCastException when passed value is wrong (i.e.: Collection is 
      * larger than one element or contained object cannot be interpreted as expected)
      * @throws NullPointerException if the value passed is null.
      * @throws ValueOutOfBoundsException if value to be set is not between MINVALUE and MAXVALUE.
     */
    @Override
    public void setValue(Collection<Object> value) {
        if(value==null){
            throw new NullPointerException("Value to be set is null.");
        }
        else if(value.size()!=4){
            throw new ClassCastException("'"+NAME+"' expects four values. "
                    + "See documentation for reference.");
        }
        else{
            try{
                this.setCategory((String) value.toArray()[CATEGORYINDEX]);                
                this.setMinPercentage((Double) value.toArray()[MINPERCINDEX]);
                this.setMaxPercentage((Double) value.toArray()[MAXPERCINDEX]);
                this.setCurrentValue((Double) value.toArray()[CURRVALINDEX]);
            }
            catch(ClassCastException e){
                throw new ClassCastException("'"+NAME+"' expects a '[String, Double, Double, Double]' "
                        + "and passed value was of type: '["+ (value.toArray()[CATEGORYINDEX]).getClass().getName()+", "+
                        (value.toArray()[MINPERCINDEX]).getClass().getName()+", "+
                        (value.toArray()[MAXPERCINDEX]).getClass().getName()+", "+
                        (value.toArray()[CURRVALINDEX]).getClass().getName()+"]'.");
            }            
        }
    }

   /** Returns the current Status of this SIMParameter (e.g.: Disabled (currently 1) 
     * or Enabled (currently 0).
     * @returns Int with the value of the estatus. @see Status Reference
     */
    @Override
    public int getStatus() {
        return this.status;
    }

/** Sets the current Status of this SIMParameter.
     * @param status the value of the new status.
     */
    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    /** Returns the MaxValue established for the SIMParameter.
     * Because this parameter have two values for MaxValue, it will return a Collection 
     * with four values (String, Double, Double, Double).
     * 
     * @return Collection of (String, Double, Double, Double).
     */
    @Override
    public Collection<Object> getMaxValue() {
        return new ArrayList(Arrays.asList(MAXVALUE));
    }

    /** Returns the DefaultValue established for the SIMParameter.
     * Because this parameter have two values for MinValue, it will return a Collection 
     * with four values (String, Double, Double, Double).
     * 
     * @return Collection of (String, Double, Double, Double).
     */
    @Override
    public Collection<Object> getMinValue() {
        return new ArrayList(Arrays.asList(MINVALUE));
    }

    /** @returns true if the SIMParameter is unique (no repeatable), false otherwise.
     */
    @Override
    public boolean isUnique() {
        return UNIQUE;
    }
    
        
     /** This method is convenience method to return the category associated without
     * having to cast the result. Note that this method isn't part of the interface.
     * 
     * @returns String with the category.
     */   
    public String getCategory(){
      return this.category;
    }
    
     /** This method is convenience method to set the category associated without
     * having to cast the parameter or having to put it as a Collection.
     * Note that this method isn't part of the interface.
     * 
     * @param category with the category value.
     * @throws ValueOutOfBounds if parameter passed is null. 
     */
    public void setCategory(String category){
      if(category!=null){
        this.category = category;
      }
       else{
           StringBuilder sb = new StringBuilder();
           sb.append("Category passed: '").append(category);
           sb.append("' cannot be 'null'.");           
           throw new ValueOutOfBoundsException(sb.toString());
       }  
    }      
    
     /** This method is convenience method to return the current value associated without
     * having to cast the result. Note that this method isn't part of the interface.
     * 
     * @returns double with the current value.
     */  
    public double getCurrentValue(){
      return this.currentValue;
    }    
    
     /** This method is convenience method to set the current value associated without
     * having to cast the parameter or having to put it as a Collection.
     * Note that this method isn't part of the interface.
     * 
     * @param category with the current value.
    * @throws ValueOutOfBoundsException if value to be set is not between MINVALUE and MAXVALUE.
     */
    public void setCurrentValue(double current){
      if(current>= (Double)this.MINVALUE[CURRVALINDEX] && current <= (Double) this.MAXVALUE[CURRVALINDEX]){
        this.currentValue = current;
      }
       else{
           StringBuilder sb = new StringBuilder();
           sb.append("Current Value passed: ").append(current);
           sb.append(" is not in range: MIN ").append(this.MINVALUE[CURRVALINDEX]);
           sb.append(" and MAX ").append(this.MAXVALUE[CURRVALINDEX]);
           sb.append(".");
           throw new ValueOutOfBoundsException(sb.toString());
       }
       
    }
    
     /** This method is convenience method to return the minPercentage associated without
     * having to cast the result. Note that this method isn't part of the interface.
     * 
     * @returns double with the minpercentage.
     */  
    public double getMinPercentage(){
      return this.minPercentage;
    }
    
    /** This method is convenience method to return the max percentage associated without
     * having to cast the result. Note that this method isn't part of the interface.
     * 
     * @returns double with the max percentage.
     */  
    public double getMaxPercentage(){
      return this.maxPercentage;
    }
    
     /** This method is convenience method to set the min percentage associated without
     * having to cast the parameter or having to put it as a Collection.
     * Note that this method isn't part of the interface.
     * 
     * @param minperc with the min percentage value.
     * @throws ValueOutOfBoundsException if value to be set is not between MINVALUE and MAXVALUE.
     */
    public void setMinPercentage(double minperc){
      if(minperc>= (Double)this.MINVALUE[MINPERCINDEX] && minperc <= (Double) this.MAXVALUE[MINPERCINDEX]){
        this.minPercentage = minperc;
      }
       else{
           StringBuilder sb = new StringBuilder();
           sb.append("MinPercentage passed: ").append(minperc);
           sb.append(" is not in range: MIN ").append(this.MINVALUE[MINPERCINDEX]);
           sb.append(" and MAX ").append(this.MAXVALUE[MINPERCINDEX]);
           sb.append(".");
           throw new ValueOutOfBoundsException(sb.toString());
       }
      
    }
    
     /** This method is convenience method to set the max percentage associated without
     * having to cast the parameter or having to put it as a Collection.
     * Note that this method isn't part of the interface.
     * 
     * @param maxperc with the max percentage value.
    * @throws ValueOutOfBoundsException if value to be set is not between MINVALUE and MAXVALUE.
     */
    public void setMaxPercentage(double maxperc){
      if(maxperc>= (Double)this.MINVALUE[MAXPERCINDEX] && maxperc <= (Double) this.MAXVALUE[MAXPERCINDEX]){
        this.maxPercentage = maxperc;
      }
       else{
           StringBuilder sb = new StringBuilder();
           sb.append("Max Percentage passed: ").append(maxperc);
           sb.append(" is not in range: MIN ").append(this.MINVALUE[MAXPERCINDEX]);
           sb.append(" and MAX ").append(this.MAXVALUE[MAXPERCINDEX]);
           sb.append(".");
           throw new ValueOutOfBoundsException(sb.toString());
       }
        
    }

    /** Returns the DefaultValue established for the SIMParameter.
     * Because this parameter have four values for DefaultValue, it will return a Collection 
     * with four values (String. String, Double, Integer).
     * 
     * @return Collection of (String. String, Double, Integer).
     */
    @Override
    public Collection<Object> getDefaultValue() {
        return new ArrayList(Arrays.asList(DEFAULTVALUE));
    }
    
     @Override
    public boolean equals(Object obj){
        if (getClass() != this.getClass()){return false;}
        
        IncomeAnnualRise myparam = (IncomeAnnualRise) obj;
        
        return myparam.getMinPercentage() == this.getMinPercentage() &&
                myparam.getMaxPercentage() == this.getMaxPercentage() &&
                myparam.getCategory().equals(this.getCategory()) &&
                myparam.getCurrentValue() == this.getCurrentValue() &&
                myparam.getStatus() == this.getStatus();
    }
    
    @Override
    public int hashCode(){
        return (int) (this.getMinPercentage() + this.getMaxPercentage() + 
                this.getCurrentValue() + this.getCategory().hashCode() + this.getStatus());
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[Name: '").append(this.NAME).append("', MinValue: ");
        sb.append(this.MINVALUE).append(", MaxValue: ").append(this.MAXVALUE);
        sb.append(", DefaultValue: ").append(this.DEFAULTVALUE);
        sb.append(", Status: ").append(this.status);
        sb.append(", CATEGORY: ").append(this.category);
        sb.append(", MINPERCENTAGE: ").append(this.minPercentage);
        sb.append(", MAXPERCENTAGE: ").append(this.maxPercentage);
        sb.append(", CURRENTVALUE: ").append(this.currentValue);
        sb.append(".");
        return sb.toString();
    }
    

    
}
