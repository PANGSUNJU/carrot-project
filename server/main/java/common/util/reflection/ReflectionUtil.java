package common.util.reflection;

import common.util.StringUtil;
import common.util.bean.BeanUtil;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @author 서영석
 * @since 2020.11.25
 * 
 * Reflection - 클래스, 인터페이스, 메소드관련 인스턴스화(로드) 및 실행 관련 Util입니다.
 */
public class ReflectionUtil {
	
	/******************************************** Class 단 ********************************************/
	/**
     * @author 서영석
	 * @since 2020.11.25 
     *
     * classLoad - 어플리케이션 내부(+Bean객체포함) OR 외부의 class 파일을 저장된 경로를 통해  class 파일 로드 후, 객체 생성
     * 
	 * classFullName과 classFilePath를 통해 실제 클래스를 인스턴스화함.
	 * 1. Class.forName을 통해 어플리케이션 내부의 class path에 있는 class객체 생성
	 * 2. 어플리케이션 내부에 class가 존재할 때
	 * 		2-1. 어플리케이션의 Spring 컨테이너에 해당 클래스 타입으로 생성된 Bean이 있는지 확인
	 * 			=> 존재할 때, Bean 객체 주입
	 * 			=> 존재하지 않을 때, 객체 생성
	 * 3. 어플리케이션 내부에 class가 존재하지 않을 때
	 * 		3-1. class or jar file이 저장된 경로를 이용하여 클래스를 로드 
	 * 
	 * 4. 객체 생성 실패시, Log 기록 및 다음 작업 목록으로 넘어감
	 */
    public static Object classAndBeanLoad (String classFilePath, String classFullName) {
    	Object clazz = null;
    	try {
    		//Class.forName을 통해 어플리케이션 내부의 class path에 있는 class객체 생성
			Class<?> c = forName(classFullName);
			if (c != null) {//어플리케이션 내부에 class가 존재할 때
				//플리케이션의 Spring 컨테이너에 해당 클래스 타입으로 생성된 Bean이 있는지 확인 후, Bean 객체 주입
				clazz = BeanUtil.getBean(c);
				
				if (clazz == null) {//Bean 객체 존재하지 않을 때
					clazz = newInstance(c);
				}
			} else {//어플리케이션 내부에 class가 존재하지 않을 때
				//class or jar file이 저장된 경로
				//file 경로 값이 있을 때만 클래스를 로드함
				if (StringUtil.isEmpty(classFilePath) == false) {
					if (StringUtil.isEmpty(classFullName) == false) {
						clazz = classLoad(classFilePath, classFullName);
					} else {
						clazz = classLoad(classFilePath);
					}
				}
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return clazz;
    }
	
	/**
     * @author 서영석
	 * @since 2019.11.17
     * 
     * forName - 어플리케이션 내부의 class path에 있는 class객체 생성
     * 
     * classFullName = package명 + class명
     */
    public static Class<?> forName (String classFullName) {
    	try {
    		return Class.forName(classFullName);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	} 
    }
    
    /**
     * @author 서영석
	 * @since 2019.11.17 
     *
     * classLoad - 어플리케이션 내부 OR 외부의 class 파일을 저장된 경로를 통해  class 파일 로드 후, 객체 생성
     * 주로 외부 class파일을 로드할 때 쓰임
     * 
     * classFilePath - 클래스파일 절대경로
     * classFullName = package명 + class명
     */
    public static Object classLoad (String classFilePath, String classFullName) {
    	try {
    		File file = new File(classFilePath);
    		//실제 경로상에 있는 .class파일을 통해 class를 읽어옴
    		ParentLastURLClassLoader classLoader = new ParentLastURLClassLoader(new URL[] { file.toURI().toURL() });
    		//읽어온 .class파일을 이용해 해당되는  패키지 내에 있는 class를 로드해 옴
    		Class<?> c = classLoader.loadClass(classFullName);
    		//class를 new 객체 생성함
    		return newInstance(c);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    /**
     * @author 서영석
	 * @since 2019.11.17
     * 
     * classLoad - 어플리케이션 내부 OR 외부의 class 파일을 저장된 경로를 통해  class 파일 로드 후, 객체 생성
     * 주로 외부 class파일을 로드할 때 쓰임
     * class명을 모를 때 사용
     * 
     * classFilePath - 클래스파일 절대경로
     */
    public static Object classLoad (String classFilePath) {
    	try {
    		File file = new File(classFilePath);
    		//실제 경로상에 있는 .class파일을 통해 class를 읽어옴
    		ParentLastURLClassLoader classLoader = new ParentLastURLClassLoader(new URL[] { file.toURI().toURL() });
    		//읽어온 .class파일을 이용해 해당되는  패키지 내에 있는 class를 로드해 옴
    		Class<?> c = classLoader.getClass();
    		//class를 new 객체 생성함
    		return newInstance(c);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    /**
     * @author 서영석
	 * @since 2019.11.17
     * 
     * newInstance - 객체 인스턴스화(로드)
     */
    public static Object newInstance(Class<?> c) {
    	try {
			return c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    /******************************************** Class 단 ********************************************/
    
    
    
    /******************************************** Method 단 ********************************************/
    /**
     * @author 서영석
	 * @since 2019.11.17
     * 
     * invokeByMethodName - 메소드 이름을 통한 메소드 호출
     * 
     * clazz - 인스턴스화(로드)된 객체
     * methodName - 메소드명
     * paramValues - 메소드의 파라메터 값
     * paramTypes - 메소드의 파라메터 타입
     * (주의 paramValues, paramTypes 순서가 같아야함)
     */
    public static Object invokeByMethodName (Object clazz, String methodName, Object[] paramValues, Class<?>[] paramTypes) {
    	try {
    		Method method = null;
        	if (paramValues != null && paramTypes != null 
        		&& paramValues.length > 0 && paramTypes.length > 0
        		&& paramValues.length == paramTypes.length) {
        		
        		System.out.println("clazz getPackage : " + clazz.getClass().getPackage());
        		System.out.println("clazz name : " + clazz.getClass().getName());
        		if (paramValues != null && paramValues.length > 0) {
        			System.out.println("clazz param value : " + paramValues[0].toString());
            		System.out.println("clazz param type : " + paramTypes[0].getTypeName());
        		}
        		
        		//메소드 객체 가지고오기
        		method = clazz.getClass().getMethod(methodName, paramTypes);
        	} else {
        		//메소드 객체 가지고오기
        		method = clazz.getClass().getMethod(methodName);
        	}
        	
        	//메소드 호출
    		return invoke(clazz, method, paramValues);
        	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	
    }
    
    /**
     * @author 서영석
	 * @since 2019.11.17
     * 
     * invoke - 메소드 호출
     * 
     * clazz - 인스턴스화(로드)된 객체
     * method - 메소드 객체
     * paramValues - 메소드의 파라메터 값
     */
    public static Object invoke (Object clazz, Method method, Object[] paramValues) {
    	try {
        	if (paramValues != null && paramValues.length > 0) {
        		//메소드 호출
        		return method.invoke(clazz, paramValues);
        	} else {
        		//메소드 호출
        		return method.invoke(clazz);
        	}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	
    }
    /******************************************** Method 단 ********************************************/
}
