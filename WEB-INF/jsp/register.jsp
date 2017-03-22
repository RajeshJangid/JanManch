<%@page import="com.janmanch.Attributes"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	final Logger logger = Logger.getLogger("register.jsp");

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
	<jsp:include page="common-header.jsp">
		<jsp:param value="register" name="item"/>
	</jsp:include>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3">
			</div>
			<div class="col-xs-12 col-sm-6">
				<br />
				<br />
				<div class="card">
					<br />
					<div class="col-sm-12">
					<h3 class="text-center">Register Yourself</h3>
					
					<form action="registeruser" method="post" onsubmit="validate();">
						
						<!--First row-->
						<div class="row">
							
							<!--First column-->
							<div class="col-md-12">
								<div class="md-form">
								    <input type="text" id="ack_id" name="ack_id" class="form-control" autocomplete="off">
								    <label for="ack_id">Your Bhamashah Acknowledgment Id</label>
								</div>
							</div>
							
						</div>
						<!--/.First row-->
						<div class="row">
							<!--Second column-->
							<div class="col-md-12">
								<div class="md-form">
									<input type="text" id="mob_no" name="mob_no" class="form-control" autocomplete="off">
									<label for="mob_no">Enter Registered Mobile Number</label>
								</div>
							</div>
						</div>
						<!--Third row-->
						<div class="row">
							<!--First column-->
							<div class="col-md-6">
								<div class="md-form">
									<input type="password" id="new_pass" name="new_pass" class="form-control" autocomplete="off">
									<label for="new_pass" data-error="wrong" data-success="right">New Password</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="md-form">
									<input type="password" id="conf_pass" name="conf_pass" class="form-control" autocomplete="off">
									<label for="conf_pass">Confirm Password</label>
								</div>
							</div>
						</div>
						<div class="row">
							<!--First column-->
							<div class="col-md-4">
								<div class="md-form">
									<button type="submit" class="btn btn-primary">Register</button>
								</div>
							</div>
							
						</div>
						<div class="row">
							<div class="col-sm-12 text-right">
								<a href="register">Already have account, click here to login.
								</a>
								<br />
								<a href="get-acknowledgment">Forgot acknowledgment? click here.
								</a>
								
							</div>
						</div>
						<br />
						<!--/.Third row-->
					</form>
					</div>
				</div>
				<br />
				<br />
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
	<script type="text/javascript">
		function validate(){
			var ack_id = $('#ack_id').val();
			var mob_no = $('#mob_no').val();
			var new_pass = $('#new_pass').val();
			var conf_pass = $('#conf_pass').val();
			
			return true;
		}
	</script>
</body>
</html>