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

import com.librethinking.simmodsys.SIMUser;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Entity
@Table(name = "\"USER\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByCUser", query = "SELECT u FROM User u WHERE u.cUser = :cUser"),
    @NamedQuery(name = "User.findByNmFirstname", query = "SELECT u FROM User u WHERE u.nmFirstname = :nmFirstname"),
    @NamedQuery(name = "User.findByNmLastname", query = "SELECT u FROM User u WHERE u.nmLastname = :nmLastname"),
    @NamedQuery(name = "User.findByVlEmail", query = "SELECT u FROM User u WHERE u.vlEmail = :vlEmail"),
    @NamedQuery(name = "User.findByVlLocked", query = "SELECT u FROM User u WHERE u.vlLocked = :vlLocked")})
public class User implements Serializable, SIMUser {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "\"C_USER\"")
    private String cUser;
    @Size(max = 20)
    @Column(name = "\"NM_FIRSTNAME\"")
    private String nmFirstname;
    @Size(max = 20)
    @Column(name = "\"NM_LASTNAME\"")
    private String nmLastname;
    @Size(max = 20)
    @Column(name = "\"VL_EMAIL\"")
    private String vlEmail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"VL_LOCKED\"")
    private boolean vlLocked;
    @Column(name = "\"VL_PASSWORD\"")
    @Size(max = 20)
    private String vlPassword;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Model> modelCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Report> reportCollection;

    public User() {
    }

    public User(String cUser) {
        this.cUser = cUser;
    }

    public User(String cUser, boolean vlLocked) {
        this.cUser = cUser;
        this.vlLocked = vlLocked;
    }

    public String getCUser() {
        return cUser;
    }

    public void setCUser(String cUser) {
        this.cUser = cUser;
    }

    public String getNmFirstname() {
        return nmFirstname;
    }

    public void setNmFirstname(String nmFirstname) {
        this.nmFirstname = nmFirstname;
    }

    public String getNmLastname() {
        return nmLastname;
    }

    public void setNmLastname(String nmLastname) {
        this.nmLastname = nmLastname;
    }

    public String getVlEmail() {
        return vlEmail;
    }

    public void setVlEmail(String vlEmail) {
        this.vlEmail = vlEmail;
    }

    public boolean getVlLocked() {
        return vlLocked;
    }

    public void setVlLocked(boolean vlLocked) {
        this.vlLocked = vlLocked;
    }
    
    public String getVlPassword() {
        return vlPassword;
    }

    public void setVlPassword(String vlPassword) {
        this.vlPassword = vlPassword;
    }

    @XmlTransient
    public Collection<Model> getModelCollection() {
        return modelCollection;
    }

    public void setModelCollection(Collection<Model> modelCollection) {
        this.modelCollection = modelCollection;
    }

    @XmlTransient
    public Collection<Report> getReportCollection() {
        return reportCollection;
    }

    public void setReportCollection(Collection<Report> reportCollection) {
        this.reportCollection = reportCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cUser != null ? cUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.cUser == null && other.cUser != null) || (this.cUser != null && !this.cUser.equals(other.cUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.User[ cUser=" + cUser + " ]";
    }

    @Override
    public String getEmail() {
        return this.getVlEmail();
    }

    @Override
    public void setEmail(String email) {
        this.setVlEmail(email);
    }

    @Override
    public String getFirstName() {
        return this.getNmFirstname();
    }

    @Override
    public void setFirstName(String firstName) {
        this.setNmFirstname(firstName);
    }

    @Override
    public String getLastName() {
        return this.getNmLastname();
    }

    @Override
    public void setLastName(String lastName) {
        this.setNmLastname(lastName);
    }

    @Override
    public boolean isLocked() {
        return this.getVlLocked();
    }

    @Override
    public void setLocked(boolean locked) {
        this.setLocked(locked);
    }

    @Override
    public String getID() {
        return this.getCUser();
    }
    
}
