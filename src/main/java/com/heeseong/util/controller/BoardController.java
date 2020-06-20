package com.heeseong.util.controller;


import com.heeseong.util.model.Board;
import com.heeseong.util.service.BoardService;
import com.heeseong.util.util.DateUtil;
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
import java.io.File;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

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
}
