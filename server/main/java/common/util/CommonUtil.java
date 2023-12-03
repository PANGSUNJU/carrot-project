package common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


public class CommonUtil {

	/**
     * @author 서영석
     * @since 2019.12.11
     * 
     * 데이터의 표준화 사용 유무 
     */
	private static boolean IS_USE_STANDARD = true;
	
	public static void setIsUseStandard (boolean isUseStandard) {
		IS_USE_STANDARD = isUseStandard;
	}
	
	public static boolean getIsUseStandard () {
		return IS_USE_STANDARD;
	}
	
	/**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 빈 문자열 검사
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }
    

    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * string to int check
     */
    public static boolean isInt (String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * string to int check
     */
    public static boolean isLong (String text) {
		try {
			Long.parseLong(text);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * string to double check
     */
	public static boolean isDouble (String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
     * @author 서영석
     * @since 2020.01.08
     * 
     * object to int
     */
	public static int parseInt (Object obj) {
		try {
			return Integer.parseInt(obj.toString());
		} catch(Exception e) {
			return 0;
		}
	}
	
	/**
     * @author 서영석
     * @since 2020.01.08
     * 
     * string to int
     */
	public static int parseInt (String text) {
		try {
			return Integer.parseInt(text);
		} catch(NumberFormatException e) {
			return 0;
		}
	}
	
	/**
     * @author 서영석
     * @since 2020.01.08
     * 
     * int to double 
     */
	public static long parseLong (int number) {
		try {
			return (long) number;
		} catch(Exception e) {
			return 0;
		}
	}
	
	/**
     * @author 서영석
     * @since 2020.01.08
     * 
     * object to double 
     */
	public static long parseLong (String text) {
		try {
			return Long.parseLong(text);
		} catch(Exception e) {
			return 0;
		}
	}

	/**
     * @author 서영석
     * @since 2020.01.08
     * 
     * object to double 
     */
	public static long parseLong (Object obj) {
		try {
			if (obj instanceof Integer) {
				return (long) obj;
			} else {
				return Long.parseLong(obj.toString());
			}
		} catch(Exception e) {
			return 0;
		}
	}
	
	/**
     * @author 서영석
     * @since 2020.01.08
     * 
     * int to double 
     */
	public static double parseDouble (int number) {
		try {
			return (double) number;
		} catch(Exception e) {
			return 0.0;
		}
	}
	
	/**
     * @author 서영석
     * @since 2020.01.08
     * 
     * object to double 
     */
	public static double parseDouble (String text) {
		try {
			return Double.parseDouble(text);
		} catch(Exception e) {
			return 0.0;
		}
	}

	/**
     * @author 서영석
     * @since 2020.01.08
     * 
     * object to double 
     */
	public static double parseDouble (Object obj) {
		try {
			if (obj instanceof Integer) {
				return (double) obj;
			} else {
				return Double.parseDouble(obj.toString());
			}
		} catch(Exception e) {
			return 0.0;
		}
	}
	
	/**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열의 모든 공백 제거
     */
	public static String allTrim(String text) {
		return text.replaceAll("\\p{Z}", "");
	}
	
	/**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열 앞뒤 공백 제거후, 문자열 사이에 존재하는 공백을 한개의 공백으로 치환
     * ex) " abcd     efg    hijk   " => "abcd efg hijk" 
     */
	public static String normalizeSpace(String text) {
		return text.trim().replaceAll("\\s+", " ");
	}
	
	/**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 숫자 빼고 모든 문자 제거
     */
	public static String getOnlyNumber (String text) {
		return text.replaceAll("[^0-9]", "");
	}
	
	/**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자 빼고 모든 숫자 제거
     */
	public static String getOnlyText (String text) {
		return text.replaceAll("[0-9]", "");
	}
	
	/**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 특정 문자열 개수 check
     */
	public static int getWordCount (String text, String word) {
		int size = 0;
		int fromIndex = -1;
		while ((fromIndex = text.indexOf(word, fromIndex + 1)) >= 0) {
			size++;
		}
		return size;
	}

	/**
	 * @author 서영석
	 * @since 2021.12.05
	 * 
	 * 현재 서버 일시 구하기
	 */
	public static Date getDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return Timestamp.valueOf(localDateTime);
	}
	
	/**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열 to Date문자열
     */
	public static boolean isDate (String text) {
		if (StringUtil.isEmpty(text) == true || StringUtil.isEmpty(getOnlyNumber(text)) == true || getOnlyNumber(text).length() < 6) {
			return false;
		}
		
		//공백을 제외한 문자얻기, 대문자로 치환
		String newText = allTrim(text).toUpperCase();
		
		try {
			//문자열의 날짜 패턴 생성
			String pattern = createDatePattern(newText);
			if (pattern == null) {
				return false;
			}
		
			SimpleDateFormat newPattern = new SimpleDateFormat(pattern);
			//문자열 날짜 형태로 변환
			newPattern.parse(newText);
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	/**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열 to Date문자열
     */
	public static String parseDateText (String text) {
		if (StringUtil.isEmpty(text) == true || StringUtil.isEmpty(getOnlyNumber(text)) == true || getOnlyNumber(text).length() < 6) {
			return null;
		}
		
		//공백을 제외한 문자얻기, 대문자로 치환
		String newText = allTrim(text).toUpperCase();
		
		//문자열의 날짜 패턴 생성 
		String pattern = createDatePattern(newText);
		if (pattern == null) {
			return null;
		}

		Date date = null;
		String dateText = null;
		try {
			SimpleDateFormat newPattern = new SimpleDateFormat(pattern);
			
			//문자열 날짜 형태로 변환
			date = newPattern.parse(newText);
			
			//DB에 저장할 날짜 패턴
			SimpleDateFormat defalutPattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateText = defalutPattern.format(date);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return dateText;
	}
	
	public static <T> List<T> mapToList (Map<?, T> map) {
		List<T> items = new ArrayList<T>();
		
		if (map != null) {
			for(Map.Entry<?, T> item : map.entrySet()) {
				items.add(item.getValue());
	        }
		}
		
		return items;
	}
	
	public static Map objectToMap(Object obj) {
		if (obj != null) {
			try {
				return (Map) obj;
			} catch (Exception e) {
				return new HashMap();
			}
		} else {
			return new HashMap();
		}
	}
	
	
	/*
	 * 시간 타입
	 * PM, AM
	 */
	public final static List<String> TIME_TYPES = Arrays.asList(new String[] {"AM", "PM", "오전", "오후"});
	
	/*
	 * 날짜 포맷 패턴's
	 */
	public final static List<Character> DATE_PATTERNS = Arrays.asList(new Character[] {'y', 'M', 'd', 'H', 'm', 's'});
	
	/*
	 * 날짜 포맷 패턴's의 최대 문자열 수
	 */
	public final static List<Integer> DATE_PATTERNS_MAX_LENGTH = Arrays.asList(new Integer[] {4, 2, 2, 2, 2, 2});
	
	/**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열의 날짜 패턴 생성
     */
	public static String createDatePattern (String date) {
		
		List<Character> DATE_PATTERNS = Arrays.asList(new Character[] {'y', 'M', 'd', 'H', 'm', 's'});
		
		//시간 표기가 (0~12 -> AM, PM사용)인지 (0~23)인지 확인 후, 날짜 포맷 패턴's에 있는 시간 패턴 변경
		int timeTypeFindIndex = -1;
		for (int i = 0; i < TIME_TYPES.size(); i++) {
			//("AM", "PM", "오전", "오후" 중 1개)가 포함된 단어가 있는지 확인, Index 위치를 담기(없으면 -1)
			if ((timeTypeFindIndex = date.indexOf(TIME_TYPES.get(i))) > -1) {
				//문자열에 포함된 ("AM", "PM", "오전", "오후" 중 1개) 삭제
				date = date.replaceAll(TIME_TYPES.get(i), "");
				//시간 패턴 변경 [H -> h]
				DATE_PATTERNS.set(3, 'h');
				break;
			}
		}
		
		//숫자를 뺀 나머지 문자열 가지고오기 ex) "2020.08.03" -> ".."
		//숫자를 뺀 나머지 문자열 가지고오기 ex) "2020.08.03 19:20:21" -> "..::"
		final char[] separators = getOnlyText(date).toCharArray();
		
		/*
		System.out.println("");
		System.out.println("@@@@@@@@@@@@@@ textToDateText @@@@@@@@@@@@@@");
		System.out.println("공백제거 텍스트 : " + date);
		System.out.println("숫자제거 텍스트 : " + String.valueOf(separators));
		*/
		
		//사용할 최대 패턴 수
		int maxPatterSize = 0;
		if (DATE_PATTERNS.size() <= separators.length) {
			maxPatterSize = DATE_PATTERNS.size();
		} else {
			maxPatterSize = separators.length;
		}
		
		//구분자별 Index 위치's (사용할 최대 패턴 수 + 시작점:-1, 종료점:date문자열의 최종 길이)
		List<Integer> sizeByPatterns = new ArrayList<Integer>();
		
		//System.out.println("");
		//System.out.println("newText : " + date);
		
		//구분자 별 Index 위치 파악 후, 앞에 있는 문자열의 수 찾은 후, 추가 (마지막 패턴 뒤에 있는 문자열을 따로 처리해줘야함)
		int fromIndex = -1;
		for (int i = 0; i < maxPatterSize; i++) {
			//구분자
			char separator = separators[i];
			
			//'현재 찾은 위치' : 이전에 찾은 위치(찾기 시작할 위치 => fromIndex) + 1 부터 찾기 시작함
			int currentFromIndex = date.indexOf(separator, fromIndex + 1);
			
			//현재 패턴의 문자열 수 = '현재 찾은 위치' - '이전에 찾은 위치' - 1 [추가]
			sizeByPatterns.add(currentFromIndex - fromIndex - 1);
			//System.out.print("[" + sizeByPatterns.get(i) +"] ");
			
			//'현재 찾은 위치'는 '이전에 찾은 위치'가 됨
			fromIndex = currentFromIndex;
		}
		//마지막 패턴 뒤에 있는 문자열 = '문자열의 길이' - '마지막에 찾은 위치(이전에 찾은 위치)' - 1 [추가]
		sizeByPatterns.add(date.length() - fromIndex - 1);
		
		
		
		//System.out.println("");
		//System.out.println("시간 타입 체킹 Index : " + timeTypeFindIndex);
		
		//패턴을 담을 변수
		StringBuilder pattern = new StringBuilder();
		
		//DATE_PATTERS 순서 대로, 각 구분자 별 Index 위치 크기만큼 문자열에 패턴 삽입 + 구분자 삽입
		//마지막 전까지만 for문 돌림
		for (int i = 0, patternIndex = 0; i < sizeByPatterns.size() && patternIndex < DATE_PATTERNS.size(); i++, patternIndex++) {
			
			//패턴 추가
			int usingSize = 0;
			for (int j = 0; j < sizeByPatterns.get(i); j++) {
				if (j >= usingSize + DATE_PATTERNS_MAX_LENGTH.get(patternIndex)) {
					usingSize += DATE_PATTERNS_MAX_LENGTH.get(patternIndex++);
					
					/*단 한개의 패턴이라도 '최대 문자열 수'를 넘어서면 -> '날짜 아님'*/
					if (i >= sizeByPatterns.size() || patternIndex >= DATE_PATTERNS.size()) {
						return null;
					}
				}
				
				pattern.append(DATE_PATTERNS.get(patternIndex));
			}
			
			//날짜 구분자 추가 (마지막 구분자까지만) 
			if (i < separators.length) {
				pattern.append(separators[i]);
			}
			
			
		}
		
		if (timeTypeFindIndex > -1) {
			pattern.insert(timeTypeFindIndex, 'a');
		}
		
		return pattern.toString();
	}
	
	
	/**
     * @author 서영석
     * @since 2020.01.26
     * 
     * ping 체크
     */
	public static boolean pingCheck (String ip) {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(ip);
			return inetAddress.isReachable(1000);
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @author 서영석
	 * @since 2020.01.26
	 *
	 * 현재 client의 HttpServletRequest 조회
	 */
	public static HttpServletRequest getHttpServletRequest () {
		try {
			ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			return servletRequestAttribute.getRequest();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @author 서영석
	 * @since 2020.01.26
	 *
	 * 현재 client의 HttpSession 조회
	 */
	public static HttpSession getHttpSession (boolean create) {
		try {
			HttpServletRequest request = getHttpServletRequest();
			if (request != null) {
				return request.getSession(create);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @author 서영석
	 * @since 2020.01.26
	 *
	 * HttpServletRequest를 활용한 Client IP 가지고오기
	 * Header의 X-FORWARDED-FOR 값을 통해 IP 조회, 만약 Header에 X-FORWARDED-FOR가 없으면 getRemoteAddr() 이걸로 조회
	 */
	public static String getClientIp () {
		try {
			HttpServletRequest request = getHttpServletRequest();
			if (null != request.getHeader("X-FORWARDED-FOR")) {
				return request.getHeader("X-FORWARDED-FOR");
			} else {
				return request.getRemoteAddr();
			}
		} catch (Exception e) {
			return null;
		}
	}


	
	/**
     * @author 서영석
     * @since 2020.01.26
     * 
     * ping 체크 (ip + port)
     */
	public static boolean pingCheck(String ip, int port) {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(ip, port), 1000);
			boolean isConnect = socket.isConnected();
			socket.close();
			return isConnect;
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/************************************* objectSetValue : Object to Object (start) *************************************/
	/**
	 * @author 서영석
	 * @since 2020.04.22
	 *
	 * [리플렉션 활용] VO객체(valueObject)의 변수들의 값 -> 목표 객체(targetObject)에 setting 해주는 메서드
	 *
	 * 단, '명칭'과 '데이터 타입'이 같아야 값이 세팅됨
	 */
	public static void objectSetValue (Object targetObject, Object valueObject) {
		objectSetValue(targetObject, valueObject, null, null);
	}

	public static void objectSetValue (Object targetObject, Object valueObject, Class<?>[] ignoreClasses) {
		objectSetValue(targetObject, valueObject, ignoreClasses, null);
	}

	public static void objectSetValue (Object targetObject, Object valueObject, String[] ignoreFields) {
		objectSetValue(targetObject, valueObject, null, ignoreFields);
	}

	/**
	 * @author 서영석
	 * @since 2020.04.22
	 *
	 * [리플렉션 활용] VO객체(valueObject)의 변수들의 값 -> 목표 객체(targetObject)에 setting 해주는 메서드 (+ 제거할 변수 목록)
	 *
	 * 단, '명칭'과 '데이터 타입'이 같아야 값이 세팅됨
	 */
	public static void objectSetValue (Object targetObject, Object valueObject, Class<?>[] ignoreClasses, String[] ignoreFields) {
		//빈값 체크
		if (valueObject == null || targetObject == null) {
			return;
		}

		//vo객체의 클래스 정보 얻기
		Class<?> valueObjectClass = valueObject.getClass();
		//vo객체의 클래스의 값이 null이 될 때 까지 반복
		while (valueObjectClass != null) {

			/* Class 필터 */
			boolean isIgnoreClassFind = false;
			if (ignoreClasses != null) {
				for (Class<?> ignoreClass : ignoreClasses) {
					if (valueObjectClass.getName().equals(ignoreClass.getName())) {
						//System.out.println("["+valueObjectClass.getName()+"] 제거(값 세팅 안하고 return) @@@@@@@@@@@@@@@@@@@@@");
						isIgnoreClassFind = true;
						break;
					}
				}

				//제거할 Class를 찾았을 때 -> 다음 상위 클래스로 이동
				if (isIgnoreClassFind == true) {
					//vo객체가 상속받은 상위클래스 정보 얻기
					valueObjectClass = valueObjectClass.getSuperclass();
					continue;//while문으로 이동
				}
			}/* Class 필터 */

			//vo객체의 Field목록 얻기
			Field[] valueObjectFields = valueObjectClass.getDeclaredFields();
			//Field 목록이 있을 때 -> 값 세팅하러 가기
			if (valueObjectFields != null && valueObjectFields.length > 0) {
				for (int i = 0; i < valueObjectFields.length; i++) {

					/* Field 필터 */
					boolean isIgnoreFieldFind = false;
					if (ignoreFields != null) {
						for (String ignoreField : ignoreFields) {
							if (valueObjectFields[i].getName().equals(ignoreField)) {
								//System.out.println("["+valueObjectFields[i].getName()+"] 제거(값 세팅 안하고 return) @@@@@@@@@@@@@@@@@@@@@");
								isIgnoreFieldFind = true;
								break;
							}
						}
						//제거할 Field를 찾았을 때 -> 다음 Field로 이동
						if (isIgnoreFieldFind == true) {
							continue;//for문으로 이동
						}
					}/* Field 필터 */

					//현재 vo객체 정보를 target객체에 값 세팅하기
					objectSetValueForField(targetObject, targetObject.getClass(), valueObject, valueObjectFields[i]);
				}
			}

			//vo객체가 상속받은 상위클래스 정보 얻기
			valueObjectClass = valueObjectClass.getSuperclass();
		}
	}

	/**
	 * @author 서영석
	 * @since 2020.04.22
	 *
	 * public objectSetValue에서만 호출 가능
	 *
	 */
	private static void objectSetValueForField (Object targetObject, Class<?> targetObjectSuperClass, Object valueObject, Field valueObjectField) {
		//빈값 체크
		if (valueObject == null || valueObjectField == null || targetObject == null || targetObjectSuperClass == null) {
			return;
		}

		//Field 접근자 타입이 private라도 필드 정보에 접근가능하게 해줌
		valueObjectField.setAccessible(true);
		try {
			//vo객체의 변수필드의 명칭을 이용하여, 목표 객체의 변수필드 찾기. 만약, 못 찾으면 -> NoSuchFieldException오류 발생 -> catch로 이동
			Field targetObjectField = targetObjectSuperClass.getDeclaredField(valueObjectField.getName());
			targetObjectField.setAccessible(true);//private 변수도 접근가능하도록 settting

			//데이터 전달 객체의 필드 데이터 타입
			String valueObjectFieldType = valueObjectField.getType().getName();

			//타겟 객체의 필드 데이터 타입
			String targetFieldType = targetObjectField.getType().getTypeName();

			/* 목표 객체의 변수필드가 상수(final)이 아니고, 데이터 타입이 같고 때 -> 같은 클래스의 변수필드 인지 확인 */
			if (Modifier.isFinal(targetObjectField.getModifiers()) == false) {

				//데이터 타입이 완전 똑같을 때
				if (valueObjectFieldType.equals(targetFieldType)) {
					//값 세팅
					targetObjectField.set(targetObject, valueObjectField.get(valueObject));
				} else {

					//타겟 변수가 기본형 변수 일 때
					if (targetObjectField.getType().isPrimitive()) {
						String typeFullName = "java.lang." + targetFieldType;
						if (typeFullName.toLowerCase().equals(valueObjectFieldType.toLowerCase())) {
							//값 세팅
							targetObjectField.set(targetObject, valueObjectField.get(valueObject));
						} else {
							throw new NoSuchFieldException();
						}
					} else if (targetObjectField.getType().isInterface()) {//타겟 변수가 인터페이스 일 때
						//데이터 전달 객체 변수의 타입이 '타겟 변수의 인터페이스'구현체인지 확인하기
						boolean isImpl = false;
						//데이터 전달 객체 변수 객체 생성
						Class<?> runtimeClass = ClassLoader.getSystemClassLoader().loadClass(valueObjectFieldType);
						//데이터 전달 객체 변수의 '인터페이스' 목록 가지고오기
						Class<?>[] impls = runtimeClass.getInterfaces();
						//인터페이스가 있는지 없는지 확인
						for(Class<?> clz : impls) {
							if(clz.getName().equals(targetFieldType)) {
								isImpl = true;
							}
						}

						//구현체라면
						if (isImpl == true) {
							//값 세팅
							targetObjectField.set(targetObject, valueObjectField.get(valueObject));
						} else {
							throw new NoSuchFieldException();
						}

					} else {
						throw new NoSuchFieldException();
					}
				}

			}

		} catch (NoSuchFieldException e) {//목표 객체에서 변수를 발견 못했을 때 -> 목표 객체의 상위클래스 변수에서 찾아보기
			//목표 객체의 상위클래스가 존재할 때 -> 재귀 호출
			if (targetObjectSuperClass.getSuperclass() != null) {
				try {
					//목표객체의 상위클래스에서 변수 찾아서 값넣어주기 위해 재귀호출함
					objectSetValueForField(targetObject, targetObjectSuperClass.getSuperclass(), valueObject, valueObjectField);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/************************************* objectSetValue : Object to Object (end) *************************************/

	/************************************* objectSetMapValue : Map to Object (start) *************************************/
	/**
	 * @author 서영석
	 * @since 2020.04.22
	 *
	 * [리플렉션 활용] Map객체(Map<?, ?>)의 변수들의 값 -> 목표 객체(targetObject)에 setting 해주는 메서드 (+ 제거할 변수 목록)
	 *
	 * 단, '명칭'과 '데이터 타입'이 같아야 값이 세팅됨
	 */
	public static void objectSetMapValue (Object targetObject, Map<?, ?> valueObject) {
		objectSetMapValue(targetObject, valueObject, null);
	}

	/**
	 * @author 서영석
	 * @since 2020.04.22
	 *
	 * [리플렉션 활용] Map객체(Map<?, ?>)의 변수들의 값 -> 목표 객체(targetObject)에 setting 해주는 메서드 (+ 제거할 변수 목록)
	 *
	 * 단, '명칭'과 '데이터 타입'이 같아야 값이 세팅됨
	 */
	public static void objectSetMapValue (Object targetObject, Map<?, ?> valueObject, String[] ignoreFields) {
		//빈값 체크
		if (valueObject == null || targetObject == null) {
			return;
		}

		for(Map.Entry<?, ?> elem : valueObject.entrySet()) {
			String fieldName = StringUtil.toString(elem.getKey());
			Object fieldValue = elem.getValue();

			if (fieldValue == null) continue;

			String fieldType = elem.getValue().getClass().getTypeName();


			/* Field 필터 */
			boolean isIgnoreFieldFind = false;
			if (ignoreFields != null) {
				for (String ignoreField : ignoreFields) {
					if (fieldName.equals(ignoreField)) {
						//System.out.println("["+valueObjectFields[i].getName()+"] 제거(값 세팅 안하고 return) @@@@@@@@@@@@@@@@@@@@@");
						isIgnoreFieldFind = true;
						break;
					}
				}
				//제거할 Field를 찾았을 때 -> 다음 Field로 이동
				if (isIgnoreFieldFind == true) {
					continue;//for문으로 이동
				}
			}/* Field 필터 */

			objectSetValueForField(targetObject, targetObject.getClass(), fieldName, fieldType, fieldValue);
		}
	}

	/**
	 * @author 서영석
	 * @since 2020.04.22
	 *
	 * [리플렉션 활용] Map객체(Map<?, ?>)의 변수들의 값 -> 목표 객체(targetObject)에 setting 해주는 메서드 (+ 제거할 변수 목록)
	 *
	 * 단, '명칭'과 '데이터 타입'이 같아야 값이 세팅됨
	 */
	public static void objectSetValueForField (Object targetObject, Class<?> targetObjectSuperClass, String fieldName, String fieldType, Object fieldValue) {
		//System.out.println("targetObjectName : " + targetObject.getClass().getName() + ", targetObjectSuperClassName : " + targetObjectSuperClass.getName() + ", fieldName : " + fieldName + ", fieldType : " + fieldType);

		//빈값 체크
		if (StringUtil.isEmpty(fieldName) == true || StringUtil.isEmpty(fieldType) == true || fieldValue == null
				|| targetObject == null || targetObjectSuperClass == null) {
			return;
		}

		try {
			//vo객체의 변수필드의 명칭을 이용하여, 목표 객체의 변수필드 찾기. 만약, 못 찾으면 -> NoSuchFieldException오류 발생 -> catch로 이동
			Field targetObjectField = targetObjectSuperClass.getDeclaredField(fieldName);
			targetObjectField.setAccessible(true);//private 변수도 접근가능하도록 settting
			//System.out.println("targetObjectFieldName : " + targetObjectField.getName() + ", targetObjectField Type Name : " + targetObjectField.getType().getTypeName() + ", " + targetObjectField.getType().getName() + ", 기본형 유무 : " + targetObjectField.getType().isPrimitive() + ", 인터페이스 유무 : " + targetObjectField.getType().isInterface());

			//타겟 객체의 필드 데이터 타입
			String targetFieldType = targetObjectField.getType().getTypeName();

			/* 목표 객체의 변수필드가 상수(final)이 아니고, 데이터 타입이 같고 때 -> 같은 클래스의 변수필드 인지 확인 */
			if (Modifier.isFinal(targetObjectField.getModifiers()) == false) {

				//데이터 타입이 완전 똑같을 때
				if (fieldType.equals(targetFieldType)) {
					//값 세팅
					targetObjectField.set(targetObject, fieldValue);
					//System.out.println("셋팅 완료");
				} else {

					//타겟 변수가 기본형 변수 일 때
					if (targetObjectField.getType().isPrimitive()) {
						String typeFullName = "java.lang." + targetFieldType;
						if (typeFullName.toLowerCase().equals(fieldType.toLowerCase())) {
							//값 세팅
							targetObjectField.set(targetObject, fieldValue);
							//System.out.println("셋팅 완료");
						} else {
							throw new NoSuchFieldException();
						}
					} else if (targetObjectField.getType().isInterface()) {//타겟 변수가 인터페이스 일 때
						//데이터 전달 객체 변수의 타입이 '타겟 변수의 인터페이스'구현체인지 확인하기
						boolean isImpl = false;
						//데이터 전달 객체 변수 객체 생성
						Class<?> runtimeClass = ClassLoader.getSystemClassLoader().loadClass(fieldType);
						//데이터 전달 객체 변수의 '인터페이스' 목록 가지고오기
						Class<?>[] impls = runtimeClass.getInterfaces();
						//인터페이스가 있는지 없는지 확인
						for(Class<?> clz : impls) {
							if(clz.getName().equals(targetFieldType)) {
								isImpl = true;
							}
						}

						//구현체라면
						if (isImpl == true) {
							//값 세팅
							targetObjectField.set(targetObject, fieldValue);
							//System.out.println("셋팅 완료");
						} else {
							throw new NoSuchFieldException();
						}
					} else {
						throw new NoSuchFieldException();
					}

				}

			} else {
				return;
			}

		} catch (NoSuchFieldException e) {//목표 객체에서 변수를 발견 못했을 때 -> 목표 객체의 상위클래스 변수에서 찾아보기
			//목표 객체의 상위클래스가 존재할 때 -> 재귀 호출
			if (targetObjectSuperClass.getSuperclass() != null) {
				try {
					//목표객체의 상위클래스에서 변수 찾아서 값넣어주기 위해 재귀호출함
					objectSetValueForField(targetObject, targetObjectSuperClass.getSuperclass(), fieldName, fieldType, fieldValue);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("");
	}

	/**
	 * @author 서영석
	 * @since 2020.04.22
	 *
	 * [리플렉션 활용] Map객체(Map<?, ?>)의 변수들의 값 -> 목표 객체(targetObject)에 setting 해주는 메서드 (+ 제거할 변수 목록)
	 *
	 * 단, '명칭'과 '데이터 타입'이 같아야 값이 세팅됨
	 */
	public static void objectSetValueForField (Object targetObject, Class<?> targetObjectSuperClass, String fieldName, Object fieldValue) {
		//빈값 체크
		if (StringUtil.isEmpty(fieldName) == true || fieldValue == null || targetObject == null || targetObjectSuperClass == null) {
			return;
		}

		try {
			//vo객체의 변수필드의 명칭을 이용하여, 목표 객체의 변수필드 찾기. 만약, 못 찾으면 -> NoSuchFieldException오류 발생 -> catch로 이동
			Field targetObjectField = targetObjectSuperClass.getDeclaredField(fieldName);
			targetObjectField.setAccessible(true);//private 변수도 접근가능하도록 settting

			/* 목표 객체의 변수필드가 상수(final)이 아니고, 데이터 타입이 같고 때 -> 같은 클래스의 변수필드 인지 확인 */
			if (Modifier.isFinal(targetObjectField.getModifiers()) == false) {
				//값 세팅
				targetObjectField.set(targetObject, targetObjectField.getType().cast(fieldValue));
				//같은 클래스일 때 ->
			} else {
				return;
			}

		} catch (NoSuchFieldException e) {//목표 객체에서 변수를 발견 못했을 때 -> 목표 객체의 상위클래스 변수에서 찾아보기
			//목표 객체의 상위클래스가 존재할 때 -> 재귀 호출
			if (targetObjectSuperClass.getSuperclass() != null) {
				try {
					//목표객체의 상위클래스에서 변수 찾아서 값넣어주기 위해 재귀호출함
					objectSetValueForField(targetObject, targetObjectSuperClass.getSuperclass(), fieldName, fieldValue);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/************************************* objectSetMapValue : Map to Object (end) *************************************/
	
	
	/**
	 * @author 서영석
	 * @since 2020.04.22
	 * 
	 * public objectSetValue에서만 호출 가능
	 * 
	 */
	public static void objectHTMLFilter (Object targetObject, Class<?> targetObjectClass) {
		//빈값 체크
		if (targetObject == null || targetObjectClass == null) {
			return;
		}
		
		//vo객체의 Field목록 얻기
		Field[] valueObjectFields = targetObjectClass.getDeclaredFields();
		
		Field test = null;
		//Field 목록이 있을 때 -> 값 세팅하러 가기
		if (valueObjectFields != null && valueObjectFields.length > 0) {
			for (int i = 0; i < valueObjectFields.length; i++) {
				//현재 vo객체 정보를 target객체에 값 세팅하기 
				//Field 접근자 타입이 private라도 필드 정보에 접근가능하게 해줌
				valueObjectFields[i].setAccessible(true);
				try {
					
					//System.out.println("valueObjectFields["+i+"].getType().getName() : " + valueObjectFields[i].getType().getName() + ", name : " + valueObjectFields[i].getName() + ", value : " + valueObjectFields[i].get(targetObject) + ", getGenericType().getTypeName() : " + valueObjectFields[i].getGenericType().getTypeName());
					
					/* 목표 객체의 변수필드가 상수(final)이 아니고, 데이터 타입이 같고 때 -> 같은 클래스의 변수필드 인지 확인 */
					if (Modifier.isFinal(valueObjectFields[i].getModifiers()) == false) {
						if (valueObjectFields[i].getGenericType().getTypeName().equals("java.lang.String")) {
							
							String value = StringUtil.toString(valueObjectFields[i].get(targetObject));
							if (StringUtil.isEmpty(value) == false) {
								//같은 클래스일 때 -> 값 세팅
								valueObjectFields[i].set(targetObject, StringUtil.cleanXSS(value));
							} else {
								continue;
							}
						} else if (valueObjectFields[i].getGenericType().getTypeName().contains("List")) {
							List<Object> list = (List<Object>) valueObjectFields[i].get(targetObject);
							if (list != null) {
								for (int j = 0; j < list.size(); j++) {
									if (list.get(j) == null) {
										continue;
									} else if (list.get(j).getClass().getTypeName().indexOf(".") < 0) {
										continue;
									} else if (list.get(j).getClass().getTypeName().equals("java.lang.String")) {
										String value = StringUtil.toString(list.get(j));
										list.set(j, StringUtil.cleanXSS(value));
									} else if (list.get(j).getClass().getTypeName().contains("java.lang")) {
										continue;
									} else if (list.get(j).getClass().getTypeName().contains("List")
											|| list.get(j).getClass().getTypeName().contains("[]")
											|| list.get(j).getClass().getTypeName().contains("Map")
											|| list.get(j).getClass().getTypeName().contains("Set")
											|| list.get(j).getClass().getTypeName().contains("Collection")
											|| list.get(j).getClass().getTypeName().contains("kr.co.sppartners")) {
										objectHTMLFilter(list.get(j), list.get(j).getClass());
									} else {
										continue;
									}
								}
							}
						} else if (valueObjectFields[i].getGenericType().getTypeName().contains("[]")) {
							Object[] arr = (Object[]) valueObjectFields[i].get(targetObject);
							if (arr != null) {
								for (int j = 0; j < arr.length; j++) {
									if (arr[j] == null) {
										continue;
									} else if (arr[j].getClass().getTypeName().indexOf(".") < 0) {
										continue;
									} else if (arr[j].getClass().getTypeName().equals("java.lang.String")) {
										String value = StringUtil.toString(arr[j]);
										arr[j] = StringUtil.cleanXSS(value);
									} else if (arr[j].getClass().getTypeName().contains("java.lang")) {
										continue;
									} else if (arr[j].getClass().getTypeName().contains("List")
											|| arr[j].getClass().getTypeName().contains("[]")
											|| arr[j].getClass().getTypeName().contains("Map")
											|| arr[j].getClass().getTypeName().contains("Set")
											|| arr[j].getClass().getTypeName().contains("Collection")
											|| arr[j].getClass().getTypeName().contains("kr.co.sppartners")) {
										objectHTMLFilter(arr[j], arr[j].getClass());
									} else {
										continue;
									}
								}
							}
						} else if (valueObjectFields[i].getGenericType().getTypeName().contains("Map")) {
							if (valueObjectFields[i].getGenericType().getTypeName().contains("Map$Entry")
								|| valueObjectFields[i].getGenericType().getTypeName().contains("Map$Node")) {
								Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) valueObjectFields[i].get(targetObject);
								if (entry != null) {
									if (entry.getValue() == null) {
										continue;
									} else if (entry.getValue().getClass().getTypeName().indexOf(".") < 0) {
										continue;
									} else if (entry.getValue().getClass().getTypeName().equals("java.lang.String")) {
										String value = StringUtil.toString(entry.getValue());
										entry.setValue(StringUtil.cleanXSS(value));
									} else if (entry.getValue().getClass().getTypeName().contains("java.lang")) {
										continue;
									} else if (entry.getValue().getClass().getTypeName().contains("List")
											|| entry.getValue().getClass().getTypeName().contains("[]")
											|| entry.getValue().getClass().getTypeName().contains("Map")
											|| entry.getValue().getClass().getTypeName().contains("Set")
											|| entry.getValue().getClass().getTypeName().contains("Collection")
											|| entry.getValue().getClass().getTypeName().contains("kr.co.sppartners")) {
										objectHTMLFilter(entry.getValue(), entry.getValue().getClass());
									} else {
										continue;
									}
								}
							} else {
								Map<Object, Object> map = (Map<Object, Object>) valueObjectFields[i].get(targetObject);
								
								if (map != null) {
									for (Object key : map.keySet()) {
										if (map.get(key) == null) {
											continue;
										} else if (map.get(key).getClass().getTypeName().indexOf(".") < 0) {
											continue;
										} else if (map.get(key).getClass().getTypeName().equals("java.lang.String")) {
											String value = StringUtil.toString(map.get(key));
											map.put(key, StringUtil.cleanXSS(value));
										} else if (map.get(key).getClass().getTypeName().contains("java.lang")) {
											continue;
										} else if (map.get(key).getClass().getTypeName().contains("List")
												|| map.get(key).getClass().getTypeName().contains("[]")
												|| map.get(key).getClass().getTypeName().contains("Map")
												|| map.get(key).getClass().getTypeName().contains("Set")
												|| map.get(key).getClass().getTypeName().contains("Collection")
												|| map.get(key).getClass().getTypeName().contains("kr.co.sppartners")) {
											objectHTMLFilter(map.get(key), map.get(key).getClass());
										} else {
											continue;
										}
									}
								}
							}
							
						} else if (valueObjectFields[i].getGenericType().getTypeName().contains("Set")) {
							Set<Object> set = (Set<Object>) valueObjectFields[i].get(targetObject);
							if (set != null) {
								for (Object obj : set) {
									if (obj == null) {
										continue;
									} else if (obj.getClass().getTypeName().indexOf(".") < 0) {
										continue;
									} else if (obj.getClass().getTypeName().equals("java.lang.String")) {
										set.remove(obj);
										String value = StringUtil.toString(obj);
										set.add(StringUtil.cleanXSS(value));
									} else if (obj.getClass().getTypeName().contains("java.lang")) {
										continue;
									} else if (obj.getClass().getTypeName().contains("List")
											|| obj.getClass().getTypeName().contains("[]")
											|| obj.getClass().getTypeName().contains("Map")
											|| obj.getClass().getTypeName().contains("Set")
											|| obj.getClass().getTypeName().contains("Collection")
											|| obj.getClass().getTypeName().contains("kr.co.sppartners")) {
										objectHTMLFilter(obj, obj.getClass());
									} else {
										continue;
									}
								}
							}
						} else if (valueObjectFields[i].getGenericType().getTypeName().contains("java.util.Collection")) {
							Collection<Object> collection = (Collection<Object>) valueObjectFields[i].get(targetObject);
							if (collection != null) {
								for (Object obj : collection) {
									if (obj == null) {
										continue;
									} else if (obj.getClass().getTypeName().indexOf(".") < 0) {
										continue;
									} else if (obj.getClass().getTypeName().equals("java.lang.String")) {
										collection.remove(obj);
										String value = StringUtil.toString(obj);
										collection.add(StringUtil.cleanXSS(value));
									} else if (obj.getClass().getTypeName().contains("java.lang")) {
										continue;
									} else if (obj.getClass().getTypeName().contains("List")
											|| obj.getClass().getTypeName().contains("[]")
											|| obj.getClass().getTypeName().contains("Map")
											|| obj.getClass().getTypeName().contains("Set")
											|| obj.getClass().getTypeName().contains("Collection")
											|| obj.getClass().getTypeName().contains("kr.co.sppartners")) {
										objectHTMLFilter(obj, obj.getClass());
									} else {
										continue;
									}
								}
							}
						}
						
						else {
							continue;
						}
					} else {
						continue;
					}
				}  catch (Exception e) {
					e.printStackTrace();
					//System.out.println("valueObjectFields[i].getGenericType().getTypeName() 에러발생 : " + valueObjectFields[i].getGenericType().getTypeName());
				}
			}
		}
		
		//System.out.println("targetObjectClass.getSimpleName() : " + targetObjectClass.getSimpleName());
		
		if (targetObjectClass.getSimpleName() != "CommonVO" && targetObjectClass.getSuperclass() != null) {
			try {
				//목표객체의 상위클래스에서 변수 찾아서 값넣어주기 위해 재귀호출함 
				objectHTMLFilter(targetObject, targetObjectClass.getSuperclass());
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		} else {
			return;
		}
	}


	/**
	 * @author 서영석
	 * @since 2019.12.09
	 * 
	 * 데이터 셋 목록 Convert LinkedHashMap<String, Object> to List<String>
	 */
	public static List<List<String>> rowDataMapToList (List<LinkedHashMap<String, Object>> rowMapData) throws Exception {
		List<List<String>> rowData = new ArrayList<List<String>>();
		for (int i = 0; i < rowMapData.size(); i++) {
			List<String> row = new ArrayList<String>();
			LinkedHashMap<String, Object> mapdata = rowMapData.get(i);
	        for( String key : mapdata.keySet() ){
	        	if (mapdata.get(key) == null) {
	        		row.add(null);//null값 대체
	        	} else {
	        		row.add(mapdata.get(key).toString());
	        	}
	        }
	        rowData.add(row);
		}
		return rowData;
	}

	public static int getRandomInt (int min, int max) {
		return (int) Math.round(Math.random() * (max - min));// + min;
	}


	/**
	 * 작성일 : 210316 작성자 : 서영석
	 *
	 * Exception -> String
	 *
	 * @param e : Exception
	 */
	public static String exceptionToString(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return "[Error] " + errors.toString();
	}
}
