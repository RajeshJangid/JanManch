package com.janmanch.dbconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class ReviewDAO {

	final Logger logger = Logger.getLogger("JanManch - ReviewDAO");

	public int SubmitReview(String aadhar_id, String subject, String location, String contact, String description) {
		int result = 0;
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("insert into review_table(user_aadhar_id,subject,location,contact,discription,publish_date) values (?,?,?,?,?,now())");
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
				String query = "select review_table.review_id,if(upvotes is null,0,upvotes) upvotes, " +
						"if(downvotes is null, 0, downvotes) downvotes, subject,DATE_FORMAT(publish_date,' %M %e, %Y ') " +
						"as publish_date,location,discription,user_aadhar_id,contact from review_table " +
						"join (select review_id, sum(upVote) as upvotes, sum(downVote) downvotes from review_likes " +
						"group by review_id) t1 on review_table.review_id=t1.review_id order by ? "+ord+" limit ?,?";
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

	public boolean isVotingAllowed(String userId, String review_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("select * from review_table where user_aadhar_id=? and review_id=?");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(review_id));
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
	
	public boolean isAlreadyLiked(String userId, String review_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("select * from review_likes where user_aadhar_id=? and review_id=? and upVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(review_id));
				logger.info("Insert likeReview query: "+ps);
				rs = ps.executeQuery();
				if(rs.next())
					return true;
				else
					return false;
			}catch(Exception e){
				logger.error("Exception in likeReview: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}
	
	public boolean isAlreadyUnLiked(String userId, String review_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("select * from review_likes where user_aadhar_id=? and review_id=? and downVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(review_id));
				logger.info("Insert likeReview query: "+ps);
				rs = ps.executeQuery();
				if(rs.next())
					return true;
				else
					return false;
			}catch(Exception e){
				logger.error("Exception in likeReview: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}
	
	public String likeReview(String userId, String review_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				if(isVotingAllowed(userId, review_id)){
					if(!isAlreadyLiked(userId, review_id)){
						ps = conn.prepareStatement("insert into review_likes(user_aadhar_id, review_id, upVote, downVote, publish_date) values (?,?,1,0,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(review_id));
						logger.info("Insert likeReview query: "+ps);
						ps.executeUpdate();
						return "success";
					}else{
						return "already";
					}
				}else{
					return "ownReview";
				}
			}catch(Exception e){
				logger.error("Exception in likeReview: "+e.getMessage());
				if(e.getMessage().contains("Duplicate")){
					return updateLikeReview(userId, review_id);
				}
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
	
	public String updateLikeReview(String userId, String review_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("update review_likes set upVote=1, downVote=0 where review_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(review_id));
				ps.setString(2, userId);
				
				logger.info("InsertReview query: "+ps);
				ps.executeUpdate();
				logger.info("update_count: "+ps.getUpdateCount());
				if(ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			}catch(Exception e){
				logger.error("Exception in InsertReview: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
	
	public String unlikeReview(String userId, String review_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				if(isVotingAllowed(userId, review_id)){
					if(!isAlreadyUnLiked(userId, review_id)){
						ps = conn.prepareStatement("insert into review_likes(user_aadhar_id, review_id, upVote, downVote, publish_date) values (?,?,0,1,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(review_id));
						logger.info("InsertReviewunlike query: "+ps);
						ps.executeUpdate();
						return "success";
					}else{
						return "already";
					}
				}else{
					return "ownReview";
				}
			}catch(Exception e){
				logger.error("Exception in unlikeReview: "+e.getMessage());
				if(e.getMessage().contains("Duplicate")){
					return updateUnLikeReview(userId, review_id);
				}
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
	
	public String updateUnLikeReview(String userId, String review_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("update review_likes set upVote=0, downVote=1 where review_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(review_id));
				ps.setString(2, userId);
				
				logger.info("update Review unlike query: "+ps);
				ps.executeUpdate();
				logger.info("update_count: "+ps.getUpdateCount());
				if(ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			}catch(Exception e){
				logger.error("Exception in update unlike Review: "+e.getMessage());
				
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
}
