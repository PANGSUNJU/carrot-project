package common.util.socket;

//import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * @author 서영석
 * @since 2019.12.11
 * 
 * 파일 Util 입니다. 
 */
public class FileUtil {

	//이미지 파일 resizing 여부
	public static boolean IMAGE_FILE_RESIZING = false;
	
	//resizing이 필요한 이미지 파일 크기
	public static int IMAGE_FILE_NEED_RESIZING_SIZE = 1024 * 1024;//1MB
	
	//resizing시 기본 이미지 크기
	public static double IMAGE_FILE_RESIZING_RATE = 0.5;
	
	public static byte[] fileToByte (File file) {
		//파일 byte를 담을 공간
		byte[] bytes = null;
		
		FileInputStream fileInput = null;
		try {
			// 파일 읽을 줄비
			fileInput = new FileInputStream(file);
			// 바이너리 공간 확보
			bytes = new byte[fileInput.available()];
			// 파일 -> byte
			fileInput.read(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileInput != null) {
				try {
					fileInput.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		return bytes;
	}
	
	public static byte[] base64Encoding(byte[] buffer) {
	    return Base64.getEncoder().encode(buffer);
	}
	
	public static byte[] base64Decoding(byte[] buffer) {
	    return Base64.getDecoder().decode(buffer);
	}
	
	public static boolean isImageFile (File file) {
		boolean result = false;
		
		try {
			String mimeType = null;//new MimetypesFileTypeMap().getContentType(file);
			String type = mimeType.split("/")[0];
			if (type.equals("image") == true) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	public static byte[] imageFileResizeToByte (File imageFile) {
		//파일 byte를 담을 공간
		byte[] bytes = null;
		
		String fileName = imageFile.getName();
		//String filePath = imageFile.getPath();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		//1MB보다 클 때
		System.out.println("file Size : " + imageFile.length());
		if (imageFile.length() > IMAGE_FILE_NEED_RESIZING_SIZE) {
			BufferedImage image = null;
			BufferedImage resizedImage = null;
			ByteArrayOutputStream baos = null;
			try {
				image = ImageIO.read(imageFile);
				if (image != null) {
					/*이미지 파일 일 때*/
					int newWidth = (int) (image.getWidth() * IMAGE_FILE_RESIZING_RATE);
					int newHeight = (int) (image.getHeight() * IMAGE_FILE_RESIZING_RATE);
					System.out.println("resizing width : " + image.getWidth() + " ->" + newWidth);
					System.out.println("resizing height : " + image.getHeight() + " ->" + newHeight);
					
					// 이미지 리사이즈
		            // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
		            // Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
		            // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
		            // Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
		            // Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
					Image resizeImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
					
					int imageType = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
					resizedImage = new BufferedImage(newWidth, newHeight, imageType);
			        Graphics2D g = resizedImage.createGraphics();
			        g.drawImage(resizeImage, 0, 0, null);
			        g.dispose();
			        
			        // resizing 파일 -> byte
			        baos = new ByteArrayOutputStream();
			        ImageIO.write(resizedImage, extension, baos);
			        bytes = baos.toByteArray();
				}
			} catch (IOException e) {
				/*이미지 파일이 아님*/
				e.printStackTrace();
			} finally {
				if (baos != null) {
					try {
						baos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if (bytes == null) {
			bytes = fileToByte(imageFile);
		}
		
		return bytes;
	}
	
	/**
	 * 작성일 : 210316 작성자 : 서영석
	 * 
	 * 로그파일 남기기
	 * 
	 * @param massage : 로그
	 */
	public static void writeLogFile(String massage, String fileName, String filePath) {
		BufferedWriter fw = null;
		try {
			// 현재 일시
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateTime = dateFormat.format(new Date());
			// 현재 일자
			String date = dateTime.split(" ")[0];

			// 현재 클래스의 디렉토리 (절대경로)
			// String currentPath = System.getProperty("user.dir");
			// [현재 클래스의 디렉토리 + 서브 폴더 디렉토리]의 존재 여부
			File dir = new File(filePath);
			if (dir.exists() == false) {
				dir.mkdir();// 존재하지 않을 때 폴더 생성
			}
			// 로그 파일명(socket_server_log_[현재 일자].txt)
			String newFileName = fileName + "_" + date + ".txt";

			// 로그 파일의 절대 경로
			String fileFullPath = filePath + File.separator + newFileName;

			/* 로그 남기기 */
			// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
			fw = new BufferedWriter(new FileWriter(fileFullPath, true));
			// 파일안에 문자열 쓰기
			fw.write(("[" + dateTime + "] " + massage + "\n"));
			fw.flush();

			// 객체 닫기
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
