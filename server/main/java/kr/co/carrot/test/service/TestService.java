package kr.co.carrot.test.service;

import kr.co.carrot.test.vo.Test;

import java.util.List;


public interface TestService {


    /**
     * 데이터 조회 테스트
     *
     * @author 서영석
     * @since 2022.09.01
     */
    public List<Test> testDataSelectList (Test test) throws Exception;

}
