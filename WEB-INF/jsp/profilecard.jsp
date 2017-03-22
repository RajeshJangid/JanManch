<%@page import="java.sql.ResultSet"%>
<%@page import="com.janmanch.dbconnect.DatabaseResource"%>
<%@page import="com.janmanch.dbconnect.CandidateDAO"%>
<%@page import="com.janmanch.Attributes"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	final Logger logger = Logger.getLogger("Mla - login");

	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);

	Attributes attrib = new Attributes();
	String host = attrib.getHost();
	String docBase = attrib.getDocBase();

	CandidateDAO cand = new CandidateDAO();
	DatabaseResource res = cand.getTopCandidateList();

	ResultSet rs = res.getRs();
	if (rs != null && rs.next()) {

		do {
%>
<div class="col-sm-6">
	<div class="card">
		<div class="row">
			<div class="col-sm-3 pt-2">
				<img class="" src="/profile/profile_pic/error" onerror="this.onerror=null;this.src='<%=host + docBase%>/images/default_profile_pic.png'" alt="Card image cap" style="padding: 0px 10px;">
			</div>
			<div class="col-sm-9">
				<div class="card-block">
					<h4><%=rs.getString("name_eng")%></h4>
					<p style="max-height: 5rem; min-height: 5rem; overflow: hidden; box-shadow: inset 0px -3px 9px -6px"><%=rs.getString("introduction")%></p>
					<div class="row">
						<div class="col-sm-12">
							<a href="<%=host + docBase%>/profile/<%=rs.getString("aadhar_id")%>" style="margin-left: auto;">View full profile</a>
						</div>
					</div>
					<button class="btn-thumbs-up btn btn-success" data-cnid="<%=rs.getInt("id")%>">
						<i class="fa fa-thumbs-up"></i> UpVote <span id="cntlike<%=rs.getInt("id")%>">(<%= rs.getInt("upvotes") %>)</span>
					</button>
					<button class="btn-thumbs-down btn btn-danger" data-cnid="<%=rs.getInt("id")%>">
						<i class="fa fa-thumbs-down"></i> DownVote <span id="cntlike<%=rs.getInt("id")%>">(<%= rs.getInt("downvotes") %>)</span>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>
<%
	} while (rs.next());
	} else {
%>
<div class="col-sm-8 p-3" style="margin: auto;">
	<h4 class="text-info text-center">No Candidate have applied for MLA elections right now.</h4>
</div>
<%
	}
%>
<!--/.Card-->