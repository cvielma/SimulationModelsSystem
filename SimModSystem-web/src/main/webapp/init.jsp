<!DOCTYPE html >
<!--  Website template by freewebsitetemplates.com  -->

<%@include file="header.jsp" %>
									<div id="content" >
									
									    <img src="<%=request.getContextPath()%>/resources/images/cotton-flower.jpg" width="726" height="546" alt="" title="">
										<div class="featured">
										      <div class="header">
											     <ul>
														<li class="first">
															<!--<p>hi.</p> -->
															<img src="<%=request.getContextPath()%>/resources/images/hi.jpg" width="50" height="62" alt="" title="" >
														</li>
														<li class="last">
                                                                                                                    <p><%= generalBundle.getString("message.welcome")%></p>
														</li>
												 </ul>
											  </div>
											  <div class="body">
											    <p>
                                                                                                    <% String error = (String)request.getAttribute("error");
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
                                                                                                    <form id="formLogin" action="<% out.print(request.getContextPath()+"/CheckLogin");%>" method="post">                                      
                                                                                                        <p> <%=generalBundle.getString("form.field.userid") %> <input name="userid" type="text"/> </p>
                                                                                                        <p> <%=generalBundle.getString("form.field.password") %> <input name="userpasswd" type="password"/> </p>
                                                                                                        <p/><input class="button.submit" type="submit" value="<%= generalBundle.getString("form.button.login")%>"/>                                                                                                        
                                                                                                    </form>
												</p>
												
												<p>
														<%=generalBundle.getString("message.whylogin")%>
												</p>
											  </div>
									    </div>
									</div>
<%@include file="footer.jsp" %>                                                                                                        
						