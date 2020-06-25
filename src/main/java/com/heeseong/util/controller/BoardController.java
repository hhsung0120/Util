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
@RequestMapping("/board")
public class BoardController {

    @Value("${default-upload-path}")
    private String defaultUploadPath;

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public ModelAndView list(@ModelAttribute Board board){
        ModelAndView mav = new ModelAndView("/board/list");

        board.setTotalCount(boardService.getBoardListCount(board));

        if(board.getTotalCount() > 0){
            List<Board> boardList = boardService.getBoardList(board);
            mav.addObject("boardList", boardList);
            mav.addObject("paging", board);
        }

        return mav;
    }

    @GetMapping("/form")
    public ModelAndView form(@RequestParam(value="idx", required = false, defaultValue = "0")Integer idx){
        ModelAndView mav = new ModelAndView("/board/form");

        Board board = new Board();
        if(idx > 0){
            board = boardService.getBoard(idx);
        }
        mav.addObject("board", board);
        return mav;
    }

    @ResponseBody
    @PostMapping("/form")
    public boolean form(@ModelAttribute Board board,
                        @RequestParam("fileList")List<MultipartFile> fileList) throws Exception{
        return boardService.saveBoard(board, fileList);
    }

    @ResponseBody
    @PostMapping("/fileDownload")
    public FileSystemResource fileDownload(@RequestParam(value="fileName", required = false, defaultValue = "")String fileName
                                         , HttpServletRequest request
                                         , HttpServletResponse response){
        return FileUtil.executeFileDownload(fileName,defaultUploadPath,request,response);
    }

    @ResponseBody
    @PostMapping("/fileDelete")
    public String fileDelete(@RequestParam(value="fileName", required = false, defaultValue = "")String fileName){
        FileUtil.executeFileDelete(defaultUploadPath, fileName);
        return "성공";
    }

    @ResponseBody
    @GetMapping("/fileTest")
    public String fileTest(@RequestParam(value="fileName", required = false, defaultValue = "")String fileName){
        System.out.println("들어왔음");
        FileUtil.executeFileReName(defaultUploadPath, defaultUploadPath, fileName, fileName);
        return "성공";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute Board board, HttpServletRequest request, HttpServletResponse response){
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
}
