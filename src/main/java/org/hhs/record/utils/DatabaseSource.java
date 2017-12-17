package org.hhs.record.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class DatabaseSource {
//    private static List<Connection> connectionList = new ArrayList();
//    public static boolean initDatabasePools(){
//        for (int i = 0;i < 10; i++) {
//            connectionList.add(generateConnection());
//        }
//        return true;
//    }
//
//
//
//    public static Statement getConnectionStatement(){
//        synchronized (connectionList){
//            if (connectionList.size() != 0){
//                return getStatement(connectionList.remove(connectionList.size()-1));
//            } else {
//                return getStatement(generateConnection());
//            }
//        }
//    }
//
//    public static boolean releaseConnection(Connection connection){
//        synchronized (connectionList){
//            connectionList.add(connection);
//        }
//        return true;
//    }
//
//    private static Connection generateConnection(){
//        String url = "jdbc:odbc:record";
//        Connection connection = null;
//        Properties properties = new Properties();
//        properties.put("charSet","GBK");
//        properties.put("useUnicode", true);
//        try {
//            connection = DriverManager.getConnection(url, properties);
//        } catch (SQLException e) {
//            log.error("initDatabaseConnecttionPools", e);
//        }
//        return connection;
//    }
//
//    private static Statement getStatement(Connection connection){
//        try {
//            return connection.createStatement();
//        } catch (SQLException e) {
//            log.error("createStatementError", e);
//        }
//        return null;
//    }
//
//    private static PreparedStatement getStatement(Connection connection, String sql){
//        try {
//            return connection.prepareStatement(sql);
//        } catch (SQLException e) {
//            log.error("createStatementError", e);
//        }
//        return null;
//    }
//
//    public static PreparedStatement getPreparedStatement(String sql){
//        return getStatement(generateConnection(),sql);
//    }
    public static Connection generateConnection(){
        String url = "jdbc:odbc:record";
        Connection connection = null;
        Properties properties = new Properties();
        properties.put("charSet","GBK");
        properties.put("useUnicode", true);
        try {
            connection = DriverManager.getConnection(url, properties);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("initDatabaseConnecttionPools", e);
        }
        return connection;
    }

    public static boolean closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("close Connection Error{}", e);
        }
        return true;
    }
}
