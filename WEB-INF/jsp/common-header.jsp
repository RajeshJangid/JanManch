<%@page import="com.janmanch.User"%>
<%@page import="com.janmanch.Attributes"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	final Logger logger = Logger.getLogger("common-header.jsp");

	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);

	Attributes attrib = new Attributes();
	String host = attrib.getHost();
	String docBase = attrib.getDocBase();
	String item = request.getParameter("item");
	item = item == null?"":item;
	
	User user = (User)session.getAttribute("user");
	
%>
<nav class="navbar navbar-toggleable-md navbar-light bg-default">
	<div class="container">
		<button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#collapseEx12" aria-controls="collapseEx2" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<a class="navbar-brand" href="<%= host+docBase%>"><img src="<%= host+docBase %>/images/janmanchlogo.png" style="height: 40px; width: auto;"/></a>
		<div class="collapse navbar-collapse" id="collapseEx12">
			
			<ul class="navbar-nav ml-auto">
				<li class="mr-2 pl-2 nav-item<%= item.equals("home")?" active":"" %>"><a href="<%=host+docBase %>" class="nav-link">Home</a></li>
				<li class="mr-2 pl-2 nav-item<%= item.equals("suggestions")?" active":"" %>"><a href="<%= host+docBase %>/suggestions" class="nav-link">Suggestions</a></li>
				<li class="mr-2 pl-2 nav-item<%= item.equals("complaints")?" active":"" %>"><a href="<%= host+docBase %>/complaints" class="nav-link">Complaints</a></li>
				<li class="mr-2 pl-2 nav-item<%= item.equals("questions")?" active":"" %>"><a href="<%= host+docBase %>/questions" class="nav-link">Queries</a></li>
				<li class="mr-2 pl-2 nav-item<%= item.equals("workproposals")?" active":"" %>"><a href="<%= host+docBase %>/workproposals" class="nav-link">Work Proposals</a></li>
                <li class="mr-2 pl-2 nav-item<%= item.equals("candidates")?" active":"" %>"><a href="<%= host+docBase %>/candidates" class="nav-link">Candidates</a></li>
                <% if(user == null){ %>
				<li class="mr-2 pl-2 nav-item<%= item.equals("register")?" active":"" %>"><a href="<%=host+docBase %>/register" class="nav-link">Register</a></li>
				<li class="mr-2 pl-2 nav-item<%= item.equals("login")?" active":"" %>"><a href="<%=host+docBase %>/login" class="nav-link">Login</a></li>
				<%}else{ %>
				<li class="mr-2 pl-2 nav-item dropdown btn-group">
                    <a class="nav-link dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="material-icons mr-2">account_circle</i></a>
                    <div class="dropdown-menu dropdown" aria-labelledby="dropdownMenu1">
                    	<% if(user.getType().equals("U")){ %>
                    	<a href="<%= host+docBase %>/user/<%= user.getAadharId() %>/profile" class="dropdown-item">My Profile</a>
                    	<%-- <a href="<%= host+docBase %>/user/<%=user.getAadharId() %>/utilities" class="dropdown-item">Dashboard</a> --%>
                    	<% }else{ %>
                    	<a href="<%= host+docBase %>/candidate/<%= user.getAadharId() %>/profile" class="dropdown-item">My Profile</a>
                    	<%-- <a href="<%= host+docBase %>/candidate/<%=user.getAadharId() %>/utilities" class="dropdown-item">Dashboard</a> --%>
                    	<% } %>
                        <a href="<%= host+docBase %>/logout" class="dropdown-item">Logout</a>
                    </div>
                </li>
				<%} %>
			</ul>
			
		</div>
	</div>
</nav>
<!--/.Navbar-->