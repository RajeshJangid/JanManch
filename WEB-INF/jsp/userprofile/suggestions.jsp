<%@page import="com.janmanch.User"%>
<%@page import="com.janmanch.Attributes"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	final Logger logger = Logger.getLogger("suggestions.jsp");

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
		<div class="row">
			<div class="col-sm-3">
				<jsp:include page="sidemenu.jsp">
					<jsp:param value="suggestions" name="item" />
				</jsp:include>
			</div>
			<div class="col-sm-9" id="companytable" style="min-height: 400px;">
				<br />
				<h3 class="ml-2">Suggestions from People</h3>
				<jsp:include page="suggestioncard.jsp">
					<jsp:param value="all" name="searchtype" />
					<jsp:param value="true" name="editmode" />
					<jsp:param value="1" name="column" />
					<jsp:param value="1" name="order" />
					<jsp:param value="1" name="pageno" />
					<jsp:param value="10" name="numRows" />
				</jsp:include>
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
		function incrementVote(id){
			var cnt = $(id).text();
			console.log(cnt);
			cnt = cnt.replace('(','');
			cnt = cnt.replace(')','');
			cnt = parseInt(cnt);
			console.log(cnt);
			cnt+=1;
			$(id).html('('+cnt+')');
		}
		
		function decrementVote(id){
			var cnt = $(id).text();
			console.log(cnt);
			cnt = cnt.replace('(','');
			cnt = cnt.replace(')','');
			cnt = parseInt(cnt);
			console.log(cnt);
			cnt-=1;
			if(cnt>=0)
				$(id).html('('+cnt+')');	
		}
		
		$(document).ready(function(){
			$(document).on('click','.fa-thumbs-up', function(){
				var id = $(this).attr('data-sid');
				<%if (user != null) {%>
				$.post(
					'<%=host + docBase%>/like/<%=user.getAadharId()%>/suggestion',
					{
						suggestion_id: id
					},
					function(response){
						response = response.trim();
						console.log(response);
						if(response == 'success'){
							incrementVote('#cntlike'+id);
						}else if(response == 'changed'){
							incrementVote('#cntlike'+id);
							decrementVote('#cntdislike'+id);
						}else if(response == 'already'){
							alert('You have already upvoted');
						}else if(response == 'ownsuggestion'){
							alert('You are not allowed to upvote your own suggestion.');
						}else if(response == 'sessionexpired'){
							alert('Your session has expired. Please login to upvote this.');
						}else{
							alert('unable to process request');
						}
					}
				);
				<%} else {%>
				alert('You need to login to upvote this.');
				<%}%>
			});
			
			$(document).on('click','.fa-thumbs-down', function(){
				var id = $(this).attr('data-sid');
				<%if (user != null) {%>
				$.post(
					'<%=host + docBase%>/dislike/<%=user.getAadharId()%>
		/suggestion',
																{
																	suggestion_id : id
																},
																function(
																		response) {
																	response = response
																			.trim();
																	if (response == 'success') {
																		incrementVote('#cntdislike'
																				+ id);
																	} else if (response == 'changed') {
																		decrementVote('#cntlike'
																				+ id);
																		incrementVote('#cntdislike'
																				+ id);
																	} else if (response == 'already') {
																		alert('You have already downvoted');
																	} else if (response == 'sessionexpired') {
																		alert('Your session has expired. Please login to upvote this.');
																	} else if (response == 'ownsuggestion') {
																		alert('You are not allowed to downvote your own suggestion.');
																	} else {
																		alert('unable to process request');
																	}
																});
	<%} else {%>
		alert('You need to login to downvote this.');
	<%}%>
		});
						});
		function fetchcompany(column, order, pageno, numRows, search) {
			var searchtype = '';
			var name = '';
			if (typeof search != 'undefined') {
				searchtype = $('#searchtype').val();
				if (searchtype == 'name') {
					name = $('#searchtext').val();
				}
			} else {
				searchtype = $('#pre-searchtype').val();
				if (searchtype == 'name') {
					name = $('#pre-name').val();
				}
			}

			console.log('parameters initialised');
			$.post('suggestioncard.jsp', {
				editmode : 'true',
				searchtype : searchtype,
				searchtext : name,
				column : column,
				order : order,
				pageno : pageno,
				numRows : numRows
			}, function(response) {
				$('#companytable').html(response.trim());

			});
		}
	</script>
</body>
</html>