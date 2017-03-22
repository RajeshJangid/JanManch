package com.janmanch.dbconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class QueriesDAO {
	final Logger logger = Logger.getLogger("JanManch - QueriesDAO");

	public int SubmitReview(String aadhar_id, String subject, String location, String contact, String description) {
		int result = 0;
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("insert into query_table(user_aadhar_id,subject,location,contact,discription,publish_date) values (?,?,?,?,?,now())");
				ps.setString(1, aadhar_id);
				ps.setString(2, subject);
				ps.setString(3, location);
				ps.setString(4, contact);
				ps.setString(5, description);

				logger.info("ReviewDAO query: " + ps);
				result = ps.executeUpdate();
			} catch (Exception e) {
				logger.error("Exception in ReviewDAO: " + e.getMessage());
			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return result;
	}

	public DatabaseResource getQueryList(int column, int order, int offset, int noRows) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		DatabaseResource dres = null;
		if (conn != null) {
			dres = new DatabaseResource();
			dres.setConn(conn);
			int param = 1;
			try {
				String ord = order == 1 ? "ASC" : "DESC";
				String query = "select query_table.query_id,if(upvotes is null,0,upvotes) upvotes, " +
							"if(downvotes is null, 0, downvotes) downvotes, subject,DATE_FORMAT(publish_date,' %M %e, %Y ') " +
							"as publish_date,location,discription,user_aadhar_id,contact from query_table " +
							"left join (select query_id, sum(upVote) as upvotes, sum(downVote) downvotes from query_likes " +
							"group by query_id) t1 on query_table.query_id=t1.query_id order by ? "+ord+" limit ?,?";
				ps = conn.prepareStatement(query);
				ps.setInt(param++, column);
				ps.setInt(param++, offset);
				ps.setInt(param++, noRows);
				dres.setPs(ps);
				logger.info("getQueryList Query: " + ps);
				dres.setRs(ps.executeQuery());
			} catch (Exception e) {
				logger.error("Exception in getQueryList: " + e.getMessage());
				dres.closeResources();
			}
		}
		return dres;
	}
	
	public DatabaseResource getQueryListForMLA(int pincode, int column, int order, int offset, int noRows) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		DatabaseResource dres = null;
		if (conn != null) {
			dres = new DatabaseResource();
			dres.setConn(conn);
			int param = 1;
			try {
				String ord = order == 1 ? "ASC" : "DESC";
				String query = "select t2.query_id,if(upvotes is null,0,upvotes) upvotes, " +
							"if(downvotes is null, 0, downvotes) downvotes, subject,DATE_FORMAT(publish_date,' %M %e, %Y ') " +
							"as publish_date,location,discription,user_aadhar_id,contact from (select * from query_table " +
							"where user_aadhar_id in (select aadhar_id from login_table where pincode=?)) t2 " +
							"left join (select query_id, sum(upVote) as upvotes, sum(downVote) downvotes from query_likes " +
							"group by query_id) t1 on t2.query_id=t1.query_id order by ? "+ord+" limit ?,?";
				ps = conn.prepareStatement(query);
				ps.setInt(param++, pincode);
				ps.setInt(param++, column);
				ps.setInt(param++, offset);
				ps.setInt(param++, noRows);
				dres.setPs(ps);
				logger.info("getQueryList Query: " + ps);
				dres.setRs(ps.executeQuery());
			} catch (Exception e) {
				logger.error("Exception in getQueryList: " + e.getMessage());
				dres.closeResources();
			}
		}
		return dres;
	}
	
	
	public DatabaseResource getQueryListForuser(String user_id, int column, int order, int offset, int noRows) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		DatabaseResource dres = null;
		if (conn != null) {
			dres = new DatabaseResource();
			dres.setConn(conn);
			int param = 1;
			try {
				String ord = order == 1 ? "ASC" : "DESC";
				String query = "select t2.query_id,if(upvotes is null,0,upvotes) upvotes, " +
							"if(downvotes is null, 0, downvotes) downvotes, subject,DATE_FORMAT(publish_date,' %M %e, %Y ') " +
							"as publish_date,location,discription,user_aadhar_id,contact from (select * from query_table " +
							"where user_aadhar_id in (select aadhar_id from login_table where user_aadhar_id=?)) t2 " +
							"left join (select query_id, sum(upVote) as upvotes, sum(downVote) downvotes from query_likes " +
							"group by query_id) t1 on t2.query_id=t1.query_id order by ? "+ord+" limit ?,?";
				

				ps = conn.prepareStatement(query);
				ps.setString(param++, user_id);
				ps.setInt(param++, column);
				ps.setInt(param++, offset);
				ps.setInt(param++, noRows);
				dres.setPs(ps);
				logger.info("getQueryListForuser Query: " + ps);
				dres.setRs(ps.executeQuery());
			} catch (Exception e) {
				logger.error("Exception in getQueryListForuser: " + e.getMessage());
				dres.closeResources();
			}
		}
		return dres;
	}
	
	public boolean isVotingAllowed(String userId, String query_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("select * from query_table where aadhar_no=? and query_id=?");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(query_id));
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
	
	public boolean isAlreadyLiked(String userId, String query_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("select * from query_likes where user_aadhar_id=? and query_id=? and upVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(query_id));
				logger.info("Insert likeQuery query: "+ps);
				rs = ps.executeQuery();
				if(rs.next())
					return true;
				else
					return false;
			}catch(Exception e){
				logger.error("Exception in likeQuery: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}
	
	public boolean isAlreadyUnLiked(String userId, String query_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("select * from query_likes where user_aadhar_id=? and query_id=? and downVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(query_id));
				logger.info("Insert likeQuery query: "+ps);
				rs = ps.executeQuery();
				if(rs.next())
					return true;
				else
					return false;
			}catch(Exception e){
				logger.error("Exception in likeQuery: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}
	
	public String likeQuery(String userId, String query_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				if(isVotingAllowed(userId, query_id)){
					if(!isAlreadyLiked(userId, query_id)){
						ps = conn.prepareStatement("insert into query_likes(user_aadhar_id, query_id, upVote, downVote, publish_date) values (?,?,1,0,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(query_id));
						logger.info("Insert likeQuery query: "+ps);
						ps.executeUpdate();
						return "success";
					}else{
						return "already";
					}
				}else{
					return "ownQuery";
				}
			}catch(Exception e){
				logger.error("Exception in likeQuery: "+e.getMessage());
				if(e.getMessage().contains("Duplicate")){
					return updateLikeQuery(userId, query_id);
				}
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
	
	public String updateLikeQuery(String userId, String query_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("update query_likes set upVote=1, downVote=0 where query_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(query_id));
				ps.setString(2, userId);
				
				logger.info("InsertQuery query: "+ps);
				ps.executeUpdate();
				logger.info("update_count: "+ps.getUpdateCount());
				if(ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			}catch(Exception e){
				logger.error("Exception in InsertQuery: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
	
	public String unlikeQuery(String userId, String query_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				if(isVotingAllowed(userId, query_id)){
					if(!isAlreadyUnLiked(userId, query_id)){
						ps = conn.prepareStatement("insert into query_likes(user_aadhar_id, query_id, upVote, downVote, publish_date) values (?,?,0,1,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(query_id));
						logger.info("InsertQueryunlike query: "+ps);
						ps.executeUpdate();
						return "success";
					}else{
						return "already";
					}
				}else{
					return "ownQuery";
				}
			}catch(Exception e){
				logger.error("Exception in unlikeQuery: "+e.getMessage());
				if(e.getMessage().contains("Duplicate")){
					return updateUnLikeQuery(userId, query_id);
				}
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
	
	public String updateUnLikeQuery(String userId, String query_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("update query_likes set upVote=0, downVote=1 where query_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(query_id));
				ps.setString(2, userId);
				
				logger.info("update Query unlike query: "+ps);
				ps.executeUpdate();
				logger.info("update_count: "+ps.getUpdateCount());
				if(ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			}catch(Exception e){
				logger.error("Exception in update unlike Query: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
	
}
