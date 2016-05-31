package moz.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moz.connectDb.*;

import net.sf.json.JSONObject;

public class ModifyPwd extends HttpServlet {


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
 ModifyPwd{"LoginId":"loginId","ModifyOldPwd":"oldPwd","ModifyNewPwd":"newPwd","Identity":"identity"}
*/

		request.setCharacterEncoding("UTF-8");
		String modifyPwd = request.getParameter("ModifyPwd");
		System.out.println(modifyPwd);
		JSONObject modifyPwdObj = JSONObject.fromObject(modifyPwd);		
		String loginId = modifyPwdObj.getString("LoginId");
		String oldPwd = modifyPwdObj.getString("ModifyOldPwd");
		String newPwd = modifyPwdObj.getString("ModifyNewPwd");
		int identity = modifyPwdObj.getInt("Identity");
		
		
		boolean state = false;
		PrintWriter out = null;
		try {
			
		state = connectdb.ModPwdConnectDb(loginId, oldPwd,newPwd,identity);
		out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		if(state){
			
			out.print("ModifySuccess");
			
		}else{
			out.print("ModifyFailed");
		}
		
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		
		
		
		
		
		
		
	}

}
