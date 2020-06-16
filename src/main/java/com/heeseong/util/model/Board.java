package com.heeseong.util.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @ToString
public class Board extends PageNavigator {

    private Integer idx;
    private String boardType;
    private String title;
    private String contents;
    //등록자
    private String registrant;
    //수정자
    private String modifier;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private String searchTitle;
    private String searchContents;
    private List<CommonFile> fileList;
}
