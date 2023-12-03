package common.util.socket;

import common.util.CommonUtil;

import java.net.Socket;
import java.net.SocketException;

public class SocketReponseManager implements Runnable {
	
	private Socket clientSocket = null;
	
	private String userIP = null;
	
	public SocketReponseManager(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.userIP = clientSocket.getRemoteSocketAddress().toString();
	}
	
	@Override
	public void run() {
		SocketManager socketManager = new SocketManager();
		
		try {
			socketManager.response(clientSocket);
		} catch (SocketException e) {
			e.printStackTrace();
			/*로그 생성*/
			String logMessage = "[" + userIP + "] clientSocket close ??? " + CommonUtil.exceptionToString(e);
			FileUtil.writeLogFile(logMessage, "socket_log", "./socket_log");
		} catch (Exception e) {
			e.printStackTrace();
			/*로그 생성*/
			String logMessage = "[" + userIP + "] " + CommonUtil.exceptionToString(e);
			FileUtil.writeLogFile(logMessage, "socket_log", "./socket_log");
		}
		
	}

}
