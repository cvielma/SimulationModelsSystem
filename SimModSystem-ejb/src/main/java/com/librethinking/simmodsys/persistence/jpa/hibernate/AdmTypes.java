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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Entity
@Table(name = "\"ADM_TYPES\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmTypes.findAll", query = "SELECT a FROM AdmTypes a"),
    @NamedQuery(name = "AdmTypes.findByCType", query = "SELECT a FROM AdmTypes a WHERE a.cType = :cType"),
    @NamedQuery(name = "AdmTypes.findByLikeCType", query = "SELECT a FROM AdmTypes a WHERE a.cType like :cType and a.vlStatus = :vlStatus"),
    @NamedQuery(name = "AdmTypes.findByVlType", query = "SELECT a FROM AdmTypes a WHERE a.vlType = :vlType"),
    @NamedQuery(name = "AdmTypes.findByDsType", query = "SELECT a FROM AdmTypes a WHERE a.dsType = :dsType"),
    @NamedQuery(name = "AdmTypes.findByVlStatus", query = "SELECT a FROM AdmTypes a WHERE a.vlStatus = :vlStatus")})
public class AdmTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "\"C_TYPE\"")
    private String cType;
    @Size(max = 50)
    @Column(name = "\"VL_TYPE\"")
    private String vlType;
    @Size(max = 2147483647)
    @Column(name = "\"DS_TYPE\"")
    private String dsType;
    @Size(max = 5)
    @Column(name = "\"VL_STATUS\"")
    private String vlStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admTypes")
    private Collection<UserPreferences> userPreferencesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admTypes")
    private Collection<State> stateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admTypes")
    private Collection<Model> modelCollection;
    @OneToMany(mappedBy = "admTypes")
    private Collection<ReportParameter> reportParameterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admTypes")
    private Collection<Report> reportCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admTypes")
    private Collection<Parameter> parameterCollection;
    @OneToMany(mappedBy = "admTypes")
    private Collection<ParameterValue> parameterValueCollection;
    @OneToMany(mappedBy = "admTypes1")
    private Collection<ParameterValue> parameterValueCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admTypes2")
    private Collection<ParameterValue> parameterValueCollection2;

    public AdmTypes() {
    }

    public AdmTypes(String cType) {
        this.cType = cType;
    }

    public String getCType() {
        return cType;
    }

    public void setCType(String cType) {
        this.cType = cType;
    }

    public String getVlType() {
        return vlType;
    }

    public void setVlType(String vlType) {
        this.vlType = vlType;
    }

    public String getDsType() {
        return dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    public String getVlStatus() {
        return vlStatus;
    }

    public void setVlStatus(String vlStatus) {
        this.vlStatus = vlStatus;
    }

    @XmlTransient
    public Collection<UserPreferences> getUserPreferencesCollection() {
        return userPreferencesCollection;
    }

    public void setUserPreferencesCollection(Collection<UserPreferences> userPreferencesCollection) {
        this.userPreferencesCollection = userPreferencesCollection;
    }

    @XmlTransient
    public Collection<State> getStateCollection() {
        return stateCollection;
    }

    public void setStateCollection(Collection<State> stateCollection) {
        this.stateCollection = stateCollection;
    }

    @XmlTransient
    public Collection<Model> getModelCollection() {
        return modelCollection;
    }

    public void setModelCollection(Collection<Model> modelCollection) {
        this.modelCollection = modelCollection;
    }

    @XmlTransient
    public Collection<ReportParameter> getReportParameterCollection() {
        return reportParameterCollection;
    }

    public void setReportParameterCollection(Collection<ReportParameter> reportParameterCollection) {
        this.reportParameterCollection = reportParameterCollection;
    }

    @XmlTransient
    public Collection<Report> getReportCollection() {
        return reportCollection;
    }

    public void setReportCollection(Collection<Report> reportCollection) {
        this.reportCollection = reportCollection;
    }

    @XmlTransient
    public Collection<Parameter> getParameterCollection() {
        return parameterCollection;
    }

    public void setParameterCollection(Collection<Parameter> parameterCollection) {
        this.parameterCollection = parameterCollection;
    }

    @XmlTransient
    public Collection<ParameterValue> getParameterValueCollection() {
        return parameterValueCollection;
    }

    public void setParameterValueCollection(Collection<ParameterValue> parameterValueCollection) {
        this.parameterValueCollection = parameterValueCollection;
    }

    @XmlTransient
    public Collection<ParameterValue> getParameterValueCollection1() {
        return parameterValueCollection1;
    }

    public void setParameterValueCollection1(Collection<ParameterValue> parameterValueCollection1) {
        this.parameterValueCollection1 = parameterValueCollection1;
    }

    @XmlTransient
    public Collection<ParameterValue> getParameterValueCollection2() {
        return parameterValueCollection2;
    }

    public void setParameterValueCollection2(Collection<ParameterValue> parameterValueCollection2) {
        this.parameterValueCollection2 = parameterValueCollection2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cType != null ? cType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmTypes)) {
            return false;
        }
        AdmTypes other = (AdmTypes) object;
        if ((this.cType == null && other.cType != null) || (this.cType != null && !this.cType.equals(other.cType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.AdmTypes[ cType=" + cType + " ]";
    }
    
}
