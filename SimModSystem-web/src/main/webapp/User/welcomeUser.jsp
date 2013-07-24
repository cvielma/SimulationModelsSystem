<%@page import="com.librethinking.simmodsys.persistence.jpa.hibernate.User"%>
<%@include file="../header.jsp" %>
									<div id="content" >
                                                                            <% User user = (User) session.getAttribute("user"); 
                                                                            if(user==null){
                                                                                    out.println("<p class=\"p.error;\">"+generalBundle.getString("message.error.nullsession")+"</p>");
                                                                            }
                                                                            %>
									
									    <img src="<%=request.getContextPath()%>/resources/images/cotton-flower.jpg" width="726" height="546" alt="" title="">
										<div class="featured">
										      <div class="header">
											     <ul>
														<li class="first">
															<!--<p>hi.</p> -->
															<img src="<%=request.getContextPath()%>/resources/images/hi.jpg" width="50" height="62" alt="" title="" >
														</li>
														<li class="last">
															<p style="height: 70%;"><% out.print(user.getNmFirstname()); %>!</p>
														</li>
												 </ul>
											  </div>
											  <div class="body">
											    <p><% String error = (String)request.getAttribute("error");
                                                                                                        if(error!=null){
                                                                                                            try{
                                                                                                                out.println("<p style=\"p.error\">"+generalBundle.getString(error)+"</p>");
                                                                                                            }
                                                                                                            catch(Exception e){
                                                                                                                out.println("<p style=\"p.error\">"+error+"</p>");
                                                                                                            }
                                                                                                            request.removeAttribute("error");
                                                                                                        }
                                                                                            
                                                                                                        out.print("<a href=\""+request.getContextPath()+generalBundle.getString("link.logout")+"\">"+generalBundle.getString("label.logout")+"</a>");
                                                                                                %>
                                                                                                
                                                                                            </p>
                                                                                                
												
												<p>
                                                                                                    <%= generalBundle.getString("message.loggedin") %>
												</p>
											  </div>
									    </div>
									</div>
<%@include file="../footer.jsp" %>						