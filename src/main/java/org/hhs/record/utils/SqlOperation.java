package org.hhs.record.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hhs.record.dao.pojo.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

@Slf4j
@Service
public class SqlOperation {

    //针对查询用
    public List<Object> GETObjectLists(String sql) throws Exception{
        if (StringUtils.isEmpty(sql)&&!sql.trim().startsWith("select")&&!sql.trim().startsWith("SELECT")){
            throw new Exception("only select");
        }
        Connection connection = DatabaseSource.generateConnection();
        Statement statement = connection.createStatement();
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet == null) {
                return Collections.emptyList();
            }
            ResultSetMetaData md = resultSet.getMetaData();
            int columnCount = md.getColumnCount();
            List<Object> objectList = new ArrayList<Object>();
            Map rowData = null;
            while (resultSet.next()){
                rowData = new HashMap(columnCount);
                for (int i = 1; i <= columnCount; i++){
                    rowData.put(md.getColumnName(i), resultSet.getObject(i));
                }
                objectList.add(rowData);
            }
            DatabaseSource.closeConnection(connection);
            return objectList;
        } catch (SQLException e) {
            log.error("sqlException", e);
        }
        return null;
    }

    //针对单条插入操作
    public boolean INSERToperation(Map<String, Object> map, Class<?> clazz)throws Exception{
        String sql = toInsertSql(map, clazz);
        Connection connection = DatabaseSource.generateConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        DatabaseSource.closeConnection(connection);
        return true;
    }

    //批量插入
    public boolean INSERToperation(List<Map<String, Object>> mapList, Class<?> clazz) throws SQLException {
        Connection connection = DatabaseSource.generateConnection();
        Statement statement = connection.createStatement();
        String sql = "";
        for (Map<String, Object> objectMap : mapList){
            sql = toInsertSql(objectMap, clazz);
            statement.executeUpdate(sql);
        }
        DatabaseSource.closeConnection(connection);
        return true;
    }

    //删除操作
    public Integer DELETEoperation(Map<String, Object> map, Class<?> clazz) throws SQLException {
        String sql = toDeleteSql(map, clazz);
        Connection connection = DatabaseSource.generateConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        DatabaseSource.closeConnection(connection);
        return 1;
    }

    public boolean UPDATEoperation(Map<String, Object> map, Map<String, Object> paramMap, Class<?> clazz) throws SQLException {
        String sql = toUpdateSql(map, paramMap, clazz);
        Connection connection = DatabaseSource.generateConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        DatabaseSource.closeConnection(connection);
        return true;
    }

    //更新操作
    private String[] getFiledName(Class<?> clazz){
        Field[] fields=clazz.getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    }

    private String toInsertSql(Map<String, Object> map, Class<?> clazz){
        String tempLineName = "(";
        String values = "VALUES(";
        String[] lineNames = getFiledName(clazz);
        for (int i = 0; i < lineNames.length; i++){
            if(lineNames.length == 1){
                tempLineName += "["+lineNames[i]+"]";
                values += "'"+map.get(lineNames[i])+"'";
            }else {
                if (i < lineNames.length - 1) {
                    tempLineName += "[" + lineNames[i] + "],";
                    values += "'" + map.get(lineNames[i]) + "',";
                } else {
                    tempLineName += "[" + lineNames[i] + "]) ";
                    values += "'" + map.get(lineNames[i]) + "')";
                }
            }
        }
        String sql = "INSERT INTO "+"["+clazz.getSimpleName().toLowerCase()+"]"+tempLineName+values;
        return sql;
    }

    private String toDeleteSql(Map<String, Object> map, Class<?> clazz){
        String[] lineNames = getFiledName(clazz);
        String sql = "";
        if(map != null) {
            String queryStr = " WHERE ";
            for (int i = 0; i < lineNames.length; i++) {
                if (lineNames.length == 1 && map.get(lineNames[i]) != null) {
                    queryStr += lineNames[i] + "='" + map.get(lineNames[i]) + "'";
                } else {
                    if (i < lineNames.length - 1 && map.get(lineNames[i]) != null) {
                        queryStr += lineNames[i] + "='" + map.get(lineNames[i]) + "' AND ";
                    } else {
                        if (map.get(lineNames[i]) != null) {
                            queryStr += lineNames[i] + "='" + map.get(lineNames[i]) + "'";
                        }
                    }
                }
            }
            sql = "DELETE FROM "+clazz.getSimpleName().toUpperCase()+queryStr;
        }else {
            sql = "DELETE FROM ["+clazz.getSimpleName().toUpperCase()+"]";
        }
        return sql;
    }

    private String toUpdateSql(Map<String, Object> map, Map<String, Object> paramMap, Class<?> clazz){
        String[] lineNames = getFiledName(clazz);
        String paramStr = " set ";
        for (int i = 0; i < lineNames.length; i++){
            if (paramMap.get(lineNames[i]) != null){
                paramStr += paramMap.get(lineNames[i])+"='"+map.get(lineNames[i])+"'";
            }
        }
        String whereStr = " WHERE ";
        for (int i = 0; i < lineNames.length; i++){
            if (lineNames.length == 1){
                if (map.get(lineNames[i]) != null) {
                    whereStr += lineNames[i] + "='" + map.get(lineNames[i])+"'";
                }
            }else {
                if (i < lineNames.length - 1) {
                    if (map.get(lineNames[i]) != null) {
                        whereStr += lineNames[i] + "='" + map.get(lineNames[i]) + "' AND ";
                    }
                } else {
                    if (map.get(lineNames[i]) != null) {
                        whereStr += lineNames[i] + "='" + map.get(lineNames[i]) + "'";
                    }
                }
            }
        }
        String sql = "UPDATE "+"["+clazz.getSimpleName().toUpperCase()+"]"+paramStr+whereStr;
        return sql;
    }

}
