package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        // 1. Создаем таблицу Users.
        userService.createUsersTable();

        // 2.Добавляем 4User(ов) в таблицу с данными.
        userService.saveUser("Andrey", "Andreevich", (byte) 35);
        userService.saveUser("Ivan", "Ivanov", (byte) 16);
        userService.saveUser("Ivan", "Ivanov", (byte) 25);
        userService.saveUser("Василий", "Васильев", (byte) 30);

        // 3. Получение всех User из базы.
        List<User> list = userService.getAllUsers();
        list.forEach(System.out::println);

        // 4. Очистка таблицы от User(ов).
        userService.cleanUsersTable();

        // 5. Удаление таблицы.
        userService.dropUsersTable();
    }
}
