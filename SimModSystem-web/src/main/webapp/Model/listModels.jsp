<%@page import="com.librethinking.simmodsys.persistence.jpa.hibernate.Model"%>
<%@page import="com.librethinking.simmodsys.persistence.jpa.hibernate.AdmTypes"%>
<%@page import="java.util.List"%>
<%@include file="../header.jsp" %>
            <div id="content">
                        <div class="content">                            
                            <h1><%=generalBundle.getString("title.configurenewmodel")%></h1>
                            <div class="scrollable">
                            
                            <ul class="article">
                                <% String info = (String)request.getAttribute("info");
                        if(info!=null){
                            try{
                                out.println("<p class=\"p.info\">"+generalBundle.getString(info)+"</p>");
                            }
                            catch(Exception e){
                                out.println("<p class=\"p.info\">"+info+"</p>");
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
                   
                 List<AdmTypes>  models = (List<AdmTypes>) session.getAttribute("models");
                 if(!models.isEmpty()){
                        for(AdmTypes a : models){
                            out.println("<li>");
                            out.println("<a href=\""+request.getContextPath()+"/ConfigureModel?modelid="+ a.getCType()+"\">");
                            out.print("<img src=\""+request.getContextPath()+"/resources/images/cotton-flower2.jpg\" width=\"132\" height=\"132\" alt=\"\" title=\"\"></a>");
                            out.print("<h2><a href=\""+request.getContextPath()+"/ConfigureModel?modelid="+ a.getCType()+"\">");
                            out.print(a.getVlType());
                            out.print("</a></h2>");
                            out.println("<p>");
                            out.print(a.getDsType());//TODO: bundle a.getDsType 
                            out.print("</p>");                        
                            out.println("<a href=\""+request.getContextPath()+"/ConfigureModel?modelid="+ a.getCType()+"\">");
                            out.print(generalBundle.getString("message.configurethismodel"));
                            out.print("</a>");
                            out.print("</li>");
                        }
                 }
                else{
                     out.println("<p>");
                     out.print(generalBundle.getString("message.nomodelsavtoconf"));                     
                     out.println("</p>");

                }
                 %>
                 </ul>
                            </div>
                        </div>
                 
                 <% if (session.getAttribute("user")!=null){ %>
                 <div class="content">
                     
                 <h1><%=generalBundle.getString("title.showmodelresults")%></h1>
                 <div class="scrollable">
                 <ul class="article two-columns">
                 <%              
               
                         
                 List<Model> savedModels = (List<Model>) session.getAttribute("savedModels");
                 if(!savedModels.isEmpty()){                     
                   
                        for(Model a : savedModels){
                            out.println("<li>");
                            out.println("<a href=\""+request.getContextPath()+"/ShowResults?modelid="+ a.getcModel()+"\">");
                            out.print("<img src=\""+request.getContextPath()+"/resources/images/cotton-flower2.jpg\" width=\"50\" height=\"50\" alt=\"\" title=\"\"></a>");
                            out.print("<h2><a href=\""+request.getContextPath()+"/ShowResults?modelid="+ a.getcModel()+"\">");
                            out.print(a.getNmModel());
                            out.print("</a></h2>");
                            out.println("<p>");
                            out.print("Model Type: "); 
                            out.print(a.getAdmTypes().getCType());//TODO: bundle a.getDsType 
                            out.print("</p>");                        
                            out.println("<a href=\""+request.getContextPath()+"/ShowResults?modelid="+ a.getcModel()+"\">");
                            out.print(generalBundle.getString("message.showmodelresults"));
                            out.print("</a>");
                            out.print("</li>");
                        }
                 }
                else{
                     out.println("<p>");
                     out.print(generalBundle.getString("message.nomodelssaved"));                     
                     out.println("</p>"); 
                }
                %>                                           

                                </ul>
                     </div>
                </div>
                <%}%>
                    
            </div>
<%@include file="../footer.jsp" %>