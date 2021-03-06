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
 * This parameter represents the monthly fixed expenses that will 
 * be subtracted from the Assets. This is for the PESM (Personal Economy Simulation Model)
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class ExpenseFixed extends PESMParameter {
    public static final String NAME = "EXPENSE.FIXEDEXP";
    public static final boolean UNIQUE = false;
    public static final Object[] MINVALUE = {"", 0.0};
    public static final Object[] MAXVALUE = {"", Double.MAX_VALUE};
    public static final Object[] DEFAULTVALUE = {"", 0.0};
    public static final byte CATEGORYINDEX = 0;
    public static final byte AMOUNTINDEX = 1;
    
    
    
    private int status = 1;
    private String category = (String) DEFAULTVALUE[CATEGORYINDEX];
    private double amount = (Double) DEFAULTVALUE[AMOUNTINDEX];
    
    
     /** This method returns the name of the SIMParameter.
     * 
     * @return The SIMParameter's name
     */
    @Override    
    public String getName() {
        return NAME;
    }

    /** Returns the value stored in this parameter, in this case,the category, 
     * and amount.
     * 
     * @return Collection of two objects (String category, Double amount).
     */
    @Override
    public Collection<Object> getValue() {
        Collection<Object> returnArray = new ArrayList<Object>();
        returnArray.add(this.getCategory());
        returnArray.add(this.getAmount());        
        return returnArray;
    }

     /** Sets the value of the category and amount at once. 
      * This is is similar to call independently {@link setCategory} and
      * {@link setAmount}, but this is the general version to 
      * comply with the SIMParameter interface.
      * 
      * @param value shoud be a Collection of two Objects (String, Double)
      * 
      * @throws ClassCastException when passed value is wrong (i.e.: Collection is 
      * larger than one element or contained object cannot be interpreted as expected)
      * @throws NullPointerException if the value passed is null.
     */
    @Override
    public void setValue(Collection<Object> value) {
        if(value==null){
            throw new NullPointerException("Value to be set is null.");
        }
        else if(value.size()!=2){
            throw new ClassCastException("'"+NAME+"' expects two values. "
                    + "See documentation for reference.");
        }
        else{
            try{
                this.setCategory((String) value.toArray()[CATEGORYINDEX]);                
                this.setAmount((Double) value.toArray()[AMOUNTINDEX]);                                
            }
            catch(ClassCastException e){
                throw new ClassCastException("'"+NAME+"' expects a '[String, Double]' "
                        + "and passed value was of type: '["+ (value.toArray()[CATEGORYINDEX]).getClass().getName()+", "+
                        (value.toArray()[AMOUNTINDEX]).getClass().getName()+"]'.");                        
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
     * with two values (String, Double).
     * 
     * @return Collection of (String, Double).
     */
    @Override
    public Collection<Object> getMaxValue() {
        return new ArrayList(Arrays.asList(MAXVALUE));
    }

    /** Returns the DefaultValue established for the SIMParameter.
     * Because this parameter have two values for MinValue, it will return a Collection 
     * with two values (String, Double).
     * 
     * @return Collection of (String, Double).
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
    
    /** This method is convenience method to return the amount associated without
     * having to cast the result. Note that this method isn't part of the interface.
     * 
     * @returns double with the amount.
     */   
    public double getAmount(){
      return this.amount;
    }
    
     /** This method is convenience method to set the amount associated without
     * having to cast the parameter or having to put it as a Collection.
     * Note that this method isn't part of the interface.
     * 
     * @param amount with the amount value.
     * @throws ValueOutOfBoundsException if value to be set is not between MINVALUE and MAXVALUE.
     */
    public void setAmount(double amount){
      if(amount>= (Double)this.MINVALUE[AMOUNTINDEX] && amount <= (Double) this.MAXVALUE[AMOUNTINDEX]){
        this.amount = amount;
      }
       else{
           StringBuilder sb = new StringBuilder();
           sb.append("Amount passed: ").append(amount);
           sb.append(" is not in range: MIN ").append(this.MINVALUE[AMOUNTINDEX]);
           sb.append(" and MAX ").append(this.MAXVALUE[AMOUNTINDEX]);
           sb.append(".");
           throw new ValueOutOfBoundsException(sb.toString());
       }
    }    

    /** Returns the DefaultValue established for the SIMParameter.
     * Because this parameter have two values for DefaultValue, it will return a Collection 
     * with two values (String, Double).
     * 
     * @return Collection of (String, Double,).
     */
    @Override
    public Collection<Object> getDefaultValue() {
        return new ArrayList(Arrays.asList(DEFAULTVALUE));
    }
    
    @Override
    public boolean equals(Object obj){
        if (getClass() != this.getClass()){return false;}
        
        ExpenseFixed myparam = (ExpenseFixed) obj;
        
        return myparam.getAmount() == this.getAmount() &&
                myparam.getCategory().equals(this.getCategory()) &&
                myparam.getStatus() == this.getStatus();
    }
    
    @Override
    public int hashCode(){
        return (int) (this.getAmount() + this.getStatus() + this.getCategory().hashCode());
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[Name: '").append(this.NAME).append("', MinValue: ");
        sb.append(this.MINVALUE).append(", MaxValue: ").append(this.MAXVALUE);
        sb.append(", DefaultValue: ").append(this.DEFAULTVALUE);
        sb.append(", Status: ").append(this.status);
        sb.append(", CATEGORY: ").append(this.category);
        sb.append(", AMOUNT: ").append(this.amount);
        sb.append(".");
        return sb.toString();
    }
    
    
}
