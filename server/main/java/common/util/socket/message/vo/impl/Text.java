package common.util.socket.message.vo.impl;

import common.util.StringUtil;
import common.util.socket.message.code.MessageCode;
import common.util.socket.message.vo.Message;

/**
 * 210320
 * 서영석
 * 
 * 소켓통신을 통해 문자 전송을 위한 Message객체 입니다.
 */
//public class Text implements MessageInterface {
public class Text extends Message {
	
	public Text () {
		super.setMessageType(MessageCode.MessageType.TEXT);
	}
	
	public Text (String text) {
		super.setMessageType(MessageCode.MessageType.TEXT);
		
		this.text = text;
	}
	
	private String text;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 210320
	 * 서영석
	 * 
	 * 문자 콘솔에 찍기
	 */
	@Override
	public boolean run() throws Exception {
		/** run() 메소드의 동작 결과 정의 (필수 변수)**/
		boolean result = false;
		
		System.out.println("text : " + text);
		if (StringUtil.isEmpty(text) == false) {
			result = true;
		} else {
			result = false;
		}
		
		return result;
	}
}
