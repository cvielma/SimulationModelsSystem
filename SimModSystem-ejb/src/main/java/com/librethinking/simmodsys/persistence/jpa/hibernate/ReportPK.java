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
import javax.validation.constraints.Size;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Embeddable
public class ReportPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "\"C_USER\"")
    private String cUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "\"NM_REPORT\"")
    private String nmReport;

    public ReportPK() {
    }

    public ReportPK(String cUser, String nmReport) {
        this.cUser = cUser;
        this.nmReport = nmReport;
    }

    public String getCUser() {
        return cUser;
    }

    public void setCUser(String cUser) {
        this.cUser = cUser;
    }

    public String getNmReport() {
        return nmReport;
    }

    public void setNmReport(String nmReport) {
        this.nmReport = nmReport;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cUser != null ? cUser.hashCode() : 0);
        hash += (nmReport != null ? nmReport.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportPK)) {
            return false;
        }
        ReportPK other = (ReportPK) object;
        if ((this.cUser == null && other.cUser != null) || (this.cUser != null && !this.cUser.equals(other.cUser))) {
            return false;
        }
        if ((this.nmReport == null && other.nmReport != null) || (this.nmReport != null && !this.nmReport.equals(other.nmReport))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.ReportPK[ cUser=" + cUser + ", nmReport=" + nmReport + " ]";
    }
    
}
