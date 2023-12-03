package common.vo;

import common.util.StringUtil;

/**
 * @author 서영석
 * @since 2021.12.06
 * 
 * 검색의 정렬 순서 관련 VO 입니다.
 */
public class SearchOrder {

	/**
	 * 정렬할 컬럼명
	 */
	private String column = "";
	
	/**
	 * 정렬 타입('ASC', 'DESC')
	 */
	private String orderType = "ASC";

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		if (StringUtil.isEmpty(column) == true) {
			this.column = "";
		} else {
			this.column = column;
		}
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		orderType = orderType.toUpperCase();
		if (orderType.equals("ASC") || orderType.equals("DESC")) {
			this.orderType = orderType;
		} else {
			//걍 냅둡시다.
		}
	}
	
}





