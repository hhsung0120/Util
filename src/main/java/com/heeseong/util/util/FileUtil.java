package com.heeseong.util.util;

import com.heeseong.util.model.CommonFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.UUID;

@Slf4j
public class FileUtil {

    /**
     * 업로드 경로에 폴더 존재하지 않을 시 폴더생성
     *
     * @param uploadPath 폴더 경로
     */
    public static void makeUploadPathDirectory(String uploadPath) {
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * 파일 확장자
     *
     * @param fileName
     * @return String
     */
    public static String getFileExtension(String fileName) {
        return StringUtils.getFilenameExtension(fileName).toLowerCase();
    }

    /**
     * UUID 생성
     *
     * @param fileName
     * @return UUID + 확장자
     */
    public static String getUuidFileName(String fileName) {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 파일 업로드 실행메서드
     *
     * @param file       multipartFile data
     * @param uploadPath 업로드 디렉토리(풀경롤 default + custom)
     * @return CommonFile
     */
    public static CommonFile executeFileUpload(MultipartFile file, String uploadPath) {
        try {
            CommonFile commonFile = new CommonFile();

            FileUtil.makeUploadPathDirectory(uploadPath);

            //파일 정보 set
            commonFile.setUploadPath(uploadPath);
            commonFile.setOriginalFileName(file.getOriginalFilename());
            commonFile.setUuid(getUuidFileName(file.getOriginalFilename()));
            commonFile.setExtension(getFileExtension(commonFile.getOriginalFileName()));

            String result = commonFile.getUploadPath() + commonFile.getUuid() + "." + commonFile.getExtension();

            File destination = new File(result);
            file.transferTo(destination);

            /*destination.createNewFile();
            FileOutputStream fos = new FileOutputStream(destination);
            fos.write(bf.getBytes());
            fos.close();*/

            return commonFile;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    /**
     * 파일 다운로드
     *
     * @param fileName   파일이름
     * @param uploadPath 파일경로
     * @param request
     * @param response
     * @return FileSystemResource
     */
    public static FileSystemResource executeFileDownload(String fileName
            , String uploadPath
            , HttpServletRequest request
            , HttpServletResponse response) {
        try {
            File file = new File(uploadPath + fileName);

            if (file.exists()) {
                String downloadFileName = fileName;

                String browser = request.getHeader("User-Agent");
                //파일 인코딩
                if (browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")) {
                    downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8").replaceAll("\\+", "%20");
                } else {
                    downloadFileName = new String(downloadFileName.getBytes("UTF-8"), "ISO-8859-1");
                }
                response.setHeader("Content-Disposition", "attachment; filename=" + downloadFileName);
                return new FileSystemResource(file);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }


    /**
     * 파일삭제 실행 메서드
     *
     * @param filePath 파일경로
     * @param fileName 파일이름
     * @return boolean
     */
    public static boolean executeFileDelete(String filePath, String fileName) {
        boolean result = false;
        try {
            File file = new File(filePath + fileName);
            if (file.exists()) {
                result = file.delete();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return result;
    }

    /**
     * 피일 이동(원본 보존 X)
     *
     * @param sorucePath 원본파일
     * @param targetPath 파일이 이동할 곳
     * @param fileName   파일 이름
     * @return boolean
     */
    public static boolean executeFileMove(String sorucePath, String targetPath, String fileName) {
        boolean result = false;
        try {
            File sourcefile = new File(sorucePath + fileName);
            if (sourcefile.exists()) {
                String moveDirectory = targetPath + "move" + File.separator;

                makeUploadPathDirectory(moveDirectory);

                File targetFile = new File(moveDirectory + fileName);

                result = sourcefile.renameTo(targetFile);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return result;
    }

    /**
     * 피일 이름 변경(원본 보존 X)
     * targetPath에 같은 파일이(이름) 존재하면 false
     *
     * @param sorucePath 원본파일
     * @param targetPath 이동할 경로
     * @param fileName   원본파일이름
     * @param reName     변경할 파일이름
     * @return boolean
     */
    public static boolean executeFileReName(String sorucePath, String targetPath, String fileName, String reName) {
        boolean result = false;
        try {
            File sourcefile = new File(sorucePath + fileName);
            if (sourcefile.exists()) {
                String moveDirectory = targetPath + "move" + File.separator;

                makeUploadPathDirectory(moveDirectory);

                File targetFile = new File(moveDirectory + reName);

                result = sourcefile.renameTo(targetFile);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return result;
    }

    //폴더 이동 및 해당 폴더 내부의 파일을 전부 확인하느 함수 생략
    //폴더 삭제 시 해당 폴더 내부에 파일이 하나라도 존재하면 삭제 불가능
    //파일 모두 읽어서 삭제처리 후 삭제 해야함
}
