package com.heeseong.util.mapper;

import com.heeseong.util.model.Board;
import com.heeseong.util.model.CommonFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface BoardMapper {

    Integer insertBoard(Board board);

    int selectBoardListCount(Board board);

    void insertFileInfo(CommonFile commonFile);

    List<Board> selectBoardList(Board board);
}





