<%@page import="com.janmanch.Attributes"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	final Logger logger = Logger.getLogger("login.jsp");

	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);

	Attributes attrib = new Attributes();
	String host = attrib.getHost();
	String docBase = attrib.getDocBase();
	String reg = (String)request.getAttribute("reg");
	if(reg == null)
		reg = "";
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
		<jsp:param value="login" name="item"/>
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
						<%
							if(reg.equals("success")){
								%>
								<div class="alert">
									<p>You have registered successfully. Please login to continue</p>
								</div>
								<%		
							}
						%>
						
					<h3 class="text-center mb-2">Login to Application</h3>
					
					<form action="verifylogin" method="post" onsubmit="validate();" autocomplete="off">
						
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
									<input type="password" id="bham_pass" name="bham_pass" class="form-control" autocomplete="off">
									<label for="bham_pass">Password</label>
								</div>
							</div>
						</div>
						<!--Third row-->
						<div class="row">
							<!--First column-->
							<div class="col-md-4">
								<div class="md-form">
									<button type="submit" class="btn btn-primary" id="login_btn">Login</button>
								</div>
							</div>
							
						</div>
						<div class="row">
							<div class="col-sm-12 text-right">
								<a href="register">Don't have account, Register here.
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
		$(document).ready(function() {
		  setTimeout(function(){
		    $('[autocomplete=off]').val('');
		  }, 200);
		});
		function validate(){
			var ack_id = $('#ack_id').val();
			return true;
		}
	</script>
</body>
</html>