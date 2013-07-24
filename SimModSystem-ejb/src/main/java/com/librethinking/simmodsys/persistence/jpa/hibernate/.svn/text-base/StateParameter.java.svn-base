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
package com.librethinking.simmodsys.persistence.jpa.hibernate;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Entity
@Table(name = "\"STATE_PARAMETER\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StateParameter.findAll", query = "SELECT s FROM StateParameter s"),
    @NamedQuery(name = "StateParameter.findByCModel", query = "SELECT s FROM StateParameter s WHERE s.stateParameterPK.cModel = :cModel"),
    @NamedQuery(name = "StateParameter.findByCState", query = "SELECT s FROM StateParameter s WHERE s.stateParameterPK.cState = :cState"),
    @NamedQuery(name = "StateParameter.findByCParam", query = "SELECT s FROM StateParameter s WHERE s.stateParameterPK.cParam = :cParam"),
    @NamedQuery(name = "StateParameter.findByCValue", query = "SELECT s FROM StateParameter s WHERE s.stateParameterPK.cValue = :cValue"),
    @NamedQuery(name = "StateParameter.findByGroup", query = "SELECT s FROM StateParameter s WHERE s.state.statePK.cState = :cState and s.parameter.nmParam = :nmParam")})
public class StateParameter implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StateParameterPK stateParameterPK;
    @Size(max = 20)
    @Column(name = "\"VL_VALUE\"")
    private String vlValue;    
    @JoinColumns({
        @JoinColumn(name = "`C_MODEL`", insertable = false, updatable = false),
        @JoinColumn(name = "`C_STATE`", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private State state;
    @JoinColumn(name = "`C_PARAM`", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Parameter parameter;

    public StateParameter() {
    }

    public StateParameter(StateParameterPK stateParameterPK) {
        this.stateParameterPK = stateParameterPK;
    }

    public StateParameter(int cModel, int cState, int cParam, int cValue) {
        this.stateParameterPK = new StateParameterPK(cModel, cState, cParam, cValue);
    }

    public StateParameterPK getStateParameterPK() {
        return stateParameterPK;
    }

    public void setStateParameterPK(StateParameterPK stateParameterPK) {
        this.stateParameterPK = stateParameterPK;
    }

    public String getVlValue() {
        return vlValue;
    }

    public void setVlValue(String vlValue) {
        this.vlValue = vlValue;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stateParameterPK != null ? stateParameterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StateParameter)) {
            return false;
        }
        StateParameter other = (StateParameter) object;
        
        try{
            if (this.getStateParameterPK().equals(other.getStateParameterPK())) {
                return true;
            }
            else{return false;}
        }catch(NullPointerException e){return false;}
        
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.StateParameter[ stateParameterPK=" + stateParameterPK + " ]";
    }
    
}
