<%@page import="com.janmanch.User"%>
<%@page import="com.janmanch.Attributes"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	final Logger logger = Logger.getLogger("home.jsp");

	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);

	Attributes attrib = new Attributes();
	String host = attrib.getHost();
	String docBase = attrib.getDocBase();
	User user = (User)session.getAttribute("user");
	String item = request.getParameter("item");
	item = item == null?"":item.trim();
	logger.info("item: "+item);
%>
<div class="list-group">
	<%-- <a href="utilities" class="list-group-item<%= item.equals("dashboard")?" active":"" %>">Dashboard </a> --%>
	<a href="profile" class="list-group-item<%= item.equals("profile")?" active":"" %>">My Profile </a> 
	<% if(user!= null && (user.getType().equals("U") || user.getType().equals("C"))){ %> 
	<a href="filecomplaint" class="list-group-item<%= item.equals("filecomplaint")?" active":"" %>">File Complaint </a> 
	<a href="submitquery" class="list-group-item<%= item.equals("submitquery")?" active":"" %>">Submit Query</a> 
	<a href="submitsuggestion" class="list-group-item<%= item.equals("submitsuggestion")?" active":"" %>">Give Suggestion</a> 
	<a href="applymla" class="list-group-item<%= item.equals("applymla")?" active":"" %>">MLA Application</a>
	<a href="submitted_suggest" class="list-group-item<%= item.equals("submitted_suggest")?" active":"" %>"> Submitted Suggestions  </a> 
	<a href="submitted_queries" class="list-group-item<%= item.equals("submitted_queries")?" active":"" %>"> Submitted Queries  </a> 
	<a href="submitted_complaints" class="list-group-item<%= item.equals("submitted_complaints")?" active":"" %>">Submitted Complaints</a>
	<%}else if(user!= null && user.getType().equals("M")){ %>
	<%-- <a href="workproposols" class="list-group-item<%= item.equals("workproposols")?" active":"" %>">My Work Proposals </a>  --%>
	<a href="complaints" class="list-group-item<%= item.equals("complaints")?" active":"" %>">Complaints Received </a> 
	<a href="suggestions" class="list-group-item<%= item.equals("suggestions")?" active":"" %>">Suggestions Received </a> 
	<a href="queries" class="list-group-item<%= item.equals("queries")?" active":"" %>">Queries Received </a> 
	<a href="proposework" class="list-group-item<%= item.equals("proposework")?" active":"" %>">Work Proposol</a>
	<a href="latestupdates" class="list-group-item<%= item.equals("latestupdates")?" active":"" %>">Add News</a>
	<%} %>
</div>