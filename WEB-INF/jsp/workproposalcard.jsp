<%@page import="com.janmanch.dbconnect.WorkPromiseDAO"%>
<%@page import="com.janmanch.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.janmanch.dbconnect.SuggestionDAO"%>
<%@page import="com.janmanch.dbconnect.DatabaseResource"%>
<%@page import="com.janmanch.Attributes"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.sql.ResultSet"%>
<%
	final Logger logger = Logger.getLogger("JanManch - workproposalcard.jsp");
	Attributes attrib = new Attributes();
	String host = attrib.getHost();
	String docBase = attrib.getDocBase();
	boolean empty = true;

		String editmode = request.getParameter("editmode");
		String column = request.getParameter("column");
		String order = request.getParameter("order");
		String pageno = request.getParameter("pageno");
		String numRows = request.getParameter("numRows");
		String searchtext = request.getParameter("searchtext");
		String searchtype = request.getParameter("searchtype");
		logger.info("editmode: " + editmode + " column: " + column + " order: " + order + " pageno: " + pageno + " numRows: " + numRows + " searchtype: " + searchtype + " ");
		if (editmode == null || (!editmode.equals("true") && !editmode.equals("false")))
			editmode = "false";

		int col = 1;
		int ord = 1;
		int off = 0;
		int noRow = 10;

		if (column != null) {
			try {
				col = Integer.parseInt(column);
			} catch (Exception e) {

			}
		}
		if (order != null) {
			try {
				ord = Integer.parseInt(order);
			} catch (Exception e) {

			}
		}
		if (pageno != null) {
			try {
				off = Integer.parseInt(pageno);
			} catch (Exception e) {

			}
		}
		if (numRows != null) {
			try {
				noRow = Integer.parseInt(numRows);
			} catch (Exception e) {

			}
		}
		int MAX_ROWS = noRow;
		int pages = 0;
		WorkPromiseDAO company = new WorkPromiseDAO();
		int totalRecords = 0;

		pages = (totalRecords % MAX_ROWS == 0 ? (totalRecords / MAX_ROWS) : (totalRecords / MAX_ROWS + 1));
		logger.info("noOfRecords: " + totalRecords + " :: max Rows per page: " + MAX_ROWS + " :: no of pages: " + pages);
		DatabaseResource data = null;
		
		data = company.getWorkPromiseListByMLA(col, ord, off <= 1 ? 0 : ((off - 1) * MAX_ROWS), MAX_ROWS);
		
		ResultSet rs = null;
		int lastrowcount = 0;
		String order1 = "1";

		if (data != null) {
			try {
				rs = data.getRs();

				if (rs != null && rs.next()) {
					empty = false;

					do {
						lastrowcount++;

						String id = rs.getString("id");

						String proposal_date = rs.getString("proposal_date");
						String title = rs.getString("title");
						String summary = rs.getString("summary");
						String upvotes = rs.getString("upvotes");
						String downvotes = rs.getString("downvotes"); 
%>
<br />
<div class="card" id="card_<%=id%>">
	<div class="col-sm-12 mt-1 mb-1">
		<p>
			<span style="font-weight: 500;">Title: </span>
			<%=title%></p>
		<p><%=summary%></p>
		
		<p>
			<span class="m-2"><i class="fa fa-thumbs-up text-success" style="cursor: pointer;" data-wpid="<%=id%>"></i><span id="cntlike<%=id%>" class="m-1">(<%=upvotes%>)
			</span></span> <span class="m-2"><i class="fa fa-thumbs-down text-danger" style="cursor: pointer;" data-wpid="<%=id%>"></i><span id="cntdislike<%=id%>" class="m-1">(<%=downvotes%>)
			</span></span> <span class="m-2 text-muted pull-right" style="font-size: .8em"><%=proposal_date%></span>
		</p>
	</div>
</div>
<%
	} while (rs.next());

				}
			} catch (Exception e) {
				logger.error("Exception " + e.getMessage());
			} finally {
				rs = null;
				data.closeResources();
			}
		}
	if (empty) {
%>
<div class="alert alert-info" style="margin-bottom: 0px;">There are no records to show.</div>
<%
	}
%>