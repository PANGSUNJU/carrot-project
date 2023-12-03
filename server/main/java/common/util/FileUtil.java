package common.util;

import common.vo.CheckMessage;
import common.vo.CommonFile;
import common.vo.CommonFile.FileStatus;
import common.vo.SystemCode.FileUploadPath;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;


/**
 * @author 서영석
 * @since 2019.11.13
 *
 * File과 관련된 기능을 정의 해놓은 Util입니다.
 * 업로드, 다운로드, 파일읽기, 파일쓰기
 *
 * 파일 정보만 가지고오기 -> multipartFileToCommonFile (multipartFileCheck기능 포함)
 * 파일 확장자 체크 -> multipartFileCheck
 * 특정 디렉토리에 파일 생성 -> fileCreate
 *
 * ※주요 활용 메소드 : fileUpload -> 파일 업로드 및 업로드 정보 반환  (fileCreate기능 및 multipartFileCheck 까지 포함되어 있음)
 */
public class FileUtil {

	/**
	 * @author 서영석
	 * @since 2019.11.13
	 *
     * 파일 기본 정보 (여러개 파일)
     */
    public static List<CommonFile> multipartFileToCommonFile (HttpServletRequest request) throws Exception {
    	return multipartFileToCommonFile(request, null);
    }
    public static List<CommonFile> multipartFileToCommonFile (HttpServletRequest request, List<String> extensions) throws Exception {
    	List<CommonFile> fileuploads = new ArrayList<CommonFile>();

    	MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
    	Iterator<String> itr = multipart.getFileNames();

		while (itr.hasNext()) {
			fileuploads.add(multipartFileToCommonFile(multipart.getFile(itr.next()), extensions));
		}

		return fileuploads;
    }

    /**
	 * @author 서영석
	 * @since 2019.11.13
	 *
	 * 파일 기본 정보 (한개 파일)
	 */
	public static CommonFile multipartFileToCommonFile (MultipartFile multipartFile) throws Exception {
		return multipartFileToCommonFile(multipartFile, null);
	}
    public static CommonFile multipartFileToCommonFile (MultipartFile multipartFile, List<String> extensions) throws Exception {
    	CommonFile commonFile = new CommonFile();

    	commonFile.setFileOriginName(getFileNameExceptExtension(multipartFile.getOriginalFilename()));
    	commonFile.setFileExtension(getFileExtension(multipartFile.getOriginalFilename()));
    	commonFile.setFileSize(multipartFile.getSize());
    	commonFile.setCheckMessage(multipartFileCheck(multipartFile, extensions));

    	return commonFile;
    }


	/**
	 * @author 서영석
	 * @since 2019.11.13
	 *
     * 업로드 파일 체크 (여러개 파일 - extensions)
     */
    public static Map<String, CheckMessage> multipartFileCheck (HttpServletRequest request, List<String> extensions) throws Exception {
    	Map<String, CheckMessage> info = new HashMap<String, CheckMessage>();

    	MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
    	Iterator<String> itr = multipart.getFileNames();

		while (itr.hasNext()) {
			MultipartFile multipartFile = multipart.getFile(itr.next());
			info.put(multipartFile.getName(), multipartFileCheck (multipartFile, extensions));
		}

		return info;
    }

    /**
     * @author 서영석
	 * @since 2019.11.13
	 *
     * 업로드 파일 체크  (한개 파일 - extensions)
     */
    public static CheckMessage multipartFileCheck (MultipartFile multipartFile, List<String> extensions) throws Exception {

    	String orginFileName = multipartFile.getOriginalFilename();
//	    System.out.println("multipartFile name : " + multipartFile.getName());
	    //원 파일명이 없는 경우 처리(첨부가 되지 않은 input file type)
	    if (orginFileName.equals("")) {
	    	return new CheckMessage(false, "파일명이 존재하지 않습니다");
	    }

	    //파일 확장자 존재 유무
	    int index = orginFileName.lastIndexOf(".");
	    if (index == -1) {
	    	return new CheckMessage(false, "파일에 확장자가 존재하지 않습니다");
	    }

	    //파일 확장자 체크
	    String fileExt = orginFileName.substring(index + 1);
	    //파일 확장자 체크 후, 컴펌 유무
	    boolean isConfirm = false;
	    if (extensions != null && extensions.size() > 0) {//체크할 확장자가 있으면 -> 체킹
	    	for (int i = 0; i < extensions.size(); i++) {
		    	if (extensions.get(i).toUpperCase().equals(fileExt.toUpperCase())) {
		    		isConfirm = true;
		    		break;
		    	}
		    }
	    } else {//체크할 확장자가 없으면 -> 컴펌
	    	isConfirm = true;
	    }

	    if (isConfirm == false) {
	    	return new CheckMessage(false, "." + fileExt + " 파일은(는) 업로드가 불가능한 확장자입니다");
	    }

	    return new CheckMessage(true, "업로드 가능한 파일 입니다");
    }

