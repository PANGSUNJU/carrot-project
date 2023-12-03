package common.util;

import common.vo.User;

import javax.servlet.http.HttpSession;

public class AuthUtil {

    public static final String LOGIN_USER_SESSION_KEY = "loginUser";

    public static final int SESSION_MAX_TIME = 60*60*1;//1시간

    public static User getLoginUser () {
        try {
            //현재 client의 HttpSession 조회
            HttpSession session = CommonUtil.getHttpSession(false);
            if(session == null || session.getAttribute(LOGIN_USER_SESSION_KEY) == null
                || ((User) session.getAttribute(LOGIN_USER_SESSION_KEY)).getUserId() == null) {
                return null;
            }else {
                return (User) session.getAttribute(LOGIN_USER_SESSION_KEY);
            }
        } catch(Exception e) {
            System.out.println("AuthUtil getLoginUser Error : ");
            e.printStackTrace();
            return null;
        }
    }

}
