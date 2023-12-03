package common.util.socket.message.vo.impl;

import common.util.socket.FileUtil;
import common.util.socket.message.code.MessageCode;
import common.util.socket.message.vo.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 210320
 * 서영석
 * 
 * 소켓통신을 통해 명령어 전송을 위한 Message객체 입니다.
 */
//public class Command implements MessageInterface {
public class Command extends Message {
	
	//명령어 최대 실행 시간(초)
	public static int COMMAND_TIMEOUT_SECONDS = 10;
	
	public Command () {
		super.setMessageType(MessageCode.MessageType.COMMEND);
	}
	
	public Command (String command) {
		super.setMessageType(MessageCode.MessageType.COMMEND);
		
		this.command = command;
	}
	
	private String command;
	
	private List<String> commandResult;

	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}

	public List<String> getCommandResult() {
		return commandResult;
	}
	public void setCommandResult(List<String> commandResult) {
		this.commandResult = commandResult;
	}

	/**
	 * 210320
	 * 서영석
	 * 
	 * 명령어 실행
	 */
	@Override
	public boolean run() throws Exception {
		/** run() 메소드의 동작 결과 정의 (필수 변수)**/
		boolean result = false;
		
		/*************** [명령어 처리 준비] JAVA(JVM) -> OS ***************/
        /*
         * 명령어 준비
         * 
         * Window : cmd.exe /C [명령어] (명령어 수행 완료후 cmd.exe 종료)
         * Linux : /bin/sh -c [명렁어] (명령어 수행 완료후 shell 종료)
         */
        String[] commend = {"", "", this.command};
        if (System.getProperty("os.name").indexOf("Windows") > -1) {
        	commend[0] = "cmd.exe";
        	commend[1] = "/C"; 
        } else {
        	commend[0] = "/bin/sh";
        	commend[1] = "-c";
        }//명령어 준비 끝
        
        
        /*************** [명령어 수행] JAVA(JVM) -> OS ***************/
        //Runtime객체 : 운영체제 기반의 프로그램을 실행시키거나 운영체제에 대한 정보를 제공
        //Process객체 : JVM 메모리에 올라와서 업무 처리를 수행할 객체 
        Process process = Runtime.getRuntime().exec(commend);
        
        /*로그 생성*/
		String logMessage = "COMMAND : " + this.command;
		FileUtil.writeLogFile(logMessage, "socket_log", "./socket_log");
        
        /*************** 업무처리가 완료 될 때 까지 기다림 ***************/
        //COMMAND_TIMEOUT_SECONDS안에 업무처리가 완료 될 때 까지 기다림(+수행 처리 시간 받기)
		//int execTime = process.waitFor();
        boolean isComplete = process.waitFor(COMMAND_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        System.out.println("isComplete : " + isComplete);
        
        /*************** [명령어 수행 결과 받을 준비 / 수행 결과 담기] OS -> JAVA(JVM) ***************/
        commandResult = new ArrayList<String>();
        
        BufferedReader resultBufferReader = null;
        BufferedReader errorBufferReader = null;
        if (isComplete == true) {
        	 resultBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
             String msg = null;
             while ((msg = resultBufferReader.readLine()) != null) {
             	if (msg.contains(System.getProperty("line.separator")) == false) {
             		msg += System.getProperty("line.separator");
             	}
             	commandResult.add(msg);
             }
             errorBufferReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
             int err_count = 0;
             while ((msg = errorBufferReader.readLine()) != null) {
             	if (err_count++ == 0) {
             		commandResult.add("================ [Error] ================" + System.getProperty("line.separator"));
             	}
             	
             	if (msg.contains(System.getProperty("line.separator")) == false) {
             		msg += System.getProperty("line.separator");
             	}
             	commandResult.add(msg);
             }
        } else {
        	commandResult.add("COMMAND : " + this.command + " - Timeout(" + COMMAND_TIMEOUT_SECONDS + "초)");
        }
        
		/*************** 클라이언트에게 명령어 수행 결과 메세지 전송 ***************/
        // 서버에서 클라이언트로 메세지 보내기
        if (isComplete && process.exitValue() == 0) {
        	//commandResult.add("[" + (execTime / 1000.0) + "초 Ok]");
        	commandResult.add(MessageCode.MessageFlag.SUCCESS.toString());
        	/** 결과 담기 **/
        	result = true;
        } else {
        	//commandResult.add("[" + (execTime / 1000.0) + "초 Error]");
        	commandResult.add(MessageCode.MessageFlag.FAIL.toString());
        	/** 결과 담기 **/
        	result = false;
        }
        
		if (resultBufferReader != null) resultBufferReader.close();
		if (errorBufferReader != null) errorBufferReader.close();
		if (process != null) process.destroy();
		
		for (int i = 0; i < commandResult.size(); i++) {
			System.out.println("result : " + commandResult.get(i));
		}
		
		return result;
	}

}
