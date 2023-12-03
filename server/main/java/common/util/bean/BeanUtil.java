package common.util.bean;

import org.springframework.beans.BeansException;

/**
 * ApplicationContextProvider에서 bean객체를 얻어 활용할 수 있도록 해주는 Util 입니다.
 *
 * @author 서영석
 * @since 2020.11.25
 */
public class BeanUtil {

	/**
	 * 해당 어플리케이션에서 스프링 컨테이너가 관리하는 bean으로 등록된 객체를 이름으로 호출
	 *
	 * @author 서영석
	 * @since 2019.11.17
	 */
	public static Object getBean(String beanName) {
		System.out.println("BeanUtil getBean(param (String) beanName : " + beanName + ")");
		try {
			return ApplicationContextProvider.getApplicationContext().getBean(beanName);
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 해당 어플리케이션에서 스프링 컨테이너가 관리하는 bean으로 등록된 객체를 객체의 타입으로 호출
	 *
	 * @author 서영석
	 * @since 2019.11.17
	 */
	public static Object getBean(Class<?> classType) {
		System.out.println("BeanUtil getBean(param (Class<?>) classType : " + classType.getName() + ")");
		try {
			return ApplicationContextProvider.getApplicationContext().getBean(classType);
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
}
