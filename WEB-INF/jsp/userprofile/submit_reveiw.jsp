
<%@page import="com.janmanch.User"%>
<%@page import="com.janmanch.dbconnect.ReviewDAO"%>
<%@page import="com.janmanch.dbconnect.SuggestionDAO"%>
<%@page import="com.janmanch.Attributes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%
	final Logger logger = Logger.getLogger("JanManch - submit_suggestion.jsp");
	Attributes attrib = new Attributes();
	String host = attrib.getHost();
	String docBase = attrib.getDocBase();
	String aadhar_id = "";

	boolean empty = true;

	String action = request.getParameter("action");
	String subject = request.getParameter("subject");
	String location = request.getParameter("location");
	String contact = request.getParameter("contact");
	String description = request.getParameter("query");
	logger.info("contact: " + contact);
	User user = (User) session.getAttribute("user");
	if (user != null) {
		if (action != null && action.equals("insert")) {
			if (subject != null && !subject.equals("") && location != null && !location.equals("") && contact != null && !contact.equals("") && description != null && !description.equals("")) {
				ReviewDAO reveiw = new ReviewDAO();
				aadhar_id = user.getAadharId();
				int result = reveiw.SubmitReview(aadhar_id, subject, location, contact, description);
				if (result > 0) {
					out.println("success");
				} else {
					out.println("error");
				}
			}
		} else {
			out.println("null");
		}
	}else{
		out.println("null");
	}
%>