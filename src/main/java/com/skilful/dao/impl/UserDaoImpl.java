package com.skilful.dao.impl;

import com.skilful.dao.UserDao;
import com.skilful.model.Book;
import com.skilful.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    private static final String GET_ALL_USERS = "SELECT users.user_id, user_name, user_age, book_name, book_author FROM users \n" +
            "LEFT JOIN books ON users.user_id = books.user_id";

    @Autowired
    private Connection connection;

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("user_name"));
                user.setAge(resultSet.getInt("user_age"));
                Book book = new Book();
                book.setName(resultSet.getString("book_name"));
                book.setAuthor(resultSet.getString("book_author"));
                user.setBook(book);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(userList, Comparator.comparingInt(User::getId));
        return userList;
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (user_name, user_age) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, user.getName());
            statement.setInt(2, user.getAge());
            int changedRows = statement.executeUpdate();
            if (changedRows > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        PreparedStatement statementBook =
                                connection.prepareStatement("INSERT INTO books (book_name, book_author, user_id) VALUES (?, ?, ?)");
                        statementBook.setString(1, user.getBook().getName());
                        statementBook.setString(2, user.getBook().getAuthor());
                        statementBook.setInt(3, id);
                        statementBook.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE user_id = ?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
              "UPDATE users SET user_name = ?, user_age = ? WHERE user_id = ?",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, user.getName());
            statement.setInt(2, user.getAge());
            statement.setInt(3, user.getId());
            int changedRows = statement.executeUpdate();
            if (changedRows > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        PreparedStatement statementBook =
                                connection.prepareStatement("UPDATE books SET book_name = ?, book_author = ? WHERE user_id = ?");
                        statementBook.setString(1, user.getBook().getName());
                        statementBook.setString(2, user.getBook().getAuthor());
                        statementBook.setInt(3, id);
                        statementBook.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
