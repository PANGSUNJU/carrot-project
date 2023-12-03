package common.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 서영석
 * @since 2021.12.06
 *
 * 그룹 정보 VO입니다.
 */
public class Org {

	/**
	 * 그룹 ID
	 */
	private String orgId;

	/**
	 * 부모 그룹 ID
	 */
	private String orgParentId;

	/**
	 * 그룹명
	 */
	private String orgName;

	/**
	 * 그룹 설명
	 */
	private String orgComment;

	/**
	 * 그룹 Level
	 */
	private int orgDepth;

	/**
	 * 배치 순서
	 */
	private int orgOrder;

	/**
	 * 등록자
	 */
	private String orgInsertUserId;

	/**
	 * 등록일시
	 */
	private String orgInsertDatetime;

	/**
	 * 수정자
	 */
	private String orgUpdateUserId;

	/**
	 * 수정일시
	 */
	private String orgUpdateDatetime;

	/**
	 * 사용여부
	 */
	private String orgIsUse;

	/**
	 * 그룹의 권한's
	 */
	private List<String> orgAuthotitise = new ArrayList<String>();

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgParentId() {
		return orgParentId;
	}

	public void setOrgParentId(String orgParentId) {
		this.orgParentId = orgParentId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgComment() {
		return orgComment;
	}

	public void setOrgComment(String orgComment) {
		this.orgComment = orgComment;
	}

	public int getOrgDepth() {
		return orgDepth;
	}

	public void setOrgDepth(int orgDepth) {
		this.orgDepth = orgDepth;
	}

	public int getOrgOrder() {
		return orgOrder;
	}

	public void setOrgOrder(int orgOrder) {
		this.orgOrder = orgOrder;
	}

	public String getOrgInsertUserId() {
		return orgInsertUserId;
	}

	public void setOrgInsertUserId(String orgInsertUserId) {
		this.orgInsertUserId = orgInsertUserId;
	}

	public String getOrgInsertDatetime() {
		return orgInsertDatetime;
	}

	public void setOrgInsertDatetime(String orgInsertDatetime) {
		this.orgInsertDatetime = orgInsertDatetime;
	}

	public String getOrgUpdateUserId() {
		return orgUpdateUserId;
	}

	public void setOrgUpdateUserId(String orgUpdateUserId) {
		this.orgUpdateUserId = orgUpdateUserId;
	}

	public String getOrgUpdateDatetime() {
		return orgUpdateDatetime;
	}

	public void setOrgUpdateDatetime(String orgUpdateDatetime) {
		this.orgUpdateDatetime = orgUpdateDatetime;
	}

	public String getOrgIsUse() {
		return orgIsUse;
	}

	public void setOrgIsUse(String orgIsUse) {
		this.orgIsUse = orgIsUse;
	}

	public List<String> getOrgAuthotitise() {
		return orgAuthotitise;
	}

	public void setOrgAuthotitise(List<String> orgAuthotitise) {
		this.orgAuthotitise = orgAuthotitise;
	}

}
