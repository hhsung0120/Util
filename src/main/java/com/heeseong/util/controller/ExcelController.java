package com.heeseong.util.controller;


import com.heeseong.util.model.Board;
import com.heeseong.util.service.BoardService;
import com.heeseong.util.util.DateUtil;
import com.heeseong.util.util.ExcelUtil;
import com.heeseong.util.util.XExcelFileReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/excel")
public class ExcelController {

    @Value("${default-upload-path}")
    private String defaultUploadPath;

    @Autowired
    private BoardService boardService;

    @GetMapping("/download")
    public void download(@ModelAttribute Board board, HttpServletRequest request, HttpServletResponse response){
        board.setPageSize(Integer.MAX_VALUE);
        List<Board> boardList = boardService.getBoardList(board);

        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        for(int i=0; i<boardList.size(); i++){
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("boardType", boardList.get(i).getBoardType());
            data.put("contents", boardList.get(i).getContents());
            data.put("title", boardList.get(i).getTitle());
            data.put("registrant", boardList.get(i).getRegistrant());
            dataList.add(data);
        }

        String [] rowTitle = {"타입","내용","제목","등록자"};
        String [] dataName = {"boardType","contents","title","registrant"};
        String fileName = "게시판엑셀"+DateUtil.getTodayAndNowTime("yyyy_MM_dd_HH_mm_ss");
        ExcelUtil.executeExcelDownload(request,response,rowTitle,dataList,dataName,fileName,"");
    }

    @ResponseBody
    @GetMapping("/read")
    public String read() throws Throwable {
        String path = defaultUploadPath+"test2.xlsx";
        System.out.println(path);
        XExcelFileReader excelRead = new XExcelFileReader(path);

        List<Map<String, Object>> result = excelRead.readRows();

        for(Map<String, Object> map : result){
            System.out.println(map.get("0"));
        }
        System.out.println("중복");

        //중복제거 후 row 수 반환
        //중복이 없다면 리스트size와 동일
        System.out.println(result.stream().map(row -> row.get("0")).distinct().count());
        System.out.println(result.size());
        return "엑셀 읽었다 캬캬";
    }
}
