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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Entity
@Table(name = "\"REPORT\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r"),
    @NamedQuery(name = "Report.findByCUser", query = "SELECT r FROM Report r WHERE r.reportPK.cUser = :cUser"),
    @NamedQuery(name = "Report.findByCReport", query = "SELECT r FROM Report r WHERE r.cReport = :cReport"),
    @NamedQuery(name = "Report.findByNmReport", query = "SELECT r FROM Report r WHERE r.reportPK.nmReport = :nmReport")})
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReportPK reportPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"C_REPORT\"")
    private int cReport;
    @JoinColumn(name = "C_USER", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "TP_REPORT")
    @ManyToOne(optional = false)
    private AdmTypes admTypes;

    public Report() {
    }

    public Report(ReportPK reportPK) {
        this.reportPK = reportPK;
    }

    public Report(ReportPK reportPK, int cReport) {
        this.reportPK = reportPK;
        this.cReport = cReport;
    }

    public Report(String cUser, String nmReport) {
        this.reportPK = new ReportPK(cUser, nmReport);
    }

    public ReportPK getReportPK() {
        return reportPK;
    }

    public void setReportPK(ReportPK reportPK) {
        this.reportPK = reportPK;
    }

    public int getCReport() {
        return cReport;
    }

    public void setCReport(int cReport) {
        this.cReport = cReport;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        hash += (reportPK != null ? reportPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportPK == null && other.reportPK != null) || (this.reportPK != null && !this.reportPK.equals(other.reportPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.Report[ reportPK=" + reportPK + " ]";
    }
    
}
