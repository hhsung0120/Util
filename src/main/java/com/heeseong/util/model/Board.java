package com.heeseong.util.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @ToString
public class Board extends PageNavigator {
    Integer idx;
    String boardType;
    String title;
    String contents;
    //등록자
    String registrant;
    //수정자
    String modifier;
    LocalDateTime regDate;
    LocalDateTime modDate;
}
