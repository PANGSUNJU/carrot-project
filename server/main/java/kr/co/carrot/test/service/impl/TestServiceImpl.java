package kr.co.carrot.test.service.impl;

import kr.co.carrot.test.dao.TestDAO;
import kr.co.carrot.test.service.TestService;
import kr.co.carrot.test.vo.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 테스트용 서비스 로직 Class 객체 입니다.
 *
 * @author 서영석
 * @since 2022.09.01
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDAO testDao;

    /**
     * 데이터 조회 테스트
     *
     * @author 서영석
     * @since 2022.09.01
     */
    @Override
    public List<Test> testDataSelectList(Test test) throws Exception {
        return testDao.testDataSelectList(test);
    }
}
