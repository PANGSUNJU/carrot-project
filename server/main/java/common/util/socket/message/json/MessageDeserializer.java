package common.util.socket.message.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import common.util.CommonUtil;
import common.util.socket.message.code.MessageCode;
import common.util.socket.message.vo.Message;

import java.io.IOException;
import java.util.HashMap;

public class MessageDeserializer extends StdDeserializer<Message> {

	private static final long serialVersionUID = 1L;
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	private final TypeReference<HashMap<String, Object>> typeReferene = new TypeReference<HashMap<String, Object>>(){};

	public MessageDeserializer() {
		this(null);
	}

	public MessageDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Message deserialize(JsonParser jsonParser, DeserializationContext arg1) throws IOException, JsonProcessingException {
		Message result = null;
		try {
			//JsonParser to JsonNode
			JsonNode node = jsonParser.getCodec().readTree(jsonParser);
			//System.out.println("node str : " + node.toString());
			
			//메세지 타입 문자열 to MessageType (String to Enum)
			MessageCode.MessageType messageType = MessageCode.MessageType.valueOf(node.get("messageType").asText());
			//System.out.println("messageType : " + messageType);
			
			/*Message 객체 생성*/
			if (messageType == MessageCode.MessageType.FILE) {//MessageType이 FILE 일 때
				//FileMessageType 확인
				MessageCode.FileMessageType fileMessageType = MessageCode.FileMessageType.valueOf(node.get("fileMessageType").asText());
				//객체 생성시 생성자에 파라메터 값(FileMessageType) 전달
				result = messageType.getClazz().getDeclaredConstructor(MessageCode.FileMessageType.class).newInstance(fileMessageType);
			} else {//MessageType이 FILE이외 다른거 일 때
				//객체 생성
				result = messageType.getClazz().newInstance();
			}
			
			//json문자열 to HashMap<String, Object>
			HashMap<String, Object> messageMap = mapper.readValue(node.toString(), typeReferene);
			
			//Map(value) -> Message(value) (단, messageType은 제외)
			String[] ignoreFields = {"messageType"};
			//Map(value) -> Message(value) (단, messageType은 제외)
			CommonUtil.objectSetMapValue(result, messageMap, ignoreFields);
			
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
