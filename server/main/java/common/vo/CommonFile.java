package common.vo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author 서영석
 * @since 2019.11.24
 *
 * File 관리 Domain 입니다.
 */
public class CommonFile extends Search {

	public CommonFile() {};

	public CommonFile(String commonFileGroupId) {
		this.commonFileGroupId = commonFileGroupId;
	};

	public CommonFile(long commonFileSeq) {
		this.commonFileSeq = commonFileSeq;
	};

	public CommonFile(String commonFileGroupId, long commonFileSeq) {
		this.commonFileGroupId = commonFileGroupId;
		this.commonFileSeq = commonFileSeq;
	};

	public CommonFile(FileType fileType) {
		this.fileType = fileType;
	};

	public CommonFile(String commonFileGroupId, FileType fileType) {
		this.commonFileGroupId = commonFileGroupId;
		this.fileType = fileType;
	};

	/**
	 * 파일 관리 아이디
	 */
	private String commonFileGroupId;

	/**
	 * 파일 SEQ
	 */
	private long commonFileSeq;

	/**
	 * 원본 파일명
	 */
	private String fileOriginName;

	/**
	 * 마스크킹 처리된 파일명
	 */
	private String fileMaskName;

	/**
	 * 파일 확장자.
	 */
	private String fileExtension;

	/**
	 * 파일 사이즈.
	 */
	private long fileSize;

	/**
	 * 파일 상대 경로
	 */
	private String fileRelativePath;

	/**
	 * 파일 절대 경로
	 */
	private String fileAbsolutePath;

	/**
	 * 파일 등록자
	 */
	private String fileInsertUserId;

	/**
	 * 파일 등록자 이름
	 */
	private String fileInsertUserName;

	/**
	 * 파일 등록일시
	 */
	private String fileInsertDatetime;



	/**
	 * 파일 다운로드 횟수
	 */
	private long fileDownloadCount;

	/**
	 * 파일 타입
	 */
	private FileType fileType = FileType.ALL_FILE;

	/**
	 * 파일 상태
	 */
	private FileStatus fileStatus = FileStatus.COMPLETE_INSERT;

	/**
	 * file 관련 메세지
	 */
	private CheckMessage checkMessage = new CheckMessage();



	public String getCommonFileGroupId() {
		return commonFileGroupId;
	}

	public void setCommonFileGroupId(String commonFileGroupId) {
		this.commonFileGroupId = commonFileGroupId;
	}

	public long getCommonFileSeq() {
		return commonFileSeq;
	}

	public void setCommonFileSeq(long commonFileSeq) {
		this.commonFileSeq = commonFileSeq;
	}

	public String getFileOriginName() {
		return fileOriginName;
	}

	public void setFileOriginName(String fileOriginName) {
		this.fileOriginName = fileOriginName;
	}

	public String getFileMaskName() {
		return fileMaskName;
	}

	public void setFileMaskName(String fileMaskName) {
		this.fileMaskName = fileMaskName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileRelativePath() {
		return fileRelativePath;
	}

	public void setFileRelativePath(String fileRelativePath) {
		this.fileRelativePath = fileRelativePath;
	}

	public String getFileAbsolutePath() {
		return fileAbsolutePath;
	}

	public void setFileAbsolutePath(String fileAbsolutePath) {
		this.fileAbsolutePath = fileAbsolutePath;
	}

	public String getFileInsertUserId() {
		return fileInsertUserId;
	}

	public void setFileInsertUserId(String fileInsertUserId) {
		this.fileInsertUserId = fileInsertUserId;
	}

	public String getFileInsertUserName() {
		return fileInsertUserName;
	}

	public void setFileInsertUserName(String fileInsertUserName) {
		this.fileInsertUserName = fileInsertUserName;
	}

	public String getFileInsertDatetime() {
		return fileInsertDatetime;
	}

	public void setFileInsertDatetime(String fileInsertDatetime) {
		this.fileInsertDatetime = fileInsertDatetime;
	}

	public long getFileDownloadCount() {
		return fileDownloadCount;
	}

	public void setFileDownloadCount(long fileDownloadCount) {
		this.fileDownloadCount = fileDownloadCount;
	}

	public FileStatus getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(FileStatus fileStatus) {
		this.fileStatus = fileStatus;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public CheckMessage getCheckMessage() {
		return checkMessage;
	}

	public void setCheckMessage(CheckMessage checkMessage) {
		this.checkMessage = checkMessage;
	}



	/**
	 * @author 서영석
	 * @since 2021.10.21
	 *
	 * 파일 등록 및 삭제 과정에 필요한 상태 값을 정의한 상수 Class 입니다.
	 * 'DB등록 전', 'DB등록 완료', 'DB삭제 전' 순서입니다.
	 */
	public enum FileStatus {
		BEFORE_INSERT("DB등록 전")
		, COMPLETE_INSERT("DB등록 완료")
		, BEFORE_DELETE("DB삭제 전");

		private FileStatus (String status) {
			this.status = status;
		}

		/**
		 * 한글값
		 */
		final private String status;

		/**
		 * 한글값 조회
		 */
		public String getValue () {
			return status;
		}
	}

	/**
	 * @author 서영석
	 * @since 2019.11.13
	 *
	 * 등록되는 파일 타입과, 해당 파일 타입의 등록가능한 확장자를 정의한 상수 Class 입니다.
	 * 첨부파일, 이미지파일, 데이터 셋 파일 순서입니다.
	 */
	public enum FileType {
		ALL_FILE(Arrays.asList("bmp","jpg","gif","png","jpeg","bmp","wma","avi","wav","mp3","mp4","mpeg","wmv","asf","pdf","ppt","pptx","docx","xls","xlsx","csv","hwp","txt","doc","zip", "json", "xml"))
		, IMG_FILE(Arrays.asList("bmp","jpg","gif","png","jpeg"))
		, DATASET_FILE(Arrays.asList("xls","xlsx","csv"))
		, DATA_FILE(Arrays.asList("xls","xlsx"));

		private FileType(List<String> extensions) { this.extensions = extensions;
		}

		/**
		 * 확장자 목록
		 */
		final private List<String> extensions;

		/**
		 * 확장자 목록 조회
		 */
		public List<String> getExtensions () {
			return extensions;
		}
	}

	/**
	 * @author 서영석
	 * @since 2019.11.13
	 *
	 * 확장자를 포함한 파일 Full 경로 (fileAbsolutePath + '/' + fileMaskName + '.' + fileExtension)
	 *
	 */
	public String getFileFullPath() {
		return this.fileAbsolutePath + File.separator + this.fileMaskName + "." + this.fileExtension;
	}
}
