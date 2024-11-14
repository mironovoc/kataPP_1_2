package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(30), " +
                "lastName VARCHAR(30), " +
                "age TINYINT)";
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate(); // Используем native SQL через Hibernate
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS Users";
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate(); // Выполняем SQL-запрос для удаления таблицы
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            // Создание нового пользователя
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            // Сохранение пользователя
            session.save(user);

            session.getTransaction().commit(); // Коммит транзакции
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id); // Получаем пользователя по ID
            if (user != null) {
                session.delete(user); // Удаляем объект из базы данных
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = Util.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class); // HQL-запрос для получения всех пользователей
            users = query.getResultList(); // Получаем список пользователей
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User"); // HQL-запрос для удаления всех пользователей
            query.executeUpdate(); // Выполнение удаления
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}