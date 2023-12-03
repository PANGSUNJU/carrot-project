package kr.co.carrot.test.dao;


import kr.co.carrot.test.vo.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 테스트용 DAO 입니다.
 *
 * @author 서영석
 * @since 2022.09.01
 */
//@Repository <-- Class 객체로 생성 해야 됨
@Mapper
public interface TestDAO {

    /**
     * 데이터 조회 테스트
     *
     * @author 서영석
     * @since 2022.09.01
     */
    public List<Test> testDataSelectList (Test test) throws Exception;
}
