package kr.co.carrot.test.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.carrot.test.service.TestService;
import kr.co.carrot.test.vo.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 테스트용 Controller 입니다.
 *
 * @author 서영석
 * @since 2022.09.01
 */
@Controller
public class TestConroller {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestService testService;

    /**
     * 데이터 조회 테스트
     *
     * @author 서영석
     * @since 2022.09.01
     */
    @RequestMapping(value = "/testDataSelectList")
    public ModelAndView testDataSelectList (@RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        System.out.println("testDataSelectList param : " );

        if (param != null && param.size() > 0) {
            System.out.println("testDataSelectList param : " + objectMapper.writeValueAsString(param));
        }
        Test test = new Test();
        mav.addObject("result", testService.testDataSelectList(test));
        return mav;
    }

    /**
     * @RequestParam HashMap<String, Object> param Test
     * GET 방식의 URL Query 데이터 가능
     * POST 방식의 Body FormData 데이터 가능
     *
     * @author 서영석
     * @since 2022.08.31
     */
    @RequestMapping(value = "/jsonTestForRequestParam")
    public ModelAndView jsonTestForRequestParam (@RequestParam HashMap<String, Object> param) throws Exception {
        if (param != null && param.size() > 0) {
            System.out.println("jsonTestForRequestParam param : " + objectMapper.writeValueAsString(param));
        }
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", param);
        return mav;
    }

    @RequestMapping(value = "/jsonTestForRequestBody")
    public ModelAndView jsonTestForRequestBody (@RequestBody HashMap<String, Object> param) throws Exception {
        if (param != null && param.size() > 0) {
            System.out.println("jsonTestForRequestBody param : " + objectMapper.writeValueAsString(param));
        }
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", param);
        return mav;
    }

    @RequestMapping(value = "/jsonTestForMultipart")
    public ModelAndView jsonTestForMultipart (HttpServletRequest request, @RequestParam(required = false) HashMap<String, Object> param) throws Exception {
        if (param != null && param.size() > 0) {
            System.out.println("jsonTestForMultipart param : " + objectMapper.writeValueAsString(param));
        }

        try {
            MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
            Iterator<String> itr = multipart.getFileNames();

            int index = 1;
            while (itr.hasNext()) {
                MultipartFile multipartFile = multipart.getFile(itr.next());
                System.out.println("File " + index + ". " + multipartFile.getOriginalFilename() + " (" + multipartFile.getSize() + ")");
            }
        } catch (Exception e) {
            System.out.println("");
            System.err.println("Mutipart Error 발생");
            e.printStackTrace();

        }


        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", param);
        return mav;
    }
}
