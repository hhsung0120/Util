package com.heeseong.util.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DatabaseMapper {

    @Select("SELECT *  FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME=#{tableName} AND table_schema = DATABASE() ORDER BY ORDINAL_POSITION")
    List<Map<String, String>> selectTableCoulumList(@Param("tableName")String tableName);

    @Select("SELECT *  FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME=#{tableName} AND table_schema = #{tableSchema} ORDER BY ORDINAL_POSITION")
    List<Map<String, String>> selectTableCoulumListOfTableSchema(@Param("tableSchema")String tableSchema, @Param("tableName")String tableName);
}





