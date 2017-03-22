package com.janmanch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Attributes {
	private static String host = "";
	private static String docBase = "";
	private static String facebookAppId = "";
	private static String facebookAppSecret = "";
	private static String googleClientId = "";
	private static String googleClientSecret = "";
	private static String uploadPath = "";
	private static String uploadPath1 = "";
	private static String rootPath="";
	private static String attachmentpath="";
	private static String importFile="";
	private static String exportFile="";
	private static String Xmluploadpath="";
	
	final static Logger logger = Logger.getLogger("Comments - Attributes");
	static{
		Properties prop = new Properties();
		InputStream inputStream = Attributes.class.getClassLoader().getResourceAsStream("janmanch.properties");
		if(inputStream!= null){
			try {
				
				prop.load(inputStream);
				host = prop.getProperty("host");
				docBase = prop.getProperty("docBase");
				logger.info("host"+host+" docBase:"+docBase);
				inputStream.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug("Unable to Load property host" + e);
				e.printStackTrace();
			}
		}
	}
	public String getHost() {
		return host;
	}
	
	public static String getFacebookAppId() {
		return facebookAppId;
	}

	public static String getFacebookAppSecret() {
		return facebookAppSecret;
	}

	public static String getGoogleClientId() {
		return googleClientId;
	}

	public static String getGoogleClientSecret() {
		return googleClientSecret;
	}

	public String getDocBase() {
		return docBase;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public String getRootPath() {
		return rootPath;
	}
	public String getUploadPath1() {
		return uploadPath1;
	}
	public String getattachmentpath() {
		return attachmentpath;
	}
	public String importFile() {
		return importFile;
	}
	public String exportFile() {
		return exportFile;
	}
	public String Xmluploadpath() {
		return Xmluploadpath;
	}
	
}
