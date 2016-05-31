package moz.service;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import moz.connectDb.*;
import net.sf.json.JSONObject;

public class LogIn extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2194815071474393296L;
	private ConnectDb connectDb = new ConnectDbImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

/**
UserLogin{"LoginId":"loginId","LoginPwd":"loginPwd","Identity":"identity"}
*/

		request.setCharacterEncoding("UTF-8");

		String userLogin = request.getParameter("UserLogin");
		System.out.println(userLogin);
		JSONObject userLoginObj = JSONObject.fromObject(userLogin);
		String loginId = userLoginObj.getString("LoginId");
		String loginPwd = userLoginObj.getString("LoginPwd");

		HttpSession session = request.getSession();
		session.setAttribute(session.getId(), loginId);
		String Id = (String) session.getAttribute(session.getId());
		System.out.println(session.getId() + "--" + Id);
		int identity = userLoginObj.getInt("Identity");
		System.out.println(identity+"------");
		// System.out.println(loginName+"------"+loginPassword);
		boolean state = false;
		PrintWriter out = null;
		try {
//			System.out.println(loginId+" "+ loginPwd+" "+ identity);
			state = connectDb.LoginConnectDb(loginId, loginPwd, identity);
			out = response.getWriter();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			if (state) {
				out.print("LogInSuccess");
			} else {
				out.print("LogInFailed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
