package kr.co.carrot.common.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.carrot.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Common Controller 입니다.
 *
 * @author 서영석
 * @since 2022.09.01
 */
@Controller
public class CommonConroller {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommonService commonService;


    @RequestMapping(value = "/")
    public String main () {
        return "redirect:/views/index.html";
    }
}
