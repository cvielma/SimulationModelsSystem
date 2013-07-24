/*
 * Copyright 2012 Christian Vielma <cvielma@librethinking.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the \"License\");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an \"AS IS\" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.librethinking.simmodsys.persistence.jpa.nativesql.postgres;

import com.librethinking.simmodsys.exceptions.business.FunctionalException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This is an specific implementation of the StateParameterModelInfo to
 * execute a Native query to obtain a State parameters' values.
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@Entity
public class PostgresStateParameterModelInfo implements Serializable{ // extends StateParameterModelInfo{
@Id
    @Column(name="\"ID\"")
    private int id;
    
    @Column(name="\"C_MODEL\"")
    private int cModel;
    
    @Column(name="\"C_STATE\"")
    private int cState;
    
    @Column(name="\"C_PARAM\"")
    private int cParam;
    
    @Column(name="\"PARAM_ORD\"")
    private int paramOrd;
    
    @Column(name="\"NM_PARAM\"")
    private String  nmParam;
    
    @Column(name="\"TYPE\"")
    private String type;
    
    @Column(name="\"VL_VALUE\"")
    private String vlValue;
    
    @Column(name="\"PARAM_NUM\"")
    private int paramNum;

    public int getcModel() {
        return cModel;
    }

    public void setcModel(int cModel) {
        this.cModel = cModel;
    }

    public int getcParam() {
        return cParam;
    }

    public void setcParam(int cParam) {
        this.cParam = cParam;
    }

    public int getcState() {
        return cState;
    }

    public void setcState(int cState) {
        this.cState = cState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNmParam() {
        return nmParam;
    }

    public void setNmParam(String nmParam) {
        this.nmParam = nmParam;
    }

    public int getParamNum() {
        return paramNum;
    }

    public void setParamNum(int paramNum) {
        this.paramNum = paramNum;
    }

    public int getParamOrd() {
        return paramOrd;
    }

    public void setParamOrd(int paramOrd) {
        this.paramOrd = paramOrd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVlValue() {
        return vlValue;
    }

    public void setVlValue(String vlValue) {
        this.vlValue = vlValue;
    }

        
    /**
     * This method extracts a List of Parameters from a result. It deletes
     * the results extracted from the original list.
     * @param resultList This will be the result of the sql converted to a list with all
     * the parameters of a given state listed.
     * @return A List with only the parameters that conforms a complete parameter.
     * @throws FunctionalException if a Parameter cannot be resolved (i.e. different number
     * of fields set, unique parameter with more than one)
     * @throws TechnicalException if any runtime error arise.
     */
    public static List<PostgresStateParameterModelInfo> extractParameter(List<PostgresStateParameterModelInfo> resultList) 
            throws FunctionalException {
        //TODO: implement
        return resultList;
    }
    
    public String getQuery() {
        //NOTE:This will work only if inserted sequentially
        return "SELECT sp.\"ID\", sp.\"C_MODEL\",  sp.\"C_STATE\", sp.\"C_PARAM\", "
            + " (sp.\"C_VALUE\" % param.\"PARAM_NUM\") as \"PARAM_ORD\",  param.\"NM_PARAM\",  param.\"TYPE\",  sp.\"VL_VALUE\","
            + " param.\"PARAM_NUM\""
            + " FROM "
            + " ( SELECT s.\"C_MODEL\", s.\"C_STATE\", s.\"C_PARAM\", "
            + " (s.\"C_VALUE\" - min(s.\"C_VALUE\") over(partition by s.\"C_MODEL\", s.\"C_STATE\", s.\"C_PARAM\")) as \"C_VALUE\","
            + " s.\"VL_VALUE\", s.\"C_VALUE\" as \"ID\""
            + " FROM \"STATE_PARAMETER\" as s"
            + " WHERE s.\"C_MODEL\" = :cModel "
            + " AND s.\"C_STATE\" = :cState "
            + " ) as sp, "
            + " (SELECT p.\"C_PARAM\", p.\"NM_PARAM\", "
            + " pv.\"TP_PARAM_VALUE\" as \"TYPE\","
            + "  (row_number() OVER(partition by pv.\"C_PARAM\" ORDER BY pv.\"C_PARAM_VALUE\" DESC) -1) as \"PARAM_SEQ\","
            + " count(pv.\"C_PARAM_VALUE\") over (partition by pv.\"C_PARAM\") as \"PARAM_NUM\" "
            + " FROM \"PARAMETER\" as p, \"PARAMETER_VALUE\" as pv"
            + " WHERE  p.\"C_PARAM\" = pv.\"C_PARAM\""
            + " ORDER BY p.\"C_PARAM\", pv.\"C_PARAM_VALUE\" ) as param"
            + " WHERE sp.\"C_PARAM\" = param.\"C_PARAM\""
            + " AND (sp.\"C_VALUE\" % param.\"PARAM_NUM\") = param.\"PARAM_SEQ\""
            + " ORDER BY sp.\"C_MODEL\", sp.\"C_STATE\", sp.\"C_PARAM\", (sp.\"C_VALUE\" % param.\"PARAM_NUM\")";        
    }
    
}
