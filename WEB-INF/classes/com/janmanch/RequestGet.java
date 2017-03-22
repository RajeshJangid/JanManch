package com.janmanch;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.janmanch.dbconnect.AuthDAO;
import com.janmanch.dbconnect.CandidateDAO;
import com.janmanch.dbconnect.ComplaintDAO;
import com.janmanch.dbconnect.ReviewDAO;
import com.janmanch.dbconnect.SuggestionDAO;
import com.janmanch.dbconnect.WorkPromiseDAO;

public class RequestGet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String root = "/WEB-INF/jsp/";
	private static String docBase = "/";
	private static Logger logger = Logger.getLogger(RequestGet.class.getName());

	static {
		Properties prop = new Properties();
		InputStream inputStream = RequestGet.class.getClassLoader().getResourceAsStream("janmanch.properties");
		if (inputStream != null) {
			try {
				prop.load(inputStream);
				docBase = prop.getProperty("docBase");
				inputStream.close();
			} catch (IOException e) {
				logger.debug("Unable to Load property thread_sleep_interval_millis" + e);
				e.printStackTrace();
			}
		}
	}

	public RequestGet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestDispatcher rd = null;
			boolean errorflag = false;
			Object request_url = request.getAttribute("javax.servlet.error.request_uri");
			Object exception = request.getAttribute("javax.servlet.error.exception");
			Object exc_type = request.getAttribute("javax.servlet.error.exception_type");
			Object status = request.getAttribute("javax.servlet.error.status_code");
			Object message = request.getAttribute("javax.servlet.error.message");

			logger.info("requestUrl:: " + request_url + " Exception: " + exception + " ,exc_type: " + exc_type + " ,status: " + status + " ,message: " + message);

			if (request_url != null) {

				String req_url = request_url.toString();
				String server_name = request.getServerName();

				if (server_name.equals("localhost")) {
					req_url = req_url.replace("/JanManch", "");
				}

				String str = req_url.substring(req_url.indexOf("/") + 1);

				if (server_name.indexOf(".") != -1)
					server_name = server_name.substring(0, server_name.indexOf("."));

				String folder = "";
				if (str.indexOf("/") != -1) {
					folder = str.substring(0, str.lastIndexOf("/"));
					str = str.substring(str.lastIndexOf("/") + 1);
				}

				if (folder.equals("") && str.equals("robots.txt")) {
					response.setStatus(200);
					logger.info("Forwarding url /robots_" + server_name + ".txt");
					getServletConfig().getServletContext().getRequestDispatcher("/robots_" + server_name + ".txt").forward(request, response);
					return;

				}

				logger.info("folder name is == " + folder);
				logger.info("Page name is == " + str);

				if (folder.equals("")) {
					if (str.equals("") || str.equals("home")) {
						str = "home.jsp";
						response.setStatus(200);
						logger.info("Forwarding url /" + str);
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					} else if (str.equals("get-acknowledgment")) {
						str = "getAcknowledgmentId.jsp";
						response.setStatus(200);
						logger.info("Forwarding url /" + str);
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					} else if (str.equals("workpromise")) {
						str = "workpromise.jsp";
						response.setStatus(200);
						logger.info("Forwarding url /" + str);
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					} else if (str.equals("login")) {
						str = "login.jsp";
						response.setStatus(200);
						logger.info("Forwarding url /" + str);
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					} else if (str.equals("logout")) {
						str = "login.jsp";
						request.getSession().invalidate();
						response.sendRedirect(docBase + "/login");
						return;
					} else if (str.equals("verifylogin")) {
						LoginRegisterUser login = new LoginRegisterUser();
						if (login.loginUser(request, response)) {
							User user = (User)request.getSession().getAttribute("user");
							String type=user.getType();
							if(type.equals("U"))
								response.sendRedirect(docBase+"/user/"+user.getAadharId()+"/profile");
							else if(type.equals("C"))
								response.sendRedirect(docBase+"/candidate/"+user.getAadharId()+"/profile");
							else
								response.sendRedirect(docBase+"/mla/"+user.getAadharId()+"/profile");
							return;
						} else {
							request.setAttribute("error","Login credentials are invalid please try again.");
							response.sendRedirect("login");
							return;
						}
					} else if (str.equals("register")) {
						str = "register.jsp";
						response.setStatus(200);
						logger.info("Forwarding url /" + str);
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					} else if (str.equals("registeruser")) {
						LoginRegisterUser login = new LoginRegisterUser();
						String result = login.registerUser(request);
						if (result.equals("invaliddetail")) {
							response.sendRedirect("register");
							return;
						} else {
							request.setAttribute("reg", "success");
							response.sendRedirect("login");
							return;
						}
					} else if (str.equals("complaints")) {
						str = "complaints.jsp";
						response.setStatus(200);
						logger.info("Forwarding url /" + str);
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					} else if (str.equals("suggestions")) {
						str = "suggestions.jsp";
						response.setStatus(200);
						logger.info("Forwarding url /" + str);
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					} else if (str.equals("questions")) {
						str = "questions.jsp";
						response.setStatus(200);
						logger.info("Forwarding url /" + str);
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					}else if (str.equals("workproposals")) {
						str = "workproposols.jsp";
						response.setStatus(200);
						logger.info("Forwarding url /" + str);
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					}else if (str.equals("candidates")) {
						str = "candidates.jsp";
						response.setStatus(200);
						logger.info("Forwarding url /" + str);
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					} else {
						errorflag = true;
					}
				} else if (folder.startsWith("user/") || folder.indexOf("user/") == 0) {
					String arr[] = folder.split("/");
					int length = arr.length;
					logger.info("length: " + length);
					if (length > 1) {
						if (userValid(request, arr[1])) {
							if (str.equals("submitquery")) {
								// /user/23922881/submitquery
								str = "userprofile/submitquery.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("utilities")) {
								str = "userprofile/dashboard.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("profile")) {
								str = "userprofile/profile.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("filecomplaint")) {
								// /user/23922881/submitquery
								str = "userprofile/filecomplaint.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("submitreview")) {
								// /user/23922881/submitquery
								str = "userprofile/submitreview.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("submitsuggestion")) {
								// /user/23922881/submitquery
								str = "userprofile/submitsuggestion.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("applymla")) {
								// /user/23922881/submitquery
								str = "userprofile/applymla.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("submitted_suggest")) {
								// /user/23922881/submitquery
								str = "userprofile/submitted_suggest.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							}
							else if (str.equals("submitted_queries")) {
								// /user/23922881/submitquery
								str = "userprofile/submitted_queries.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							}
							else if (str.equals("submitted_complaints")) {
								// /user/23922881/submitquery
								str = "userprofile/submitted_complaints.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("submitcandidate")) {
								// /user/23922881/submitquery
								response.setStatus(200);
								PrintWriter out = response.getWriter();
								User user = (User) request.getSession().getAttribute("user");
								int result = new CandidateDAO().addCandidate(user.getAadharId(), request);
								if (result == 1){
									user.setType("C");
									out.write("success");
								}else
									out.write("failed");
								return;
							} else if (str.equals("profilepic")) {
								response.setStatus(200);
								logger.info("Forwarding url /" + "../images/profile_pics/DSC02568.JPG");
								getServletConfig().getServletContext().getRequestDispatcher(root + "../images/profile_pics/DSC02568.JPG").forward(request, response);
								return;
							} else {
								errorflag = true;
							}
						} else {
							response.sendRedirect(docBase + "/login");
							return;
						}
					} else {
						errorflag = true;
					}

				} else if (folder.startsWith("candidate/") || folder.indexOf("candidate/") == 0) {
					String arr[] = folder.split("/");
					int length = arr.length;
					logger.info("length: " + length);
					if (length > 1) {
						if (userValid(request, arr[1])) {
							if (str.equals("submitquery")) {
								// /user/23922881/submitquery
								str = "userprofile/submitquery.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("utilities")) {
								str = "userprofile/dashboard.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("profile")) {
								str = "userprofile/profile.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("filecomplaint")) {
								// /user/23922881/submitquery
								str = "userprofile/filecomplaint.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("submitreview")) {
								// /user/23922881/submitquery
								str = "userprofile/submitreview.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("submitsuggestion")) {
								// /user/23922881/submitquery
								str = "userprofile/submitsuggestion.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("applymla")) {
								// /user/23922881/submitquery
								str = "userprofile/applymla.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							}else if (str.equals("submitted_suggest")) {
								// /user/23922881/submitquery
								str = "userprofile/submitted_suggest.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							}
							else if (str.equals("submitted_queries")) {
								// /user/23922881/submitquery
								str = "userprofile/submitted_queries.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							}
							else if (str.equals("submitted_complaints")) {
								// /user/23922881/submitquery
								str = "userprofile/submitted_complaints.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("submitcandidate")) {
								// /user/23922881/submitquery
								response.setStatus(200);
								PrintWriter out = response.getWriter();
								User user = (User) request.getSession().getAttribute("user");
								int result = new CandidateDAO().addCandidate(user.getAadharId(), request);
								if (result == 1)
									out.write("success");
								else
									out.write("failed");
								return;
							} else if (str.equals("profilepic")) {
								response.setStatus(200);
								logger.info("Forwarding url /" + "../images/profile_pics/DSC02568.JPG");
								getServletConfig().getServletContext().getRequestDispatcher(root + "../images/profile_pics/DSC02568.JPG").forward(request, response);
								return;
							} else if (str.equals("proposework")) {
								// /user/23922881/submitquery
								str = "userprofile/proposework.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("workproposols")) {
								// /user/23922881/submitquery
								str = "userprofile/workproposols.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("profile")) {
								str = "userprofile/profile.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else {
								errorflag = true;
							}
						} else {
							response.sendRedirect(docBase + "/login");
							return;
						}
					} else {
						errorflag = true;
					}
				} else if (folder.startsWith("mla/") || folder.indexOf("mla/") == 0) {
					String arr[] = folder.split("/");
					int length = arr.length;
					logger.info("length: " + length);
					if (length > 1) {
						if (userValid(request, arr[1])) {
							if (str.equals("complaints")) {
								// /user/23922881/submitquery
								str = "userprofile/complaints.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("suggestions")) {
								str = "userprofile/suggestions.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("queries")) {
								str = "userprofile/questions.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("utilities")) {
								str = "userprofile/dashboard.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("proposework")) {
								// /user/23922881/submitquery
								str = "userprofile/proposework.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("workproposols")) {
								// /user/23922881/submitquery
								str = "userprofile/workproposols.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							}else if (str.equals("latestupdates")) {
								// /user/23922881/submitquery
								str = "userprofile/Add_news.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							}  else if (str.equals("profile")) {
								str = "userprofile/profile.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else {
								errorflag = true;
							}
						} else {
							response.sendRedirect(docBase + "/login");
							return;
						}
					} else {
						errorflag = true;
					}
				} else if (folder.equals("profile")) {
					String arr[] = folder.split("/");
					int length = arr.length;
					logger.info("length: " + length);
					AuthDAO auth= new AuthDAO();
					User user = auth.getMLACandidateInfo(str);
					if (user!=null) {
						// /user/23922881/submitquery
						str = "userprofile/mlafullprofile.jsp";
						response.setStatus(200);
						request.setAttribute("mlaInfo", user);
						
						logger.info("Forwarding url /" + str);
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					}else{
						str = "pagenotfound.jsp";
						getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
						return;
					}

				} else if (folder.startsWith("insert/") || folder.indexOf("insert/") == 0) {
					String arr[] = folder.split("/");
					int length = arr.length;
					logger.info("length: " + length);
					if (length > 1) {
						if (userValid(request, arr[1])) {
							if (str.equals("submit_review")) {
								str = "userprofile/submit_reveiw.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} if (str.equals("submit_query")) {
								str = "userprofile/submit_query.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("submit_suggestion")) {
								// /user/23922881/submitquery
								str = "userprofile/submit_suggestion.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("submit_Complain")) {
								// /user/23922881/submitquery
								str = "userprofile/submit_Complain.jsp";
								response.setStatus(200);
								logger.info("Forwarding url /" + str);
								getServletConfig().getServletContext().getRequestDispatcher(root + str).forward(request, response);
								return;
							} else if (str.equals("submitworkproposol")) {
								// /user/23922881/submitquery
								response.setStatus(200);
								String title = request.getParameter("title");
								String contact = request.getParameter("contact");
								String description = request.getParameter("description");
								String requirements = request.getParameter("requirements");
								String budget = request.getParameter("budget");
								String ddays = request.getParameter("ddays");
								String dmonths = request.getParameter("dmonths");
								String dyears = request.getParameter("dyears");
								String location = request.getParameter("location");
								User user = (User) request.getSession().getAttribute("user");
								String aadhar_id = user.getAadharId();
								PrintWriter out = response.getWriter();
								String result = new WorkPromiseDAO().insertWorkPromise(aadhar_id, title, description, requirements, location, contact, budget, ddays, dmonths, dyears);
								out.write(result);
								return;
							} else if (str.equals("saveprofile")) {
								// /user/23922881/submitquery
								response.setStatus(200);
								User user = (User) request.getSession().getAttribute("user");
								String aadharId = user.getAadharId();
								String introduction = request.getParameter("introduction");
								PrintWriter out = response.getWriter();
								if (introduction != null) {
									out.write(new AuthDAO().updateIntroduction(aadharId, introduction));
								}
								return;
							} else {
								logger.info("page not valid");
								errorflag = true;
							}
						} else {
							logger.info("user not valid");
							errorflag = true;
						}
					} else {
						logger.info("url not valid");
						errorflag = true;
					}
				} else if (folder.startsWith("like/") || folder.indexOf("like/") == 0) {
					// /like/{userid}/{comment_type}

					String arr[] = folder.split("/");
					int length = arr.length;
					logger.info("length: " + length);
					if (length > 1) {
						if (userValid(request, arr[1])) {
							HttpSession session = request.getSession();
							User user = (User) session.getAttribute("user");
							PrintWriter out = response.getWriter();

							if (str.equals("complaint")) {
								String complaint_id = request.getParameter("complaint_id");
								String user_id = user.getAadharId();
								ComplaintDAO cdo = new ComplaintDAO();
								response.setStatus(200);
								out.write(cdo.likeComplaint(user_id, complaint_id));
								return;
							} else if (str.equals("suggestion")) {
								String suggestion_id = request.getParameter("suggestion_id");
								String user_id = user.getAadharId();
								SuggestionDAO sdo = new SuggestionDAO();
								response.setStatus(200);
								out.write(sdo.likeSuggestion(user_id, suggestion_id));
								return;
							} else if (str.equals("query")) {
								String complaint_id = request.getParameter("query_id");
								String user_id = user.getAadharId();
								ReviewDAO rdo = new ReviewDAO();
								response.setStatus(200);
								out.write(rdo.likeReview(user_id, complaint_id));
								return;
							} else if (str.equals("candidate")) {
								String candidate_id = request.getParameter("candidate_id");
								String user_id = user.getAadharId();
								CandidateDAO cdo = new CandidateDAO();
								response.setStatus(200);
								out.write(cdo.likeCandidate(user_id, candidate_id));
								return;
							}else if (str.equals("workproposol")) {
								String proposal_id = request.getParameter("proposal_id");
								String user_id = user.getAadharId();
								WorkPromiseDAO rdo = new WorkPromiseDAO();
								response.setStatus(200);
								out.write(rdo.likeWorkProposal(user_id, proposal_id));
								return;
							} else {
								errorflag = true;
							}
						} else {
							errorflag = true;
						}
					} else {
						errorflag = true;
					}
				} else if (folder.startsWith("dislike/") || folder.indexOf("dislike/") == 0) {
					// /like/{userid}/{comment_type}

					String arr[] = folder.split("/");
					int length = arr.length;
					logger.info("length: " + length);
					if (length > 1) {
						if (userValid(request, arr[1])) {
							HttpSession session = request.getSession();
							User user = (User) session.getAttribute("user");
							PrintWriter out = response.getWriter();

							if (str.equals("complaint")) {
								String complaint_id = request.getParameter("complaint_id");
								String user_id = user.getAadharId();
								ComplaintDAO cdo = new ComplaintDAO();
								response.setStatus(200);
								out.write(cdo.unlikeComplaint(user_id, complaint_id));
								return;
							} else if (str.equals("suggestion")) {
								String suggestion_id = request.getParameter("suggestion_id");
								String user_id = user.getAadharId();
								SuggestionDAO sdo = new SuggestionDAO();
								response.setStatus(200);
								out.write(sdo.unlikeSuggestion(user_id, suggestion_id));
								return;
							} else if (str.equals("query")) {
								String complaint_id = request.getParameter("query_id");
								String user_id = user.getAadharId();
								ReviewDAO rdo = new ReviewDAO();
								response.setStatus(200);
								out.write(rdo.unlikeReview(user_id, complaint_id));
								return;
							}else if (str.equals("candidate")) {
								String candidate_id = request.getParameter("candidate_id");
								String user_id = user.getAadharId();
								CandidateDAO cdo = new CandidateDAO();
								response.setStatus(200);
								out.write(cdo.unlikeCandidate(user_id, candidate_id));
								return;
							}else if (str.equals("workproposol")) {
								String proposal_id = request.getParameter("proposal_id");
								String user_id = user.getAadharId();
								WorkPromiseDAO rdo = new WorkPromiseDAO();
								response.setStatus(200);
								out.write(rdo.unlikeWorkProposal(user_id, proposal_id));
								return;
							} else {
								errorflag = true;
							}
						} else {
							errorflag = true;
						}
					} else {
						errorflag = true;
					}
				} else {
					errorflag = true;
				}

				if (errorflag) {
					Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
					if (statusCode == 404) {
						response.setStatus(404);
						rd = request.getRequestDispatcher(root + "pagenotfound.jsp");
					} else {
						rd = request.getRequestDispatcher(root + "/servererror.jsp");
					}
				}

				rd.forward(request, response);
			} else {
				logger.error("request_url was null");
			}

			logger.info("req_url: " + request.getAttribute("javax.servlet.error.request_uri"));
		} catch (Exception e) {
			logger.error(" Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean userValid(HttpServletRequest request, String userId) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null && user.getAadharId().equals(userId)) {
			return true;
		}
		return false;
	}

}