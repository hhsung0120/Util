package com.heeseong.util.service;

import com.heeseong.util.mapper.BoardMapper;
import com.heeseong.util.model.Board;
import com.heeseong.util.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.sql.SQLSyntaxErrorException;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class BoardService {

    @Value("${default-upload-path}")
    private String defaultUploadPath;

    @Autowired
    private BoardMapper boardMapper;

    /**
     * 게시판 저장
     * idx > 0 수정
     * idx == null 저장
     * @param board
     * @param fileList
     * @return boolean
     * @throws Exception
     */
    @Transactional
    public boolean saveBoard(Board board, List<MultipartFile> fileList) throws Exception {
        if(board.getIdx() == null){
            this.insertBoard(board);
            if(board.getIdx() > 0){
                this.fileSave(board.getIdx(), fileList, defaultUploadPath);
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


    /**
     * 드라이브 파일 저장 및 DB 파일 정보 저장
     * @param idx 보드 IDX
     * @param fileList 파일 리스트
     * @param uploadPath 업로드패스
     * @throws Exception
     */
    private void fileSave(Integer idx, List<MultipartFile> fileList, String uploadPath) throws Exception{
        for(MultipartFile file : fileList){
            FileUtil.fileUploadExecute(file, uploadPath);
        }
    }
}
