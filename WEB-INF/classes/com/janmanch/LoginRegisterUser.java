package com.janmanch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.janmanch.dbconnect.AuthDAO;

public class LoginRegisterUser {
	final String CLIENT_ID = "ad7288a4-7764-436d-a727-783a977f1fe1"; 
	final Logger logger = Logger.getLogger(LoginRegisterUser.class.getName());
	public boolean loginUser(HttpServletRequest request, HttpServletResponse response){
		String ack_id = request.getParameter("ack_id");
		String pass = request.getParameter("bham_pass");
		ack_id = ack_id == null?"":ack_id.trim();
		pass = pass == null?"":pass.trim();
		
		if(!pass.equals("") && !ack_id.equals("")){
			AuthDAO auth = new AuthDAO();
			User user = auth.loginUser(ack_id, pass);
			if(user!=null){
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				return true;
			}
		}
		return false;
	}
	
	public String registerUser(HttpServletRequest request){
		String ack_id = request.getParameter("ack_id");
		String mob_no = request.getParameter("mob_no");
		String password = request.getParameter("new_pass");
		ack_id = ack_id == null?"":ack_id.trim();
		mob_no = mob_no == null?"":mob_no.trim();
		password = password == null?"":password.trim();
		
		if(!password.equals("") && !ack_id.equals("")){
			String requrl = "https://apitest.sewadwaar.rajasthan.gov.in/app/live/Service/family/details/"+ack_id+"?client_id="+CLIENT_ID;
			APICallUtility api = new APICallUtility();
			JSONObject jsonobj = api.sendGetRequest(requrl);
			logger.info("first api request executed");
			if(jsonobj !=null){
				try{
					JSONObject obj = jsonobj.getJSONObject("hof_Details");
					
					if(obj!=null){
						String familyid = (String)obj.get("FAMILYIDNO");
						logger.info("family_id: "+familyid);
						if(familyid!=null){
							requrl = "https://apitest.sewadwaar.rajasthan.gov.in/app/live/Service/hofAndMember/ForApp/"+familyid+"?client_id="+CLIENT_ID;
							jsonobj = api.sendGetRequest(requrl);
							logger.info("second_req executed ");
							if(jsonobj !=null){
								try{
									obj = jsonobj.getJSONObject("hof_Details");
									if(obj!=null){
										String mobile = (String)obj.get("MOBILE_NO");
										if(mobile!=null && mobile.equals(mob_no)){
											AuthDAO auth = new AuthDAO();
											return auth.registerUser(jsonobj, password);
										}
									}
								}catch(Exception e){
									logger.info("Exception "+e.getMessage());
								}
							}
						}else{
							
						}
					}
				}catch(Exception e){
					logger.info("Exception "+e.getMessage());
				}
			}
		}
		
		return "invaliddetail";
	}
}
