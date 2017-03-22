<%@page import="com.janmanch.User"%>
<%@page import="com.janmanch.Attributes"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	final Logger logger = Logger.getLogger("mlafullprofile.jsp");

	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);

	Attributes attrib = new Attributes();
	String host = attrib.getHost();
	String docBase = attrib.getDocBase();
	User user = (User) request.getAttribute("mlaInfo");
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
</head>
<body>
	<jsp:include page="../common-header.jsp">
		<jsp:param value="home" name="item" />
	</jsp:include>
	<div class="container-fluid">
		<div class="row mt-1">
			<div class="col-sm-12">
				<div class="card">
					<div class="row pl-3 pr-3">
						<div class="col-sm-12">
							<br />
							<div class="row">
								<div class="col-sm-2">
									<img class="" src="<%=user.getName()%>" onerror="this.onerror=null;this.src='<%=host + docBase%>/images/default_profile_pic.png'" alt="Card image cap" style="">
								</div>
								<div class="col-sm-10">
									<h3>Mla Profile</h3>
									<div class="row">
										<div class="col-sm-6">
											<div class="row">
												<div class="col-sm-4">
													<p>Name of Candidate: </p>
												</div>
												<div class="col-sm-8">
													<h5><%=user.getName()%></h5>
												</div>
											</div>
											
											
										</div>
										<div class="col-sm-6">
											<div class="row">
												<div class="col-sm-6">
													<p>Constituencies Applied For: </p>
												</div>
												<div class="col-sm-6">
													<h5><%
													if (user.getConstitutions() != null) {
												%>
												<%=user.getConstitutions().toString()%>
												<%
													}
												%></h5>
												</div>
											</div>
											
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
										<div class="row">
												<div class="col-sm-4">
													<p>Relative Experience: </p>
												</div>
												<div class="col-sm-8">
													<h5><%=user.getMlaExperience()%></h5>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
										<div class="row">
												<div class="col-sm-4">
													<p>Contact Details: </p>
												</div>
												<div class="col-sm-8">
													<h5><%=user.getMlaContact()%></h5>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<p>Information about Candidate</p>
											<h5><%=user.getIntroduction()%>
											</h5>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../common-footer.jsp"></jsp:include>
	<script type="text/javascript" src="<%=host + docBase%>/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/tether.min.js"></script>
	<!-- Bootstrap core JavaScript -->
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/bootstrap.min.js"></script>
	<!-- MDB core JavaScript -->
	<script type="text/javascript" src="<%=host + docBase%>/mdb/js/mdb.min.js"></script>
	<script type="text/javascript">
		
		function saveDetails(){
			var intro = $('#introduction').val();
			$.post(
					'<%=host + docBase%>/insert/<%=user.getAadharId()%>/saveprofile',
							{
								introduction : intro

							}, function(response) {
								response = response.trim();
								if (response == 'success') {
									alert('Your profile has been updated.');
								} else {
									alert('unable to update your profile.');
								}
							});
		}
	</script>
</body>
</html>