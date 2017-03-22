
<%@page import="com.janmanch.dbconnect.Latestnews"%>
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
		String news = request.getParameter("news");
	
		String department = request.getParameter("department");
		
		aadhar_id = user.getAadharId();

	
		if (action != null && action.equals("insert")) {
			if (news != null && !news.equals("") && department != null && !department.equals("")) {
				Latestnews suggest = new Latestnews();
	
				int result = suggest.InsertNews(news, department);
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