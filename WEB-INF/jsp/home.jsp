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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Jan Manch</title>
<link href="https://fonts.googleapis.com/css?family=Lato:300,300i,400,400i,700,700i" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto:300i,400,400i,500,500i|Slabo+27px" rel="stylesheet">
<!-- Bootstrap -->
<link href="<%=host + docBase%>/mdb/css/bootstrap.min.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Dancing+Script" rel="stylesheet">
<link href="<%=host + docBase%>/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=host + docBase%>/mdb/css/mdb.min.css" rel="stylesheet">
<link href="<%=host + docBase%>/mdb/css/style.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<style type="text/css">
.panel-primary {
    border: 1px solid #337ab7;
}
.panel {
    margin-bottom: 20px;
    background-color: #fff;
    border-radius: 4px;
    -webkit-box-shadow: 0 1px 1px rgba(0,0,0,.05);
    box-shadow: 0 2px 5px 0 rgba(0,0,0,.16), 0 2px 10px 0 rgba(0,0,0,.12);
}
.panel-primary>.panel-heading {
    color: #fff;
    background-color: #337ab7;
    border-color: #337ab7;
}
.panel-heading {
    padding: 10px 15px;
    border-bottom: 1px solid transparent;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
}
</style>
</head>
<body>
	<jsp:include page="common-header.jsp">
		<jsp:param value="home" name="item" />
	</jsp:include>
	<div class="container-fluid">
	
	
	
	
	
	
	
	<div class="row pt-3 pb-2">
			<div class="col-sm-2">
				<div class="panel panel-primary">
					<div class="panel-heading" style="font-weight: bold;">Home</div>
					<div class="panel-heading" style="background-color: aliceblue;"><a href="<%=host+docBase %>/register">Register</a></div>
					<div class="panel-heading" style="background-color: aliceblue;"><a href="<%=host+docBase %>/login">Login</a></div>
					<div class="panel-heading" style="background-color: aliceblue;"><a href="<%= host+docBase %>/suggestions">Suggestions</a></div>
					<div class="panel-heading" style="background-color: aliceblue;"><a href="<%= host+docBase %>/complaints">Complaints</a></div>
					<div class="panel-heading" style="background-color: aliceblue;"><a href="<%= host+docBase %>/questions">Queries</a></div>
				</div>
			</div>
			<div class="col-sm-7">
				<div class="panel panel-primary">
					<div class="panel-heading">Latest Updates</div>
					<div class="panel-body" style="max-height: 20rem; min-height: 20rem; overflow: auto;">
						<jsp:include page="userprofile/latestupdates.jsp"></jsp:include>
					</div>
				</div>
			</div>
			
				<div class="col-sm-3">
				<div class="panel panel-primary">
					<div class="panel-heading" style="font-weight: bold;">Help</div>
					<div class="panel-heading" style="background-color: aliceblue;"><a href="Help_collection/Register_info.html" target="blank">How to Register?</a></div>
					<div class="panel-heading" style="background-color: aliceblue;"><a href="Help_collection/login_info.html" target="blank">How to Login?</a></div>
					<div class="panel-heading" style="background-color: aliceblue;"><a href="Help_collection/complaint.html" target="blank">How to Submit a Complain?</a></div>
					<div class="panel-heading" style="background-color: aliceblue;"><a href="Help_collection/suggestion.html" target="blank">How to Submit a Suggestion?</a></div>
					<div class="panel-heading"style="background-color: aliceblue;"><a href="Help_collection/Query.html" target="blank">How to Submit a Query?</a></div>
				</div>
			</div>
			
				
		</div>
	
		<div class="row pt-3 pb-2">
			<div class="col-sm-4">
				<div class="panel panel-primary">
					<div class="panel-heading">Latest Complaints</div>
					<div class="panel-body" style="max-height: 20rem; min-height: 20rem; overflow: auto;">
						<jsp:include page="complaintcard.jsp"></jsp:include>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="panel panel-primary">
					<div class="panel-heading">Latest Suggestions</div>
					<div class="panel-body" style="max-height: 20rem; min-height: 20rem; overflow: auto;">
						<jsp:include page="suggestioncard.jsp"></jsp:include>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="panel panel-primary">
					<div class="panel-heading">Latest Queries</div>
					<div class="panel-body" style="max-height: 20rem; min-height: 20rem; overflow: auto;">
						<jsp:include page="questioncard.jsp"></jsp:include>
					</div>
				</div>
			</div>
				
		</div>
	</div>
	<jsp:include page="common-footer.jsp"></jsp:include>
	<script type="text/javascript" src="<%=host + docBase%>/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/tether.min.js"></script>
	<!-- Bootstrap core JavaScript -->
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/bootstrap.min.js"></script>
	<!-- MDB core JavaScript -->
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/mdb.min.js"></script>
</body>
</html>