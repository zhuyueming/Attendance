package moz.connectDb;

public interface ConnectDb {
	public boolean LoginConnectDb(String LoginId,String LoginPwd,int Identity) throws Exception;
	public String getNameConnectDb(String LoginId,int Identity)throws Exception;
	public boolean ModPwdConnectDb(String LoginId,String OldPwd,String NewPwd,int Identity) throws Exception;
	public boolean SignInConnectDb(String ScanResult,String UserId) throws Exception;
	public boolean saveTeacherQRCode(String ClassId,String QRCode) throws Exception;
	public boolean SaveSession(String UserId,String SessionId) throws Exception;
}
