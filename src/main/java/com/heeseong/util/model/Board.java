package com.heeseong.util.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @Setter @ToString
public class Board {
    Integer idx;
    String boardType;
    String title;
    String contents;
    //등록자
    String registrant;
    //수정자
    String modifier;
    LocalDate regDate;
    LocalDate modDate;
}
