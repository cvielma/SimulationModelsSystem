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
@Table(name = "\"PARAMETER\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parameter.findAll", query = "SELECT p FROM Parameter p"),
    @NamedQuery(name = "Parameter.findByCParam", query = "SELECT p FROM Parameter p WHERE p.cParam = :cParam"),
    @NamedQuery(name = "Parameter.findByNmParam", query = "SELECT p FROM Parameter p WHERE p.nmParam = :nmParam"),
    @NamedQuery(name = "Parameter.findByVlUnique", query = "SELECT p FROM Parameter p WHERE p.vlUnique = :vlUnique")})
public class Parameter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"C_PARAM\"")
    private Integer cParam;
    @Size(max = 30)
    @Column(name = "\"NM_PARAM\"")
    private String nmParam;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"VL_UNIQUE\"")
    private boolean vlUnique;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parameter")
    private Collection<ReportParameter> reportParameterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parameter")
    private Collection<StateParameter> stateParameterCollection;
    @JoinColumn(name = "\"VL_STATUS\"")
    @ManyToOne(optional = false)
    private AdmTypes admTypes;

    public Parameter() {
    }

    public Parameter(Integer cParam) {
        this.cParam = cParam;
    }

    public Parameter(Integer cParam, boolean vlUnique) {
        this.cParam = cParam;
        this.vlUnique = vlUnique;
    }

    public Integer getCParam() {
        return cParam;
    }

    public void setCParam(Integer cParam) {
        this.cParam = cParam;
    }

    public String getNmParam() {
        return nmParam;
    }

    public void setNmParam(String nmParam) {
        this.nmParam = nmParam;
    }

    public boolean getVlUnique() {
        return vlUnique;
    }

    public void setVlUnique(boolean vlUnique) {
        this.vlUnique = vlUnique;
    }

    @XmlTransient
    public Collection<ReportParameter> getReportParameterCollection() {
        return reportParameterCollection;
    }

    public void setReportParameterCollection(Collection<ReportParameter> reportParameterCollection) {
        this.reportParameterCollection = reportParameterCollection;
    }

    @XmlTransient
    public Collection<StateParameter> getStateParameterCollection() {
        return stateParameterCollection;
    }

    public void setStateParameterCollection(Collection<StateParameter> stateParameterCollection) {
        this.stateParameterCollection = stateParameterCollection;
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
        hash += (cParam != null ? cParam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parameter)) {
            return false;
        }
        Parameter other = (Parameter) object;
        if ((this.cParam == null && other.cParam != null) || (this.cParam != null && !this.cParam.equals(other.cParam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.Parameter[ cParam=" + cParam + " ]";
    }
    
}
