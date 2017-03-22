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
	User user = (User) session.getAttribute("user");
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
	<jsp:include page="../common-header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row mt-1">
			<div class="col-sm-3">
				<jsp:include page="sidemenu.jsp">
					<jsp:param value="profile" name="item" />
				</jsp:include>
			</div>
			<div class="col-sm-9">
				<div class="card">
					<div class="row pl-3 pr-3">
						<div class="col-sm-12">
							<br />
							<h3>Profile</h3>
							<form>
								<div class="row mt-3">
									<div class="col-sm-6">
										<div class="md-form">
											<input disabled type="text" id="form71" value="<%= user.getName() %>" class="form-control" style="color: black;">
											<label class="active" for="form81">Name</label>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="md-form">
											<input disabled type="text" id="form71" value="<%= user.getAadharId() %>" class="form-control" style="color: black;">
											<label class="active" for="form81">Aadhar Number</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="md-form">
											<input disabled type="text" id="form71" value="<%= user.getFamilyIdNo() %>" class="form-control" style="color: black;">
											<label class="active" for="form81">Family Id</label>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="md-form">
											<input disabled type="text" id="form71" value="<%= user.getBahamashahId() %>" class="form-control" style="color: black;">
											<label class="active" for="form81">Bahamashah Id/Acknowledgment Id</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="md-form">
											<input disabled type="text" id="form71" value="<%= user.getmId() %>" class="form-control" style="color: black;">
											<label class="active" for="form81">Member Id</label>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="md-form">
											<input disabled type="text" id="form71" value="<%= user.getEmailId() == null?"":user.getEmailId()  %>" class="form-control" style="color: black;">
											<label class="active" for="form81">Email Address</label>
										</div>
									</div>
								</div>
								<% if(!user.getType().equals("U")){ %>
								<div class="row">
									<div class="col-sm-6">
										<div class="md-form">
											<input disabled type="text" id="form71" value="Active Candidate" class="form-control" style="color: black;">
											<label class="active" for="form81">Candidate status </label>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="md-form">
											<input disabled type="text" id="form71" value="BJP" class="form-control" style="color: black;">
											<label class="active" for="form81">Leader Party</label>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-sm-12">
										<div class="md-form">
											<textarea type="text" id="introduction" class="md-textarea" style="min-height: 7rem;"><%= user.getIntroduction()%></textarea>
											<label class="active" for="introduction">My Introduction </label>
											
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<button class="btn btn-primary" type="button" onclick="saveDetails();">Save Profile</button>
									</div>
								</div>
								<%} %>
							</form>
							
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
				'<%= host+docBase%>/insert/<%= user.getAadharId()%>/saveprofile',
				{
					introduction: intro
					
				},
				function(response){
					response = response.trim();	
					if(response == 'success'){
						alert('Your profile has been updated.');
					}else{
						alert('unable to update your profile.');	
					}
				}
			);
		}
	</script>
</body>
</html>