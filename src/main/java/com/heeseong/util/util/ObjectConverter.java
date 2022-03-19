package com.heeseong.util.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectConverter {

    /**
     * object to json
     *
     * @param input 입력 문자열
     * @return String
     */
    static public String objectToJson(Object input) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            log.info("objectToJson Exception", e.getMessage());
            return "";
        }
    }

    /**
     * map to json
     *
     * @param input 입력 맵
     * @return String
     */
    static public String mapToJson(Map<?, ?> input) {
        return ObjectConverter.objectToJson(input);
    }

    /**
     * list to json
     *
     * @param input 입력 리스트
     * @return String
     */
    static public <T> String listToJson(TypeReference<List<T>> input) {
        return ObjectConverter.objectToJson(input);
    }

    /**
     * json(String)를 List<객체>로 변환
     *
     * @param input  입력 문자열
     * @param output 출력 리스트 타입
     * @return List<T>
     */
    static public <T> List<T> jsonToList(String input, TypeReference<List<T>> output) {
        return ObjectConverter.jsonToList(input, output, false);
    }

    /**
     * json to list
     *
     * @param input         입력 문자열
     * @param output        출력 리스트 타입
     * @param ignoreUnknown : 변환할 수 없는 객체 존재시 처리방법
     * @return List<T>
     */
    static public <T> List<T> jsonToList(String input, TypeReference<List<T>> output, boolean ignoreUnknown) {
        ObjectMapper mapper = new ObjectMapper();
        if (ignoreUnknown) {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        try {
            return mapper.readValue(input, output);
        } catch (IOException e) {
            log.info("jsonToList Exception", e.getMessage());
            return null;
        }
    }

    /**
     * json to object
     *
     * @param input  입력 문자열
     * @param output 출력 포조 객체 타입
     * @return Object
     */
    static public <T> T jsonToObject(String input, Class<T> output) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (T) mapper.readValue(input, output);
        } catch (IOException e) {
            log.info("jsonToObject Exception", e.getMessage());
            return null;
        }
    }

    /**
     * json to map
     *
     * @param input 입력 문자열
     * @return map
     */
    static public <T> T jsonToMap(String input) {
        return (T) ObjectConverter.jsonToObject(input, Map.class);
    }

    /**
     * object to map
     *
     * @param input
     * @return
     */
    static public Map objectToMap(Object input) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(input, Map.class);
    }

    /**
     * 일반 객체를 원하는 List<객체>로 변환
     *
     * @param input
     * @param output
     * @return List<T>
     */
    static public <T> List<T> objectToList(Object input, TypeReference<List<T>> output) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(input, output);

    }

    static public <T> T objectToObject(Object fromValue, Class<T> toValueType) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(fromValue, toValueType);
    }

    /**
     * strin to map
     *
     * @param input
     * @return map
     */
    static public Map stringToMap(String input) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(input, Map.class);
        } catch (IOException e) {
            log.info("stringToMap", e);
            return null;
        }
    }


}