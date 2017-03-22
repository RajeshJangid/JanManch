package com.janmanch.dbconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.janmanch.User;


public class AuthDAO {
	final Logger logger = Logger.getLogger("JanManch - AuthDAO");
	
	public String registerUser(JSONObject jsonobj, String password){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try{
			if(jsonobj !=null){
				try{
					JSONObject obj = jsonobj.getJSONObject("hof_Details");
					if(obj!=null){
						String NAME_ENG = obj.get("NAME_ENG").toString();
						String MOBILE_NO = obj.get("MOBILE_NO").toString();
						String BHAMASHAH_ID = obj.get("BHAMASHAH_ID").toString();
						String PIN_CODE = obj.get("PIN_CODE").toString();
						String M_ID = obj.get("M_ID").toString();
						String FAMILYIDNO = obj.get("FAMILYIDNO").toString();
						String AADHAR_ID = obj.get("AADHAR_ID").toString();
						String EMAIL = obj.get("EMAIL").toString();
						ps = conn.prepareStatement("insert into login_table(password, type, bhamashah_id, name_eng, mobile_no, aadhar_id, m_id, email, familyidno, pincode)" +
								" values(md5(?),?,?,?,?,?,?,?,?,?)");
						int param = 1;
						ps.setString(param++, password);
						ps.setString(param++, "U");
						ps.setString(param++, BHAMASHAH_ID);
						ps.setString(param++, NAME_ENG);
						ps.setString(param++, MOBILE_NO);
						ps.setString(param++, AADHAR_ID);
						ps.setString(param++, M_ID);
						ps.setString(param++, EMAIL);
						ps.setString(param++, FAMILYIDNO);
						ps.setString(param++, PIN_CODE);
						logger.info("query user registration : "+ps);
						ps.executeUpdate();
						return "success";
					}
				}catch(Exception e){
					System.out.println("Exception "+e.getMessage());
					if(e.getMessage().contains("Duplicate")){
						return "alreadyregistered";
					}
				}
			}
			
		}catch(Exception e){
			
		}finally{
			DBConnection.closeResources(conn, ps, null, null);
		}
		return "error";
	}
	
	public User loginUser(String ack_id, String pass){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("select * from login_table where bhamashah_id=? and password=md5(?)");
			ps.setString(1, ack_id);
			ps.setString(2, pass);
			logger.info("login query = "+ps);
			rs = ps.executeQuery();
			if(rs!=null && rs.next()){
				User user = new User();
				user.setBahamashahId(rs.getString("bhamashah_id"));
				user.setEmailId(rs.getString("email"));
				user.setFamilyIdNo(rs.getString("familyidno"));
				user.setmId(rs.getString("m_id"));
				user.setName(rs.getString("name_eng"));
				user.setType(rs.getString("type"));
				user.setAadharId(rs.getString("aadhar_id"));
				user.setIntroduction(rs.getString("introduction"));
				try{
					user.setPincode(Integer.parseInt(rs.getString("pincode")));
				}catch(Exception e){
					user.setPincode(0);
				}
				return user;
			}
			
		}catch(Exception e){
			logger.error("Exception "+e.getMessage());
		}finally{
			DBConnection.closeResources(conn, ps, null, rs);
		}
		return null;
	}
	
	public String updateIntroduction(String aadharId, String introducation){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("update login_table set introduction=? where aadhar_id=?");
			ps.setString(1, introducation);
			ps.setString(2, aadharId);
			logger.info("login query = "+ps);
			ps.executeUpdate();
			return "success";
		}catch(Exception e){
			logger.error("Exception "+e.getMessage());
		}finally{
			DBConnection.closeResources(conn, ps, null, null);
		}
		return "failed";
	}
	
	public User getMLACandidateInfo(String aadhar_id){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("select name_eng,mobile_no,bhamashah_id,m_id,type, email,familyidno,pincode,profile_pic,introduction, ml.aadhar_id, previous_experience, constituency_id, qualifications, contact, joining_party from mla_candidate ml join login_table lt on ml.aadhar_id = lt.aadhar_id where ml.aadhar_id=?");
			ps.setString(1, aadhar_id);
			
			logger.info("login query = "+ps);
			rs = ps.executeQuery();
			if(rs!=null && rs.next()){
				User user = new User();
				user.setBahamashahId(rs.getString("bhamashah_id"));
				user.setEmailId(rs.getString("email"));
				user.setFamilyIdNo(rs.getString("familyidno"));
				user.setmId(rs.getString("m_id"));
				user.setName(rs.getString("name_eng"));
				user.setType(rs.getString("type"));
				user.setAadharId(rs.getString("aadhar_id"));
				user.setIntroduction(rs.getString("introduction"));
				user.setMlaEducation(rs.getString("qualifications"));
				user.setMlaContact(rs.getString("contact"));
				
				try{
					user.setPincode(Integer.parseInt(rs.getString("pincode")));
				}catch(Exception e){
					user.setPincode(0);
				}
				return user;
			}
			
		}catch(Exception e){
			logger.error("Exception "+e.getMessage());
		}finally{
			DBConnection.closeResources(conn, ps, null, rs);
		}
		return null;
	}
}
