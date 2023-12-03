package common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 서영석
 * @since 2019.11.13
 * 
 * 문자열과 관련된 기능을 정의 해놓은 Util입니다.
 */
public class StringUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);
	
	public static final String NULL_TEXT = "NULL";
	
	public static String toString(Object obj) {
		if (obj == null) {
			return null;
		} else {
			try {
				return obj.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public static String toStringNotNull(Object obj) {
		if (obj == null) {
			return "";
		} else {
			try {
				return obj.toString();
			} catch (Exception e) {
				return "";
			}
		}
	}
	
	/**
     * @author 서영석
     * @since 2020.11.26
     * 
     * 객체를 문자열로 바꾼 후, 문자열 길이 반환
     */
	public static int stringLength(Object obj) {
		if (obj == null) {
			return 0;
		} else {
			try {
				return obj.toString().length();
			} catch (Exception e) {
				return 0;
			}
		}
	}
	
	/**
     * @author 서영석
     * @since 2020.11.26
     * 
     * 문자열이 Null or null or NULL인 경우 실제 null값 세팅 
     */
	public static boolean isNullText(String text) {
		if (isEmpty(text) == false && text.toUpperCase().equals(NULL_TEXT)) {
			return true;
		} else {
			return false;
		}
	}
	
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 빈 문자열 검사
     */
    public static boolean isEmpty(String text) {
        return text == null || text.trim().length() == 0;
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * indexOf - 문자 검색 후, 해당 문자의 위치(index)반환
     */
    public static int indexOf(String text, String searchText) {
        if (text == null || searchText == null) {
            return -1;
        }
        return text.indexOf(searchText);
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * lastIndexOf - 문자 검색 후, 해당 문자의 위치(index)반환
     */
    public static int lastIndexOf(String text, String searchText) {
    	if (text == null || searchText == null) {
            return -1;
        }
        return text.lastIndexOf(searchText);
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * substringBetween - 특정 문자열 사이에 값을 뽑아내는 메서드
     */
    public static String substringBetween(String text, String startText, String endText) {
		if (isEmpty(text) == true || isEmpty(startText) == true || isEmpty(endText) == true) {
			return null;
		}
		text = text.toLowerCase();
		startText = startText.toLowerCase();
		endText = endText.toLowerCase();
		
		int start = text.indexOf(startText);
		if (start != -1) {
			int end = text.indexOf(endText, start + startText.length());
			if (end != -1) {
				return text.substring(start + startText.length(), end);
			}
		}
		return null;
	}

    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 모든 공백 제거
     */
    public static String removeSpace(String text) {
        if (isEmpty(text)) {
            return text;
        }
        int length = text.length();
        char[] newCharList = new char[length];
        int count = 0;
        for (int i = 0; i < length; i++) {
            if (Character.isWhitespace(text.charAt(i)) == false) {
            	newCharList[count++] = text.charAt(i);
            }
        }
        if (count == length) {
            return text;
        }

        return new String(newCharList, 0, count);
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * html의 특수문자를 표현하기 위해
     */
    public static String textToHtml(String text) {
    	text = text.replaceAll("&lt;", "<");
    	text = text.replaceAll("&gt;", ">");
    	text = text.replaceAll("&amp;", "&");
    	text = text.replaceAll("&nbsp;", " ");
    	text = text.replaceAll("&apos;", "\'");
    	text = text.replaceAll("&quot;", "\"");
		return  text;
	}

    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * Html 코드가 들어간 문서를 표시할때 태그에 손상없이 보이기 위한 메서드
     */
	public static String htmlToText(String text) {
		StringBuffer textBuffer = new StringBuffer("");

		char character;
		int length = text.length();

		for (int i = 0; i < length; i++) {
			character = (char) text.charAt(i);

			switch (character) {
				case '<':
					textBuffer.append("&lt;");
					break;
				case '>':
					textBuffer.append("&gt;");
					break;
				case '"':
					textBuffer.append("&quot;");
					break;
				case 10:
					textBuffer.append("<br>");
					break;
				case ' ':
					textBuffer.append("&nbsp;");
					break;
				//case '&' :
				//strTxt.append("&amp;");
				//break;
				default:
					textBuffer.append(character);
			}
		}

		text = textBuffer.toString();
		return text;
	}
	
	public static String cleanXSS (String value) {
		return value
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;")
				.replaceAll("\\(", "&#40;")
				.replaceAll("\\)", "&#41;")
				.replaceAll("'", "&#39;")
				.replaceAll("eval\\((.*)\\)", "")
				.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"")
				.replaceAll("(?!)script", "")
				.replaceAll("(?!)iframe", "")
				.replaceAll("\'", "")
				.replaceAll(";", "");
	}

	public static String cleanSQL (String value) {
		return value
				.replaceAll("(?!)SELECT", "")
				.replaceAll("(?!)INSERT", "")
				.replaceAll("(?!)DELETE", "")
				.replaceAll("(?!)UPDATE", "")
				.replaceAll("(?!)CREATE", "")
				.replaceAll("(?!)REPLACE", "")
				.replaceAll("(?!)DROP", "")
				.replaceAll("(?!)LIMIT", "")
				.replaceAll("(?!)AND", "")
				.replaceAll("(?!)OR", "")
				.replaceAll("(?!)IN", "")
				.replaceAll("(?!)JOIN", "")
				.replaceAll("(?!)IF", "")
				.replaceAll("(?!)CASE", "")
				.replaceAll("(?!)IS", "")
				.replaceAll("(?!)NULL", "")
				.replaceAll("(?!)ALL", "")
				.replaceAll("(?!)KILL", "")
				.replaceAll("(?!)UNION", "")
				.replaceAll("(?!)BETWEEN", "")
				.replaceAll("(?!)LIKE", "")
				.replaceAll("(?!)ADD", "")
				.replaceAll("(?!)DATABASE", "")
				.replaceAll("(?!)ALTER", "")
				.replaceAll("(?!)VALUE", "")

				.replaceAll("'", "")
				.replaceAll("`", "")
				.replaceAll("=", "")
				.replaceAll(">", "")
				.replaceAll("<", "")
				.replaceAll("/", "")
				.replaceAll("#", "")
				.replaceAll("--", "")
				.replaceAll(";", "");
	}

    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열을 지정한 분리자에 의해 배열로 리턴하는 메서드.
     */
	public static ArrayList<String> split(String text, String separator) throws NullPointerException {
		ArrayList<String> words = new ArrayList<String>();
		if (isEmpty(text) == false && separator != null) {
			int startIndex = -1;
	    	int endIndex = -1;
	        while(true) {
	        	startIndex++;
	        	endIndex = text.indexOf(separator, startIndex);
	        	if (endIndex > -1) {
	        		words.add(text.substring(startIndex, endIndex));
	        		startIndex = endIndex;
	        	} else {
	        		words.add(text.substring(startIndex, text.length()));
	        		break;
	        	}
	        }
	        return words;
		} else {
			if (isEmpty(text) == false) {
				words.add(text);
			}
	        return words;
		}
    }
	
	/**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열을 지정한 여러개의 분리자에 의해 배열로 리턴하는 메서드.
     */
	public static ArrayList<String> split(String text, String[] separator) throws NullPointerException {
		ArrayList<String> words = new ArrayList<String>();
		if (isEmpty(text) == false && separator != null && separator.length > 0) {
			words.add(text);
			for (int i = 0; i < separator.length; i++) {
				ArrayList<String> subWords = new ArrayList<String>();
				for (int j = 0; j < words.size(); j++) {
					subWords.addAll(split(words.get(j), separator[i]));
				}
				words = subWords;
			}
			return words;
		} else {
			return words;
		}
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열을 지정한 분리자에 의해 지정된 길이의 배열로 리턴하는 메서드.
     */
    public static ArrayList<String> split(String text, String separator, int maxLength) throws NullPointerException {
    	ArrayList<String> list = new ArrayList<String>(); 
    	int startIndex = -1;
    	int endIndex = -1;
        while(true) {
        	startIndex++;
        	endIndex = text.indexOf(separator, startIndex);
        	if (endIndex > -1) {
        		list.add(text.substring(startIndex, endIndex));
        		startIndex = endIndex;
        	} else {
        		list.add(text.substring(startIndex, text.length()));
        		break;
        	}
        	
        	if (list.size() >= maxLength) {
        		break;
        	}
        }
        return list;
    }

    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 소문자 변환
     */
    public static String lowerCase(String text) {
        if (isEmpty(text) == true) {
            return text;
        } else {
        	return text.toLowerCase();
        }
    }

    /**
     * 대문자 변환
     */
    public static String upperCase(String text) {
    	if (isEmpty(text) == true) {
            return text;
        } else {
        	return text.toUpperCase();
        }
    }

    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 현재날짜(년,월,일)를 구하는 기능
     */
    public static String getToday() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return dateFormat.format(timestamp.getTime());
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 현재날짜(년,월,일)를 구하는 기능
     */
    public static String getToday(String pattern) {
    	String defaultPattern = "yyyy-MM-dd";
    	if (isEmpty(pattern) == true) {
    		pattern = defaultPattern;
    	}
    	
    	SimpleDateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
		} catch (Exception e) {
			dateFormat = new SimpleDateFormat(defaultPattern, Locale.KOREA);
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return dateFormat.format(timestamp.getTime());
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 현재날짜(년,월,일)를 구하는 기능
     */
    public static String getToday(String yearSuffix, String monthSuffix, String daySuffix) {
    	String defaultPattern = "yyyy년MM월dd일";
    	if (isEmpty(yearSuffix) == true) {
    		yearSuffix = "";
    	}
    	if (isEmpty(monthSuffix) == true) {
    		monthSuffix = "";
    	}
    	if (isEmpty(daySuffix) == true) {
    		daySuffix = "";
    	}
    	
    	String pattern = "yyyy" + yearSuffix + "MM" + monthSuffix + "dd" + daySuffix;
    	
    	SimpleDateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
		} catch (Exception e) {
			dateFormat = new SimpleDateFormat(defaultPattern, Locale.KOREA);
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return dateFormat.format(timestamp.getTime());
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 17자리의 현재일시를 구하는 기능
     */
    public static String getDateTime() {
		// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyyyMMddHHmmssSSS";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return dateFormat.format(timestamp.getTime());
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 원하는 패턴의 현재일시 구하는 기능
     */
    public static String getDateTime(String pattern) {
    	// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
    	String defaultPattern = "yyyyMMddHHmmssSSS";
    	if (isEmpty(pattern)) {
    		pattern = defaultPattern;
    	}
		SimpleDateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
		} catch (Exception e) {
			dateFormat = new SimpleDateFormat(defaultPattern, Locale.KOREA);
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return dateFormat.format(timestamp.getTime());
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 현재 일시 - addDay => 원하는 패턴의 일시를 구하는 기능 
     */
    public static String getDateTime(String pattern, int addDay) {
    	// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
    	String defaultPattern = "yyyyMMddHHmmssSSS";	
    	if (pattern == null) {
    		pattern = defaultPattern;
    	}
		SimpleDateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
		} catch (Exception e) {
			dateFormat = new SimpleDateFormat(defaultPattern, Locale.KOREA);
		}
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, addDay);
		Date date = cal.getTime();
		return dateFormat.format(date.getTime());
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 현재 일시 - addDay => 원하는 패턴의 일시를 구하는 기능 
     */
    public static String getDateTime(String pattern, int addDay, int addHour, int addMin, int addSec) {
    	// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
    	String defaultPattern = "yyyyMMddHHmmssSSS";	
    	if (pattern == null) {
    		pattern = defaultPattern;
    	}
		SimpleDateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
		} catch (Exception e) {
			dateFormat = new SimpleDateFormat(defaultPattern, Locale.KOREA);
		}
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, addDay);
		cal.add(Calendar.HOUR, addHour);
		cal.add(Calendar.MINUTE, addMin);
		cal.add(Calendar.SECOND, addSec);
		Date date = cal.getTime();
		return dateFormat.format(date.getTime());
    }

	/**
	 * @author 서영석
	 * @since 2021.12.05
	 *
	 * String -> Date
	 */
	public static Date stringToDate (String date, String pattern) {
		Date result = null;
		try {
			SimpleDateFormat transFormat = new SimpleDateFormat(pattern);
			result = transFormat.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author 서영석
	 * @since 2021.12.05
	 *
	 * Date -> String
	 */
	public static String dateToString (Date date, String pattern) {
		String result = null;
		try {
			SimpleDateFormat transFormat = new SimpleDateFormat(pattern);
			result = transFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 현재 일시(17자리)와, 랜덤숫자(4자리)를 이용하여 키값 생성
     */
    public static String getCreateKey (String prefix) {
		int random = new Random().nextInt(9999);
		String result = prefix + "_" + getDateTime() + "_" + numberToText(random, 4);
		return result;
    }

	/**
	 * @author 서영석
	 * @since 2019.11.13
	 *
	 * 현재 일시(17자리)와, 랜덤숫자(4자리)를 이용하여 키값 생성
	 */
	public static String getRandomKey (int length) {
		char[][] characters = {
			{'!', '@', '#', '$', '%', '^', '&', '*', '-', '_', '+', '='},
			{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'Z', 'Y', 'Z'},
			{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'z', 'y', 'z'},
			{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'}
		};

		String randomText = "";
		for (int i = 0; i < length; i++) {
			int randomIndex1 = CommonUtil.getRandomInt(0, characters.length - 1);
			char[] chars = characters[randomIndex1];
			int randomIndex2 = CommonUtil.getRandomInt(0, chars.length - 1);
			randomText += chars[randomIndex2];
		}

		return randomText;
	}
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열이 Date문자열(yyyy-MM-dd)로 포맷 가능한지
     * text: 문자열
     * pattern: 문자열의 날짜 패턴
     */
    public static boolean isDate(String text, String pattern) {
    	try {
    		Date date = new SimpleDateFormat(pattern).parse(text);
    		text = new SimpleDateFormat("yyyy-MM-dd").format(date);
        	return true;
    	} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			return false;
		}
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열을 날짜형태로 Convert
     * text: 문자열
     * pattern: 문자열의 날짜 패턴
     * newPattern: 해당 문자열을 Converting할 날짜 패턴 
     */
    public static String textToDateText (String text, String pattern, String newPattern) {
		String defaultPattern = "yyyy-MM-dd";
		if (isEmpty(newPattern) == true) {
			newPattern = defaultPattern;
    	}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = new Date();
		try {
			date = dateFormat.parse(text);
			dateFormat.applyPattern(newPattern);
			return dateFormat.format(date);
        } catch (Exception e) {
            //e.printStackTrace();
            return text;
        }
	}
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 숫자 -> 문자열 -> 문자열 길이가 length보다 작을 때, length길이 만큼될 수 있도록 앞에 '0'을 붙여줌
     */
    public static String numberToText (int number, int length) {
    	String text = Integer.toString(number);
    	if (text.length() < length) {
            int emptyLength = length - text.length();
            for (int i = 0; i < emptyLength; i++) {
            	text = "0" + text;
            }
        }
        return text;
    }

    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열이 지정한 길이를 초과했을때 해당 문자열을 삭제하는 메서드
     * @param text 원본 문자열 배열
     * @param maxLength 지정길이
     * @return 지정길이로 자른 문자열
     */
    public static String cutString(String text, int maxLength) {
        String result = null;
        if (text != null) {
            if (text.length() > maxLength) {
                result = text.substring(0, maxLength);
            } else
                result = text;
        }
        return result;
    }
	

    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열이 지정한 길이를 초과했을때 지정한길이에다가 해당 문자열을 붙여주는 메서드.
     * @param text 원본 문자열 배열
     * @param addText 더할문자열
     * @param maxLength 지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String text, String addText, int maxLength) {
        String result = null;
        if (text != null) {
            if (text.length() > maxLength) {
            	result = text.substring(0, maxLength) + addText;
            } else
            	result = text;
        }
        return result;
    }

    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * <p>기준 문자열에 포함된 모든 대상 문자(char)를 제거한다.</p>
     *
     * <pre>
     * StringUtil.remove(null, *)       = null
     * StringUtil.remove("", *)         = ""
     * StringUtil.remove("queued", 'u') = "qeed"
     * StringUtil.remove("queued", 'z') = "queued"
     * </pre>
     *
     * @param text  입력받는 기준 문자열
     * @param remove  입력받는 문자열에서 제거할 대상 문자열
     * @return 제거대상 문자열이 제거된 입력문자열. 입력문자열이 null인 경우 출력문자열은 null
     */
    public static String remove(String text, char remove) {
        if (isEmpty(text) || text.indexOf(remove) == -1) {
            return text;
        }
        char[] chars = text.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        
        return new String(chars, 0, pos);
    }


    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 원본 문자열의 포함된 특정 문자열을 새로운 문자열로 변환하는 메서드
     * @param text 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replace(String text, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = text;
        String srcStr  = text;

        while (srcStr.indexOf(subject) >= 0) {
            preStr = srcStr.substring(0, srcStr.indexOf(subject));
            nextStr = srcStr.substring(srcStr.indexOf(subject) + subject.length(), srcStr.length());
            srcStr = nextStr;
            rtnStr.append(preStr).append(object);
        }
        rtnStr.append(nextStr);
        return rtnStr.toString();
    }

    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 원본 문자열의 포함된 특정 문자열 첫번째 한개만 새로운 문자열로 변환하는 메서드
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열 / source 특정문자열이 없는 경우 원본 문자열
     */
    public static String replaceOnce(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        if (source.indexOf(subject) >= 0) {
            preStr = source.substring(0, source.indexOf(subject));
            nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
            rtnStr.append(preStr).append(object).append(nextStr);
            return rtnStr.toString();
        } else {
            return source;
        }
    }

    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * <code>subject</code>에 포함된 각각의 문자를 object로 변환한다.
     *
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replaceChar(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        String srcStr  = source;

        char chA;

        for (int i = 0; i < subject.length(); i++) {
            chA = subject.charAt(i);

            if (srcStr.indexOf(chA) >= 0) {
                preStr = srcStr.substring(0, srcStr.indexOf(chA));
                nextStr = srcStr.substring(srcStr.indexOf(chA) + 1, srcStr.length());
                srcStr = rtnStr.append(preStr).append(object).append(nextStr).toString();
            }
        }

        return srcStr;
    }

    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 문자열을 다양한 문자셋(EUC-KR[KSC5601],UTF-8..)을 사용하여 인코딩하는 기능 역으로 디코딩하여 원래의 문자열을
     * 복원하는 기능을 제공함 String temp = new String(문자열.getBytes("바꾸기전 인코딩"),"바꿀 인코딩");
     * String temp = new String(문자열.getBytes("8859_1"),"KSC5601"); => UTF-8 에서
     * EUC-KR
     *
     * @param text - 문자열
     * @param encoding - 원래의 인코딩된 값
     * @param decoding - 디코딩할 문자값
     * @return 인(디)코딩 문자열
     * @see
     */
    public static String textDecoding(String text, String encoding, String decoding) {
		if (text == null) {
			return null;
		}
	
		try {
			text = new String(text.getBytes(encoding), decoding);
		} catch (UnsupportedEncodingException e) {
			text = null;
		}
	
		return text;
    }
    
    /**
     * @author 서영석
     * @since 2019.11.13
     * 
     * 특정 길이 만큼 문자열 뽑기 
     */
    public static String fixedText (String text, int maxLength) {
		if (text == null) text = "";
		
		text = text.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		if (text.length() > maxLength) {
			text = text.substring(0, maxLength) + "...(중략)";
		} else if (text.length() < maxLength) {
			int emptySize = maxLength - text.length();
			if (emptySize == 1) {
				text += " ";
			} else {
				int halfEmptySize = emptySize / 2;
				for (int i = 0; i < halfEmptySize; i++) {
					text = " " + text + " ";
				}
				if (0 < emptySize % 2) {
					text += " ";
				}
			}
		}
		
		return text;
	}
    
    /**
     * @author 서영석
     * @since 2021.09.28
     * 
     * 스네이크 표기법 => 카멜 표기법 
     */
    public static String snakeToCamel (String text) {
    	String camelText = "";
    	
    	if (text != null) {
    		text = removeSpace(text);
    		text = text.toLowerCase();
    		
    		String[] texts = text.split("_");
    		if (texts.length > 1) {
    			camelText += texts[0];
    			for (int i = 1; i < texts.length; i++) {
    				if (texts[i].length() > 0) {
    					char[] chars = texts[i].toCharArray();
        				chars[0] = Character.toUpperCase(chars[0]);
        				camelText += new String(chars);
    				} else {
    					continue;
    				}
    			}
    		} else {
    			camelText = text;
    		}
    	}
    	
    	return camelText;
    }
    
    /**
     * @author 서영석
     * @since 2021.09.28
     * 
     * 카멜 표기법 => 스네이크 표기법
     */
    public static String camelToSnake (String text, boolean isUpperCase) {
    	String snakeText = "";
    	
    	if (text != null) {
    		text = removeSpace(text);
    		
    		String[] texts = text.split("[A-Z]");
			for (int i = 0; i < texts.length; i++) {
				if (texts[i].length() > 0) {
					snakeText += (texts[i] + "_");
					int currentIndex = snakeText.length() - 1 - i;
					if (currentIndex <= text.length() - 1) {
						//System.out.println("text.toCharArray()[currentIndex] : " + text.toCharArray()[currentIndex]);
						snakeText += text.toCharArray()[currentIndex];
					}
				} else {
					continue;
				}
			}

			try {
				snakeText = snakeText.substring(0, snakeText.length() - 1);
			} catch (StringIndexOutOfBoundsException e) {
				snakeText = text;
			}
    		
    		if (isUpperCase == true) {
    			snakeText = snakeText.toUpperCase();
    		} else {
    			snakeText = snakeText.toLowerCase();
    		}
    		
    	}
    	
    	return snakeText;
    }

	/**
	 * @author 서영석
	 * @since 2021.03.24
	 *
	 * 문자열 나누기
	 *
	 * @param text - 문자열
	 * @param bufferSize - 문자열을 나눌 기준 값
	 *
	 * @throws Exception
	 */
	public static List<String> textSeparation (String text, int bufferSize) throws Exception {
		List<String> result = new ArrayList<String>();

		if (text == null) {
			throw new Exception("문자열이 없음(null)");
		}

		if (bufferSize <= 0) {
			throw new Exception("bufferSize는 0초과이어야 합니다.");
		}

		int unitSize = text.length() / bufferSize;
		int remainSize = text.length() % bufferSize;

		for (int i = 0; i < unitSize; i++) {
			int startIndex = i * bufferSize;
			int endIndex = (i + 1) * bufferSize;
			//System.out.println(startIndex + ", " + endIndex);
			result.add(text.substring(startIndex, endIndex));
		}
		if (remainSize > 0) {
			result.add(text.substring(unitSize * bufferSize, text.length()));
		}

		return result;
	}
}
