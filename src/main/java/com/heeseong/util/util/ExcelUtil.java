package com.heeseong.util.util;

import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    /**
     * @param request
     * @param response
     * @param rowTitle 컬럼 제목
     * @param dataList 데이터
     * @param dataName 컬럼 이름
     * @param fileName 다운로드 파일 이름
     * @param password 비밀번호 == "" 비번 없음
     */
    public static void executeExcelDownload(HttpServletRequest request, HttpServletResponse response, String[] rowTitle, List<Map<String, Object>> dataList, String[] dataName, String fileName, String password) {
        try {
            // Excel Write
            XSSFWorkbook workbook = new XSSFWorkbook();

            // Sheet 생성
            XSSFSheet sheet = workbook.createSheet("Sheet1"); // 시트 생성

            // Font 설정
            XSSFFont titleFont = workbook.createFont(); // 폰트 객체 생성
            titleFont.setFontHeightInPoints((short) 11); //글씨 크기 설정
            titleFont.setFontName("맑은 고딕"); // 글씨체 설정 (ARIAL)
            titleFont.setColor(new XSSFColor(new java.awt.Color(255, 255, 255), null));
            titleFont.setBold(true);

            // 제목의 스타일 지정
            XSSFCellStyle titleStyle = workbook.createCellStyle(); // 스타일 객체 생성
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(64, 64, 64), null));
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0), null));
            titleStyle.setBorderBottom(BorderStyle.THIN);
            titleStyle.setBorderTop(BorderStyle.THIN);
            titleStyle.setBorderLeft(BorderStyle.THIN);
            titleStyle.setBorderRight(BorderStyle.THIN);
            titleStyle.setFont(titleFont);

            // Title Row 생성
            XSSFRow titleRow = sheet.createRow((short) 0);
            for (int i = 0; i < rowTitle.length; i++) {
                /* cell에 Title 맵핑 */
                XSSFCell cell = titleRow.createCell(i);
                cell.setCellStyle(titleStyle); // 셀 스타일 정의
                cell.setCellValue(rowTitle[i]); // 셀 내용 삽입
            }
            // Font 설정
            XSSFFont contentFont = workbook.createFont(); // 폰트 객체 생성
            contentFont.setFontName("맑은 고딕"); // 글씨체 설정 (ARIAL)
            contentFont.setFontHeightInPoints((short) 10); //글씨 크기 설정
            titleFont.setBold(false);

            // 기본스타일 지정
            XSSFCellStyle contentStyle = workbook.createCellStyle(); // 셀 스타일 객체 생성
            contentStyle.setAlignment(HorizontalAlignment.CENTER);
            contentStyle.setFont(contentFont); // 폰트 적용
            contentStyle.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0), null));
            contentStyle.setBorderBottom(BorderStyle.THIN);
            contentStyle.setBorderTop(BorderStyle.THIN);
            contentStyle.setBorderLeft(BorderStyle.THIN);
            contentStyle.setBorderRight(BorderStyle.THIN);

            // Cell에 내용 맵핑
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> data = dataList.get(i);
                // Content Row 생성
                XSSFRow contentRow = sheet.createRow(i + 1);
                for (int j = 0; j < dataName.length; j++) {
                    /* cell에 Content 맵핑 */
                    XSSFCell cell = contentRow.createCell(j);
                    // auto size
                    if (i < 30) {
                        sheet.autoSizeColumn(j);
                        try {
                            sheet.setColumnWidth(j, (sheet.getColumnWidth(j)) * 3 / 2);
                        } catch (Exception e) {
                            sheet.setColumnWidth(j, 25000);
                        }
                    }
                    cell.setCellStyle(contentStyle); // 셀 스타일 정의
                    cell.setCellType(CellType.STRING);

                    // 셀 내용 삽입
                    cell.setCellValue(data.get(dataName[j]).toString());
                }
            }
            // 한글깨짐 처리
            String userAgent = request.getHeader("User-Agent");
            if (userAgent.contains("MSIE") || userAgent.contains("Trident") || userAgent.contains("Chrome")) {
                fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            // ContetnType, Header 정보 설정
            response.setContentType("application/vnd.ms-excel;");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            OutputStream os = response.getOutputStream();

            // 비밀번호 설정
            if (!"".equals(password)) {
                POIFSFileSystem fileSystem = new POIFSFileSystem();
                EncryptionInfo info = new EncryptionInfo(EncryptionMode.standard);
                Encryptor enc = info.getEncryptor();
                enc.confirmPassword(password);
                OutputStream encryptedDS = enc.getDataStream(fileSystem);
                workbook.write(encryptedDS);
                encryptedDS.close();
                fileSystem.writeFilesystem(os);
            } else {
                workbook.write(os);
            }
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}