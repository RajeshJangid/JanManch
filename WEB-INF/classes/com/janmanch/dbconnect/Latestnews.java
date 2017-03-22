package com.janmanch.dbconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

public class Latestnews {

	

	final Logger logger = Logger.getLogger("JanManch - latestnews");
	public DatabaseResource Getlatestnews(int column, int order, int offset, int noRows) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		DatabaseResource dres = null;
		if (conn != null) {
			dres = new DatabaseResource();
			dres.setConn(conn);
			int param = 1;
			try {
				String ord = order == 1 ? "ASC" : "DESC";
			
				ps = conn.prepareStatement("select news_id,news,department,DATE_FORMAT(publish_date,' %M %e, %Y ') as publish_date from news order by ? " + ord + " limit ?,?");
			
				ps.setInt(param++, column);
				ps.setInt(param++, offset);
				ps.setInt(param++, noRows);
				dres.setPs(ps);
				logger.info("latestnews Query: " + ps);
				dres.setRs(ps.executeQuery());
			} catch (Exception e) {
				logger.error("Exception in latestnews: " + e.getMessage());
				dres.closeResources();
			}
		}
		return dres;
	}
	public int InsertNews(String news, String department) {
		int result = 0;
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("insert into news(news,department,publish_date) values (?,?,now())");
				ps.setString(1, news);
				ps.setString(2, department);
				

				logger.info("InsertNews query: " + ps);
				result = ps.executeUpdate();
			} catch (Exception e) {
				logger.error("Exception in InsertNews: " + e.getMessage());
			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return result;
	}
	
	
}
