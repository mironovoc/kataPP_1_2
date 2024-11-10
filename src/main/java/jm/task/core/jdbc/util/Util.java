package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
        final static String DRIVER = "com.mysql.cj.jdbc.Driver";
        final static String URL = "jdbc:mysql://localhost:3306/bd1";
        final static String USER = "root";
        final static String PASSWORD = "123456";

        public static Connection getConnection() throws ClassNotFoundException, SQLException {
                Connection con = null;
                try {
                        // Загружаем драйвер
                        Class.forName(DRIVER);
                        // Устанавливаем соединение с базой данных
                        con = DriverManager.getConnection(URL, USER, PASSWORD);
                } catch (ClassNotFoundException | SQLException e) {
                        throw e;  // Пробрасываем исключение дальше, чтобы оно было обработано выше
                }
                return con;
        }
}