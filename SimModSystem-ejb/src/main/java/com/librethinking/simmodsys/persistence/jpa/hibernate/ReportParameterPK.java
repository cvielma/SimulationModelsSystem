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

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Embeddable
public class ReportParameterPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"C_REPORT\"")
    private int cReport;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"C_PARAM\"")
    private int cParam;

    public ReportParameterPK() {
    }

    public ReportParameterPK(int cReport, int cParam) {
        this.cReport = cReport;
        this.cParam = cParam;
    }

    public int getCReport() {
        return cReport;
    }

    public void setCReport(int cReport) {
        this.cReport = cReport;
    }

    public int getCParam() {
        return cParam;
    }

    public void setCParam(int cParam) {
        this.cParam = cParam;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cReport;
        hash += (int) cParam;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportParameterPK)) {
            return false;
        }
        ReportParameterPK other = (ReportParameterPK) object;
        if (this.cReport != other.cReport) {
            return false;
        }
        if (this.cParam != other.cParam) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.ReportParameterPK[ cReport=" + cReport + ", cParam=" + cParam + " ]";
    }
    
}
