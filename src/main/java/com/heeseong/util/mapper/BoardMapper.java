package com.heeseong.util.mapper;

import com.heeseong.util.model.Board;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMapper {

    Integer insertBoard(Board board);
}





