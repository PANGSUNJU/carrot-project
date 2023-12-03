package common.util.socket.message.vo.impl;

import common.util.StringUtil;
import common.util.socket.FileUtil;
import common.util.socket.message.code.MessageCode;
import common.util.socket.message.vo.Message;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 210320
 * 서영석
 * 
 * 소켓통신을 통해 파일 전송을 위한 Message객체 입니다.
 */
//public class File implements MessageInterface {
public class File extends Message {
	
	public File (MessageCode.FileMessageType fileMessageType) {
		super.setMessageType(MessageCode.MessageType.FILE);
		
		this.newFilePath = "./";
		
		this.fileMessageType = fileMessageType;
	}
	
	
	public File (MessageCode.FileMessageType fileMessageType, String originFileName, String originFilePath) {
		super.setMessageType(MessageCode.MessageType.FILE);
		
		this.originFileName = originFileName;
		this.originFilePath = originFilePath;
		this.newFileName = originFileName;
		this.newFilePath = "./";
		
		this.fileMessageType = fileMessageType;
	}
	
	public File (MessageCode.FileMessageType fileMessageType, String originFileName, String originFilePath, String newFileName, String newFilePath) {
		super.setMessageType(MessageCode.MessageType.FILE);
		
		this.originFileName = originFileName;
		this.originFilePath = originFilePath;
		this.newFileName = newFileName;
		this.newFilePath = newFilePath;
		
		this.fileMessageType = fileMessageType;
	}
	
	/**
	 * 원본 파일명 (확장자까지 포함)
	 */
	private String originFileName;
	
	/**
	 * 원본 파일경로 (절대경로)
	 */	
	private String originFilePath;
	
	/**
	 * 원본 파일 binary
	 */	
	private String originFileBinary;
	
	/**
	 * 새로운 파일명 (확장자까지 포함)
	 */	
	private String newFileName;
	
	/**
	 * 새로운 파일경로 (절대경로)
	 */	
	private String newFilePath;
	
	/**
	 * 파일 크기
	 */
	private long fileLength;
	
	/**
	 * 파일 전송 타입(가지고오기, 내보내기)
	 */
	private MessageCode.FileMessageType fileMessageType;
	
	/**
	 * 이미지 파일 리사이징 완료 여부 (default:false)
	 */
	private boolean resizingComplete = false;

	public String getOriginFileName() {
		return originFileName;
	}
	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public String getOriginFilePath() {
		return originFilePath;
	}
	public void setOriginFilePath(String originFilePath) {
		this.originFilePath = originFilePath;
	}

	public String getOriginFileBinary() {
		return originFileBinary;
	}
	public void setOriginFileBinary(String originFileBinary) {
		this.originFileBinary = originFileBinary;
	}

	public String getNewFileName() {
		return newFileName;
	}
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public String getNewFilePath() {
		return newFilePath;
	}
	public void setNewFilePath(String newFilePath) {
		this.newFilePath = newFilePath;
	}

	public long getFileLength() {
		return fileLength;
	}
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public MessageCode.FileMessageType getFileMessageType() {
		return fileMessageType;
	}

	public void setFileMessageType(MessageCode.FileMessageType fileMessageType) {
		this.fileMessageType = fileMessageType;
	}

	public boolean getResizingComplete() {
		return resizingComplete;
	}


	public void setResizingComplete(boolean resizingComplete) {
		this.resizingComplete = resizingComplete;
	}