    /**
     * @author 서영석
	 * @since 2019.11.17
	 *
     * 업로드 파일 정보 조회 (여러개 파일)
     */
    public static List<CommonFile> fileUpload (HttpServletRequest request) throws Exception {
    	return fileUpload(request, null);
	}
	public static List<CommonFile> fileUpload (HttpServletRequest request, List<String> extensions) throws Exception {
		List<CommonFile> fileUploads = new ArrayList<CommonFile>();

		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
		Iterator<String> multiFiles = multipart.getFileNames();

		while (multiFiles.hasNext()) {
			MultipartFile multipartFile = multipart.getFile(multiFiles.next());
			fileUploads.add(fileUpload(multipartFile, request, extensions));
		}

		return fileUploads;
	}

	/**
	 * @author 서영석
	 * @since 2019.11.17
	 *
     * 업로드 파일 정보 조회 (한개 파일)
     */
	public static CommonFile fileUpload (MultipartFile multipartFile, HttpServletRequest request) throws Exception {
		return fileUpload(multipartFile, request, null);
	}
	public static CommonFile fileUpload (MultipartFile multipartFile, HttpServletRequest request, List<String> extensions) throws Exception {
		long startTime = System.currentTimeMillis();/*삭제 요망*/
		//파일 정보 객체
		CommonFile fileUpload = new CommonFile();
		try {
			//프로젝트 내부 경로
			String relativePath = FileUploadPath.MAIN_RELATIVE_PATH.getOSFileUploadPath();
			//프로젝트 내부 경로 = 프로젝트 내부 경로 + /년도
			relativePath += File.separator + LocalDate.now().getYear();
			//프로젝트 내부 경로 = 프로젝트 내부 경로 + /년도 + /월
			relativePath += File.separator + LocalDate.now().getMonthValue();
			//절대 경로(프로젝트 내부 경로의 절대경로)
			String absolutePath = request.getSession().getServletContext().getRealPath(relativePath);
			//확장자를 제거한 원본 파일명
			String originName = getFileNameExceptExtension(multipartFile.getOriginalFilename());
			//새로 생성될 파일명
			String maskName = StringUtil.getCreateKey("bigdata");
			//원본 파일의 확장자
			String extension = getFileExtension(multipartFile.getOriginalFilename());
			//원본 파일의 파일크기(byte단위)
			long size = multipartFile.getSize();

			//파일 정보 셋팅 (시작)
			fileUpload.setFileRelativePath(relativePath);
			fileUpload.setFileAbsolutePath(absolutePath);
			fileUpload.setFileOriginName(originName);
			fileUpload.setFileMaskName(maskName);
			fileUpload.setFileExtension(extension);
			fileUpload.setFileSize(size);
			fileUpload.setFileStatus(FileStatus.BEFORE_INSERT);
			fileUpload.setCheckMessage(new CheckMessage(true, "파일 정보 조회 성공"));
			//파일 정보 셋팅 (끝)
		} catch (Exception e) {
			fileUpload.setCheckMessage(new CheckMessage(false, "파일 정보 조회 에러", e.getMessage()));
		}

		//실제 디렉토리에 업로드 파일 생성
		if (extensions != null && extensions.size() > 0) {//extensions가 존재하면 -> 파일 검사 -> 통과 시, 파일 생성
			//파일 확장자 검사
			fileUpload.setCheckMessage(multipartFileCheck(multipartFile, extensions));

			if (fileUpload.getCheckMessage().getIsSuccess() == true) {//확장자 검사 통과 -> 성공 -> 파일 생성
				fileCreate(fileUpload, multipartFile);
				long endTime = System.currentTimeMillis();/*삭제 요망*/
				long totalTime = endTime - startTime;/*삭제 요망*/
				System.out.println("업로드 경로 : " + fileUpload.getFileAbsolutePath() + ", 파일명 : " + multipartFile.getOriginalFilename() + "(" + fileUpload.getFileMaskName() + ") 파일 업로드 소요시간 : " + totalTime / 1000.0 + "초");/*삭제 요망*/
			} else {//확장자 검사 통과 -> 실패
				System.out.println(multipartFile.getOriginalFilename() + " 파일 확장자 검사 통과 실패");/*삭제 요망*/
			}
		} else {//extensions가 존재하지 않으면 -> 파일 생성
			fileCreate(fileUpload, multipartFile);
			long endTime = System.currentTimeMillis();/*삭제 요망*/
			long totalTime = endTime - startTime;/*삭제 요망*/
			System.out.println("업로드 경로 : " + fileUpload.getFileAbsolutePath() + ", 파일명 : " + multipartFile.getOriginalFilename() + "(" + fileUpload.getFileMaskName() + ") 파일 업로드 소요시간 : " + totalTime / 1000.0 + "초");/*삭제 요망*/
		}

		return fileUpload;
	}

