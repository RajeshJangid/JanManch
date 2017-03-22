package com.janmanch.dbconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseResource {
	private Connection conn  = null;
	private PreparedStatement ps = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public PreparedStatement getPs() {
		return ps;
	}
	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}
	public Statement getStmt() {
		return stmt;
	}
	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	public ResultSet getRs() {
		return rs;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	
	public void closeResources(){
		try{
			if(rs!=null){
				rs.close();
			}
		}catch(Exception e){
			
		}finally{
			rs = null;
		}
		
		try{
			if(stmt!=null)
				stmt.close();
		}catch(Exception e){
			
		}finally{
			stmt = null;
		}
		
		try{
			if(ps!=null){
				ps.close();
			}
		}catch(Exception e){
			
		}finally{
			ps = null;
		}
		
		try{
			if(conn!=null)
				DBConnection.closeConnection(conn);
		}catch(Exception e){
			
		}finally{
			conn = null;
		}
	}
	
	public static Connection getConnection(){
		return DBConnection.getConnection();
	}
}
