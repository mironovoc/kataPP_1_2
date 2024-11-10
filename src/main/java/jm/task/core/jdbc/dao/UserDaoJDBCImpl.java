package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(30), " +
                "lastName VARCHAR(30), " +
                "age TINYINT)";
        try(Connection con = Util.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS Users";
        try (Connection con = Util.getConnection();
            Statement stmt = con.createStatement()){
            stmt.execute(query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO Users (name, lastName, age) " +
                "VALUES (?, ?, ?)";
        try (Connection con = Util.getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM Users WHERE id = ?";
        try (Connection con = Util.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection con = Util.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");
                users.add(new User(id, name, lastName, age));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error fetching all users", e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection con = Util.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute("DELETE FROM Users");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error cleaning users table", e);
        }
    }
}
