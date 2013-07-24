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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Entity
@Table(name = "\"REPORT_PARAMETER\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReportParameter.findAll", query = "SELECT r FROM ReportParameter r"),
    @NamedQuery(name = "ReportParameter.findByCReport", query = "SELECT r FROM ReportParameter r WHERE r.reportParameterPK.cReport = :cReport"),
    @NamedQuery(name = "ReportParameter.findByCParam", query = "SELECT r FROM ReportParameter r WHERE r.reportParameterPK.cParam = :cParam")})
public class ReportParameter implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReportParameterPK reportParameterPK;
    @JoinColumn(name = "C_PARAM", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Parameter parameter;
    @JoinColumn(name = "TP_STATUS")
    @ManyToOne
    private AdmTypes admTypes;

    public ReportParameter() {
    }

    public ReportParameter(ReportParameterPK reportParameterPK) {
        this.reportParameterPK = reportParameterPK;
    }

    public ReportParameter(int cReport, int cParam) {
        this.reportParameterPK = new ReportParameterPK(cReport, cParam);
    }

    public ReportParameterPK getReportParameterPK() {
        return reportParameterPK;
    }

    public void setReportParameterPK(ReportParameterPK reportParameterPK) {
        this.reportParameterPK = reportParameterPK;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
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
        hash += (reportParameterPK != null ? reportParameterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportParameter)) {
            return false;
        }
        ReportParameter other = (ReportParameter) object;
        if ((this.reportParameterPK == null && other.reportParameterPK != null) || (this.reportParameterPK != null && !this.reportParameterPK.equals(other.reportParameterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.ReportParameter[ reportParameterPK=" + reportParameterPK + " ]";
    }
    
}
