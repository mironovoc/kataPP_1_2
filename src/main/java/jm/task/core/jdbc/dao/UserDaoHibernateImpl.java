package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        // Конфигурация SessionFactory
        sessionFactory = new Configuration().addAnnotatedClass(User.class).buildSessionFactory();
    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            // SQL-запрос для создания таблицы
            String query = "CREATE TABLE IF NOT EXISTS Users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(30), " +
                    "lastName VARCHAR(30), " +
                    "age INT)";

            session.createSQLQuery(query).executeUpdate();

            session.getTransaction().commit(); // Коммит транзакции
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback(); // Откат транзакции при ошибке
            }
        } finally {
            session.close(); // Закрыть сессию
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            // SQL-запрос для удаления таблицы
            String query = "DROP TABLE IF EXISTS Users";
            session.createSQLQuery(query).executeUpdate();

            session.getTransaction().commit(); // Коммит транзакции
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback(); // Откат транзакции при ошибке
            }
        } finally {
            session.close(); // Закрыть сессию
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        try {
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
            if (session.getTransaction() != null) {
                session.getTransaction().rollback(); // Откат транзакции при ошибке
            }
        } finally {
            session.close(); // Закрыть сессию
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            // Получение пользователя по ID
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user); // Удаление пользователя
            }

            session.getTransaction().commit(); // Коммит транзакции
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback(); // Откат транзакции при ошибке
            }
        } finally {
            session.close(); // Закрыть сессию
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        List<User> users = null;
        try {
            session.beginTransaction();

            // Получение всех пользователей
            Query<User> query = session.createQuery("FROM User", User.class);
            users = query.getResultList();

            session.getTransaction().commit(); // Коммит транзакции
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback(); // Откат транзакции при ошибке
            }
        } finally {
            session.close(); // Закрыть сессию
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            // Очистка таблицы
            String query = "DELETE FROM Users";
            session.createSQLQuery(query).executeUpdate();

            session.getTransaction().commit(); // Коммит транзакции
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback(); // Откат транзакции при ошибке
            }
        } finally {
            session.close(); // Закрыть сессию
        }
    }

    // Закрытие SessionFactory при завершении работы приложения
    public void close() {
        sessionFactory.close();
    }
}