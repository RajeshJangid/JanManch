<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.janmanch.Attributes"%>
<%
	final Logger logger = Logger.getLogger("Mla - signup");
	Attributes attrib = new Attributes();
	String host = attrib.getHost();
	String docBase = attrib.getDocBase();
	
	String website = request.getParameter("website");
	if(website == null)
		website = "";
	logger.info("website"+ website);
	
	
	%>
<!DOCTYPE html>
<html lang="en">
	<head>
	
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		<title>Sign Up</title>
		<link href="https://fonts.googleapis.com/css?family=Lato:300,300i,400,400i,700,700i" rel="stylesheet">  
		<link href="https://fonts.googleapis.com/css?family=Roboto:300i,400,400i,500,500i|Slabo+27px" rel="stylesheet"> 
		
		<!-- Bootstrap -->
		<link href="<%=host+docBase%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="<%=host+docBase%>/font-awesome/css/font-awesome.min.css"
			rel="stylesheet">
		<link href="<%=host+docBase%>/css/login.css" rel="stylesheet">
		<link href="<%=host+docBase%>/css/headerfooter.css" rel="stylesheet">
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
			<script src="<%=host+docBase%>/js/html5shiv.min.js"></script>
			<script src="<%=host+docBase%>/js/respond.min.js"></script>
		<![endif]-->
		
	</head>
	<body>
	

		<div class="container-fluid" style="background-color: #e1f5ff;">
			<div class="row">
				<div class="login-card col-xs-12 col-sm-8 col-md-6 col-lg-4 col-sm-offset-2 col-md-offset-3 col-lg-offset-4" style="margin-top:25px">
					<form>
						<h2 class="text-center">Sign Up</h2>
						
						<div class="form-group" style="position: relative;">
							<label class="text-muted" for="name">Aadhar Number</label>
							<input class="form-control" id="domain" name="domain" placeholder="Aadhar Number" type="text" value="<%=website%>"/>
							<div id="domain-error" style="display: none;z-index: 10; position: absolute; background-color: rgb(255, 86, 86); padding: 5px; color: white; bottom: -35px; left: 40px;" class="input-text-error">
								<span style="position: absolute; top: -5px; left: 6px; display: inline-block; transform: rotateZ(45deg); width: 10px; height: 10px; background-color: rgb(255, 86, 86);"></span>
								Please enter valid domain name.
							</div>
						</div>
						
						<div class="form-group" style="position: relative;">
							<label class="text-muted" for="name">Name</label>
							<input class="form-control" id="name" name="name" placeholder="enter your name" type="text"/>
							<div id="name-error" style="display: none;z-index: 10; position: absolute; background-color: rgb(255, 86, 86); padding: 5px; color: white; bottom: -35px; left: 40px;" class="input-text-error">
								<span style="position: absolute; top: -5px; left: 6px; display: inline-block; transform: rotateZ(45deg); width: 10px; height: 10px; background-color: rgb(255, 86, 86);"></span>
								Only alphabets are allowed. Name must contain at least 2 characters.
							</div>
						</div>
						
						
						<div class="form-group" style="position: relative;">
							<label class="text-muted" for="name"> Email Id</label>
							<input class="form-control" id="email_id" name="email_id" placeholder="email@example.com" type="text"/>
							<div id="email-error" style="display: none;z-index: 10; position: absolute; background-color: rgb(255, 86, 86); padding: 5px; color: white; bottom: -35px; left: 40px;" class="input-text-error">
								<span style="position: absolute; top: -5px; left: 6px; display: inline-block; transform: rotateZ(45deg); width: 10px; height: 10px; background-color: rgb(255, 86, 86);"></span>
								Please enter valid email id.
							</div>
						</div>
						
						
						<div class="form-group" style="position: relative;">
							<label class="text-muted" for="name">Address</label>
							<input class="form-control" id="address" name="address" placeholder="address _details" type="text"/>
							<div id="email-error" style="display: none;z-index: 10; position: absolute; background-color: rgb(255, 86, 86); padding: 5px; color: white; bottom: -35px; left: 40px;" class="input-text-error">
								<span style="position: absolute; top: -5px; left: 6px; display: inline-block; transform: rotateZ(45deg); width: 10px; height: 10px; background-color: rgb(255, 86, 86);"></span>
								Please enter valid address.
							</div>
						</div>
						
							<div class="form-group" style="position: relative;">
							<label class="text-muted" for="name">Contact</label>
							<input class="form-control" id="address" name="address" placeholder="enter contact" type="text"/>
							<div id="email-error" style="display: none;z-index: 10; position: absolute; background-color: rgb(255, 86, 86); padding: 5px; color: white; bottom: -35px; left: 40px;" class="input-text-error">
								<span style="position: absolute; top: -5px; left: 6px; display: inline-block; transform: rotateZ(45deg); width: 10px; height: 10px; background-color: rgb(255, 86, 86);"></span>
								Please enter valid contact.
							</div>
						</div>
							<div class="form-group" style="position: relative;">
							<label class="text-muted" for="name">Account</label>
							<input class="form-control" id="address" name="address" placeholder="enter valid account number" type="text"/>
							<div id="email-error" style="display: none;z-index: 10; position: absolute; background-color: rgb(255, 86, 86); padding: 5px; color: white; bottom: -35px; left: 40px;" class="input-text-error">
								<span style="position: absolute; top: -5px; left: 6px; display: inline-block; transform: rotateZ(45deg); width: 10px; height: 10px; background-color: rgb(255, 86, 86);"></span>
								Please enter valid account number.
							</div>
						</div>
						<div class="form-group" style="position: relative;">
							<label class="text-muted" for="name">Work Promises</label>
				 <textarea rows="15" cols="45" id="work_promises">
				 
				 </textarea> 
							<div id="email-error" style="display: none;z-index: 10; position: absolute; background-color: rgb(255, 86, 86); padding: 5px; color: white; bottom: -35px; left: 40px;" class="input-text-error">
								<span style="position: absolute; top: -5px; left: 6px; display: inline-block; transform: rotateZ(45deg); width: 10px; height: 10px; background-color: rgb(255, 86, 86);"></span>
							
							</div>
						</div>
						
						<div class="form-group">
							<div id="pass-response-message" class="alert alert-success hidden"></div>
	
		<!-- <input id="reset-btn"  style="float: right; background: #c5c5c5; margin: 5px 5px 0 0; width: 30%;
	color: #FFFFFF; border: 0 none; border-radius: 1px; cursor: pointer; padding: 5px 5px; margin: 5px 5px 16px 0;"  value="Reset"   type="button">						 -->
						<!-- <input id="submit_btn"  style="float: right; background: #546393; margin: 5px 50px 0 0; width: 30%; 
	color: #FFFFFF; border: 0 none; border-radius: 1px; cursor: pointer; padding: 5px 5px; margin: 5px 5px 16px 0;"  value="Register"   type="button"> -->
			
						<input id="submit_btn"
							style="float: right; background: #546393; margin: -3px 11px 19px 1px;width: 30%; font-weight: bold; color: #FFFFFF; border: 0 none; border-radius: 1px; cursor: pointer; padding: 5px 5px;"
							value="Register" type="button"> 
							
						
							
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<script src="<%= host+docBase %>/js/jquery.min.js"></script>
		<script src="<%= host+docBase %>/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript">

    
		
							$(document).ready(function(){
								$('#submit_btn').on('click', function(){
									var domain = $("#domain").val().trim();
								    var email = $("#email_id").val().trim();
								    var name = $("#name").val().trim();
									console.log("alertwww");
					                 validate();
					                 console.log("alert");
					                	$.post('<%=host + docBase%>/domainregistration.jsp',
										{
											domain : domain,
											email : email,
											name : name
										},
									function(response) {
										response = response.trim();
										if (response == 'success') {
											$('#pass-response-message').html('signup successfully.');
											window.location.replace('<%=host + docBase%>/commentcode.jsp');
											$(
													'#pass-response-message')
													.removeClass(
															'hidden');
											$(
													'#pass-response-message')
													.removeClass(
															'alert-danger');
											$(
													'#pass-response-message')
													.addClass(
															'alert-success');
											reset();
											
										} else {
											if (response == 'error') {
												reset();
												$(
														'#pass-response-message')
														.html(
																'domain already registered.');
											
												<%-- window.location.replace('<%=host + docBase%>/signup.jsp'); --%>
												
											} else {
												$(
														'#pass-response-message')
														.html(
																'plz fill the data correctly.');
											}
											$(
													'#pass-response-message')
													.removeClass(
															'hidden');
											$(
													'#pass-response-message')
													.removeClass(
															'alert-success');
											$(
													'#pass-response-message')
													.addClass(
															'alert-danger');
											
										}
										setTimeout(
												function() {
													$(
															'#pass-response-message')
															.addClass(
																	'hidden');
												},
												2000);
									});
					
					                 
					});
				/* 	$('#reset-btn').on('click',
							reset); */
						});
		
		 function reset()
					{
						$('#domain').val('');
					   $('#email_id').val('');
					    $('#name').val('');
					}
					
					
					
		
    
			function validate(){
				var domain = $("#domain").val().trim();
			    var email = $("#email_id").val().trim();
			    var name = $("#name").val().trim();
			    if (domain=="") {
			        $('#domain').focus();
			        $('#domain').parent().addClass('has-error');
			        $( "#domain-error" ).show();
			        setTimeout(function() {
				        $("#domain-error").hide();
				    }, 10000);
			        return false;
			    }
			    if (domain!='') {
			    	domain = domain.replace(/^(https?|http):\/\//, '');
			    	
			    	if ((domain.lastIndexOf("/")+1) == domain.length) {
			    		domain = domain.substring(0, domain.length - 1);
			    	}
			    	
			    	var pattern =/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
				    if (!pattern.test(domain)) {
				    	var domainregex = new RegExp("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\-]*[A-Za-z0-9])$");
				    	if(!domainregex.test(domain)){
				    		$('#domain').focus();
					        $('#domain').parent().addClass('has-error');
							$( "#domain-error" ).show();
						 	setTimeout(function() {
						        $("#domain-error").hide();
						    }, 10000);
							return false;
						}else{
							var pattern =/^([a-zA-Z0-9_.-]+\.)+([a-zA-Z]{2,})$/;
							if (!pattern.test(domain)) {
								$('#domain').focus();
						        $('#domain').parent().addClass('has-error');
								$( "#domain-error" ).show();
							 	setTimeout(function() {
							        $("#domain-error").hide();
							    }, 10000);
								return false;
							}
						}
				 	}
			    }
			    
			    $('#domain').parent().removeClass('has-error');
			    
			    if (email=='') {
			        $('#email_id').focus();
			        $('#email_id').parent().addClass('has-error');
			        $( "#email-error" ).show();
			        setTimeout(function() {
				        $("#email-error").hide();
				    }, 10000);
			        return false;
			    }
			    if (email!='') {
			    	var reg = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			        if (!reg.test(email)){
			        	$('#email_id').focus();
				        $('#email_id').parent().addClass('has-error');
				        $( "#email-error" ).show();
				        setTimeout(function() {
					        $("#email-error").hide();
					    }, 10000);
			            return false; 
			       	} 
			    }
			    
			    $('#email_id').parent().removeClass('has-error');
			    
			    if (name=='') {
			    	$('#name').focus();
			        $('#name').parent().addClass('has-error');
			        $( "#name-error" ).show();
			        setTimeout(function() {
				        $("#name-error").hide();
				    }, 10000);
			        return false;
			    }
			    
			    $('#name').parent().removeClass('has-error');
			    
			    return true;
			}
		</script>
	</body>
</html>