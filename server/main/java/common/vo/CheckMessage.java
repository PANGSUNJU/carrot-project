package common.vo;

/**
 * @author 서영석
 * @since 2019.11.14
 *
 * 데이터 체크, 유효성 검사 등과 같이 검사와 관련된 변수를 정의한 Class 입니다.
 */
public class CheckMessage {

	public CheckMessage() {}

	public CheckMessage(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public CheckMessage(String message) {
		this.message = message;
	}

	public CheckMessage(boolean isSuccess, String message) {
		this.isSuccess = isSuccess;
		this.message = message;
	}

	public CheckMessage(boolean isSuccess, String message, int status) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.status = status;
	}

	public CheckMessage(boolean isSuccess, String message, String error) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.error = error;
	}

	/**
	 * 체크 완료(사용가능 or 성공) 여부
	 */
	private boolean isSuccess;

	/**
	 * 체크한 상태의 메세지값
	 */
	private String message;

	/**
	 * 에러 메세지
	 */
	private String error;

	/**
	 * 체크한 상태의 상태값 -> 상황마다 다름
	 */
	private int status;

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
