package common.vo;

import java.util.LinkedHashMap;
import java.util.Map;


public class SystemCode {

	//191자 => mariaDB에서 indexing가능한 최대 크기 765Byte/한글 4Byte(utf8mb4)
	public final static int DEFAULT_VARCHAR_SIZE = 191;

	//mariaDB에서 indexing가능한 varchar 최대 크기 765Byte
	public final static int MAX_VARCHAR_SIZE = 765;

	//프로그램 이름
	public final static String PROGRAM_TITLE = "도로교통 위험분석시스템";

	//프로그램 이름 앞에 붙는 문자열
	public final static String PROGRAM_TITLE_PREFIX = "";

	/**
	 * @author 서영석
	 * @since 2020.12.24
	 *
	 * 시스템의 사용자 권한을 정의한 상수 Class 입니다.
	 */
	public enum AuthorityType {
		ROLE_ADMIN("관리자")
		, ROLE_USER("사용자")
		, permitAll("API연계 수집");

		private AuthorityType (String value) {
			this.value = value;
		}

		/**
		 * 한글값
		 */
		final private String value;

		/**
		 * 한글값 조회
		 */
		public String getValue () {
			return value;
		}

		/**
		 * 데이터 수집 타입 목록 조회
		 */
		public static Map<AuthorityType, String> getAuthorityTypeList () {
			Map<AuthorityType, String> info = new LinkedHashMap<AuthorityType, String>();
			AuthorityType[] array = AuthorityType.values();
			for (int i = 0; i < array.length; i++) {
				info.put(array[i], array[i].getValue());
			}
			return info;
		}
	}

	/**
	 * @author 서영석
	 * @since 2020.12.24
	 *
	 * 시스템의 사용자 권한을 정의한 상수 Class 입니다.
	 */
	public enum TrafficAnalysisResultType {
		CAR_TO_CAR("차 대 차")
		, CAR_TO_PEOPLE("차 대 사람")
		, ROAD_CRACK("도로 크랙")
		, ROAD_HOLE("도로 웅덩이");

		private TrafficAnalysisResultType (String value) {
			this.value = value;
		}

		/**
		 * 한글값
		 */
		final private String value;

		/**
		 * 한글값 조회
		 */
		public String getValue () {
			return value;
		}

		/**
		 * 데이터 수집 타입 목록 조회
		 */
		public static Map<TrafficAnalysisResultType, String> getTrafficAnalysisResultTypeList () {
			Map<TrafficAnalysisResultType, String> info = new LinkedHashMap<TrafficAnalysisResultType, String>();
			TrafficAnalysisResultType[] array = TrafficAnalysisResultType.values();
			for (int i = 0; i < array.length; i++) {
				info.put(array[i], array[i].getValue());
			}
			return info;
		}

