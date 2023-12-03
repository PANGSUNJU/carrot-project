package common.util.socket.message;


import common.util.socket.message.code.MessageCode;

public class Header {
	
	public Header() {}
	
	public Header(MessageCode.CommunicationType communicationType) {
		this.communicationType = communicationType;
	}
	
	private MessageCode.CommunicationType communicationType;
	
	public MessageCode.CommunicationType getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(MessageCode.CommunicationType communicationType) {
		this.communicationType = communicationType;
	}
	
}
