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
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Entity
@Table(name = "\"STATE\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "State.findAll", query = "SELECT s FROM State s"),
    @NamedQuery(name = "State.findByCModel", query = "SELECT s FROM State s WHERE s.statePK.cModel = :cModel"),
    @NamedQuery(name = "State.findByCState", query = "SELECT s FROM State s WHERE s.statePK.cState = :cState")})
public class State implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StatePK statePK;
   
    @JoinColumn(name = "`C_MODEL`", referencedColumnName ="`C_MODEL`", insertable = false, updatable = false)
    @ManyToOne(optional = false) 
    private Model model;
   
    @JoinColumn(name = "\"TP_STATE\"")
    @ManyToOne(optional = false)
    private AdmTypes admTypes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "state")
    private Collection<StateParameter> stateParameterCollection;

    public State() {
    }

    public State(StatePK statePK) {
        this.statePK = statePK;
    }

    public State(int cModel, int cState) {
        this.statePK = new StatePK(cModel, cState);
    }

    public StatePK getStatePK() {
        return statePK;
    }

    public void setStatePK(StatePK statePK) {
        this.statePK = statePK;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public AdmTypes getAdmTypes() {
        return admTypes;
    }

    public void setAdmTypes(AdmTypes admTypes) {
        this.admTypes = admTypes;
    }

    @XmlTransient
    public Collection<StateParameter> getStateParameterCollection() {
        return stateParameterCollection;
    }

    public void setStateParameterCollection(Collection<StateParameter> stateParameterCollection) {
        this.stateParameterCollection = stateParameterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statePK != null ? statePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof State)) {
            return false;
        }
        State other = (State) object;
        if ((this.statePK == null && other.statePK != null) || (this.statePK != null && !this.statePK.equals(other.statePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.State[ statePK=" + statePK + " ]";
    }
    
}
