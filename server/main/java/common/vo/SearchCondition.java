package common.vo;

import common.util.StringUtil;

/**
 * @author 서영석
 * @since 2021.12.06
 *
 * 검색 조건 VO 입니다.
 */
public class SearchCondition {

	/**
	 * 현재 검색 조건 연산자 (OR, AND) (주의 - 검색 조건 앞에 붙음)
	 *
	 * Search class의 searchOperator변수보다 우선순위를 가짐
	 *
	 * 순서: 0
	 */
	private String operator = "AND";

	/**
	 * 검색 조건 앞에 붙이는 문자 ex) 여는 괄호 '('
	 *
	 * 순서: 1
	 */
	private String prefix = "";

	/**
	 * 검색 컬럼
	 *
	 * 순서: 2
	 */
	private String column = "";

	/**
	 * 검색어 비교 연산자 타입 ('=', '!=', '<>', '>', '>=', '<', '<=' 'like', 'is <not> null')
	 *
	 * 순서: 3
	 */
	private String comparisonType = "=";

	/**
	 * 검색값
	 *
	 * 순서: 4
	 */
	private Object value = null;

	/**
	 * 검색 조건 뒤에 붙이는 문자 ex) 닫는 괄호 ')'
	 *
	 * 순서: 5
	 */
	private String suffix = "";


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

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getComparisonType() {
		return comparisonType;
	}

	public void setComparisonType(String comparisonType) {
		comparisonType = comparisonType.toUpperCase();
		if (comparisonType.equals("=")
			|| comparisonType.equals("!=") || comparisonType.equals("<>")
			|| comparisonType.equals(">") || comparisonType.equals(">=")
			|| comparisonType.equals("<") || comparisonType.equals("<=")
			|| comparisonType.equals("LIKE") || comparisonType.equals("IS")
			|| comparisonType.equals("IS NOT")) {
			this.comparisonType = comparisonType;
		} else {
			//걍 냅둡시다.
		}
	}


	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
		operator = operator.toUpperCase();
		if (operator.equals("AND") || operator.equals("OR")) {
			this.operator = operator;
		} else {
			//걍 냅둡시다.
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}