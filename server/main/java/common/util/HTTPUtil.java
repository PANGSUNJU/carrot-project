package common.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HTTPUtil {

	/**
	 * @author 서영석
	 * @throws Exception 
	 * @since 2020.07.21
	 * 
	 * [GET] HTTP 통신
	 */
	public synchronized String httpGetConnection (String urlText) {
		return httpGetConnection(urlText, null, null);
	}
	public synchronized String httpGetConnection (String urlText, Map<String, Object> parameter) {
		return httpGetConnection(urlText, parameter, null);
	}
	public synchronized String httpGetConnection (String urlText, Map<String, Object> parameter, Map<String, String> headers) {
		StringBuffer response = new StringBuffer();
		
		URL url = null;
		try {
			if (parameter != null && parameter.isEmpty() == false) {
				urlText += "?" + createUrlQuery(parameter, "UTF-8"); 
				System.out.println("url@ : " + urlText);
			}
			//System.out.println("=====url : "+urlText);
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpURLConnection httpCon = null;
		try {

			/* Connection */
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36");
			if (headers != null && headers.isEmpty() == false) {
				for (String key : headers.keySet()) {
					httpCon.setRequestProperty(key, headers.get(key));
				}
			}
			httpCon.setConnectTimeout(1000 * 60);//http통신 최대 커넥션 시간(1분)
			httpCon.setReadTimeout(1000 * 60);//http통신 커넥션 이후, 데이터를 받아오는데 걸리는 최대 시간(1분)
			httpCon.setDoInput(true);//받아올 데이터가 있을 때, 사용
			
			//HTTP Request 결과 코드
			int responseCode = httpCon.getResponseCode();

			//Response 결과 데이터 받기
			BufferedReader input = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "UTF-8"));//"UTF-8"

			//Response 결과 데이터를 문자열로 만들기
			String result = "";
			while ((result = input.readLine()) != null) {
				System.out.println("result : " + result);
				response.append(result);
			}

			//InputStream, BufferedReader 종료
			input.close();

			//HTTP Connection 종료
			httpCon.disconnect();

			if (responseCode != 200) {
				System.out.println("[HTTP " + responseCode + " 에러]" + url);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//문자열로된 Response 결과 return
		return response.toString();
	}
	
	/**
	 * @author 서영석
	 * @throws Exception 
	 * @since 2020.07.21
	 * 
	 * [POST] HTTP 통신
	 */
	public synchronized String httpPostConnection (String urlText) {
		return httpGetConnection(urlText, null, null);
	}
	public synchronized String httpPostConnection (String urlText, Map<String, Object> parameter) {
		return httpGetConnection(urlText, parameter, null);
	}
	public synchronized String httpPostConnection (String urlText, Map<String, Object> parameter, Map<String, String> headers) {
		StringBuffer response = new StringBuffer();
		
		URL url = null;
		try {
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpURLConnection httpCon = null;
		try {
			/* Connection */
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36");
			httpCon.setConnectTimeout(1000 * 60);//http통신 최대 커넥션 시간(1분)
			httpCon.setReadTimeout(1000 * 60);//http통신 커넥션 이후, 데이터를 받아오는데 걸리는 최대 시간(1분)
			httpCon.setDoInput(true);//받아올 데이터가 있을 때, 사용

			
			//보낼 파라메터 데이터가 있을 때
			if (parameter != null && parameter.isEmpty() == false) {
				String dataQuery = createUrlQuery(parameter, null);
				
				httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//보낼 데이터의 형태
				httpCon.setRequestProperty("Content-Length", String.valueOf(dataQuery.length()));
				if (headers != null && headers.isEmpty() == false) {
					for (String key : headers.keySet()) {
						httpCon.setRequestProperty(key, headers.get(key));
					}
				}
				httpCon.setDoOutput(true);
				
				DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());
				wr.writeBytes(dataQuery);
				wr.flush();
				wr.close();
			}
			
			//HTTP Request 결과 코드
			int responseCode = httpCon.getResponseCode();
			/*if (responseCode == 200) {
				//Response 결과 데이터 받기
				BufferedReader input = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
				
				//Response 결과 데이터를 문자열로 만들기
				String result = null;
				while ((result = input.readLine()) != null) {
					response.append(result);
				}
				
				//InputStream, BufferedReader 종료
				input.close();
				
				//HTTP Connection 종료
				httpCon.disconnect();
			} else {
				System.out.println("[HTTP " + responseCode + " 에러]" + url);
			}*/

			//Response 결과 데이터 받기
			BufferedReader input = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));

			//Response 결과 데이터를 문자열로 만들기
			String result = "";
			while ((result = input.readLine()) != null) {
				response.append(result);
			}

			//InputStream, BufferedReader 종료
			input.close();

			//HTTP Connection 종료
			httpCon.disconnect();

			if (responseCode != 200) {
				System.out.println("[HTTP " + responseCode + " 에러]" + url);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//문자열로된 Response 결과 return
		return response.toString();
	}
	
	/**
	 * @author 서영석
	 * @throws Exception 
	 * @since 2020.07.21
	 * 
	 * 파라메터 데이터를 HTTP통신을 위한 문자열로 변환시켜주는 메서드
	 * Map -> String
	 */
	public String createUrlQuery (Map<String, Object> parameter, String encoding) {
		if (parameter.isEmpty() == true) {
			return "";
		} else {
			StringBuilder query = new StringBuilder();
		    for(Map.Entry<String,Object> param : parameter.entrySet()) {
		        try {
		        	if(query.length() > 0) {
		        		query.append('&');
		        	}
		        	
		        	if (StringUtil.isEmpty(encoding) == true) {
		        		query.append(param.getKey());
			        	query.append('=');
			        	query.append(param.getValue());
		        	} else {
		        		query.append(URLEncoder.encode(param.getKey(), encoding));
			        	query.append('=');
			        	query.append(URLEncoder.encode(String.valueOf(param.getValue()), encoding));
		        	}
		        	
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		    }
		    
		    return query.toString();
		}
	}
}
