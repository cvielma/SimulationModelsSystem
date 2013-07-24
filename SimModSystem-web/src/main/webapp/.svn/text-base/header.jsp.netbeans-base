<%@page import="java.util.ResourceBundle"%>
<html>

<head>
	<title>Model Simulation System - Test</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<%=request.getContextPath()%>/resources/css/style.css" rel="stylesheet" type="text/css" />
        <script src="<%=request.getContextPath()%>/resources/js/validations.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/js/prototype.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/js/scriptaculous.js" type="text/javascript"></script>

	<!--[if IE 6]>
		<link href="css/ie6.css" rel="stylesheet" type="text/css" />
	<![endif]-->
	<!--[if IE 7]>
        <link href="css/ie7.css" rel="stylesheet" type="text/css" />  
	<![endif]-->
</head>
<% ResourceBundle generalBundle = (ResourceBundle) session.getAttribute("generalbundle");%>
<body>
	  <div id="background">
			  <div id="page">
			
					 <div class="header">
						<div class="footer">
							<div class="body">
							  
									<div id="sidebar">
									    <a href="<%=request.getContextPath()%>/simmodsys"><img id="logo" src="<%=request.getContextPath()%>/resources/images/logo.gif" with="154" height="74" alt="" title=""/></a>
									
										
										<ul class="navigation">
                                                                                    <% int whichActive =0;
                                                                                      if(request.getRequestURI().contains("init.jsp")){
                                                                                        whichActive =1;
                                                                                      }
                                                                                        else if(request.getRequestURI().contains("listModels.jsp") || 
                                                                                        request.getRequestURI().contains("configureModel.jsp") ||
                                                                                        request.getRequestURI().contains("showResults.jsp")){
                                                                                        whichActive =2;
                                                                                      }
                                                                                       else if(request.getRequestURI().contains("registerUser.jsp") || request.getRequestURI().contains("welcomeUser.jsp")){
                                                                                        whichActive =3;
                                                                                      }
                                                                                    
                                                                                        
                                                                                     %>
                                                                                    <li <%if(whichActive==1){out.print(" class=\"active\"");}%>><a href="<%=request.getContextPath()+generalBundle.getString("header.menu.link.1.default") %>"><%= generalBundle.getString("header.menu.text.1.default")%></a></li>
											<% if(session.getAttribute("user")==null){
                                                                                            
                                                                                            out.print("<li");
                                                                                            if(whichActive==2){out.print(" class=\"active\"");}
                                                                                            out.print("><a href=\""+request.getContextPath()+generalBundle.getString("header.menu.link.2.default")+"\">"+generalBundle.getString("header.menu.text.2.default")+"</a></li>");
                                                                                            
                                                                                            out.print("<li");
                                                                                            if(whichActive==3){out.print(" class=\"active\"");}
                                                                                            out.print("><a href=\""+request.getContextPath()+generalBundle.getString("header.menu.link.3.default")+"\">"+generalBundle.getString("header.menu.text.3.default")+"</a></li>");
                                                                                        }
                                                                                        else{
                                                                                            out.print("<li");
                                                                                            if(whichActive==2){out.print(" class=\"active\"");}
                                                                                            out.print("><a href=\""+request.getContextPath()+generalBundle.getString("header.menu.link.2.logged")+"\">"+generalBundle.getString("header.menu.text.2.logged")+"</a></li>");
                                                                                            out.print("<li");
                                                                                            if(whichActive==3){out.print(" class=\"active\"");}
                                                                                            out.print("><a href=\""+request.getContextPath()+generalBundle.getString("header.menu.link.3.logged")+"\">"+generalBundle.getString("header.menu.text.3.logged")+"</a></li>");
                                                                                        }
                                                                                        %>
                                                                                        <li class="last" ><a href="<%=request.getContextPath()+generalBundle.getString("header.menu.link.4.default") %>"><%= generalBundle.getString("header.menu.text.4.default")%></a></li>
										</ul>
										
										<div class="connect">
										    <a href="#" class="facebook">&nbsp;</a>
											<a href="#" class="twitter">&nbsp;</a>
											<a href="#" class="vimeo">&nbsp;</a>
										</div>
										
										<div class="footenote">
										  <span>2012 - </span>
										  <span><a href="www.librethinking.com">Librethinking.com</a></span>
										</div>
										
									</div>