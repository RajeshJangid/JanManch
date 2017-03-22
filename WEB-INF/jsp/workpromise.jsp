<%@page import="com.janmanch.Attributes"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	final Logger logger = Logger.getLogger("workpromise.jsp");

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
</head>
<body>
	<jsp:include page="common-header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-1">
			</div>
			<div class="col-xs-12 col-sm-10 col-sm-offset-1">
				<br />
				<br />
				<div class="card">
					<div class="col-sm-12">
					<br />
					<h3 class="text-center">Work Promise Form</h3>
					<form>
						<div class="row">
							<!--First column-->
							<div class="col-md-12">
								<div class="md-form">
									<input type="text" id="form81" class="form-control validate">
									<label for="form81" data-error="wrong" data-success="right">Subject</label>
								</div>
							</div>
						</div>
						<!--Second row-->
						<div class="row">
							<!--First column-->
							<div class="col-md-12">
								<div class="md-form">
									<textarea type="text" id="form76" class="md-textarea" style="min-height: 10rem"></textarea>
									<label for="form76">Complete Work Summary</label>
								</div>
							</div>
						</div>
						<!--/.Second row-->
						<!--First row-->
						<div class="row">
							<!--First column-->
							<div class="col-md-6">
								<div class="md-form">
									<input type="text" id="form81" class="form-control validate">
									<label for="form81" data-error="wrong" data-success="right">Deadline</label>
								</div>
							</div>
							<!--Second column-->
							<div class="col-md-6">
								<div class="md-form">
									<input type="text" id="form82" class="form-control validate">
									<label for="form82" data-error="wrong" data-success="right">Budget</label>
								</div>
							</div>
						</div>
						<!--/.First row-->
						
						<!--Third row-->
						<div class="row">
							<!--First column-->
							<div class="col-md-4">
								<div class="md-form">
									<button type="button" class="btn btn-primary">Add</button>
									<button type="button" class="btn btn-default">Reset</button>
								</div>
							</div>
							
						</div>
						<!--/.Third row-->
					</form>
					</div>
				</div>
				<br />
				<br />
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=host + docBase%>/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/tether.min.js"></script>
	<!-- Bootstrap core JavaScript -->
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/bootstrap.min.js"></script>
	<!-- MDB core JavaScript -->
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/mdb.min.js"></script>
</body>
</html>