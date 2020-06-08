package com.heeseong.util.service;

import com.heeseong.util.model.Board;
import org.springframework.stereotype.Service;


@Service
public class BoardService {


    /**
     * 게시판 저장
     * idx > 0 수정
     * idx == 0 저장
     * @param board
     * @return boolean
     */
    public boolean saveBoard(Board board) {
        if(board.getIdx() > 0){
            this.insertBoard(board);
        }

        return true;
    }

    private Integer insertBoard(Board board) {
        //return boardMapper.insertBoard(board);
        return 1;
    }


}
