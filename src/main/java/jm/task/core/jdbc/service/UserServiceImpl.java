package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoHibernateImpl();

    // Создание таблицы
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    // Удаление таблицы
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    // Добавляет user
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.println("User с " + name + " добавлен в базу данных.");
    }

    // Удаляет по id
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    // Ввывод таблицы
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    // Очистка таблицы
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
