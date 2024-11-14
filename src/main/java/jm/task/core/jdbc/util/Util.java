package jm.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import jm.task.core.jdbc.model.User;  // Импортируйте класс User, если он у вас есть

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // Параметры для подключения через JDBC
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/bd1";
    private final static String USER = "root";
    private final static String PASSWORD = "123456";

    private static SessionFactory sessionFactory;

    // Метод для получения подключения через Hibernate (SessionFactory)
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Конфигурируем Hibernate через Java API
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect") // Диалект для MySQL
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver") // Драйвер для MySQL
                        .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/bd1") // URL подключения к базе
                        .setProperty("hibernate.connection.username", "root") // Имя пользователя базы данных
                        .setProperty("hibernate.connection.password", "123456") // Пароль для подключения
                        .setProperty("hibernate.hbm2ddl.auto", "update")  // Действие с базой данных (обновление схемы)
                        .setProperty("hibernate.show_sql", "true") // Включение вывода SQL-запросов в консоль
                        .setProperty("hibernate.format_sql", "true"); // Форматирование SQL-запросов

                // Создание ServiceRegistry для Hibernate
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                // Создание SessionFactory
                sessionFactory = configuration.addAnnotatedClass(User.class)  // Указываем класс сущности User
                        .buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
                throw new ExceptionInInitializerError("SessionFactory initialization failed!" + e);
            }
        }
        return sessionFactory;
    }

    // Метод для получения подключения через JDBC
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