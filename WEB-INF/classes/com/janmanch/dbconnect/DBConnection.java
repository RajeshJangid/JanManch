package com.janmanch.dbconnect;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.servlet.http.*; 

import org.apache.log4j.Logger;

class Connection1 {

    private static final Logger logger = Logger.getLogger("JanManch - DBConnection");
    Connection con1 = null;
    long time = -1;

    Connection1(Connection con) {
        con1 = con;
        time = System.currentTimeMillis();
    }

    synchronized Connection getConnection() {
        if (System.currentTimeMillis() - time > 300000) {
            try {
                if (con1 != null) {
                    con1.close();
                }
            } catch (Exception e) {
                logger.error("error while closing connection....." + e);
            }
            con1 = DBConnection.createConnection();
        }
        time = System.currentTimeMillis();
        return con1;
    }

}

public class DBConnection {

    private static final Logger logger = Logger.getLogger("JanManch - DBconnection");
    private static Connection1 con[] = null;
    private static String databaseip = null;
    private static String databasename = null;
    private static String databaseuser = null;
    private static String databasepassword = null;
    
    private static int MAX_POOL_SIZE = 0;
    private static int current_count = 0;
    private static String conn_type="direct";

    public static DataSource dataSource = null;
    
    static {
        Properties prop = new Properties();
        //read properties file here
        try {
            InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");
            if (inputStream != null) {
                prop.load(inputStream);
                databaseuser = prop.getProperty("db.username");
                databasepassword = prop.getProperty("db.password");
                databaseip = prop.getProperty("db.databaseip");
                databasename = prop.getProperty("db.databasename");
                conn_type = prop.getProperty("db.conn_type");
                
                MAX_POOL_SIZE = Integer.parseInt(prop.getProperty("db.maxpoolsize"));
                inputStream.close();
            } else {
                logger.info("Unable to load properties");
            }
        } catch (Exception e) {
            logger.error("database parameters not properly set.." + e);
            /*e.printStackTrace();*/
        }
        //ip,databse,user,password,no of connection
        con = new Connection1[MAX_POOL_SIZE];
        int i = 0;
        while (i < MAX_POOL_SIZE) {
            con[i] = new Connection1(DBConnection.createConnection());
            i++;
        }
        
    }

    static Connection createConnection() {
        Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://" + databaseip + "/" + databasename + "?user=" + databaseuser + "&password=" + databasepassword);
        } catch (Exception e) {
            logger.error("error Unable to create connection..... " + e);
            /*e.printStackTrace();	*/
        }
        return connect;
    }

    public static synchronized Connection getConnection() {
    	Connection conn = null;
    	try{
	    	if(conn_type.equals("JNDI")){
	    		conn = dataSource.getConnection();
	    	}else{
	    		
	    		if (current_count >= MAX_POOL_SIZE) {
		            current_count = 0;
		        }
		        conn = con[current_count].getConnection();
		        current_count++;
	    	}
    	}catch(Exception e){
    		logger.error("exception in get connection.. "+e);
    	}
        return conn;
    }

    static Connection getJNDIconnection(){
    	Connection connection = null;
        try {
          connection = dataSource.getConnection();
          
        } catch (Exception e) {
          e.printStackTrace();
        } 
        return connection;
    }
    
    static void closeConnection(Connection conn){
    	if(conn_type.equals("JNDI")){
			try {
				conn.close();
			} catch (SQLException e) {
				
				logger.info("Exception occured while closing connection..."+e.getMessage());
			}
    	}
    }
    
    public static void closeResources(Connection conne, PreparedStatement ps, Statement stmt, ResultSet rs) {
		try {
			if(rs!=null)
				rs.close();
		} catch (Exception e) {

		}
		try {
			if(stmt!=null)
				stmt.close();
		} catch (Exception e) {

		}
		try {
			if(ps!=null)
				ps.close();
		} catch (Exception e) {

		}
		
		closeConnection(conne);
		
	}
}