	/**
	 * 210320
	 * 서영석
	 * 
	 * file정보를 활용하여 Binary로 만들기
	 */
	public boolean fileToBinary() throws Exception {
		
		boolean result = false;
		
		// 원본 파일명 체크
		if (StringUtil.isEmpty(this.originFileName) == true) {
			throw new Exception("파일명이 없음");
		}
		// 원본 파일경로 체크
		if (StringUtil.isEmpty(this.originFilePath) == true) {
			throw new Exception("파일 경로가 없음");
		}

		// 원본 파일 메모리에 생성
		System.out.println(this.originFilePath + java.io.File.separator + this.originFileName);
		java.io.File file = new java.io.File(this.originFilePath + java.io.File.separator + this.originFileName);
		
		// 원본 파일 존재하는지 확인
		if (file.exists() == false) {
			throw new Exception("파일이 존재하지 않음");
		}
		// 원본 파일인지 아닌지 확인
		if (file.isFile() == false) {
			throw new Exception("파일이 아님");
		}
		
		// 원본 파일크기 세팅
		this.fileLength = file.length();

		// 새로운 파일명이 없을 때, 원본 파일명 넣기
		if (StringUtil.isEmpty(this.newFileName) == true) {
			this.newFileName = this.originFileName;
		}
		// 새로운 파일경로가 없을 때, 소켓 서버의 디렉토리 경로 넣기
		if (StringUtil.isEmpty(this.newFilePath) == true) {
			this.newFilePath = ".";//현재 디렉토리
		}
		
		FileInputStream fileInput = null;
		try {
			//이미지 파일에대한 유무
			boolean isImageFile = FileUtil.isImageFile(file);
			
			//파일 byte를 담을 공간
			byte[] bytes = null;
			
			//이미지 파일일 때, 축소
			if (FileUtil.IMAGE_FILE_RESIZING && isImageFile == true) {
				bytes = FileUtil.imageFileResizeToByte(file);
			} else {//이미지 파일이 아닐 때
				bytes = FileUtil.fileToByte(file);
			}
			
			// bytes -> String
			this.originFileBinary = new String(FileUtil.base64Encoding(bytes)); 
			/*처리 결과*/
			result = true;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (fileInput != null)
				fileInput.close();
		}
		
		return result;
	}
	
	/**
	 * 210320
	 * 서영석
	 * 
	 * Binary로 파일 만들기
	 */
	public boolean binaryToFile() throws Exception {
		
		boolean result = false;
		
		// 생성할 파일명 체크
		if (StringUtil.isEmpty(this.newFileName) == true) {
			throw new Exception("저장할 파일명이 없음");
		}
		// 생성할 파일경로 체크
		if (StringUtil.isEmpty(this.newFilePath) == true) {
			this.newFilePath = ".";//현재 디렉토리
		}
		
		FileOutputStream fileOutput = null;
		try {
			// 생성할 파일의 폴더 생성
			java.io.File path = new java.io.File(this.newFilePath); 
			if (path.exists() == false || path.isDirectory() == false) {
				path.mkdir();
			}
			// 파일 최종 경로 (파일 디렉토리 + 파일명)
			String fileFullPath = this.newFilePath + java.io.File.separator + this.newFileName;
			// 파일 읽을 줄비
			fileOutput = new FileOutputStream(fileFullPath);
			// String -> byte -> 파일
			fileOutput.write(FileUtil.base64Decoding(this.originFileBinary.getBytes()));
			
			// 바이너리 비우기
			this.originFileBinary = null;
			
			/*처리 결과*/
			result = true;
		} finally {
			if (fileOutput != null)
				fileOutput.close();
		}
		
		return result;
	}
	
	/**
	 * 210320
	 * 서영석
	 * 
	 * MessageType:FILE_IMPORT(파일 가져오기)
	 *  1. fileToBinary() 메소드를 활용하여 [파일 객체 -> Binary로 변형]
	 *  (단, originFileName, originFilePath 필수) 
	 *  끝
	 * 
	 * MessageType:FILE_EXPORT(파일 내보내기)
	 *  1. binaryToFile() 메소드를 활용하여 [Binary -> 파일 객체 생성]
	 *  (단, newFileName, newFilePath, originFileBinary 필수)
	 *  끝
	 */
	@Override
	public boolean run() throws Exception {
		/** run() 메소드의 동작 결과 정의 (필수 변수)**/
		boolean result = false;
		
		if (this.fileMessageType == MessageCode.FileMessageType.IMPORT) {
			//파일 객체 -> Binary
			result = fileToBinary();
			
			/*로그 생성*/
			String logMessage = fileMessageType.toString() + " - ";
			logMessage += "originFileName:" + originFilePath + "/" + originFileName;
			logMessage += ", newFile:" + newFilePath + "/" + newFileName;
			logMessage += ", result:" + result;
			FileUtil.writeLogFile(logMessage, "socket_log", "./socket_log");
		} else if (this.fileMessageType == MessageCode.FileMessageType.EXPORT) {
			//Binary -> 파일 객체
			result = binaryToFile();
			
			/*로그 생성*/
			String logMessage = fileMessageType.toString() + " - ";
			logMessage += "originFileName:" + originFilePath + "/" + originFileName;
			logMessage += ", newFile:" + newFilePath + "/" + newFileName;
			logMessage += ", result:" + result;
			FileUtil.writeLogFile(logMessage, "socket_log", "./socket_log");
		} else {
			throw new Exception("FileMessageType이 Null이거나 잘못되었습니다.");
		}
		
		return result;
	}
	
}
