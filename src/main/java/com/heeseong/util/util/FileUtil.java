package com.heeseong.util.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
        String extension = getFileExtension(fileName);
        return UUID.randomUUID().toString().replace("-", "")+"."+extension;
    }

    public static void fileUploadExecute(MultipartFile file, String uploadPath) throws Exception{
        try {
            FileUtil.makeUploadPathDirectory(uploadPath);


        }catch (Exception e){
            e.getMessage();
        }
    }
}
