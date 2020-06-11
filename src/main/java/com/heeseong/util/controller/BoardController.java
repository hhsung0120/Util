package com.heeseong.util.controller;


import com.heeseong.util.model.Board;
import com.heeseong.util.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public ModelAndView list(@ModelAttribute Board board){
        ModelAndView mav = new ModelAndView("/board/list");
        board.setListCount(boardService.getBoardListCount(board));

        return mav;
    }

    @GetMapping("/form")
    public ModelAndView form(){
        ModelAndView mav = new ModelAndView("/board/form");
        return mav;
    }

    @ResponseBody
    @PostMapping("/form")
    public boolean form(@ModelAttribute Board board
                       ,@RequestParam("file") List<MultipartFile> fileList) throws Exception{
        return boardService.saveBoard(board, fileList);
    }
}
