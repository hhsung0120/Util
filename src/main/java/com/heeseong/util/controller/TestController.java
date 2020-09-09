package com.heeseong.util.controller;


import com.heeseong.util.model.Board;
import com.heeseong.util.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @ResponseBody
    @GetMapping("/date")
    public String date(@ModelAttribute Board board){
        String result = "실패";
        try{
            Object value = DateUtil.getCalculatorDateAndTime("2020-0d7-23T23:59:58",1,1,1,1,1,1 );
            return value.toString();
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
