package common.util.socket.message;

import common.util.socket.message.code.MessageCode;

public class MessageProtocol {

	public MessageProtocol() {}
	
	public MessageProtocol(MessageCode.CommunicationType communicationType) throws Exception {
		this.header = new Header(communicationType);
		this.body = new Body<>();
	}
	
	private Header header;
	
	private Body body;

	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}
	
}
