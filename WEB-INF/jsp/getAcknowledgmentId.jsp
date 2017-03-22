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
			<div class="col-xs-12 col-sm-10 col-sm-offset-1">
				<br /> <br />
				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">
						<div class="card">
							<div class="card-block">
								<div class="text-center">
									<h3>
										<i class="fa fa-lock"></i> Get Acknowledment Id
									</h3>
									<hr class="mt-2 mb-2">
								</div>
								<!--Body-->
								<div class="md-form">
									<i class="fa fa-phone-square prefix"></i>
									<input id="ack-id-text" type="text" id="form2" class="form-control">
									<label for="form2">Your Registered Mobile No:</label>
								</div>
								<div id="form-otp-group" class="md-form">
									<i class="fa fa-key prefix"></i>
									<input id="otp-text" type="text" id="ack-form" class="form-control">
									<label for="form2">Enter OTP:</label>
								</div>
								<div class="text-center">
									<button id="get-otp-btn" class="btn btn-deep-purple">Get OTP</button>
									<button id="get-ack-id-btn" class="btn btn-deep-purple hidden">Get Ack Id</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=host + docBase%>/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/tether.min.js"></script>
	<!-- Bootstrap core JavaScript -->
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/bootstrap.min.js"></script>
	<!-- MDB core JavaScript -->
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/mdb.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$('#form-otp-group').hide();
		$('#get-ack-id-btn').hide();
		
		$('#get-otp-btn').on('click', function(){
			$('#get-ack-id-btn').show();
			$('#form-otp-group').show();
			$('#get-otp-btn').hide();
		});
		
		$('#get-ack-id-btn').on('click', function(){
			
		});
	});
	</script>
</body>
</html>