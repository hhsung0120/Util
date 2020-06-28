package com.heeseong.util.controller;


import com.heeseong.util.service.DatabaseService;
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
    public String getColums(@RequestParam(value="tableName",required = false, defaultValue = "")String tableName){
        databaseService.getPrintObjectFromTableCoulumList("","board", "private");
        return "콘솔 확인!!!";
    }

    @ResponseBody
    @GetMapping("autoInsertIncludeAutoincrement")
    public String autoInsertIncludeAutoincrement(){
        databaseService.autoInsertIncludeAutoincrement();
        return "콘솔 확인!!!";
    }





}
