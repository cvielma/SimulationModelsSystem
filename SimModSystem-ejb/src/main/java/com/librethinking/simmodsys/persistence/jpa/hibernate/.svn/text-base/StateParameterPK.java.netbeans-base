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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Embeddable
public class StateParameterPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"C_MODEL\"")
    private int cModel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"C_STATE\"")
    private int cState;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"C_PARAM\"")
    private int cParam;
    @Basic(optional = false)
    @NotNull     
    @Column(name = "\"C_VALUE\"")
    private int cValue;

    public StateParameterPK() {
    }

    public StateParameterPK(int cModel, int cState, int cParam, int cValue) {
        this.cModel = cModel;
        this.cState = cState;
        this.cParam = cParam;
        this.cValue = cValue;
    }

    public int getCModel() {
        return cModel;
    }

    public void setCModel(int cModel) {
        this.cModel = cModel;
    }

    public int getCState() {
        return cState;
    }

    public void setCState(int cState) {
        this.cState = cState;
    }

    public int getCParam() {
        return cParam;
    }

    public void setCParam(int cParam) {
        this.cParam = cParam;
    }

    public int getCValue() {
        return cValue;
    }

    public void setCValue(int cValue) {
        this.cValue = cValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cModel;
        hash += (int) cState;
        hash += (int) cParam;
        hash += (int) cValue;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StateParameterPK)) {
            return false;
        }
        StateParameterPK other = (StateParameterPK) object;
        if (this.cModel != other.cModel) {
            return false;
        }
        if (this.cState != other.cState) {
            return false;
        }
        if (this.cParam != other.cParam) {
            return false;
        }
        if (this.cValue != other.cValue) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.StateParameterPK[ cModel=" + cModel + ", cState=" + cState + ", cParam=" + cParam + ", cValue=" + cValue + " ]";
    }
    
}
