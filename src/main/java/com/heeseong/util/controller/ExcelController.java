package com.heeseong.util.controller;


import com.heeseong.util.model.Board;
import com.heeseong.util.service.BoardService;
import com.heeseong.util.util.DateUtil;
import com.heeseong.util.util.ExcelUtil;
import com.heeseong.util.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
        ExcelUtil.executeExcelDownload(request,response,rowTitle,dataList,dataName,fileName,"123");
    }

    @GetMapping("/read")
    public void read(@ModelAttribute Board board, HttpServletRequest request, HttpServletResponse response){

    }
}
