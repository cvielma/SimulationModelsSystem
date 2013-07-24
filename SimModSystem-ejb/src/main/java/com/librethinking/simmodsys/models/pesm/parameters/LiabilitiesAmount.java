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
import java.util.Collection;

/**
 * This parameter represents the current Amount in Liabilities
 * This is for the PESM (Personal Economy Simulation Model)
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */
public class LiabilitiesAmount extends PESMParameter {
    public static final String NAME = "LIABILITIES.AMOUNT";
    public static final boolean UNIQUE = true;
    public static final double MINVALUE = 0.0;
    public static final double MAXVALUE = Double.MAX_VALUE;
    public static final double DEFAULTVALUE = 0.0;
    
    private int status = 1;
    private double amount = DEFAULTVALUE;

     /** This method returns the name of the SIMParameter.
     * 
     * @return The SIMParameter's name
     */
    @Override    
    public String getName() {
        return NAME;
    }

    /** Returns the value stored in this parameter, in this case, the amount of
     * the Liabilities. 
     * 
     * @return Collection of one Double object (with the current amount of the Liabilities).
     */
    @Override
    public Collection<Object> getValue() {
        Collection<Object> returnArray = new ArrayList<Object>();
        returnArray.add(this.getAmount());
        return returnArray;
    }

    
    /** Sets the value of the current amount of the Liabilities. This is is similar to 
     * {@link getAmount}, but this is the general version to comply with the
     * interface SIMParameter.
     * 
     * @param value shoud be a Collection of one Double. 
     * 
     * @throws ClassCastException when passed value is wrong (i.e.: Collection is 
     * larger than one element or contained object cannot be interpreted as Double)
     * @throws NullPointerException if the value passed is null.
     * @throws ValueOutOfBoundsException if value to be set is not between MINVALUE and MAXVALUE.
     */
    @Override
    public void setValue(Collection<Object> value) {
        if(value==null){
            throw new NullPointerException("Value to be set is null.");
        }
        else if(value.size()!=1){
            throw new ClassCastException("'"+NAME+"' expects one and only one value. "
                    + "See documentation for reference.");
        }
        else{
            try{
                this.setAmount((Double) value.toArray()[0]);                
            }
            catch(ClassCastException e){
                throw new ClassCastException("'"+NAME+"' expects a 'double' and passed "
                        + "value was of type: '"+ (value.toArray()[0]).getClass().getName()+"'.");
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
     * Because this parameter only have a MaxValue, it will return a Collection 
     * with only a value of type double.
     * 
     * @return Collection of one Double.
     */
    @Override
    public Collection<Object> getMaxValue() {
        Collection<Object> returnArray = new ArrayList<Object>();
        returnArray.add(MAXVALUE);
        return returnArray;
    }

    /** Returns the MinValue established for the SIMParameter.
     * Because this parameter only have a MinValue, it will return a Collection 
     * with only a value of type double.
     * 
     * @return Collection of one Double.
     */
    @Override
    public Collection<Object> getMinValue() {
        Collection<Object> returnArray = new ArrayList<Object>();
        returnArray.add(MINVALUE);
        return returnArray;
    }

    /** @returns true if the SIMParameter is unique (no repeatable), false otherwise.
     */
    @Override
    public boolean isUnique() {
       return UNIQUE;
    }

    /** Returns the DefaultValue established for the SIMParameter.
     * Because this parameter only have a DefaultValue, it will return a Collection 
     * with only a value of type double.
     * 
     * @return Collection of one Double.
     */
    @Override
    public Collection<Object> getDefaultValue() {
        Collection<Object> returnArray = new ArrayList<Object>();
        returnArray.add(DEFAULTVALUE);
        return returnArray;
    }   
    
    /** This method is convenience method to return the amount associated without
     * having to cast the result. Note that this method isn't part of the interface.
     * 
     * @returns Liabilities Amount value.
     */   
    public double getAmount(){
      return this.amount;
    }
    
     /** This method is convenience method to set the amount associated without
     * having to cast the parameter or having to put it as a Collection.
     * Note that this method isn't part of the interface.
     * 
     * @param amount with the Liabilities Amount value.
     * @throws ValueOutOfBoundsException if value to be set is not between MINVALUE and MAXVALUE.
     */
    public void setAmount(double amount){
      if(amount>= this.MINVALUE && amount <= this.MAXVALUE){
        this.amount = amount;
      }
       else{
           StringBuilder sb = new StringBuilder();
           sb.append("Amount passed: ").append(amount);
           sb.append(" is not in range: MIN ").append(this.MINVALUE);
           sb.append(" and MAX ").append(this.MAXVALUE);
           sb.append(".");
           throw new ValueOutOfBoundsException(sb.toString());
       }
    }
    
     @Override
    public boolean equals(Object obj){
        if (getClass() != this.getClass()){return false;}
        
        LiabilitiesAmount myparam = (LiabilitiesAmount) obj;
        
        return myparam.getAmount() == this.getAmount() &&
                myparam.getStatus() == this.getStatus();
    }
    
    @Override
    public int hashCode(){
        return (int) (this.getAmount() + this.getStatus());
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[Name: '").append(this.NAME).append("', MinValue: ");
        sb.append(this.MINVALUE).append(", MaxValue: ").append(this.MAXVALUE);
        sb.append(", DefaultValue: ").append(this.DEFAULTVALUE);
        sb.append(", Status: ").append(this.status).append(", AMOUNT: ");
        sb.append(this.amount).append(".");
        return sb.toString();
    }
    


    
}
