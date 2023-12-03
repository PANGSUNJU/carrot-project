package common.vo;

import common.util.AuthUtil;
import common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 서영석
 * @since 2021.12.06
 *
 * 공통 검색 VO 입니다.(상속받으세요)
 */
public class Search extends User {

	public Search () {
		if (AuthUtil.getLoginUser() != null) {
			User loginUser = AuthUtil.getLoginUser();
			super.setUserId(loginUser.getUserId());
			super.setLoginId(loginUser.getLoginId());
			super.setName(loginUser.getName());
			super.setEmail(loginUser.getEmail());
			super.setGender(loginUser.getGender());
			super.setBirthDate(loginUser.getBirthDate());
			super.setMobile(loginUser.getMobile());
			super.setAddress(loginUser.getAddress());
			super.setDetailAddress(loginUser.getDetailAddress());
			super.setRegistrationDatetime(loginUser.getRegistrationDatetime());
			super.setWithdrawDatetime(loginUser.getWithdrawDatetime());
			super.setIsWithdraw(loginUser.getIsWithdraw());
			super.setLoginFailedCount(loginUser.getLoginFailedCount());
			super.setIsAccountLock(loginUser.getIsAccountLock());
			super.setUserAuthotities(loginUser.getUserAuthotities());
		}
	}

	/**
	 * 검색 목록's
	 */
	private List<SearchCondition> searchConditionList = new ArrayList<SearchCondition>();

	/**
	 * 정렬 순서's
	 */
	private List<SearchOrder> searchOrderList = new ArrayList<SearchOrder>();

	/**
	 * 현재 페이지
	 */
	private int currentPage = 1;

	/**
	 * 한 페이지당 조회될 데이터 수
	 */
	private int perPage = 10;

	/**
	 * 총 개시물 수
	 */
	private int totalRows = 0;

	/**
	 * 페이징 시작할 row 넘버
	 */
	private int startRowNumber = 0;


	/**
	 * 페이징 사용 유무
	 */
	private boolean isUsePaging = true;


	public List<SearchCondition> getSearchConditionList() {
		return searchConditionList;
	}

	public void setSearchConditionList(List<SearchCondition> searchConditionList) {
		this.searchConditionList = searchConditionList;
	}

	public List<SearchOrder> getSearchOrderList() {
		return searchOrderList;
	}

