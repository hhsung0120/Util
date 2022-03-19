package com.heeseong.util.controller;


import com.heeseong.util.model.Board;
import com.heeseong.util.service.DatabaseService;
import com.heeseong.util.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/database")
public class DatabaseUtilController {

    @Autowired
    private DatabaseService databaseService;

    @ResponseBody
    @GetMapping("getTableCoulumList")
    public String getColums(@RequestParam(value = "tableName", required = false, defaultValue = "") String tableName) {
        databaseService.getPrintObjectFromTableCoulumList("", "board", "private");
        String test = DateUtil.getToday("-");
        return "콘솔 확인!!!";
    }

    @ResponseBody
    @GetMapping("autoInsertIncludeAutoincrement")
    public String autoInsertIncludeAutoincrement() {
        Board board = new Board();
        board.setBoardType("1");
        board.setContents("asd");
        board.setTitle("sdfsdf");
        board.setRegistrant("시스템");
        board.setModifier(board.getRegistrant());
        board.setRegDate(DateUtil.getTodayAndNowTime());
        board.setModDate(board.getRegDate());

        int count = databaseService.autoInsertIncludeAutoincrement("", "board", board);
        System.out.println(count);
        return "콘솔 확인!!!";
    }


}
