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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Embeddable
public class StatePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"C_MODEL\"")
    private int cModel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"C_STATE\"")
    private int cState;

    public StatePK() {
    }

    public StatePK(int cModel, int cState) {
        this.cModel = cModel;
        this.cState = cState;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cModel;
        hash += (int) cState;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatePK)) {
            return false;
        }
        StatePK other = (StatePK) object;
        if (this.cModel != other.cModel) {
            return false;
        }
        if (this.cState != other.cState) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.StatePK[ cModel=" + cModel + ", cState=" + cState + " ]";
    }
    
}
