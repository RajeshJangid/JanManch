<%@page import="com.janmanch.Attributes"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	final Logger logger = Logger.getLogger("Mla - login");

	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);

	Attributes attrib = new Attributes();
	String host = attrib.getHost();
	String docBase = attrib.getDocBase();
%>
<footer class="page-footer elegant-color center-on-small-only">
	<div class="container-fluid">
		<div class="row">
			
		</div>
	</div>
	<div class="footer-copyright text-center rgba-black-light">
		<div class="container-fluid">
			© 2017 Copyright: <a href="https://mdbootstrap.com"> JanManch </a>
		</div>
	</div>
</footer>