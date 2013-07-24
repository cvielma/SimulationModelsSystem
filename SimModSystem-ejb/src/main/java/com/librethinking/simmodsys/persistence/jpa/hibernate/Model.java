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
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Entity
@Table(name = "\"MODEL\"")
@XmlRootElement 
@NamedQueries({
    @NamedQuery(name = "Model.findAll", query = "SELECT m FROM Model m"),
    @NamedQuery(name = "Model.findByCUser", query = "SELECT m FROM Model m WHERE m.user.cUser = :cUser ORDER BY m.nmModel "),
    @NamedQuery(name = "Model.findByCModel", query = "SELECT m FROM Model m WHERE m.cModel = :cModel"),
    @NamedQuery(name = "Model.findByCModelAndcUser", query = "SELECT m FROM Model m WHERE m.cModel = :cModel and m.user.cUser = :cUser"),    
    @NamedQuery(name = "Model.findByUnique", query = "SELECT m FROM Model m WHERE m.nmModel = :nmModel and m.user.cUser = :cUser")        
    })
public class Model implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name="modelPKgen", sequenceName="\"MODEL_C_MODEL_seq\"", allocationSize=1)
    @GeneratedValue(generator="modelPKgen", strategy= GenerationType.SEQUENCE)
    @Column(name = "\"C_MODEL\"", unique=true)    
    private int cModel;
    
    @OneToMany(cascade = CascadeType.ALL,  fetch= FetchType.EAGER)//, mappedBy = "model", fetch= FetchType.EAGER)
    @JoinColumn(name= "`C_MODEL`")
    private Collection<State> stateCollection;
       
    @JoinColumn(name = "`C_USER`", referencedColumnName ="`C_USER`", updatable = false)
    @ManyToOne(optional = false)
    private User user;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "\"NM_MODEL\"")
    private String nmModel;     
    
    @JoinColumn(name = "\"TP_MODEL\"")
    @ManyToOne(optional = false)
    private AdmTypes admTypes;

    public Model() {
    }

    public Model(String user, String name){
        this.user= new User(user);
        this.nmModel= name;
    }
    
    public AdmTypes getAdmTypes() {
        return admTypes;
    }

    public void setAdmTypes(AdmTypes admTypes) {
        this.admTypes = admTypes;
    }

    public int getcModel() {
        return cModel;
    }

    public void setcModel(int cModel) {
        this.cModel = cModel;
    }

    public Collection<State> getStateCollection() {
        return stateCollection;
    }

    public void setStateCollection(Collection<State> stateCollection) {
        this.stateCollection = stateCollection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }    
    
    public String getNmModel() {
        return nmModel;
    }

    public void setNmModel(String nmModel) {
        this.nmModel = nmModel;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Model other = (Model) obj;
        if (!Objects.equals(this.cModel, other.cModel)) {
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = this.cModel;        
        return hash;
    }    
    
     
   
}
