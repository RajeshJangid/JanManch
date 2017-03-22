<%@page import="java.sql.ResultSet"%>
<%@page import="com.janmanch.dbconnect.DatabaseResource"%>
<%@page import="com.janmanch.dbconnect.ConstituencyDAO"%>
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
			<div class="col-sm-3">
				<jsp:include page="sidemenu.jsp">
					<jsp:param value="applymla" name="item"/>
				</jsp:include>
			</div>
			<div class="col-sm-9">
				<div class="card">
					<div class="row p-3">
						<div class="col-sm-12">
							<h3>Apply for MLA Elections</h3>
							<p>This application will provide you a platform to stand out. Collect as many votes from people as possible. 
							Remember that people not cast there vote until you have work plans for them and also depend on how you respond 
							to problems common people in your territory are facing. To get Elected in future election you need to provide worksheet 
							which contains all your previous work information as well as what you will be doing next.</p>
							
							<p>
								This application does not mean that you have applied for MLA elections but it will let your voters to know 
								you better in election periods and afterwards. 
							</p>
							
							<p>To apply for MLA please click the below button.</p>
							<form>
								<!--Second row-->
								<div class="row">
									<!--First column-->
									<div class="col-md-12">
										<div class="md-form">
											<textarea type="text" id="experience" placeholder="I have worked here since..." class="md-textarea" style="min-height: 10rem"></textarea>
											<label for="experience">Experience</label>
										</div>
									</div>
								</div>
								<!--/.Second row-->
								<!--First row-->
								<div class="row">
									<!--First column-->
									<div class="col-md-6">
										<div class="row">
											<div class="col-sm-5">
										<label>Constituency</label>
											</div>
											<div class="col-sm-7">
											<div class="md-form">
											
										<%
											ConstituencyDAO cdo = new ConstituencyDAO();
											DatabaseResource res = cdo.getConstituncyList();
											if(res!=null){
													try{
														ResultSet rs = res.getRs();
														if(rs!=null && rs.next()){
										%>
											<select id="constitution" class="form-control">
												<% do{ %>
												<option value="<%= rs.getString("c_id")%>"><%= rs.getString("name") %></option>
												
												<% }while(rs.next());%>
											</select>
											<%
														}
													}catch(Exception e){
														
													}
													} 
											
											%>
										</div>
											</div>
										</div>
										
									</div>
									<!--First column-->
									<div class="col-md-6">
										<div class="md-form">
											<input type="text" id="party" class="form-control">
											<label for="party">Party</label>
										</div>
									</div>
								</div>
								<div class="row">
									
								</div>
								<!--/.First row-->
								<div class="row">
									<!--First column-->
									<div class="col-md-12">
										<div class="md-form">
											<textarea type="text" id="qualification" placeholder="Graduate, HSC Qualifiied..." class="md-textarea"></textarea>
											<label for="qualification">Qualification Details</label>
										</div>
									</div>
								</div>
								<div class="row">
									<!--First column-->
									<div class="col-md-12">
										<div class="md-form">
											<input type="text" id="contact" class="form-control">
											<label for="contact">Contact Details</label>
										</div>
									</div>
								</div>
								
							</form>
							<% if(user!=null && user.getType().equals("U")){ %>
							<button id="mla-btn" class="btn btn-primary">Apply for MLA</button>
							<%}else{ %>
							<button class="btn btn-success">You are already a Candidate.</button>
							<%} %>
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
		$(document).ready(function(){
			
			$('#mla-btn').on('click',function(){
				<% if(user!=null){%>
				var  experience = $('#experience').val();
				var  constitution = $('#constitution').val();
				var party  = $('#party').val();
				var qualification  = $('#qualification').val();
				var contact  = $('#contact').val();
				
				$.post(
						'<%= host+docBase%>/user/<%= user.getAadharId()%>/submitcandidate',
						{
							experience:experience,
							constitution:constitution,
							party:party,
							qualification:qualification,
							contact:contact
						},
						function(response){
							response = response.trim();
							if(response == 'success'){
								alert('You have successfully applied for MLA');
								$('#mla-btn').remove();
							}else{
								alert('Currently the service is not available. please try again.')
							}
						}
				);
				<%}else{%>
				alert('Please login to apply for candidate for MLA');
				<%}%>
			});
		});
	</script>
</body>
</html>