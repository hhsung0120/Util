package com.heeseong.util.service;

import com.heeseong.util.mapper.DatabaseMapper;
import com.heeseong.util.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DatabaseService {

    @Autowired
    private DatabaseMapper databaseMapper;

    /**
     * 스키마가 공백이면 현재 연결된 데이터베이스 테이블 선택
     * @param tableSchema DB 스키마
     * @param tableName 테이블 이름
     * @param accessModifier 접근제어자(public, private, protected)
     * @return
     */
    public void getPrintObjectFromTableCoulumList(String tableSchema, String tableName, String accessModifier){
        log.info("tableName : {}", tableName);

        List<Map<String,String>> results = new ArrayList<>();
        StringBuilder printString = new StringBuilder();

        if(StringUtil.isEmpty(tableSchema)){
            results = databaseMapper.selectTableCoulumList(tableName);
        }else{
            results = databaseMapper.selectTableCoulumListOfTableSchema(tableSchema, tableName);
        }

        System.out.println("####################### start of ["+tableName+"] #######################");
        System.out.println("");
        for(Map<String, String> resultMap :results ){
            String dataType ="";
            String dbDataType = resultMap.get("DATA_TYPE");
            if("int".equalsIgnoreCase(dbDataType) || "NUMERIC".equalsIgnoreCase(dbDataType)){
                dataType = "int";
            } else if("FLOAT".equalsIgnoreCase(dbDataType)) {
                dataType = "float";
            } else if("DOUBLE".equalsIgnoreCase(dbDataType)) {
                dataType = "double";
            } else if("TIMESTAMP".equalsIgnoreCase(dbDataType) || "DATETIME".equalsIgnoreCase(dbDataType)  || "DATE".equalsIgnoreCase(dbDataType)) {
                dataType = "Date";
            } else {
                dataType = "String";
            }
            printString.append(accessModifier+" "+dataType+" "+resultMap.get("COLUMN_NAME") +";"+ System.getProperty("line.separator"));
        }
        System.out.println(printString);
        System.out.println("####################### end of ["+tableName+"] #######################");
    }

    /**
     *
     */
    public void autoInsertIncludeAutoincrement() {
        //makeInsertConstruction
    }
}
