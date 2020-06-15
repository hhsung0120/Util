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

        if(idx > 0){
            Board board = boardService.getBoard(idx);
            mav.addObject("board", board);
        }
        return mav;
    }

    @ResponseBody
    @PostMapping("/form")
    public boolean form(@ModelAttribute Board board) throws Exception{
        return boardService.saveBoard(board);
    }
}
