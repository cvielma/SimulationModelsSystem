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
@Table(name = "\"PARAMETER_VALUE\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParameterValue.findAll", query = "SELECT p FROM ParameterValue p"),
    @NamedQuery(name = "ParameterValue.findByCParam", query = "SELECT p FROM ParameterValue p WHERE p.parameterValuePK.cParam = :cParam"),
    @NamedQuery(name = "ParameterValue.findByCParamValue", query = "SELECT p FROM ParameterValue p WHERE p.parameterValuePK.cParamValue = :cParamValue"),
    @NamedQuery(name = "ParameterValue.findByVlDefault", query = "SELECT p FROM ParameterValue p WHERE p.vlDefault = :vlDefault")})    
public class ParameterValue implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParameterValuePK parameterValuePK;
    @Size(max = 20)
    @Column(name = "\"VL_DEFAULT\"")
    private String vlDefault;
    @JoinColumn(name = "\"VL_MIN_VALUE\"")
    @ManyToOne
    private AdmTypes admTypes;
    @JoinColumn(name = "\"VL_MAX_VALUE\"")
    @ManyToOne
    private AdmTypes admTypes1;
    @JoinColumn(name = "\"TP_PARAM_VALUE\"")
    @ManyToOne(optional = false)
    private AdmTypes admTypes2;

    public ParameterValue() {
    }

    public ParameterValue(ParameterValuePK parameterValuePK) {
        this.parameterValuePK = parameterValuePK;
    }

    public ParameterValue(int cParam, int cParamValue) {
        this.parameterValuePK = new ParameterValuePK(cParam, cParamValue);
    }

    public ParameterValuePK getParameterValuePK() {
        return parameterValuePK;
    }

    public void setParameterValuePK(ParameterValuePK parameterValuePK) {
        this.parameterValuePK = parameterValuePK;
    }

    public String getVlDefault() {
        return vlDefault;
    }

    public void setVlDefault(String vlDefault) {
        this.vlDefault = vlDefault;
    }

    public AdmTypes getAdmTypes() {
        return admTypes;
    }

    public void setAdmTypes(AdmTypes admTypes) {
        this.admTypes = admTypes;
    }

    public AdmTypes getAdmTypes1() {
        return admTypes1;
    }

    public void setAdmTypes1(AdmTypes admTypes1) {
        this.admTypes1 = admTypes1;
    }

    public AdmTypes getAdmTypes2() {
        return admTypes2;
    }

    public void setAdmTypes2(AdmTypes admTypes2) {
        this.admTypes2 = admTypes2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parameterValuePK != null ? parameterValuePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParameterValue)) {
            return false;
        }
        ParameterValue other = (ParameterValue) object;
        if ((this.parameterValuePK == null && other.parameterValuePK != null) || (this.parameterValuePK != null && !this.parameterValuePK.equals(other.parameterValuePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.ParameterValue[ parameterValuePK=" + parameterValuePK + " ]";
    }
    
}
