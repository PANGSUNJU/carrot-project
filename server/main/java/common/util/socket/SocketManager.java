package common.util.socket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.util.StringUtil;
import common.util.socket.message.MessageProtocol;
import common.util.socket.message.code.MessageCode;
import common.util.socket.message.vo.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;

public class SocketManager {
	
	public static final int DEFAULT_SOCKET_PORT = 2200;
	
	//최대 패킷 byte
	private final int maxPacketSize = 65000;
	//기본 Socket연결 최대 시간(밀리세컨드)
	private final int defalutSocketTimeout = 1 * 60 * 1000;//1분
	//Socket 메세지 데이터 읽을 때, 최대 시간(밀리세컨드)
	private final int defalutMessageTimeout = 10 * 1000 * 1000;//10초
	
	
	private ObjectMapper mapper = null;
	private TypeReference<MessageProtocol> typeReferene = null;
	{
		/*************** [통신 처리 준비] 메세지 JSON Parsing ***************/
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		typeReferene = new TypeReference<MessageProtocol>() {};
	}
	

	public Message request(Socket clientSocket, Message message) throws Exception {
		List<Message> messageList = Arrays.asList(message);
		messageList = request(clientSocket, messageList);
		return messageList.get(0);
	}

	public List<Message> request(Socket clientSocket, List<Message> messageList) throws Exception {
		if (clientSocket == null || clientSocket.isClosed() == true) {
			throw new Exception("소켓이 없거나 닫쳤습니다.");
		}
		// 결과 메세지 목록
		List<Message> responseMessageList = null;
		
		InputStream input = null;
		DataInputStream dataInput = null;
		OutputStream output = null;
		DataOutputStream dataOutput = null;
		try {
			
			/*************** [통신 처리 준비] 클라이언트 -> 서버 ***************/
			// OutputStream - Client에서 Server로 메세지 발송
			output = clientSocket.getOutputStream();
			// socket의 OutputStream 정보를 OutputStream out에 넣은 뒤
			dataOutput = new DataOutputStream(output);
			/*************** 요청 메세지 구성 ***************/
			// 통신하기 위한 메세지 규약 생성 (요청 메시지 타입 : 명령어)
			MessageProtocol mp = new MessageProtocol(MessageCode.CommunicationType.REQUEST);
			// Body에 데이터 전송할 데이터 담기
			mp.getBody().setMessageList(messageList);
			/*************** 요청 메세지 전송 ***************/
			// 메세지 규약 전체를 JSON문자열로 변경
			String requestProtocolToJson = mapper.writeValueAsString(mp);
			//System.out.println("requestProtocolToJson : " + requestProtocolToJson);
			
			System.out.println("메세지 전송 시작!");
			// JSON문자열을 여러개로 나눔
			List<String> requestProtocolToJsonList = StringUtil.textSeparation(requestProtocolToJson, maxPacketSize);
			System.out.println("requestProtocolToJsonList.size() : " + requestProtocolToJsonList.size());
			
			for (String text : requestProtocolToJsonList) {
				// 메세지 담기
				dataOutput.writeUTF(text);
				
				// 메세지 전송
				//dataOutput.flush();
			}
			
			// 종료 메세지 담기
			dataOutput.writeUTF(MessageCode.MessageFlag.MESSAGE_END.toString());
			// 종료 메세지 전송
			dataOutput.flush();
			
			System.out.println("메세지 전송 완료!");
			
			/*************** [통신 처리 준비] 서버 -> 클라이언트 ***************/
			// InputStream - Server에서 보낸 메세지 Client로 가져옴
			input = clientSocket.getInputStream();
			// socket의 InputStream 정보를 InputStream in에 넣은 뒤
			dataInput = new DataInputStream(input);
			
			/*************** 서버 결과 메세지 확인 ***************/
			System.out.println("응답 대기 시작!");
			
			// 클라이언트에서 온 메세지 확인
			String responseProtocolToJson = "";
			while (true) {
				String text = dataInput.readUTF();
				//System.out.println((a++) + ". " + text);
				if (text.equals(MessageCode.MessageFlag.MESSAGE_END.toString())) {
					System.out.println("응답 완료!");
					break;
				}
				responseProtocolToJson += text;
			}
			//System.out.println("responseProtocolToJson : " + responseProtocolToJson);
			
			// 메세지 규약 전체를 JSON문자열로 변경
			MessageProtocol responseMP = mapper.readValue(responseProtocolToJson, typeReferene);
			
			// 결과 메세지 목록
			responseMessageList = responseMP.getBody().getMessageList();
			
			for (int i = 0; i < messageList.size(); i++) {
				messageList.set(i, responseMessageList.get(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dataInput != null)
				dataInput.close();
			if (input != null)
				input.close();

			if (dataOutput != null)
				dataOutput.close();
			if (output != null)
				output.close();
			
			/*소켓 종료*/
			if (clientSocket != null)
				socketClose(clientSocket);
		}
		
		return responseMessageList;
	}

	public void response(Socket clientSocket) throws Exception {
		if (clientSocket == null || clientSocket.isClosed() == true) {
			throw new Exception("소켓이 없거나 닫쳤습니다.");
		}

		InputStream input = null;
		DataInputStream dataInput = null;
		OutputStream output = null;
		DataOutputStream dataOutput = null;
		try {
			/*************** [통신 처리 준비] 클라이언트 -> 서버 ***************/
			// InputStream - Client에서 보낸 메세지 Server로 가져옴
			input = clientSocket.getInputStream();
			// socket의 InputStream 정보를 InputStream in에 넣은 뒤
			dataInput = new DataInputStream(input);
			
			/*************** [통신 처리 준비] 서버 -> 클라이언트 ***************/
			// OutputStream - Server에서 Client로 메세지 발송
			output = clientSocket.getOutputStream();
			// socket의 OutputStream 정보를 OutputStream out에 넣은 뒤
			dataOutput = new DataOutputStream(output);
			
			/*************** 클라이언트 메세지 확인 ***************/
			System.out.println("요청 대기 시작!");
			// 클라이언트에서 온 메세지 확인
			String requestProtocolToJson = "";
			while (true) {
				String text = dataInput.readUTF();
				if (text.equals(MessageCode.MessageFlag.MESSAGE_END.toString())) {
					System.out.println("요청 완료!");
					break;
				}
				requestProtocolToJson += text;
			}
			//System.out.println("requestProtocolToJson : " + requestProtocolToJson);
			
			// 메세지 규약 전체를 JSON문자열로 변경
			MessageProtocol requestMP = mapper.readValue(requestProtocolToJson, typeReferene);
			// 메세지 목록
			List<Message> requestMessageList = requestMP.getBody().getMessageList();
			/*************** 클라이언트 메세지 처리 ***************/
			// 메세지 실행
			for (int i = 0; i < requestMessageList.size(); i++) {
				try {
					// 메세지 실행 결과 (true(=SUCCESS) or false(=FAIL))
					boolean result = requestMessageList.get(i).run();
					
					/** run() 메소드의 동작 결과값 활용 -> '메세지 결과 세팅' (※필수) **/
					if (result == true) {
						requestMessageList.get(i).setResultMessageByFlag(MessageCode.MessageFlag.SUCCESS);//result값은 자동으로 true로 정의됨
					} else {
						requestMessageList.get(i).setResultMessageByFlag(MessageCode.MessageFlag.FAIL);//result값은 자동으로 false로 정의됨
					}
				} catch (Exception e) {
					/** run() 메소드의 에러 결과값 활용 -> '메세지 결과 세팅' (※필수) **/
					requestMessageList.get(i).setResultMessageByError(e);//result값은 자동으로 false로 정의됨
				}
			}
			
			
			/*************** 클라이언트에게 결과 메세지 전송 ***************/
			// 결과 메세지 JSON문자열로 변경
			String responseProtocolToJson = mapper.writeValueAsString(requestMP);
			//System.out.println("responseProtocolToJson : " + responseProtocolToJson);
			
			System.out.println("결과 메세지 전송 시작!");
			// JSON문자열을 여러개로 나눔
			List<String> responseProtocolToJsonList = StringUtil.textSeparation(responseProtocolToJson, maxPacketSize);
			System.out.println("responseProtocolToJsonList size : " + responseProtocolToJsonList.size());
			for (String text : responseProtocolToJsonList) {
				//System.out.println("text : " + text);
				// 메세지 담기
				dataOutput.writeUTF(text);
				// 메세지 전송
				//dataOutput.flush();
			}
			
			// 종료 메세지 담기
			dataOutput.writeUTF(MessageCode.MessageFlag.MESSAGE_END.toString());
			// 종료 메세지 전송
			dataOutput.flush();
			
			System.out.println("결과 메세지 전송 완료!");
			
		} finally {
			if (dataOutput != null)
				dataOutput.close();
			if (output != null)
				output.close();

			if (dataInput != null)
				dataInput.close();
			if (input != null)
				input.close();
		}
	}

	/**
	 * 210316 서영석
	 * 
	 * Socket 생성
	 * 
	 * @param socket : 소켓 객체
	 * @return
	 */
	public Socket socketCreate(String ip, int port) {
		SocketAddress socketAddress = null;
		Socket socket = null;
		try {
			socketAddress = new InetSocketAddress(ip, port);
			socket = new Socket();
			socket.setSoTimeout(defalutSocketTimeout);
			socket.connect(socketAddress, defalutMessageTimeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return socket;
	}
	
	/**
	 * 210316 서영석
	 * 
	 * Socket 생성
	 */
	public Socket socketCreate(String ip, int port, int timeout) {
		Socket socket = null;
		try {
			socket = new Socket(ip, port);
			socket.setSoTimeout(timeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return socket;
	}

	/**
	 * 210316 서영석
	 * 
	 * Socket 닫기
	 */
	public boolean socketClose(Socket socket) {
		boolean result = false;
		try {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

}
