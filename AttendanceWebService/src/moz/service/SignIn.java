package moz.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moz.connectDb.*;

import net.sf.json.JSONObject;

public class SignIn extends HttpServlet {

	private static final long serialVersionUID = -4160634220097202645L;
	private ConnectDb connectdb = new ConnectDbImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 /**
         * SignIn{"LoginId":"loginId","ScanResult":"scanResult"}
         */
		request.setCharacterEncoding("UTF-8");
		String signIn = request.getParameter("SignIn");
		System.out.println(signIn);
		JSONObject signInObj = JSONObject.fromObject(signIn);		
		String scanResult = signInObj.getString("ScanResult");
		String loginId = signInObj.getString("LoginId");
		System.out.println(scanResult+"------"+loginId);
		
		boolean state = false;
		PrintWriter out = null;
		try {
			
		state = connectdb.SignInConnectDb(scanResult,loginId);
		out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		if(state){
			
			out.print("SignInSuccess");
			
		}else{
			out.print("SignInFailed");
		}
		
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		
	}

}
