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
					<jsp:param value="submitquery" name="item" />
				</jsp:include>
			</div>
			<div class="col-sm-9">
				<div class="card">
					<div class="row pl-3 pr-3">
						<div class="col-sm-12">
							<br />
							<h3 class="text-center">Submit Your Query</h3>
							<form>
								<div class="row">
									<!--First column-->
									<div class="col-md-12">
										<div class="md-form">
											<input type="text" id="form80" class="form-control">
											<label for="form80">Subject</label>
										</div>
									</div>
								</div>
								<!--Second row-->
								<div class="row">
									<!--First column-->
									<div class="col-md-12">
										<div class="md-form">
											<textarea type="text" id="form76" class="md-textarea" style="min-height: 10rem"></textarea>
											<label for="form76">Your Query</label>
										</div>
									</div>
								</div>
								<!--/.Second row-->
								<!--First row-->
								<div class="row">
									<!--First column-->
									<div class="col-md-6">
										<div class="md-form">
											<input type="text" id="form81" class="form-control">
											<label for="form81">Location</label>
										</div>
									</div>
									<!--Second column-->
									<div class="col-md-6">
										<div class="md-form">
											<input type="text" id="form82" class="form-control">
											<label for="form82">Contact</label>
										</div>
									</div>
								</div>
								<!--/.First row-->
								<!--Third row-->
								<div class="row">
									<!--First column-->
									<div class="col-md-12">
										<div class="md-form">
											<button type="button" class="btn btn-primary" onClick="SubmitQuery();">Submit Query</button>
											<button type="button" class="btn btn-default" onClick="resetfields1();">Reset</button>
										</div>
									</div>
								</div>
								<!--/.Third row-->
							</form>
							<div id="suggestion-message" class="alert alert-success hidden"></div>
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
		
	
function resetfields1() {

	$('#form76').val('');
	$('#form81').val('');
	$('#form82').val('');
	$('#form80').val('');
	
}

	
	function SubmitQuery() {
		var subject = $('#form80').val().trim();
		var query = $('#form76').val().trim();
		var location = $('#form81').val().trim();
		var contact = $('#form82').val().trim();
		if (query != '' && location != '' && contact != '' && subject !='') {
			$.post(
					'<%=host + docBase%>/insert/<%= user.getAadharId()%>/submit_query',
					{
						action : 'insert',
						subject : subject,
						query : query,
						location : location,
						contact : contact

					},
					function(response) {
						response = response.trim();
						if (response == 'success') {
							showresponsemessage(
									'#suggestion-message',
									'Thank you for your response. Your query has been stored. Please visit queries section to see what other people are talking about.',
									'alert-success');
							resetfields1();
							/* window.location.reload(); */
						} else if (response == 'null') {
							showresponsemessage(
									'#suggestion-message',
									'Your session is expired try logging in again.',
									'alert-warning');
						} else {
							showresponsemessage(
									'#suggestion-message',
									'Unable to store your query, please try again. If problem persists please contact administrator.',
									'alert-danger');
						}
					});
			}
		}

		function showresponsemessage(id, message, type) {
			$(id).removeClass('hidden');
			$(id).removeClass('alert-danger').removeClass('alert-success')
					.removeClass('alert-info').removeClass('alert-warning');
			$(id).html(message);
			$(id).addClass(type);
			setTimeout(function() {
				$(id).addClass('hidden');
			}, 5000);
		}
	</script>
</body>
</html>