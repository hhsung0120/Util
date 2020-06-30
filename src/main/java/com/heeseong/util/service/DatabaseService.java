package com.heeseong.util.service;

import com.heeseong.util.mapper.DatabaseMapper;
import com.heeseong.util.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

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
                dataType = "LocalDateTime";
            } else {
                dataType = "String";
            }
            printString.append(accessModifier+" "+dataType+" "+resultMap.get("COLUMN_NAME") +";"+ System.getProperty("line.separator"));
        }
        System.out.println(printString);
        System.out.println("####################### end of ["+tableName+"] #######################");
    }

    private Map<String, Object> makeInsertConstruction(String tableName, Object o) {
        return makeInsertConstruction("", tableName, o);
    }

    private Map<String, Object> makeInsertConstruction(String tableSchema, String tableName, Object o) {
        List<Map<String,String>> compareList = new ArrayList<Map<String, String>>();

        if(StringUtil.isEmpty(tableSchema)){
            compareList = databaseMapper.selectTableCoulumList(tableName);
        }else{
            compareList = databaseMapper.selectTableCoulumListOfTableSchema(tableSchema, tableName);
        }

        Map<String,Object> result = new HashMap<String,Object>();

        //userIdx,userId,encPassword,userName,email,emailAgree
        StringJoiner insertColumn =new StringJoiner(",");
        //#{userIdx},#{userId},#{encPassword},#{userName}
        StringJoiner insertValue =new StringJoiner(",");

        Class<?> c =o.getClass();

        if(o instanceof Map<?,?>){
            Map map = (Map) o;
            for(Object key : map.keySet()){
                Object value = map.get(key);
                String keyString = key.toString();
                for(Map<String,String> m : compareList){
//						DB에 존재하는 컬럼인지 확인
                    if(keyString.equals(m.get("COLUMN_NAME"))){
                        result.put(keyString, value);
                        insertColumn.add(keyString);
                        insertValue.add("#{"+keyString+"}");
                    }
                }
            }
        }else{
            //object class fields
            Field[] fields =c.getDeclaredFields();

            for(Field field :fields){
                log.debug("type : {}",field.getType());
                boolean isContain=false;
                field.setAccessible(true);
                try {
                    if(field.get(o) !=null ){
                        if(field.getType().isPrimitive()){
                            //type가 기본형인 경우 (int,boolean 등)
                            for(Map<String,String> m:compareList){
                                if(field.getName().equals(m.get("COLUMN_NAME"))){
                                    isContain = true;
                                    result.put(field.getName(),field.get(o));
                                }
                            }

                        }else if (field.getType().isAssignableFrom(String.class)){
                            //type가 String인 경우
                            for(Map<String,String> m:compareList){
                                if(field.getName().equals(m.get("COLUMN_NAME"))){
                                    isContain = true;
                                    result.put(field.getName(),field.get(o));
                                }
                            }
                        }else if (field.getType().isAssignableFrom(java.util.Date.class) ||field.getType().isAssignableFrom(java.sql.Date.class) || field.getType().isAssignableFrom(java.time.LocalDateTime.class)){
                            if(!"".equals(field.get(o))){
                                for(Map<String,String> m:compareList){
                                    if(field.getName().equals(m.get("COLUMN_NAME"))){
                                        isContain = true;
                                        result.put(field.getName(),field.get(o));
                                    }
                                }
                            }
                        }else if (field.getType().isAssignableFrom(Locale.class)){
                            if(!"".equals(field.get(o))){
                                for(Map<String,String> m:compareList){
                                    if(field.getName().equals(m.get("COLUMN_NAME"))){
                                        isContain = true;
                                        result.put(field.getName(),field.get(o).toString());
                                    }
                                }
                            }
                        }else if (field.getType().isEnum()){
                            if(!"".equals(field.get(o))){
                                for(Map<String,String> m:compareList){
                                    if(field.getName().equals(m.get("COLUMN_NAME"))){
                                        result.put(field.getName(),field.get(o));
                                        isContain = true;
                                    }
                                }
                            }
                        }
                    //log.debug("field.getType().isInstance(String.class) {} : " ,field.getType().isInstance(String.class));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.info("makeInsertStatement error {}", o.toString());
                    log.info("printStackTrace",e);
                }
                if(isContain){
                    insertColumn.add(field.getName());
                    insertValue.add("#{"+field.getName()+"}");
                }
            }
        }
        result.put("insertColumn", insertColumn.toString());
        result.put("insertValue", insertValue.toString());

        return result;
    }

    public int autoInsertIncludeAutoincrement(String tableSchema, String tableName, Object o) {
        Map<String,Object> result = new HashMap<>();
        if(StringUtil.isEmpty(tableSchema)){
            result = makeInsertConstruction(tableName,o);
        }else{
            result = makeInsertConstruction(tableSchema, tableName,o);
        }
        result.put("tableName", tableName);
        databaseMapper.insertTableAndGetAutoIncrementKey(result);
        return Integer.parseInt(result.get("autoIncrementKey").toString());
    }
}
