package common.util.socket.message.vo;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import common.util.socket.message.code.MessageCode;
import common.util.socket.message.json.MessageDeserializer;

/**
 * 서영석
 * 210321
 * 
 * 최상위 통신 Message 객체
 */
@JsonDeserialize(using = MessageDeserializer.class)
public abstract class Message {
	
	/**
	 * 전송 메세지 타입
	 */
	private MessageCode.MessageType messageType;
	
	/**
	 * 메세지 전송 결과
	 */
	private boolean result = false;
	
	/**
	 * 메세지 전송 결과
	 */
	private String resultMessage = MessageCode.MessageFlag.FAIL.toString();
	
	/**
	 * 서영석
	 * 210321
	 * 
	 * Message동작
	 */
	public abstract boolean run() throws Exception;
	
	//messageType - get
	public MessageCode.MessageType getMessageType() {
		return messageType;
	}
	//messageType - set
	public void setMessageType(MessageCode.MessageType messageType) {
		this.messageType = messageType;
	}
	
	//result - get
	public boolean getResult() {
		return result;
	}
	//result - set
	public void setResult(boolean result) {
		this.result = result;
		
		//동작결과가 true -> SUCCESS
		if (result == true) {
			this.resultMessage = MessageCode.MessageFlag.SUCCESS.toString();
		}
		//동작결과가 false -> FAIL
		else {
			this.resultMessage = MessageCode.MessageFlag.FAIL.toString();
		}
	}

	//resultMessage - get
	public String getResultMessage() {
		return resultMessage;
	}
	//resultMessage - set
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	/********** resultMessage 별도 정의 (시작) **********/
	/**
	 * MessageFlag를 활용한 resultMessage 정의 
	 * @param messageFlag
	 */
	public void setResultMessageByFlag(MessageCode.MessageFlag messageFlag) {
		this.resultMessage = messageFlag.toString();
		
		//SUCCESS -> 동작결과 true
		if (messageFlag == MessageCode.MessageFlag.SUCCESS) {
			this.result = true;
		}
		//FAIL -> 동작결과 false
		else if (messageFlag == MessageCode.MessageFlag.FAIL) {
			this.result = false;
		}
		//SUCCESS, FAIL이외 일때 -> 뭐 없음
		else {
			return;
		}
	}
	/**
	 * Exception를 활용한 resultMessage 정의 
	 * @param e
	 */
	public void setResultMessageByError(Exception e) {
		//에러메세지 세팅 -> result:false
		this.resultMessage = MessageCode.MessageFlag.ERROR.toString() + " : " + e.getMessage();
		this.result = false;
	}
	/********** resultMessage 별도 정의 (종료) **********/
}
