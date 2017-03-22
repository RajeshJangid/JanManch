<%@page import="com.janmanch.User"%>
<%@page import="com.janmanch.Attributes"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	final Logger logger = Logger.getLogger("candidates.jsp");

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
<style type="text/css">
.panel-primary {
	border: 1px solid #337ab7;
}

.panel {
	margin-bottom: 20px;
	background-color: #fff;
	border-radius: 4px;
	-webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
	box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .16), 0 2px 10px 0
		rgba(0, 0, 0, .12);
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
		<jsp:param value="candidates" name="item" />
	</jsp:include>
	<div class="container-fluid">
		<div class="row pt-3 pb-2">
				<jsp:include page="profilecard.jsp"></jsp:include>
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
			$(document).on('click','.btn-thumbs-up', function(){
				var id = $(this).attr('data-cnid');
				<% if(user!=null){%>
				$.post(
					'<%=host+docBase%>/like/<%= user.getAadharId()%>/candidate',
					{
						candidate_id: id
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
							alert('You have already upvoted this candidate.');
						}else if(response == 'ownsuggestion'){
							alert('You are not allowed to upvote yourself.');
						}else if(response == 'sessionexpired'){
							alert('Your session has expired. Please login to upvote this candidate.');
						}else{
							alert('unable to process request');
						}
					}
				);
				<%}else{%>
				alert('You need to login to upvote this candidate.');
				<%}%>
			});
			
			$(document).on('click','.btn-thumbs-down', function(){
				var id = $(this).attr('data-cnid');
				<% if(user!=null){%>
				$.post(
					'<%=host+docBase%>/dislike/<%= user.getAadharId()%>/candidate',
					{
						candidate_id: id
					},
					function(response){
						response = response.trim();
						if(response == 'success'){
							incrementVote('#cntdislike'+id);
						}else if(response == 'changed'){
							decrementVote('#cntlike'+id);
							incrementVote('#cntdislike'+id);
						}else if(response == 'already'){
							alert('You have already downvoted this candidate.');
						}else if(response == 'sessionexpired'){
							alert('Your session has expired. Please login to downvote the candidate.');
						}else if(response == 'candidatehimself'){
							alert('You are not allowed to downvote yourself.');
						}else{
							alert('unable to process request');
						}
					}
				);
				<%}else{%>
				alert('You need to login to downvote this candidate.');
				<%}%>
				
			});
		});
			</script>
</body>
</html>