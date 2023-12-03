package common.util.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author 서영석
 * @since 2019.11.17
 * 
 * Spring 컨테이너(ApplicationContext)에 접근하기 위한 Class 입니다.
 * ApplicationContextAware 구현체
 */
public class ApplicationContextProvider implements ApplicationContextAware {
    
	/**
	 * 해당 어플리케이션의 인스턴스(bean)들의 정보를 담은 객체 
	 */
    private static ApplicationContext applicationContext;
    
    /**
     * @author 서영석
     * @since 2019.11.17
     * 
     * ApplicationContextAware를 구현하기 위한 메소드
     * Spring 구동 시, 해당 Class가 스캔 당하면 applicationContext 객체가 생성됨
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }
    
    /**
     * @author 서영석
     * @since 2019.11.17
     * 
     * applicationContext 객체 호출
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
}