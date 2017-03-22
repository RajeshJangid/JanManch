package com.janmanch.dbconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class CandidateDAO {
	private static Logger logger = Logger.getLogger(CandidateDAO.class.getName());
	
	public int addCandidate(String aadhar_id, HttpServletRequest request){
		int result = 0;
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		String experience = request.getParameter("experience");
		String constitution = request.getParameter("constitution");
		String party = request.getParameter("party");
		String qualification = request.getParameter("qualification");
		String contact = request.getParameter("contact");
		if(conn!=null){
			try{
				ps = conn.prepareStatement("update login_table set type='C' where aadhar_id=?");
				ps.setString(1,aadhar_id );
				
				logger.info("InsertCandidate query: "+ps);
				result = ps.executeUpdate();
				
				DBConnection.closeResources(null, ps, null, null);
				ps = conn.prepareStatement("insert into mla_candidate(aadhar_id, previous_experience, constituency_id, qualifications, contact, joining_party) values(?,?,?,?,?,?)");
				int param = 1;
				ps.setString(param++, aadhar_id);
				ps.setString(param++, experience);
				ps.setString(param++, constitution);
				ps.setString(param++, party);
				ps.setString(param++, qualification);
				ps.setString(param++, contact);
				
				ps.executeUpdate();
				
			}catch(Exception e){
				logger.error("Exception in InsertCandidate: "+e.getMessage());
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return result;
	}
	
	public DatabaseResource getTopCandidateList(){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		DatabaseResource res = null;
		if(conn!=null){
			res = new DatabaseResource();
			res.setConn(conn);
			try{
				String query = "select * from (select name_eng,mobile_no,bhamashah_id,m_id,email,familyidno,pincode,profile_pic,"+
" introduction, ml.id, ml.aadhar_id, previous_experience, constituency_id, qualifications, contact, "+
" joining_party from mla_candidate ml join login_table lt on ml.aadhar_id = lt.aadhar_id) as t1 left join"+
" (select sum(upVote) as upvotes, sum(downVote) as downvotes, candidate_id from candidate_votes group by candidate_id) t2 on t1.id=t2.candidate_id";
				ps = conn.prepareStatement(query);
				
				logger.info("InsertCandidate query: "+ps);
				res.setPs(ps);
				rs = ps.executeQuery();
				res.setRs(rs);
				return res;
			}catch(Exception e){
				logger.error("Exception in InsertCandidate: "+e.getMessage());
				res.closeResources();
			}
		}
		return null;
	}
	
	public boolean isVotingAllowed(String userId, String Candidate_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("select * from mla_candidate where aadhar_id=? and id=? ");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(Candidate_id));
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

	public boolean isAlreadyLiked(String userId, String Candidate_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("select * from candidate_votes where user_aadhar_id=? and candidate_id=? and upVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(Candidate_id));
				logger.info("Insert likeCandidate query: " + ps);
				rs = ps.executeQuery();
				if (rs.next())
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Exception in likeCandidate: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}

	public boolean isAlreadyUnLiked(String userId, String Candidate_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("select * from candidate_votes where user_aadhar_id=? and candidate_id=? and downVote=1");
				ps.setString(1, userId);
				ps.setInt(2, Integer.parseInt(Candidate_id));
				logger.info("Insert likeCandidate query: " + ps);
				rs = ps.executeQuery();
				if (rs.next())
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Exception in likeCandidate: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return true;
	}

	public String likeCandidate(String userId, String Candidate_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				if (isVotingAllowed(userId, Candidate_id)) {
					if (!isAlreadyLiked(userId, Candidate_id)) {
						ps = conn.prepareStatement("insert into candidate_votes(user_aadhar_id, candidate_id, upVote, downVote, publish_date) values (?,?,1,0,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(Candidate_id));
						logger.info("Insert likeCandidate query: " + ps);
						ps.executeUpdate();
						return "success";
					} else {
						return "already";
					}
				} else {
					return "candidatehimself";
				}
			} catch (Exception e) {
				logger.error("Exception in likeCandidate: " + e.getMessage());
				if (e.getMessage().contains("Duplicate")) {
					return updateLikeCandidate(userId, Candidate_id);
				}
			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}

	public String updateLikeCandidate(String userId, String Candidate_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("update candidate_votes set upVote=1, downVote=0 where candidate_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(Candidate_id));
				ps.setString(2, userId);

				logger.info("InsertCandidate query: " + ps);
				ps.executeUpdate();
				logger.info("update_count: " + ps.getUpdateCount());
				if (ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			} catch (Exception e) {
				logger.error("Exception in InsertCandidate: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}

	public String unlikeCandidate(String userId, String Candidate_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				if (isVotingAllowed(userId, Candidate_id)) {
					if (!isAlreadyUnLiked(userId, Candidate_id)) {
						ps = conn.prepareStatement("insert into candidate_votes(user_aadhar_id, candidate_id, upVote, downVote, publish_date) values (?,?,0,1,now())");
						ps.setString(1, userId);
						ps.setInt(2, Integer.parseInt(Candidate_id));
						logger.info("InsertCandidateunlike query: " + ps);
						ps.executeUpdate();
						return "success";
					} else {
						return "already";
					}
				} else {
					return "candidatehimself";
				}
			} catch (Exception e) {
				logger.error("Exception in unlikeCandidate: " + e.getMessage());
				if (e.getMessage().contains("Duplicate")) {
					return updateUnLikeCandidate(userId, Candidate_id);
				}
			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}

	public String updateUnLikeCandidate(String userId, String Candidate_id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement("update candidate_votes set upVote=0, downVote=1 where candidate_id=? and user_aadhar_id=?");
				ps.setInt(1, Integer.parseInt(Candidate_id));
				ps.setString(2, userId);

				logger.info("update Candidate unlike query: " + ps);
				ps.executeUpdate();
				logger.info("update_count: " + ps.getUpdateCount());
				if (ps.getUpdateCount() == 1)
					return "changed";
				else
					return "already";
			} catch (Exception e) {
				logger.error("Exception in update unlike Candidate: " + e.getMessage());

			} finally {
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return "failed";
	}
}
