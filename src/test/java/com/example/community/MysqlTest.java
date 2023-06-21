package com.example.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MysqlTest {

    @Test
    public void mysql() {
        // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://123.57.25.20:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        final String USER = "root";
        final String PASS = "root";
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM user_info";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 通过字段检索
                String name = rs.getString("name");
                String password = rs.getString("password");

                // 输出数据
                System.out.print(", 站点名称: " + name);
                System.out.print(", 站点 URL: " + password);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
