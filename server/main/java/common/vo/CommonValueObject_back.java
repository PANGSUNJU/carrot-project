package common.vo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 서영석
 * @since 2021.09.28
 * 
 * 공통 Value Object
 */
public class CommonValueObject_back extends LinkedHashMap<String, Object> {

	/**
	 * Search객체 생성 여부 (Defalut:true)
	 */
	private boolean isSearchCreate = true;
	
	/**
     * Constructs an empty insertion-ordered {@code LinkedHashMap} instance
     * with the specified initial capacity and load factor.
     *
     * @param  initialCapacity the initial capacity
     * @param  loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive
     */
    public CommonValueObject_back(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        init();
    }

    /**
     * Constructs an empty insertion-ordered {@code LinkedHashMap} instance
     * with the specified initial capacity and a default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity
     * @throws IllegalArgumentException if the initial capacity is negative
     */
    public CommonValueObject_back(int initialCapacity) {
        super(initialCapacity);
        init();
    }

    /**
     * Constructs an empty insertion-ordered {@code LinkedHashMap} instance
     * with the default initial capacity (16) and load factor (0.75).
     */
    public CommonValueObject_back() {
        super();
        init();
    }
    
    /**
     * 커스텀
     */
    public CommonValueObject_back(boolean isSearchCreate) {
        super();
        
        this.isSearchCreate = isSearchCreate;
        init();
    }

    /**
     * Constructs an insertion-ordered {@code LinkedHashMap} instance with
     * the same mappings as the specified map.  The {@code LinkedHashMap}
     * instance is created with a default load factor (0.75) and an initial
     * capacity sufficient to hold the mappings in the specified map.
     *
     * @param  m the map whose mappings are to be placed in this map
     * @throws NullPointerException if the specified map is null
     */
    public CommonValueObject_back(Map<String, Object> m) {
        super();
        init();
    }
    
    /**
     * Constructs an empty {@code LinkedHashMap} instance with the
     * specified initial capacity, load factor and ordering mode.
     *
     * @param  initialCapacity the initial capacity
     * @param  loadFactor      the load factor
     * @param  accessOrder     the ordering mode - {@code true} for
     *         access-order, {@code false} for insertion-order
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive
     */
    public CommonValueObject_back(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor);
        init();
    }
    
    
    
    
    
    /** 
	 * CommonValueObject 초기화 (Search 객체 생성)
	 */
    public void init () {
    	if (this.isSearchCreate == true) {
        	super.put((String) "search", (Object) new Search());
    	} else {
    		return;
    	}
    }
    
    
    
    /** 
	 * 검색 목록에 데이터 추가하기
	 * 
	 * @param column : 검색 컬럼명
	 * @param keyword : 검색어
	 */
	public void addSearchCondition (String column, String keyword) throws Exception {
		Search search = (Search) super.get("search");
		search.addSearchCondition(column, keyword);
		super.put("search", search);
	}
	
	/** 
	 * 검색 목록에 데이터 추가하기
	 * 
	 * @param column : 검색 컬럼명
	 * @param keyword : 검색어
	 * @param comparisonType : 검색어 비교 연산자 타입 ('=', 'like', 'is <not> null')
	 * @param currentSearchOperator : 현재 검색 조건 연산자 (AND, OR)
	 */
	public void addSearchCondition (String column, String comparisonType, String keyword, String currentSearchOperator) throws Exception {
		((Search) super.get("search")).addSearchCondition(column, comparisonType, keyword, currentSearchOperator);
	}
	
	/** 
	 * 검색 정렬 목록에 데이터 추가하기
	 * 
	 * @param column : 검색 컬럼명
	 */
	public void addSearchOrder (String column) throws Exception {
		((Search) super.get("search")).addSearchOrder(column);
	}
	
	/** 
	 * 검색 목록에 데이터 추가하기
	 * 
	 * @param column : 검색 컬럼명
	 * @param orderType : 정렬 타입('ASC', 'DESC')
	 */
	public void addSearchOrder (String column, String orderType) throws Exception {
		((Search) super.get("search")).addSearchOrder(column, orderType);
	}
	
	/** 
	 * 검색 객체 가지고가기
	 */
	public Search getSearch() {
		if (super.get("search") == null) {
			return null;
		} else {
			return (Search) super.get("search");
		}
	}
}
