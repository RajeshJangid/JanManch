package com.janmanch.dbconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class ConstituencyDAO {
private static Logger logger = Logger.getLogger(ConstituencyDAO.class.getName());
	
	public int addCandidate(String aadhar_id){
		int result = 0;
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		if(conn!=null){
			try{
				ps = conn.prepareStatement("update login_table set type='C' where aadhar_id=?");
				ps.setString(1,aadhar_id );
				
				logger.info("InsertComplaint query: "+ps);
				result = ps.executeUpdate();
			}catch(Exception e){
				logger.error("Exception in InsertComplaint: "+e.getMessage());
			}finally{
				DBConnection.closeResources(conn, ps, null, null);
			}
		}
		return result;
	}
	
	public DatabaseResource getConstituncyList(){
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		DatabaseResource res = null;
		if(conn!=null){
			res = new DatabaseResource();
			res.setConn(conn);
			try{
				ps = conn.prepareStatement("select * from constituency_directory");
				
				logger.info("InsertComplaint query: "+ps);
				res.setPs(ps);
				rs = ps.executeQuery();
				res.setRs(rs);
				return res;
			}catch(Exception e){
				logger.error("Exception in InsertComplaint: "+e.getMessage());
				res.closeResources();
			}
		}
		return null;
	}
}
