package com.janmanch.dbconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class ComplaintDAO {

	final Logger logger = Logger.getLogger("JanManch - ComplaintDAO");

	public int InsertComplaint(String aadhar_id, String subject, String location, String department, String subDivision, String description) {
		int result = 0;
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("insert into complain_table(aadhar_no,subject,department,complain_type,location,description,publish_date) values (?,?,?,?,?,?,now())");
				ps.setString(1, aadhar_id);
				ps.setString(2, subject);
				ps.setString(3, department);
				ps.setString(4, subDivision);
				ps.setString(5, location);
				ps.setString(6, description);

				logger.info("InsertComplaint query: " + ps);
				result = ps.executeUpdate();
			} catch (Exception e) {
				logger.error("Exception in InsertComplaint: " + e.getMessage());
			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return result;
	}

	public DatabaseResource getComplainList(int column, int order, int offset, int noRows) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		DatabaseResource dres = null;
		if (conn != null) {
			dres = new DatabaseResource();
			dres.setConn(conn);
			int param = 1;
			try {
				String ord = order == 1 ? "ASC" : "DESC";

				ps = conn.prepareStatement("select complain_table.complain_id,if(upvotes is null,0,upvotes) upvotes, " + "if(downvotes is null, 0, downvotes) downvotes, subject,DATE_FORMAT(publish_date,' %M %e, %Y ') " + "as publish_date,location,description,department,aadhar_no,complain_type from complain_table " + "left join (select complain_id, sum(upVote) as upvotes, sum(downVote) downvotes from complain_likes " + "group by complain_id) t1 on complain_table.complain_id=t1.complain_id order by ? " + ord + " limit ?,?");
				ps.setInt(param++, column);
				ps.setInt(param++, offset);
				ps.setInt(param++, noRows);
				dres.setPs(ps);
				logger.info("getComplainList Query: " + ps);
				dres.setRs(ps.executeQuery());
			} catch (Exception e) {
				logger.error("Exception in getComplainList: " + e.getMessage());
				dres.closeResources();
			}
		}
		return dres;
	}
	
	public DatabaseResource getComplainListForMLA(int pincode, int column, int order, int offset, int noRows) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		DatabaseResource dres = null;
		if (conn != null) {
			dres = new DatabaseResource();
			dres.setConn(conn);
			int param = 1;
			try {
				String ord = order == 1 ? "ASC" : "DESC";
				String query = "select t2.complain_id,if(upvotes is null,0,upvotes) upvotes, if(downvotes is null, 0, downvotes) " +
						"downvotes, subject,DATE_FORMAT(publish_date,' %M %e, %Y ') as publish_date,location,description,department," +
						"aadhar_no,complain_type from (select * from complain_table where aadhar_no in (select aadhar_id from " +
						"login_table where pincode=?)) t2 left join (select complain_id, sum(upVote) as upvotes, " +
						"sum(downVote) downvotes from complain_likes group by complain_id) t1 on t2.complain_id=t1.complain_id " +
						"order by ? "+ord+" limit ?,?";
				ps = conn.prepareStatement(query);
				ps.setInt(param++, pincode);
				ps.setInt(param++, column);
				ps.setInt(param++, offset);
				ps.setInt(param++, noRows);
				dres.setPs(ps);
				logger.info("getComplainList Query: " + ps);
				dres.setRs(ps.executeQuery());
			} catch (Exception e) {
				logger.error("Exception in getComplainList: " + e.getMessage());
				dres.closeResources();
			}
		}
		return dres;
	}
	
	
	
	
	public DatabaseResource getComplainListuser(String user_id, int column, int order, int offset, int noRows) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		DatabaseResource dres = null;
		if (conn != null) {
			dres = new DatabaseResource();
			dres.setConn(conn);
			int param = 1;
			try {
				String ord = order == 1 ? "ASC" : "DESC";
				String query = "select t2.complain_id,if(upvotes is null,0,upvotes) upvotes, if(downvotes is null, 0, downvotes) " +
						"downvotes, subject,DATE_FORMAT(publish_date,' %M %e, %Y ') as publish_date,location,description,department," +
						"aadhar_no,complain_type from (select * from complain_table where aadhar_no in (select aadhar_id from " +
						"login_table where aadhar_id=?)) t2 left join (select complain_id, sum(upVote) as upvotes, " +
						"sum(downVote) downvotes from complain_likes group by complain_id) t1 on t2.complain_id=t1.complain_id " +
						"order by ? "+ord+" limit ?,?";
				ps = conn.prepareStatement(query);
				ps.setString(param++, user_id);
				ps.setInt(param++, column);
				ps.setInt(param++, offset);
				ps.setInt(param++, noRows);
				dres.setPs(ps);
				logger.info("getComplainList Query: " + ps);
				dres.setRs(ps.executeQuery());
			} catch (Exception e) {
				logger.error("Exception in getComplainList: " + e.getMessage());
				dres.closeResources();
			}
		}
		return dres;
	}

	public boolean isVotingAllowed(String userId, String complaint_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("select * from complain_table where aadhar_no=? and complain_id=?");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(complaint_id));
				logger.info("is voting allowed " + ps);
				rs = ps.executeQuery();
				if (rs.next())
					return false;
				else
					return true;
			} catch (Exception e) {
				logger.error("Exception in is voting allowed: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return false;
	}

	public boolean isAlreadyLiked(String userId, String complaint_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("select * from complain_likes where user_aadhar_id=? and complain_id=? and upVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(complaint_id));
				logger.info("Insert likeComplaint query: " + ps);
				rs = ps.executeQuery();
				if (rs.next())
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Exception in likeComplaint: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}

	public boolean isAlreadyUnLiked(String userId, String complaint_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("select * from complain_likes where user_aadhar_id=? and complain_id=? and downVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(complaint_id));
				logger.info("Insert likeComplaint query: " + ps);
				rs = ps.executeQuery();
				if (rs.next())
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Exception in likeComplaint: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}

	public String likeComplaint(String userId, String complaint_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				if (isVotingAllowed(userId, complaint_id)) {
					if (!isAlreadyLiked(userId, complaint_id)) {
						ps = conn.prepareStatement("insert into complain_likes(user_aadhar_id, complain_id, upVote, downVote, publish_date) values (?,?,1,0,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(complaint_id));
						logger.info("Insert likeComplaint query: " + ps);
						ps.executeUpdate();
						return "success";
					} else {
						return "already";
					}
				} else {
					return "owncomplaint";
				}
			} catch (Exception e) {
				logger.error("Exception in likeComplaint: " + e.getMessage());
				if (e.getMessage().contains("Duplicate")) {
					return updateLikeComplaint(userId, complaint_id);
				}
			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}

	public String updateLikeComplaint(String userId, String complaint_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("update complain_likes set upVote=1, downVote=0 where complain_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(complaint_id));
				ps.setString(2, userId);

				logger.info("InsertComplaint query: " + ps);
				ps.executeUpdate();
				logger.info("update_count: " + ps.getUpdateCount());
				if (ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			} catch (Exception e) {
				logger.error("Exception in InsertComplaint: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}

	public String unlikeComplaint(String userId, String complaint_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				if (isVotingAllowed(userId, complaint_id)) {
					if (!isAlreadyUnLiked(userId, complaint_id)) {
						ps = conn.prepareStatement("insert into complain_likes(user_aadhar_id, complain_id, upVote, downVote, publish_date) values (?,?,0,1,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(complaint_id));
						logger.info("InsertComplaintunlike query: " + ps);
						ps.executeUpdate();
						return "success";
					} else {
						return "already";
					}
				} else {
					return "owncomplaint";
				}
			} catch (Exception e) {
				logger.error("Exception in unlikeComplaint: " + e.getMessage());
				if (e.getMessage().contains("Duplicate")) {
					return updateUnLikeComplaint(userId, complaint_id);
				}
			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}

	public String updateUnLikeComplaint(String userId, String complaint_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("update complain_likes set upVote=0, downVote=1 where complain_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(complaint_id));
				ps.setString(2, userId);

				logger.info("update complaint unlike query: " + ps);
				ps.executeUpdate();
				logger.info("update_count: " + ps.getUpdateCount());
				if (ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			} catch (Exception e) {
				logger.error("Exception in update unlike complaint: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
}
