package com.heeseong.util.mapper;

import com.heeseong.util.model.Board;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface BoardMapper {

    Integer insertBoard(Board board);

    int selectBoardListCount(Board board);
}





