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
@Table(name = "\"USER_PREFERENCES\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserPreferences.findAll", query = "SELECT u FROM UserPreferences u"),
    @NamedQuery(name = "UserPreferences.findByCUser", query = "SELECT u FROM UserPreferences u WHERE u.userPreferencesPK.cUser = :cUser"),
    @NamedQuery(name = "UserPreferences.findByTpPref", query = "SELECT u FROM UserPreferences u WHERE u.userPreferencesPK.tpPref = :tpPref"),
    @NamedQuery(name = "UserPreferences.findByVlPref", query = "SELECT u FROM UserPreferences u WHERE u.vlPref = :vlPref")})
public class UserPreferences implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserPreferencesPK userPreferencesPK;
    @Size(max = 2147483647)
    @Column(name = "\"VL_PREF\"")
    private String vlPref;
    @JoinColumn(name = "TP_PREF", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AdmTypes admTypes;

    public UserPreferences() {
    }

    public UserPreferences(UserPreferencesPK userPreferencesPK) {
        this.userPreferencesPK = userPreferencesPK;
    }

    public UserPreferences(String cUser, String tpPref) {
        this.userPreferencesPK = new UserPreferencesPK(cUser, tpPref);
    }

    public UserPreferencesPK getUserPreferencesPK() {
        return userPreferencesPK;
    }

    public void setUserPreferencesPK(UserPreferencesPK userPreferencesPK) {
        this.userPreferencesPK = userPreferencesPK;
    }

    public String getVlPref() {
        return vlPref;
    }

    public void setVlPref(String vlPref) {
        this.vlPref = vlPref;
    }

    public AdmTypes getAdmTypes() {
        return admTypes;
    }

    public void setAdmTypes(AdmTypes admTypes) {
        this.admTypes = admTypes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userPreferencesPK != null ? userPreferencesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserPreferences)) {
            return false;
        }
        UserPreferences other = (UserPreferences) object;
        if ((this.userPreferencesPK == null && other.userPreferencesPK != null) || (this.userPreferencesPK != null && !this.userPreferencesPK.equals(other.userPreferencesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.UserPreferences[ userPreferencesPK=" + userPreferencesPK + " ]";
    }
    
}
