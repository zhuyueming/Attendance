package moz.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moz.connectDb.*;

import net.sf.json.JSONObject;

public class ReceiveQRCode extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConnectDb connectdb = new ConnectDbImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		/**
		 * QRCode{"StringQRCode":"stringQRCode"}
		 * * TeacherQRCode{"ClassId":"classId","StringQRCode":"stringQRCode"}
		 */
		request.setCharacterEncoding("UTF-8");
		String teacherQRCode = request.getParameter("TeacherQRCode");
		System.out.println(teacherQRCode);
		JSONObject signObj = JSONObject.fromObject(teacherQRCode);
		String classId = signObj.getString("ClassId");
		String stringQRCode = signObj.getString("StringQRCode");
	
	
	
		boolean state = false;
		PrintWriter out = null;
		try {
			
		state = connectdb.saveTeacherQRCode(classId,stringQRCode);
		out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		if(state){
			
			out.print("isOk");
			
		}else{
			out.print("notOK");
		}
		
		} catch (Exception e) {
		
			e.printStackTrace();
		}

		
	}

}
