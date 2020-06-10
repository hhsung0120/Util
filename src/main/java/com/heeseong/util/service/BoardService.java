package com.heeseong.util.service;

import com.heeseong.util.mapper.BoardMapper;
import com.heeseong.util.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


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
    public boolean saveBoard(Board board, List<MultipartFile> fileList) {
        if(board.getIdx() == null){
            this.insertBoard(board);
            if(board.getIdx() > 0){
                this.fileSave(board.getIdx(), fileList);
            }
        }else{

        }

        return true;
    }

    /**
     * 게시판 저장
     * @param board
     * @return
     */
    private Integer insertBoard(Board board) {
        LocalDateTime currentDate = LocalDateTime.now();
        board.setRegistrant("하니성");
        board.setModifier(board.getRegistrant());
        board.setRegDate(currentDate);
        board.setModDate(board.getRegDate());
        return boardMapper.insertBoard(board);
    }


    public int getBoardListCount(Board board) {
        return boardMapper.selectBoardListCount(board);
    }


    private void fileSave(Integer idx, List<MultipartFile> fileList) {
    }
}
