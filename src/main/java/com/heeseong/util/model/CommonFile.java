package com.heeseong.util.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommonFile {
    private Integer idx;
    private Integer boardIdx;
    private String uploadPath;
    private String uuid;
    private String originalFileName;
    private String extension;
    //등록자
    String registrant;
    //수정자
    String modifier;
    LocalDateTime regDate;
    LocalDateTime modDate;
}
