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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Entity
@Table(name = "\"ADM_MTYPES\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmMtypes.findAll", query = "SELECT a FROM AdmMtypes a"),
    @NamedQuery(name = "AdmMtypes.findByCMtype", query = "SELECT a FROM AdmMtypes a WHERE a.cMtype = :cMtype"),
    @NamedQuery(name = "AdmMtypes.findByDsMtype", query = "SELECT a FROM AdmMtypes a WHERE a.dsMtype = :dsMtype")})
public class AdmMtypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "\"C_MTYPE\"")
    private String cMtype;
    @Size(max = 150)
    @Column(name = "\"DS_MTYPE\"")
    private String dsMtype;

    public AdmMtypes() {
    }

    public AdmMtypes(String cMtype) {
        this.cMtype = cMtype;
    }

    public String getCMtype() {
        return cMtype;
    }

    public void setCMtype(String cMtype) {
        this.cMtype = cMtype;
    }

    public String getDsMtype() {
        return dsMtype;
    }

    public void setDsMtype(String dsMtype) {
        this.dsMtype = dsMtype;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cMtype != null ? cMtype.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmMtypes)) {
            return false;
        }
        AdmMtypes other = (AdmMtypes) object;
        if ((this.cMtype == null && other.cMtype != null) || (this.cMtype != null && !this.cMtype.equals(other.cMtype))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.librethinking.simmodsys.persistence.jpa.hibernate.AdmMtypes[ cMtype=" + cMtype + " ]";
    }
    
}
