package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/bd1";
    private final static String USER = "root";
    private final static String PASSWORD = "123456";

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