	/**
	 * @author 서영석
	 * @since 2019.11.17
	 *
     * 실제 디렉토리에 업로드 파일 생성
     */
	public static void fileCreate (CommonFile fileupload, MultipartFile multipartFile) throws Exception  {
		try {
			//디렉토리 만들기.
			File dir = new File(fileupload.getFileAbsolutePath());
			boolean isExistDir = true;
			if (dir.exists() == false) {
				isExistDir = dir.mkdirs();
			}
			//디렉토리가 존재할 때
			if (isExistDir == true) {
				//디렉토리에 쓰기 권한이 있을 때
				if (dir.canWrite() == true) {
					//디렉토리 경로에 파일 생성
				    multipartFile.transferTo(new File(fileupload.getFileAbsolutePath(), fileupload.getFileMaskName() + "." + fileupload.getFileExtension()));
				    fileupload.setCheckMessage(new CheckMessage(true, "파일 활용 가능"));
				} else {
					fileupload.setCheckMessage(new CheckMessage(false, "쓰기 권한이 없습니다"));
				}
			} else {
				fileupload.setCheckMessage(new CheckMessage(false, "디렉토리 생성 불가"));
			}
		} catch (Exception e) {
			fileupload.setCheckMessage(new CheckMessage(false, "파일 업르드 에러", e.getMessage()));
		}
	}

	/**
	 * @author 서영석
	 * @since 2019.11.17
	 *
     * 실제 디렉토리에 업로드 파일 생성
     */
	public static void fileRemove (String fileFullPath) throws Exception {
		//commonFile.getFileAbsolutePath() + File.separator + commonFile.getFileMaskName() + "." + commonFile.getFileExtension()
		File file = new File(fileFullPath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * @author 서영석
	 * @since 2019.11.17
	 *
     * 파일 확장자 얻기
     */
	public static String getFileExtension (String fileName) {
		if (StringUtil.isEmpty(fileName) == true) {
			return null;
		} else {
			int index = StringUtil.lastIndexOf(fileName, ".");
			if (index > -1) {
				return StringUtil.lowerCase(fileName.substring(index + 1));
			} else {
				return null;
			}
		}
	}

	/**
	 * @author 서영석
	 * @since 2019.11.17
	 *
     * 확장자를 제거한 원본 파일명 얻기
     */
	public static String getFileNameExceptExtension (String fileName) {
		if (StringUtil.isEmpty(fileName) == true) {
			return fileName;
		} else {
			int index = StringUtil.lastIndexOf(fileName, ".");
			if (index > -1) {
				return fileName.substring(0, index);
			} else {
				return fileName;
			}
		}
	}

	/**
	 * @author 서영석
	 * @since 2019.11.17
	 *
     * File의 용량(byte)을 각 단위에 맞게 변형
     */
    public static String convertFileSize (String bytes) {
        String retFormat = "0";
        Double size = Double.parseDouble(bytes);
        String[] s = { "bytes", "KB", "MB", "GB", "TB", "PB" };
        if (bytes != null && bytes.length() > 0 && !bytes.equals("0") ) {
              int idx = (int) Math.floor(Math.log(size) / Math.log(1024));
              DecimalFormat df = new DecimalFormat("#,###.##");
              double ret = ((size / Math.pow(1024, Math.floor(idx))));
              retFormat = df.format(ret) + " " + s[idx];
         } else {
              retFormat += " " + s[0];
         }

         return retFormat;
    }
}
