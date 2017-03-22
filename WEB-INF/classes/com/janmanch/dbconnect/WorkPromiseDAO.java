package com.janmanch.dbconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class WorkPromiseDAO {
	final Logger logger = Logger.getLogger("JanManch - WorkPromiseDAO");

	public String insertWorkPromise(String aadhar_id,String title,String summary,String requirements,String location,
			String contact,String budget,String duration_days,String duration_months,String duration_years) {
		
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("insert into work_proposals(aadhar_id,title,summary,requirements,location,contact,budget,duration_days, duration_months, duration_years, proposal_date) values(?,?,?,?,?,?,?,?,?,?, now())");
				ps.setString(1, aadhar_id);
				ps.setString(2, title);
				ps.setString(3, summary);
				ps.setString(4, requirements);
				ps.setString(5, location);
				ps.setString(6, contact);
				ps.setString(7, budget);
				ps.setString(8, duration_days);
				ps.setString(9, duration_months);
				ps.setString(10, duration_years);

				logger.info("InsertComplaint query: " + ps);
				ps.executeUpdate();
				return "success";
			} catch (Exception e) {
				logger.error("Exception in InsertComplaint: " + e.getMessage());
			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		
		return "failed";
	}

	public DatabaseResource getWorkPromiseListByMLA(int column, int order, int offset, int noRows) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		DatabaseResource dres = null;
		if (conn != null) {
			dres = new DatabaseResource();
			dres.setConn(conn);
			int param = 1;
			try {
				String ord = order == 1 ? "ASC" : "DESC";
				String query = "select aadhar_id, if(upvotes is null,0,upvotes) upvotes, " + "if(downvotes is null, 0, downvotes) downvotes, title, summary, location, proposal_date, is_approved, id from work_proposals "+
"left join (select proposal_id, sum(upVote) as upvotes, sum(downVote) downvotes from work_proposal_likes "+
"group by proposal_id) as t1 on work_proposals.id = t1.proposal_id order by ? "+ord+" limit ?,?";
				//String query = "select * from work_proposals where aadhar_id in (select aadhar_id from login_table where type='M') order by ? "+ord+" limit ?,?";
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
	
	public boolean isVotingAllowed(String userId, String proposal_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("select * from work_proposal where aadhar_id=? and id=? ");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(proposal_id));
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

	public boolean isAlreadyLiked(String userId, String proposal_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("select * from work_proposal_likes where user_aadhar_id=? and proposal_id=? and upVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(proposal_id));
				logger.info("Insert likeWorkProposal query: " + ps);
				rs = ps.executeQuery();
				if (rs.next())
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Exception in likeWorkProposal: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}

	public boolean isAlreadyUnLiked(String userId, String proposal_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("select * from work_proposal_likes where user_aadhar_id=? and proposal_id=? and downVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(proposal_id));
				logger.info("Insert likeWorkProposal query: " + ps);
				rs = ps.executeQuery();
				if (rs.next())
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Exception in likeWorkProposal: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}

	public String likeWorkProposal(String userId, String proposal_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				if (isVotingAllowed(userId, proposal_id)) {
					if (!isAlreadyLiked(userId, proposal_id)) {
						ps = conn.prepareStatement("insert into work_proposal_likes(user_aadhar_id, proposal_id, upVote, downVote, publish_date) values (?,?,1,0,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(proposal_id));
						logger.info("Insert likeWorkProposal query: " + ps);
						ps.executeUpdate();
						return "success";
					} else {
						return "already";
					}
				} else {
					return "candidatehimself";
				}
			} catch (Exception e) {
				logger.error("Exception in likeWorkProposal: " + e.getMessage());
				if (e.getMessage().contains("Duplicate")) {
					return updateLikeWorkProposal(userId, proposal_id);
				}
			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}

	public String updateLikeWorkProposal(String userId, String proposal_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("update work_proposal_likes set upVote=1, downVote=0 where proposal_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(proposal_id));
				ps.setString(2, userId);

				logger.info("InsertWorkProposal query: " + ps);
				ps.executeUpdate();
				logger.info("update_count: " + ps.getUpdateCount());
				if (ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			} catch (Exception e) {
				logger.error("Exception in InsertWorkProposal: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}

	public String unlikeWorkProposal(String userId, String proposal_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				if (isVotingAllowed(userId, proposal_id)) {
					if (!isAlreadyUnLiked(userId, proposal_id)) {
						ps = conn.prepareStatement("insert into work_proposal_likes(user_aadhar_id, proposal_id, upVote, downVote, publish_date) values (?,?,0,1,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(proposal_id));
						logger.info("InsertWorkProposalunlike query: " + ps);
						ps.executeUpdate();
						return "success";
					} else {
						return "already";
					}
				} else {
					return "candidatehimself";
				}
			} catch (Exception e) {
				logger.error("Exception in unlikeWorkProposal: " + e.getMessage());
				if (e.getMessage().contains("Duplicate")) {
					return updateUnLikeWorkProposal(userId, proposal_id);
				}
			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}

	public String updateUnLikeWorkProposal(String userId, String proposal_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("update work_proposal_likes set upVote=0, downVote=1 where proposal_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(proposal_id));
				ps.setString(2, userId);

				logger.info("update WorkProposal unlike query: " + ps);
				ps.executeUpdate();
				logger.info("update_count: " + ps.getUpdateCount());
				if (ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			} catch (Exception e) {
				logger.error("Exception in update unlike WorkProposal: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
	
	
}
