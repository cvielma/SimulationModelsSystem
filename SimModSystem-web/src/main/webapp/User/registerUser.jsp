<%-- 
    Document   : registerUser
    Created on : Aug 2, 2012, 1:50:14 PM
    Author     : Christian Vielma <cvielma@librethinking.com>
--%>
<%@page import="com.librethinking.simmodsys.persistence.jpa.hibernate.User"%>
<%@include file="../header.jsp" %>
<div id="content" >
     <div class="content">
    <h1><%=generalBundle.getString("title.registeruser")%></h1>
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
                            <div style="display:none;" id="error-registerUserForm" class="formError"> <%=generalBundle.getString("error.form.registerUserForm")%></div>
                            <form action="<% out.print(request.getContextPath()+"/SaveUser");%>" method="post" name="registerUserForm">                                      
                                <table>
                                    <tr> <td/>
                                            <td><%=generalBundle.getString("form.field.userid")%></td>
                                            <td><input id="<%=generalBundle.getString("field.mand.text")%>.user.id" name="userid" type="text" onblur="validateField('userid', 'registerUserForm');"/></td>
                                            <td><div style="display:none;" id="error-userid" class="fieldError"> <%=generalBundle.getString("error.field.mandatory")%></div></td>                                            
                                    </tr>
                                    <tr> <td/><td><%=generalBundle.getString("form.field.password")%></td>
                                        <td> <input id="<%=generalBundle.getString("field.mand.passwd")%>.userpasswd" name="userpasswd" type="password" onblur="validateField('userpasswd', 'registerUserForm');"/></td>
                                        <td><div style="display:none;" id="error-userpasswd" class="fieldError"> <%=generalBundle.getString("error.field.mandatory")%></div></td>                                        
                                    </tr>
                                 
                                    <tr> <td/><td><%=generalBundle.getString("form.field.confirmpassword")%></td>
                                        <td> <input id="<%=generalBundle.getString("field.mand.passwdconfirm")%>.userpasswdconfirm" name="userpasswdconfirm" type="password" onblur="validateField('userpasswdconfirm', 'registerUserForm');"/></td>
                                        <td> <div style="display:none;" id="error-userpasswdconfirm" class="fieldError"> <%=generalBundle.getString("error.field.passwnotmatch")%></div></td>
                                        </tr>
                                    <tr> <td/><td><%=generalBundle.getString("form.field.firstname")%></td>
                                        <td> <input id="<%=generalBundle.getString("field.mand.text")%>.userfn" name="userfn" type="text" onblur="validateField('userfn', 'registerUserForm');"/> </td>
                                        <td> <div style="display:none;" id="error-userfn" class="fieldError"> <%=generalBundle.getString("error.field.mandatory")%></div></td>
                                        </tr>
                                    <tr> <td/><td><%=generalBundle.getString("form.field.lastname")%></td>
                                        <td> <input id="<%=generalBundle.getString("field.mand.text")%>.userln" name="userln" type="text" onblur="validateField('userln', 'registerUserForm');"/> </td>
                                        <td>   <div style="display:none;" id="error-userln" class="fieldError"> <%=generalBundle.getString("error.field.mandatory")%></div></td>
                                        </tr>
                                    <tr> <td/><td><%=generalBundle.getString("form.field.email")%></td>
                                        <td> <input id="<%=generalBundle.getString("field.mand.email")%>.userem" name="userem" type="text" onblur="validateField('userem', 'registerUserForm');"/> </td>
                                        <td><div style="display:none;" id="error-userem" class="fieldError"> <%=generalBundle.getString("error.field.invalidemail")%></div></td>
                                        </tr> 
                                    <tr> <td/><td/><td><input type="button" value="<%=generalBundle.getString("form.button.register")%>" onclick="validateForm('registerUserForm');"/></td></tr>
                                </table>
                            </form>
     </div>
</div>
<%@include file="../footer.jsp" %>						

