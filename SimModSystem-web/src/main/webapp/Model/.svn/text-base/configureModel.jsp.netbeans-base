<%-- 
    Document   : configureModel
    Created on : Aug 3, 2012, 6:09:31 PM
    Author     : Christian Vielma <cvielma@librethinking.com>
--%>


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
       <%SIMModel model = (SIMModel)session.getAttribute("configuremodel");%>
        <h1><%=generalBundle.getString("title.configuringmodel")%> <%= model.getType()%> </h1>
            
             <% String info = (String)request.getAttribute("info");
                        if(info!=null){
                            out.println("<p style=\"color:blue;\">"+info+"</p>");
                            request.removeAttribute("info");
                        }
                 String error = (String)request.getAttribute("error");
                        if(error!=null){
                            out.println("<p style=\"color:red;\">"+error+"</p>");
                            request.removeAttribute("error");
                        } 
                %>
        <form action="<% out.print(request.getContextPath()+"/ExecuteModel");%>" method="post" name="configureModelForm" >
        <%  ResourceBundle bundle = (ResourceBundle) session.getAttribute("parameterbundle");
       
            out.println("<div id=\"initialState\">");
            out.println("<table><tr><td>");
            out.println("<h2>"+generalBundle.getString("label.initialstate")+"</h2>");
            out.println("</td><td>&nbsp;</td><td>");
            out.println("<button type=\"button\" id=\""+generalBundle.getString("label.initialstate")+"Button\" class=\"showCategButton\" onclick=\"showCategory('"+generalBundle.getString("label.initialstate")+"');\">></button>");
            out.println("</td></tr></table>");
            out.println("<div id=\""+generalBundle.getString("label.initialstate")+"\" style=\"display:none;\">");
            out.println("<div>");
            SIMState st = model.newInitialStateMock();
            Map<String, Collection<SIMParameter>> parammap = st.describe();
            for(String s : parammap.keySet()){
                out.println("<table><tr><td>");
                out.println("<h3>"+generalBundle.getString("state."+s.toLowerCase())+"</h3>");
                out.println("</td><td>&nbsp;</td><td>");
                out.println("<button type=\"button\" id=\""+generalBundle.getString("state."+s.toLowerCase())+"Button\" class=\"showCategButton\" onclick=\"showCategory('"+generalBundle.getString("state."+s.toLowerCase())+"');\">></button>");
                out.println("</td></tr></table>");
                out.println("<div id=\""+generalBundle.getString("state."+s.toLowerCase())+"\" style=\"display:none;\">");
                out.println("<div>");
                for(SIMParameter param : parammap.get(s)){
                    out.println(SIMParameterAdapter.SIMParameterToHTML(param, "initial.", model.getType().toLowerCase(), bundle, true, "configureModelForm", true));
                }
                out.println("</div></div>");
            }
            out.println("</div></div>");
            out.println("</div>");
            out.println("<br/><br/>");
            
            out.println("<div id=\"finalState\">");
            out.println("<table><tr><td>");
            out.println("<h2>"+generalBundle.getString("label.finalstate")+"</h2>");
            out.println("</td><td>&nbsp;</td><td>");
            out.println("<button type=\"button\" id=\""+generalBundle.getString("label.finalstate")+"Button\" class=\"showCategButton\" onclick=\"showCategory('"+generalBundle.getString("label.finalstate")+"');\">></button>");
            out.println("</td></tr></table>");
            out.println("<div id=\""+generalBundle.getString("label.finalstate")+"\" style=\"display:none;\">");
            out.println("<div>");
            st = model.newFinalStateMock();
            parammap = st.describe();
            Set<Set<SIMParameter>> setParams = st.getParameters();
            for(String s : parammap.keySet()){
                out.println("<table><tr><td>");
                out.println("<h3>"+s+"</h3>");
                out.println("</td><td>&nbsp;</td><td>");                
                out.println("<button type=\"button\" id=\""+s+"Button\"  class=\"showCategButton\" onclick=\"showCategory('"+s+"');\">></button>");
                out.println("</td></tr></table>");
                out.println("<div id=\""+s+"\" style=\"display:none;\">");
                out.println("<div>");
                for(SIMParameter param : parammap.get(s)){
                    out.println(SIMParameterAdapter.SIMParameterToHTML(param, "final.", model.getType().toLowerCase(), bundle, true, "configureModelForm", false));
                }
                out.println("</div></div>");
            }
            out.println("</div></div>");
            out.println("</div>");
        
        %>
        <br/>
        <input type="button" value="<%=generalBundle.getString("form.button.executemodel")%>" onclick="validateForm('configureModelForm');"/>
        </form>
    </div>
</div>
<%@include file="../footer.jsp" %>		
