package com.heeseong.util.service;

import com.heeseong.util.mapper.BoardMapper;
import com.heeseong.util.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    /**
     * 게시판 저장
     * idx > 0 수정
     * idx == 0 저장
     * @param board
     * @return boolean
     */
    public boolean saveBoard(Board board) {
        if(board.getIdx() == null){
            this.insertBoard(board);
        }else{

        }

        return true;
    }

    private Integer insertBoard(Board board) {
        LocalDate currentDate = LocalDate.now();
        System.out.println(board.toString());
        board.setRegistrant("하니성");
        board.setModifier(board.getRegistrant());
        board.setRegDate(currentDate);
        board.setModDate(board.getRegDate());

        return boardMapper.insertBoard(board);
    }


}
