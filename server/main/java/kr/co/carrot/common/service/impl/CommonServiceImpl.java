package kr.co.carrot.common.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.carrot.common.dao.CommonDAO;
import kr.co.carrot.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Common 서비스 로직 Class 객체 입니다.
 *
 * @author 서영석
 * @since 2022.09.01
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommonDAO commonDAO;


}
