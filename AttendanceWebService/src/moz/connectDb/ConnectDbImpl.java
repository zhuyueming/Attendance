package moz.connectDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDbImpl implements ConnectDb {
	PreparedStatement preparedStatement;
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	final static String url = "jdbc:mysql://127.0.0.1:3306/attendance";
	final static String user = "root";
	final static String password = "admin";
	final static int Identity = 1;
	final static String selectStudents = "select * from students";
	final static String selectTeachers = "select * from teachers";
	final static String selectSession = "select * from onlineusers";
	final static String selectTeacherQRCode ="select * from qrcode";
	final static String updateSession = "update  onlineusers set sessionId=? where Id=?";
	final static String insertSession = "insert into onlineusers (Id,sessionId) values (?,?)";
	final static String updateQRCode = "update  qrcode set qrCode=? where classId=?";
	final static String insertQRCode = "insert into qrcode (classId,qrCode) values (?,?)";
	

	public ConnectDbImpl() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 利用身份返回结果集
	public ResultSet switchIdentity(int Identity) throws Exception {
		switch (Identity) {
		case 1:
			return statement.executeQuery(selectStudents);
		case 2:
			return statement.executeQuery(selectTeachers);
		}
		return null;
	}

	// 登陆
	@Override
	public boolean LoginConnectDb(String LoginId, String LoginPwd, int Identity)
			throws Exception {

		
		resultSet = switchIdentity(Identity);
		// 5. 输出结果集的数据
		while (resultSet.next()) {
			String userId = resultSet.getString("Id");
			String userPwd = resultSet.getString("Password");
			System.out.println(resultSet.getString("Id") + ":"
					+ resultSet.getString("Password"));
			if (userId.equals(LoginId) && userPwd.equals(LoginPwd)) {
				return true;
			}
		}
		// 6. 关闭连接,命令对象，结果集
		return false;
	}

	@Override
	public boolean ModPwdConnectDb(String LoginId, String OldPwd,
			String NewPwd, int Identity) throws Exception {

		resultSet = switchIdentity(Identity);

		// 5. 输出结果集的数据
		while (resultSet.next()) {
			String userName = resultSet.getString("Id");
			String userPwd = resultSet.getString("Password");
			System.out.println(resultSet.getString("Id") + ":"
					+ resultSet.getString("Password"));
			if (userName.equals(LoginId) && userPwd.equals(OldPwd)) {
				switch (Identity) {
				case 1:
					preparedStatement = connection
							.prepareStatement("update  students set Password=? where Id=?");
					break;
				case 2:
					preparedStatement = connection
							.prepareStatement("update teachers set Password=? where Id=?");
					break;

				}
				preparedStatement.setString(1, NewPwd);
				preparedStatement.setString(2, LoginId);
				preparedStatement.executeUpdate();
				return true;
			}

		}

		return false;
	}

	@Override
	public boolean SignInConnectDb(String scanResult, String userId)
			throws Exception {

		resultSet = statement.executeQuery(selectTeacherQRCode);
		while (resultSet.next()) {
			
			String classId1 = resultSet.getString("classId");
			String qrCode = resultSet.getString("qrCode");
		String classId2 = userId.substring(0, 7);
			if(classId1.equals(classId2)){
				if (scanResult.equals(qrCode)) {
					PreparedStatement preparedStatement = connection
					.prepareStatement("update  students set flag=? where Id=?");
					preparedStatement.setString(1, "1");
					preparedStatement.setString(2, userId);
					preparedStatement.executeUpdate();
					return true;
				} 
			}
		}
		return false;
	}

	@Override
	public boolean saveTeacherQRCode(String ClassId,String QRCode) throws Exception {
		
		String classId = ClassId;
		String qrCode = QRCode;
		final String selectTeacherQRCode = "select qrCode from qrcode where classId="+classId;
		
		resultSet = statement.executeQuery(selectTeacherQRCode);
		
		System.out.println(classId + ".............." + qrCode);
		
		
		boolean result =resultSet.first();
		
		if(result){//当前表中存在此classId
			//String Id = resultSet.getString("classId");
			preparedStatement = connection.prepareStatement(updateQRCode);
			preparedStatement.setString(1, qrCode);
			preparedStatement.setString(2, classId);
			preparedStatement.executeUpdate();//更新qrCode
			return true;
		}else{
			//当前表中不存在此classId
			preparedStatement = connection.prepareStatement(insertQRCode);
			preparedStatement.setString(1, classId);
			preparedStatement.setString(2, qrCode);
			preparedStatement.executeUpdate();//插入classId，qrCode
			
			return true;
		}

		

		
		
		
	}

	@Override
	public String getNameConnectDb(String LoginId, int Identity)
			throws Exception {
		resultSet = switchIdentity(Identity);
		while (resultSet.next()) {
			String userId = resultSet.getString("Id");
			String Name = resultSet.getString("Name");
			if (userId.equals(LoginId))
				return Name;
		}
		return null;
	}

	@Override
	public boolean SaveSession(String UserId, String SessionId)
			throws Exception {
		resultSet = statement.executeQuery(selectSession);
		String userId = UserId;
		String sessionId = SessionId;
		

		while (resultSet.next()) {
			String Id = resultSet.getString("Id");
			if (UserId.equals(Id)) {
				preparedStatement = connection.prepareStatement(updateSession);
				preparedStatement.setString(1, sessionId);
				preparedStatement.setString(2, userId);
				preparedStatement.executeUpdate();
				return true;
			} else {
				preparedStatement = connection.prepareStatement(insertSession);
				preparedStatement.setString(1, userId);
				preparedStatement.setString(2, sessionId);
				preparedStatement.executeUpdate();
				return true;
			}

		

		}
		return false;
	}
}
