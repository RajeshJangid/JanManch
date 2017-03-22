package com.janmanch.dbconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class SuggestionDAO {

	final Logger logger = Logger.getLogger("JanManch - SuggestionDAO");

	public int InsertSuggestion(String aadhar_id, String suggestion, String location, String contact, String subject) {
		int result = 0;
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("insert into suggestion_table(user_aadhar_id,subject,description,location,contact,publish_date) values (?,?,?,?,?,now())");
				ps.setString(1, aadhar_id);
				ps.setString(2, subject);
				ps.setString(3, suggestion);
				ps.setString(4, location);
				ps.setString(5, contact);
				logger.info("InsertSuggestion query: " + ps);
				result = ps.executeUpdate();
			} catch (Exception e) {
				logger.error("Exception in InsertSuggestion: " + e.getMessage());
			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return result;
	}

	public DatabaseResource getSuggestionList(int column, int order, int offset, int noRows) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		DatabaseResource dres = null;
		if (conn != null) {
			dres = new DatabaseResource();
			dres.setConn(conn);
			int param = 1;
			try {
				String ord = order == 1 ? "ASC" : "DESC";
				String query = "select suggestion_table.suggestion_id,if(upvotes is null,0,upvotes) upvotes, " +
						"if(downvotes is null, 0, downvotes) downvotes, subject,DATE_FORMAT(publish_date,' %M %e, %Y ') " +
						"as publish_date,location,description,user_aadhar_id,contact from suggestion_table " +
						"left join (select suggestion_id, sum(upVote) as upvotes, sum(downVote) downvotes from suggestion_likes " +
						"group by suggestion_id) t1 on suggestion_table.suggestion_id=t1.suggestion_id order by ? "+ord+" limit ?,?";
				ps = conn.prepareStatement(query);
				ps.setInt(param++, column);
				ps.setInt(param++, offset);
				ps.setInt(param++, noRows);
				dres.setPs(ps);
				logger.info("getSuggestionList Query1: " + ps);
				dres.setRs(ps.executeQuery());
			} catch (Exception e) {
				logger.error("Exception in getSuggestionList: " + e.getMessage());
				dres.closeResources();
			}
		}
		return dres;
	}
	
	
	public DatabaseResource getSuggestionList_user(String user_id,int column, int order, int offset, int noRows) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		DatabaseResource dres = null;
		if (conn != null) {
			dres = new DatabaseResource();
			dres.setConn(conn);
			int param = 1;
			try {
				String ord = order == 1 ? "ASC" : "DESC";
				String query = "select t2.suggestion_id,if(upvotes is null,0,upvotes) upvotes,if(downvotes is null, 0, downvotes) downvotes, subject,"
						+ "DATE_FORMAT(publish_date,' %M %e, %Y ') as publish_date,location,description,user_aadhar_id,contact from"
						+ " (select * from suggestion_table where user_aadhar_id in (select aadhar_id from login_table "
						+ "where aadhar_id=?)) t2 left join (select suggestion_id, sum(upVote) as upvotes, sum(downVote) downvotes "
						+ "from suggestion_likes group by suggestion_id) t1 on t2.suggestion_id=t1.suggestion_id "
						+ " order by ? "+ord+" limit ?,?";
				ps = conn.prepareStatement(query);
				ps.setString(param++, user_id);
				ps.setInt(param++, column);
				ps.setInt(param++, offset);
				ps.setInt(param++, noRows);
				dres.setPs(ps);
				logger.info("getSuggestionList Query1: " + ps);
				dres.setRs(ps.executeQuery());
			} catch (Exception e) {
				logger.error("Exception in getSuggestionList: " + e.getMessage());
				dres.closeResources();
			}
		}
		return dres;
	}
	
	
	
	
	
	
	public DatabaseResource getSuggestionListForMLA(int pincode, int column, int order, int offset, int noRows) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		DatabaseResource dres = null;
		if (conn != null) {
			dres = new DatabaseResource();
			dres.setConn(conn);
			int param = 1;
			try {
				String ord = order == 1 ? "ASC" : "DESC";
				String query = "select t2.suggestion_id,if(upvotes is null,0,upvotes) upvotes, " +
						"if(downvotes is null, 0, downvotes) downvotes, subject,DATE_FORMAT(publish_date,' %M %e, %Y ') " +
						"as publish_date,location,description,user_aadhar_id,contact from (select * from suggestion_table " +
						"where aadhar_no in (select aadhar_id from login_table where pincode=?)) t2 " +
						"left join (select suggestion_id, sum(upVote) as upvotes, sum(downVote) downvotes from suggestion_likes " +
						"group by suggestion_id) t1 on t2.suggestion_id=t1.suggestion_id order by ? "+ord+" limit ?,?";
				ps = conn.prepareStatement(query);
				ps.setInt(param++, column);
				ps.setInt(param++, offset);
				ps.setInt(param++, noRows);
				dres.setPs(ps);
				logger.info("getSuggestionList Query1: " + ps);
				dres.setRs(ps.executeQuery());
			} catch (Exception e) {
				logger.error("Exception in getSuggestionList: " + e.getMessage());
				dres.closeResources();
			}
		}
		return dres;
	}
	
	public boolean isVotingAllowed(String userId, String suggestion_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("select * from suggestion_table where user_aadhar_id=? and suggestion_id=?");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(suggestion_id));
				logger.info("is voting allowed "+ps);
				rs = ps.executeQuery();
				if(rs.next())
					return false;
				else
					return true;
			}catch(Exception e){
				logger.error("Exception in is voting allowed: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return false;
	}
	
	public boolean isAlreadyLiked(String userId, String suggestion_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("select * from suggestion_likes where user_aadhar_id=? and suggestion_id=? and upVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(suggestion_id));
				logger.info("Insert likeSuggestion query: "+ps);
				rs = ps.executeQuery();
				if(rs.next())
					return true;
				else
					return false;
			}catch(Exception e){
				logger.error("Exception in likeSuggestion: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}
	
	public boolean isAlreadyUnLiked(String userId, String suggestion_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("select * from suggestion_likes where user_aadhar_id=? and suggestion_id=? and downVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(suggestion_id));
				logger.info("Insert likeSuggestion query: "+ps);
				rs = ps.executeQuery();
				if(rs.next())
					return true;
				else
					return false;
			}catch(Exception e){
				logger.error("Exception in likeSuggestion: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}
	
	public String likeSuggestion(String userId, String suggestion_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				if(isVotingAllowed(userId, suggestion_id)){
					if(!isAlreadyLiked(userId, suggestion_id)){
						ps = conn.prepareStatement("insert into suggestion_likes(user_aadhar_id, suggestion_id, upVote, downVote, publish_date) values (?,?,1,0,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(suggestion_id));
						logger.info("Insert likeSuggestion query: "+ps);
						ps.executeUpdate();
						return "success";
					}else{
						return "already";
					}
				}else{
					return "ownsuggestion";
				}
			}catch(Exception e){
				logger.error("Exception in likeSuggestion: "+e.getMessage());
				if(e.getMessage().contains("Duplicate")){
					return updateLikeSuggestion(userId, suggestion_id);
				}
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
	
	public String updateLikeSuggestion(String userId, String suggestion_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("update suggestion_likes set upVote=1, downVote=0 where suggestion_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(suggestion_id));
				ps.setString(2, userId);
				
				logger.info("InsertSuggestion query: "+ps);
				ps.executeUpdate();
				logger.info("update_count: "+ps.getUpdateCount());
				if(ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			}catch(Exception e){
				logger.error("Exception in InsertSuggestion: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
	
	public String unlikeSuggestion(String userId, String suggestion_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				if(isVotingAllowed(userId, suggestion_id)){
					if(!isAlreadyUnLiked(userId, suggestion_id)){
						ps = conn.prepareStatement("insert into suggestion_likes(user_aadhar_id, suggestion_id, upVote, downVote, publish_date) values (?,?,0,1,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(suggestion_id));
						logger.info("InsertSuggestionunlike query: "+ps);
						ps.executeUpdate();
						return "success";
					}else{
						return "already";
					}
				}else{
					return "ownSuggestion";
				}
			}catch(Exception e){
				logger.error("Exception in unlikeSuggestion: "+e.getMessage());
				if(e.getMessage().contains("Duplicate")){
					return updateUnLikeSuggestion(userId, suggestion_id);
				}
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
	
	public String updateUnLikeSuggestion(String userId, String suggestion_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("update suggestion_likes set upVote=0, downVote=1 where suggestion_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(suggestion_id));
				ps.setString(2, userId);
				
				logger.info("update Suggestion unlike query: "+ps);
				ps.executeUpdate();
				logger.info("update_count: "+ps.getUpdateCount());
				if(ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			}catch(Exception e){
				logger.error("Exception in update unlike Suggestion: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}

}
