package moz.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moz.connectDb.*;

import net.sf.json.JSONObject;

public class GetName extends HttpServlet {


	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7257315684131452248L;
	private ConnectDb connectdb = new ConnectDbImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/**
		  AskForName{"LoginId":"loginId","Identity":"identity"}
		 */

		request.setCharacterEncoding("UTF-8");
		String askForName = request.getParameter("AskForName");
		System.out.println(askForName);
		JSONObject modifyPwdObj = JSONObject.fromObject(askForName);		
		String loginId = modifyPwdObj.getString("LoginId");
		int identity = modifyPwdObj.getInt("Identity");
		
		
		String state;
		PrintWriter out = null;
		try {
			
		state = connectdb.getNameConnectDb(loginId,identity);
		out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		if(state!=null){
			System.out.println("username:"+state);
			out.print(state);
			
		}else{
			out.print("CheckNameError");
		}
		
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	

	}

}
