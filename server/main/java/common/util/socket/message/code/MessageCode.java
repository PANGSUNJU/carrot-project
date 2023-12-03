package common.util.socket.message.code;


import common.util.socket.message.vo.Message;
import common.util.socket.message.vo.impl.Command;
import common.util.socket.message.vo.impl.File;
import common.util.socket.message.vo.impl.Text;

/**
 * 210317
 * 서영석
 * 
 * Socket통신 시, 필요한 메세지 규약 저장소입니다.
 */
public class MessageCode {

	/**
	 * 210317
	 * 서영석
	 * 
	 * 메세지 전송 타입(요청 OR 응답)
	 */
	public enum CommunicationType {
		REQUEST
		, RESPONSE;
	}
	
	/**
	 * 210317
	 * 서영석
	 * 
	 * 통신할 메세지 타입
	 */
	public enum MessageType {
		FILE(File.class)//파일 전송
		, TEXT(Text.class)//Text 전송
		, COMMEND(Command.class);//명령어 전송
		
		private Class<? extends Message> clazz;
		
		private MessageType (Class<? extends Message> clazz) {
	        this.clazz = clazz;
	    }

		public Class<? extends Message> getClazz() {
			return clazz;
		}
	}
	
	/**
	 * 210317
	 * 서영석
	 * 
	 * 통신할 메세지 타입
	 */
	public enum FileMessageType {
		IMPORT//파일 가져오기
		, EXPORT//파일 내보내기
	}
	/**
	 * 210317
	 * 서영석
	 * 
	 * 통신을 할 때, 필요한 구분자  
	 */
	public enum MessageFlag {
		MESSAGE_START
		, MESSAGE_END
		
		, HEAD_START
		, HEAD_END
		
		, BODY_START
		, BODY_END
		
		, FILE_START
		, FILE_END
		
		, SUCCESS
		, FAIL
		, ERROR;
	}
}
