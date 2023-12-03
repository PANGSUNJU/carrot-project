package common.util.socket.message;

import common.util.socket.message.vo.Message;

import java.util.List;

public class Body<T extends Message> {
	
	private List<T> messageList;

	public List<T> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<T> messageList) {
		this.messageList = messageList;
	}

}
