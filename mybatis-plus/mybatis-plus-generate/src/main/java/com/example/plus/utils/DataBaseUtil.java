package com.example.plus.utils;

import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtil {

    public static List<JSONObject> getTableList(String url, String username, String password){
        List<JSONObject> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("?user=");
        sb.append(username);
        sb.append("&password=");
        sb.append(password);
        sb.append("&useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8");
        System.out.println(sb.toString());
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(sb.toString());
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = databaseMetaData .getTables(connection.getCatalog(), null, "%", types);
            while(rs.next()){
                JSONObject obj = new JSONObject();
                System.out.println("table name:" + rs.getString(3) + "***" + rs.getString(5));
                obj.put("tableName",rs.getString(3));
                obj.put("tableComment",rs.getString(5));
                list.add(obj);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }


    public static void main(String[] args) {
        /*String conStr = "jdbc:mysql://127.0.0.1:3306";
        conStr += "/china_union?user=root&password=123456&";
        conStr += "&useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(conStr);
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = databaseMetaData .getTables(connection.getCatalog(), null, "%", types);
            while(rs.next()){
                System.out.println("table name:" + rs.getString(3) + "***" + rs.getString(5));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/
        getTableList("jdbc:mysql://127.0.0.1:3306/china_union","root","123456");
    }

}


