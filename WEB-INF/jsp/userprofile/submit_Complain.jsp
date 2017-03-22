
<%@page import="com.janmanch.User"%>
<%@page import="com.janmanch.dbconnect.ComplaintDAO"%>
<%@page import="com.janmanch.Attributes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%
	final Logger logger = Logger.getLogger("JanManch - submit_complain.jsp");
	Attributes attrib = new Attributes();
	String host = attrib.getHost();
	String docBase = attrib.getDocBase();
	String aadhar_id = "";
	User user = (User)session.getAttribute("user");
	if(user!=null){
		boolean empty = true;
	
		String action = request.getParameter("action");
		String subject = request.getParameter("subject");
		String description = request.getParameter("description");
		String department = request.getParameter("department");
		String location = request.getParameter("location");
		String subDivision = request.getParameter("subDivision");
		aadhar_id = user.getAadharId();
		logger.info("subDivision: " + subDivision);
	
		if (action != null && action.equals("insert")) {
			if (subject != null && !subject.equals("") && description != null && !description.equals("") && department != null && !department.equals("") && location != null && !location.equals("") && subDivision != null && !subDivision.equals("")) {
				ComplaintDAO suggest = new ComplaintDAO();
	
				int result = suggest.InsertComplaint(aadhar_id, subject, location, department, subDivision, description);
				if (result > 0) {
					out.println("success");
				} else {
					out.println("error");
				}
			}
		} else {
			out.println("null");
		}
	} else {
		out.println("null");
	}
%>