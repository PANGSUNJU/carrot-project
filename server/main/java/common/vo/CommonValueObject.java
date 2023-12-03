package common.vo;

import common.util.CryptoUtil;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 서영석
 * @since 2021.09.28
 *
 * 공통 Value Object
 *
 * Object or List 데이터 활용시, 사용 (int, String, boolean... 등에는 사용할 필요 없음)
 */
public class CommonValueObject {

	public final static String DEFAULT_FILE_GROUP_NAME = "commonFileList";


	/**
	 * 검색 객체
	 */
	private Search search = new Search();

	/**
	 * object Data 객체
	 *
	 * 활용 방법 (규칙이니까 꼭 치키세요)
	 *
	 * 1. 단일 등록(Insert)
	 * 2. 단일 수정(Update)
	 * 3. 단일 삭제(Delete)
	 * 4. 상세 조회(SelectOne)
	 *
	 * 위의 경우를 제외하고 사용하면 안됨
	 */
	private LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();

	/**
	 * list Data 객체
	 *
	 * 1. 다중 등록(Muliple Insert)
	 * 2. 다중 수정(Muliple Update)
	 * 3. 다중 삭제(Muliple Delete)
	 * 4. 목록 조회(SelectList)
	 */
	private List<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();

	/**
	 * 암호화 대상 Key's
	 *
	 * data, dataList에 사용
	 */
	private List<String> encodingTargetKeys = new ArrayList<String>();


	/**
	 * 공통 파일을 담을 객체
	 *
	 */
	private HashMap<String, List<CommonFile>> commonFileGroup = new HashMap<String, List<CommonFile>>();

	/**
	 * 메세지
	 */
	private CheckMessage checkMessage = new CheckMessage();

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public LinkedHashMap<String, Object> getData() {
		return data;
	}

	public void setData(LinkedHashMap<String, Object> data) {
		this.data = data;
	}

	public List<LinkedHashMap<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<LinkedHashMap<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public List<String> getEncodingTargetKeys() {
		return encodingTargetKeys;
	}

	public void setEncodingTargetKeys(List<String> encodingTargetKeys) {
		this.encodingTargetKeys = encodingTargetKeys;
	}

	public HashMap<String, List<CommonFile>> getCommonFileGroup() {
		if (commonFileGroup.get(DEFAULT_FILE_GROUP_NAME) == null) {
			commonFileGroup.put(DEFAULT_FILE_GROUP_NAME, new ArrayList<CommonFile>(1));
		}
		return commonFileGroup;
	}

	public void setCommonFileGroup(HashMap<String, List<CommonFile>> commonFileGroup) {
		this.commonFileGroup = commonFileGroup;
	}

	public CheckMessage getCheckMessage() {
		return checkMessage;
	}

	public void setCheckMessage(CheckMessage checkMessage) {
		this.checkMessage = checkMessage;
	}

	/**
	 * @author 서영석
	 * @since 2021.11.10
	 *
	 * RSA 암호화 디코딩 (data)
	 */
	public void dataDecodingByRsa () {
		//session in RSA 개인키 (조회이후 session에서 암호화 Key 삭제)
		PrivateKey privateKey = CryptoUtil.getPrivateKeyInSession();
		//데이터 복호화 (RSA 비대칭키 암호화 활용)
		for (int i = 0; i < this.encodingTargetKeys.size(); i++) {
			String key = this.encodingTargetKeys.get(i);//암호화 대상 Key
			String encodingValue = (String) this.data.get(key);//암호화된 값
			String decodingValue = null;
			try {
				decodingValue = CryptoUtil.decodingByRsa(encodingValue, privateKey);//복호화된 값
			} catch (Exception e) {
				decodingValue = encodingValue;
				e.printStackTrace();
			}
			//System.out.println("key: " + key + ", encodingValue: " + encodingValue + ", decodingValue: " + decodingValue);
			this.data.put(key, decodingValue);//복호화된 값 set
		}
	}

	/**
	 * @author 서영석
	 * @since 2021.11.10
	 *
	 * RSA 암호화 디코딩 (dataList)
	 */
	public void dataListDecodingByRsa () {
		//session in RSA 개인키 (조회이후 session에서 암호화 Key 삭제)
		PrivateKey privateKey = CryptoUtil.getPrivateKeyInSession();
		//데이터 복호화 (RSA 비대칭키 암호화 활용)
		for (int i = 0; i < this.dataList.size(); i++) {
			for (int j = 0; j < this.encodingTargetKeys.size(); j++) {
				String key = this.encodingTargetKeys.get(j);//암호화 대상 Key
				String encodingValue = (String) this.dataList.get(i).get(key);//암호화된 값
				String decodingValue = null;
				try {
					decodingValue = CryptoUtil.decodingByRsa(encodingValue, privateKey);//복호화된 값
				} catch (Exception e) {
					decodingValue = encodingValue;
					e.printStackTrace();
				}
				//System.out.println("key: " + key + ", encodingValue: " + encodingValue + ", decodingValue: " + decodingValue);
				this.dataList.get(i).put(key, decodingValue);//복호화된 값 set
			}
		}
	}

	/**
	 * @author 서영석
	 * @since 2021.11.10
	 *
	 * XSS 공격 방지
	 *
	 */
	public void htmlFilter (Object targetObject, Class<?> targetObjectClass) {

	}

	/**
	 * @author 서영석
	 * @since 2021.11.10
	 *
	 * SQL Injection 공격 방지
	 *
	 */
	public void sqlFilter (Object targetObject, Class<?> targetObjectClass) {

	}

}