		public static TrafficAnalysisResultType codeToType (String code) {
			if (code != null && code.length() > 0) {
				if (code.equals("0")) {
					return ROAD_CRACK;
				} else if (code.equals("1")) {
					return ROAD_HOLE;
				} else if (code.equals("2")) {
					return CAR_TO_CAR;
				} else if (code.equals("3")) {
					return CAR_TO_PEOPLE;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	}

	/**
	 * @author 서영석
	 * @since 2020.12.24
	 *
	 * OpenAPI의 서비스 타입을 정의한 상수 Class 입니다.
	 */
	public enum APIType {
		REST
		, RSS
		, SOAP;

		/**
		 * 데이터 수집 타입 목록 조회
		 */
		public static Map<APIType, String> getAPITypeList () {
	    	Map<APIType, String> info = new LinkedHashMap<APIType, String>();
	    	APIType[] array = APIType.values();
	    	for (int i = 0; i < array.length; i++) {
	    		info.put(array[i], array[i].toString());
	    	}
	    	return info;
		}
	}

	/**
	 * @author 서영석
	 * @since 2020.12.24
	 *
	 * OpenAPI의 데이터 포맷 타입을 정의한 상수 Class 입니다.
	 */
	public enum APIDataFormatType {
		JSON
		, XML;

		/**
		 * 데이터 수집 타입 목록 조회
		 */
		public static Map<APIDataFormatType, String> getAPIDataFormatTypeList () {
	    	Map<APIDataFormatType, String> info = new LinkedHashMap<APIDataFormatType, String>();
	    	APIDataFormatType[] array = APIDataFormatType.values();
	    	for (int i = 0; i < array.length; i++) {
	    		info.put(array[i], array[i].toString());
	    	}
	    	return info;
		}
	}

	/**
	 * @author 서영석
	 * @since 2019.11.13
	 *
	 * 업로드 파일에 대한 저장 폴더 경로를 정의한 상수 Class
	 * MAIN-메인 저장소, TEMP-임시 저장소
	 */
	public enum FileUploadPath {
		/* 관리 시스템 파일 업로드 경로 */
		//상대 경로 저장소(프로젝트 내부 -> Windows, Linux 구분 없음)
		MAIN_RELATIVE_PATH("\\resources\\common\\file\\upload\\main", "/resources/common/file/upload/main")
		, TEMP_RELATIVE_PATH("\\resources\\common\\file\\upload\\temp", "/resources/common/file/upload/temp")
		//절대 경로 저장소(Windows, Linux)
		, MAIN_ABSOLUTE_PATH("C:\\bigdata\\common\\upload\\main", "/bigdata/common/upload/main")
		, TEMP_ABSOLUTE_PATH("C:\\bigdata\\common\\upload\\temp", "/bigdata/common/upload/temp");

		private FileUploadPath(String windowFileUploadPath, String linuxFileUploadPath) {
			this.windowFileUploadPath = windowFileUploadPath;
			this.linuxFileUploadPath = linuxFileUploadPath;
		}

		final private String windowFileUploadPath;

		final private String linuxFileUploadPath;

		public String getWindowFileUploadPath() {
			return windowFileUploadPath;
		}

		public String getLinuxFileUploadPath() {
			return linuxFileUploadPath;
		}

		/**
		 * OS 타입별 파일 업로드 경로 return
		 */
		public String getOSFileUploadPath() {
			if (System.getProperty("os.name").indexOf("Windows") > -1) {
	        	return windowFileUploadPath;
	        } else {
	        	return linuxFileUploadPath;
	        }
		}
	}


	/**
	 * @author 서영석
	 * @since 2019.11.13
	 *
	 * 데이터 수집 타입을 정의한 상수 Class 입니다.
	 */
	public enum CollectionType {
		FILE("파일 수집")
		, DB("DB연계 수집")
		, API("API연계 수집")
		, CRAWLING("웹데이터 수집")
		, SOCKET("소켓통신 수집");

		private CollectionType (String value) {
			this.value = value;
		}

		/**
		 * 한글값
		 */
		final private String value;

		/**
		 * 한글값 조회
		 */
		public String getValue () {
			return value;
		}

		/**
		 * 데이터 수집 타입 목록 조회
		 */
		public static Map<CollectionType, String> getCollectionTypeList () {
	    	Map<CollectionType, String> info = new LinkedHashMap<CollectionType, String>();
	    	CollectionType[] array = CollectionType.values();
	    	for (int i = 0; i < array.length; i++) {
	    		info.put(array[i], array[i].getValue());
	    	}
	    	return info;
		}
	}

	/**
	 * @author 서영석
	 * @since 2019.12.03
	 *
	 * 스케줄 작업 상태 타입을 정의한 상수 Class 입니다.
	 */
	public enum TaskStatus {
		START("작동 시작")
		, COMPLETE("작동 완료")
		, ERROR("작동 오류");

		private TaskStatus (String message) {
			this.message = message;
		}

		/**
		 * 한글 메세지
		 */
		final private String message;

		/**
		 * 한글 메세지 조회
		 */
		public String getMessage () {
			return message;
		}

		/**
		 * 스케줄 상태 타입 목록 조회
		 */
		public static Map<TaskStatus, String> getTaskStatusList () {
	    	Map<TaskStatus, String> info = new LinkedHashMap<TaskStatus, String>();
	    	TaskStatus[] array = TaskStatus.values();
	    	for (int i = 0; i < array.length; i++) {
	    		info.put(array[i], array[i].getMessage());
	    	}
	    	return info;
		}
	}

	/**
	 * @author 서영석
	 * @since 2019.12.03
	 *
	 * 날짜 통계 관련 타입을 정의한 상수 Class 입니다.
	 */
	public enum DateStatsGroupType {
		DATE_FLOW("시계열")
		, DATE_CYCLE("주기별");//통계 데이터 조회 총 행 수: 31(1, 2, 3, ..., 31)

		private DateStatsGroupType (String message) {
			this.message = message;
		}

		/**
		 * 한글 메세지
		 */
		final private String message;

		/**
		 * 한글 메세지 조회
		 */
		public String getMessage () {
			return message;
		}

		/**
		 * 날짜 통계 관련 타입 목록 조회
		 */
		public static Map<DateStatsGroupType, String> getDateStatusType () {
			Map<DateStatsGroupType, String> info = new LinkedHashMap<DateStatsGroupType, String>();
			DateStatsGroupType[] array = DateStatsGroupType.values();
			for (int i = 0; i < array.length; i++) {
				info.put(array[i], array[i].getMessage());
			}
			return info;
		}
	}
	/**
	 * @author 서영석
	 * @since 2019.12.03
	 *
	 * 날짜 통계 관련 타입을 정의한 상수 Class 입니다.
	 */
	public enum DateStatsType {
		YEAR("년도별(시계열)")//시계열도 되고 주기도됨 (최상위 Date이기 때문)

		, DATE_FLOW_QUARTER("분기별(시계열)")
		, DATE_FLOW_MONTH("월별(시계열)")
		, DATE_FLOW_WEEK("주별(시계열)")
		, DATE_FLOW_DAY("일별(시계열)")

		, DATE_CYCLE_QUARTER("분기별(주기별)")//통계 데이터 조회 총 행 수: 4(1분기, 2분기, 3분기, 4분기)
		, DATE_CYCLE_MONTH("월별(주기별)")//통계 데이터 조회 총 행 수: 12(1월, 2월, 3월, ..., 12월)
		, DATE_CYCLE_WEEK("요일별(주기별)")//통계 데이터 조회 총 행 수: 7(월, 화, 수, ..., 일)
		, DATE_CYCLE_DAY("일별(주기별)")//통계 데이터 조회 총 행 수: 31(1, 2, 3, ..., 31)
		, DATE_CYCLE_TIME("시간별(주기별)");//통계 데이터 조회 총 행 수: 31(1, 2, 3, ..., 31)

		private DateStatsType (String message) {
			this.message = message;
		}

		/**
		 * 한글 메세지
		 */
		final private String message;

		/**
		 * 한글 메세지 조회
		 */
		public String getMessage () {
			return message;
		}

		/**
		 * 날짜 통계 관련 타입 목록 조회
		 */
		public static Map<DateStatsType, String> getDateStatusType () {
			Map<DateStatsType, String> info = new LinkedHashMap<DateStatsType, String>();
			DateStatsType[] array = DateStatsType.values();
			for (int i = 0; i < array.length; i++) {
				info.put(array[i], array[i].getMessage());
			}
			return info;
		}
	}


}