	public void setSearchOrderList(List<SearchOrder> searchOrderList) {
		this.searchOrderList = searchOrderList;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getStartRowNumber() {
		return (currentPage - 1) * perPage;
	}

	public void setStartRowNumber(int startRowNumber) {
		this.startRowNumber = startRowNumber;
	}

	public boolean getIsUsePaging() {
		return isUsePaging;
	}

	public void setIsUsePaging(boolean isUsePaging) {
		this.isUsePaging = isUsePaging;
	}

	/**
	 * 검색 목록에 데이터 추가하기
	 *
	 * @param column : 검색 컬럼명
	 * @param value : 검색어
	 */
	public void addSearchCondition (String column, String value) throws Exception {
		if (StringUtil.isEmpty(column) == false) {
			SearchCondition searchCondition = new SearchCondition();
			searchCondition.setColumn(column);
			searchCondition.setValue(value);

			searchConditionList.add(searchCondition);
		} else {
			throw new Exception("addSearchCondition 메서드 에러 : 검색 조건의 컬럼명 값이 'null' 또는 '공백'입니다.");
		}
	}

	/**
	 * 검색 목록에 데이터 추가하기
	 *
	 * @param operator : 현재 검색 조건 연산자 (AND, OR)
	 * @param column : 검색 컬럼명
	 * @param comparisonType : 검색어 비교 연산자 타입 ('=', 'like', 'is <not> null')
	 * @param value : 검색어
	 */
	public void addSearchCondition (String operator, String column, String comparisonType, String value) throws Exception {
		if (StringUtil.isEmpty(column) == false) {
			SearchCondition searchCondition = new SearchCondition();
			searchCondition.setOperator(operator);
			searchCondition.setColumn(column);
			searchCondition.setComparisonType(comparisonType);
			searchCondition.setValue(value);

			searchConditionList.add(searchCondition);
		} else {
			throw new Exception("addSearchCondition 메서드 에러 : 검색 조건의 컬럼명 값이 'null' 또는 '공백'입니다.");
		}
	}
	/**
	 * 검색 목록에 데이터 set (같은 컬럼명이 존재 할 시 -> value 대체 / 없을 시 -> 검색 목록에 추가)
	 *
	 * @param column : 검색 컬럼명
	 */
	public void setSearchCondition (String column, String value) throws Exception {
		boolean isFind = false;
		for (int i = 0; i < searchConditionList.size(); i++) {
			if (searchConditionList.get(i).getColumn().equals(column) == true) {
				searchConditionList.get(i).setColumn(column);
				searchConditionList.get(i).setValue(value);
				isFind = true;
				break;
			}
		}

		if (isFind == false) {
			addSearchCondition(column, value);
		}
	}
	/**
	 * 검색 목록에 데이터 set (같은 컬럼명이 존재 할 시 -> value 대체 / 없을 시 -> 검색 목록에 추가)
	 *
	 * @param column : 검색 컬럼명
	 */
	public void setSearchCondition (String operator, String column, String comparisonType, String value) throws Exception {
		boolean isFind = false;
		for (int i = 0; i < searchConditionList.size(); i++) {
			if (searchConditionList.get(i).getColumn().equals(column) == true) {
				searchConditionList.get(i).setOperator(operator);
				searchConditionList.get(i).setColumn(column);
				searchConditionList.get(i).setComparisonType(comparisonType);
				searchConditionList.get(i).setValue(value);
				isFind = true;
				break;
			}
		}

		if (isFind == false) {
			addSearchCondition(operator, column, comparisonType, value);
		}
	}
	/**
	 * 검색 목록에 데이터 삭제하기
	 *
	 * @param column : 검색 컬럼명
	 */
	public void removeSearchCondition (String column) throws Exception {
		for (int i = 0; i < searchConditionList.size(); i++) {
			if (searchConditionList.get(i).getColumn().equals(column) == true) {
				searchConditionList.remove(i);
				break;
			}
		}
	}

	/**
	 * 검색 정렬 목록에 데이터 추가하기
	 *
	 * @param column : 검색 컬럼명
	 */
	public void addSearchOrder (String column) throws Exception {
		if (StringUtil.isEmpty(column) == false) {
			SearchOrder searchOrder = new SearchOrder();
			searchOrder.setColumn(column);

			searchOrderList.add(searchOrder);
		} else {
			throw new Exception("addSearchOrder 메서드 에러 : 정렬 조건의 컬럼명 값이 'null' 또는 '공백'입니다.");
		}
	}

	/**
	 * 검색 목록에 데이터 추가하기
	 *
	 * @param column : 검색 컬럼명
	 * @param orderType : 정렬 타입('ASC', 'DESC')
	 */
	public void addSearchOrder (String column, String orderType) throws Exception {
		if (StringUtil.isEmpty(column) == false) {
			SearchOrder searchOrder = new SearchOrder();
			searchOrder.setColumn(column);
			searchOrder.setOrderType(orderType);

			searchOrderList.add(searchOrder);
		} else {
			throw new Exception("addSearchOrder 메서드 에러 : 정렬 조건의 컬럼명 값이 'null' 또는 '공백'입니다.");
		}
	}
	/**
	 * 검색 목록에 데이터 set (같은 컬럼명이 존재 할 시 -> value 대체 / 없을 시 -> 검색 목록에 추가)
	 *
	 * @param column : 검색 컬럼명
	 */
	public void setSearchOrder (String column) throws Exception {
		boolean isFind = false;
		for (int i = 0; i < searchOrderList.size(); i++) {
			if (searchOrderList.get(i).getColumn().equals(column) == true) {
				searchOrderList.get(i).setColumn(column);
				isFind = true;
				break;
			}
		}

		if (isFind == false) {
			addSearchOrder(column);
		}
	}
	/**
	 * 검색 목록에 데이터 set (같은 컬럼명이 존재 할 시 -> value 대체 / 없을 시 -> 검색 목록에 추가)
	 *
	 * @param column : 검색 컬럼명
	 */
	public void setSearchOrder (String column, String orderType) throws Exception {
		boolean isFind = false;
		for (int i = 0; i < searchOrderList.size(); i++) {
			if (searchOrderList.get(i).getColumn().equals(column) == true) {
				searchOrderList.get(i).setColumn(column);
				searchOrderList.get(i).setOrderType(orderType);
				isFind = true;
				break;
			}
		}

		if (isFind == false) {
			addSearchOrder(column, orderType);
		}
	}
	/**
	 * 검색 목록에 데이터 삭제하기
	 *
	 * @param column : 검색 컬럼명
	 */
	public void removeSearchOrder (String column) throws Exception {
		for (int i = 0; i < searchOrderList.size(); i++) {
			if (searchOrderList.get(i).getColumn().equals(column) == true) {
				searchOrderList.remove(i);
				break;
			}
		}
	}
}




