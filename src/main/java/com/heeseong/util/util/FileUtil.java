package com.heeseong.util.util;

import com.heeseong.util.model.CommonFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.util.UUID;

public class FileUtil {

    /**
     * 업로드 경로에 폴더 존재하지 않을 시 폴더생성
     */
    public static void makeUploadPathDirectory(String uploadPath) {
        File destPath = new File(uploadPath);
        if (!destPath.exists()) {
            destPath.mkdirs();
        }
    }

    /**
     * 파일 확장자
     * @param fileName
     * @return String
     */
    public static String getFileExtension(String fileName) {
        return StringUtils.getFilenameExtension(fileName).toLowerCase();
    }

    /**
     * UUID 생성
     * @param fileName
     * @return UUID + 확장자
     */
    public static String getUuidFileName(String fileName){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 파일 업로드 실행메서드
     * @param file multipartFile data
     * @param uploadPath 업로드 디렉토리(풀경롤 default + custom)
     * @return CommonFile
     */
    public static CommonFile fileUploadExecute(MultipartFile file, String uploadPath){
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

            return commonFile;
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }
}
