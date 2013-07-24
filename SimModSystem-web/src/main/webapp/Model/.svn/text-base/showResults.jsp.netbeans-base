<%-- 
    Document   : configureModel
    Created on : Aug 3, 2012, 6:09:31 PM 
    Author     : Christian Vielma <cvielma@librethinking.com>
--%>


<%@page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="com.librethinking.simmodsys.web.adapters.SIMParameterAdapter"%>
<%@page import="com.librethinking.simmodsys.SIMParameter"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Collection"%>
<%@page import="com.librethinking.simmodsys.SIMState"%>
<%@page import="com.librethinking.simmodsys.SIMModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="../header.jsp" %>
<div id="content" >
   <div class="content">
       
       <%SIMModel model = (SIMModel)session.getAttribute("model");%>
       <h1><%=generalBundle.getString("title.results")%></h1>
        <div class="showContent">
            <%  
                  String info = (String)request.getAttribute("info");
                        if(info!=null){ 
                             try{
                                out.println("<p style=\"color:blue;\">"+generalBundle.getString(info)+"</p>");
                            }
                            catch(Exception e){
                                out.println("<p style=\"color:blue;\">"+info+"</p>");
                            }                            
                            request.removeAttribute("info");
                        }
                 String error = (String)request.getAttribute("error");
                        if(error!=null){
                            try{
                                out.println("<p style=\"p.error\">"+generalBundle.getString(error)+"</p>");
                            }
                            catch(Exception e){
                                out.println("<p style=\"p.error\">"+error+"</p>");
                            }
                            request.removeAttribute("error");
                        } 
                %>
       <% boolean modelSaved = request.getAttribute("modelSaved")==null? false: (Boolean) request.getAttribute("modelSaved");
          boolean modelLoaded = request.getAttribute("modelLoaded")==null? false: (Boolean) request.getAttribute("modelLoaded");
          if(session.getAttribute("user")!=null){
           if(!modelSaved && !modelLoaded){
        %>
        <div class="pageResultFrame">
            <form action="<%=request.getContextPath()+"/SaveModel"%>" method="post" name="saveModelForm">
                <table>
                    <tr><td><%=generalBundle.getString("label.savemodel")%></td>
                        <td/>
                        <td><input type="text" id="mand.modelname" name="modelname"/></td>
                        <td><div style="display:none;" id="error-modelname" class="fieldError"> <%=generalBundle.getString("error.field.mandatory")%></div></td>
                        <td><input type="button" onClick="validateForm('saveModelForm');" value="<%=generalBundle.getString("label.savemodel")%>"/></td>
                        <td/>
                    </tr>
                </table>
            </form>    
        </div>
        <%  }
            else if(modelSaved && !modelLoaded){
       
       %>
               <table class="actionLink">
                    <tr><td><%=generalBundle.getString("message.modelsaved")%></td></tr>
                </table>
         <%   }
          }
        %>         
        <br />        
        <div class="pageResultFrame">
            <%out.println("<a class=\"actionLink\" href=\""+request.getContextPath()+"/ShowReport\">Show Report</a>");%>
        </div>
        
        <br/>
        <div class="pageResultFrame">
        <%
             out.println("<table><tr><td>");
             out.println("<span class=\"actionLink\">"+generalBundle.getString("label.showresult")+"</span>");
             out.println("</td><td>&nbsp;</td><td>");
             out.println("<button type=\"button\" id=\"showResultsButton\" class=\"showCategButton\" onclick=\"showCategory('showResults');\">></button>");
             out.println("</td></tr></table>");
             out.println("<div id=\"showResults\" style=\"display:none;\">");
             out.println("<div>");
        %>
        
        <%
           out.println("<table class=\"pagination\"><tr>");
           for(SIMState st : model.getStates()){
               if(st.getNumericID() % 30 == 1){
                   out.println("</tr><tr>");
               }
               out.println("<td onClick=\"showState('"+st.getNumericID()+"')\"><a href=\"#\">"+st.getNumericID()+"</a></td>");               
           }
           out.println("</tr></table>");
         %>          
          <br/>      
         <div class="stateResultFrame">
             
        <%  
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("parameterbundle");
            
            int i = 0;
            for(SIMState st : model.getStates()){
                 Map<String, Collection<SIMParameter>> parammap = st.describe();
                 out.println("<div id=\""+st.getNumericID()+"\" class=\"stateResult\"");
                 if(i==0){out.print(" style=\"display:block;\" > "); i++;}
                 else{out.print(" style=\"display:none;\" >  ");}
                 out.println("<div>");
                out.println("<h2> "+generalBundle.getString("label.state")+st.getNumericID()+" </h2>");
                for(String s : parammap.keySet()){
                    out.println("<table><tr><td>");
                    out.println("<h3>"+generalBundle.getString("state."+s.toLowerCase())+"</h3>");
                     out.println("</td><td>&nbsp;</td><td>");
                    out.println("<button type=\"button\" id=\""+generalBundle.getString("state."+s.toLowerCase())+generalBundle.getString("label.state")+st.getNumericID()+"Button\" class=\"showCategButton\" onclick=\"showCategory('"+generalBundle.getString("state."+s.toLowerCase())+generalBundle.getString("label.state")+st.getNumericID()+"');\">></button>");
                    out.println("</td></tr></table>");
                    out.println("<div id=\""+generalBundle.getString("state."+s.toLowerCase())+generalBundle.getString("label.state")+st.getNumericID()+"\" style=\"display:none;\">");
                    out.println("<div>");
                    for(SIMParameter param : parammap.get(s)){
                        out.println(SIMParameterAdapter.SIMParameterToHTML(param, "results.", model.getType().toLowerCase(), bundle, false, "", false));
                    }
                    out.println("</div></div>");
                }
                out.println("</div>");   
                out.println("</div>");                            
           }
           
           out.println("</div>");   
           out.println("</div>");  
           %>
         </div>
        </div>
       </div> 
     </div>
</div>
<%@include file="../footer.jsp" %>		
