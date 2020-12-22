package com.skilful.dao.impl;

import com.skilful.dao.UserDao;
import com.skilful.model.Book;
import com.skilful.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoJDBCImpl implements UserDao {

    private static final String GET_ALL_USERS = "SELECT users.user_id, user_name, user_age, book_name, book_author FROM users \n" +
            "LEFT JOIN books ON users.user_id = books.user_id";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(GET_ALL_USERS, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setName(rs.getString("user_name"));
            user.setAge(rs.getInt("user_age"));
            Book book = new Book();
            book.setName(rs.getString("book_name"));
            book.setAuthor(rs.getString("book_author"));
            user.setBook(book);
            return user;
        });
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update("INSERT INTO users (user_name, user_age) VALUES (?, ?)", user.getName(), user.getAge());
        int id = getAllUsers().get(getAllUsers().size()-1).getId();
        jdbcTemplate.update(
                "INSERT INTO books (book_name, book_author, user_id) VALUES (?,?,?)",
                user.getBook().getName(), user.getBook().getAuthor(), id);

    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM users WHERE user_id = ?", id);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
                "UPDATE users SET user_name = ?, user_age = ? WHERE user_id = ?",
                user.getName(), user.getAge(), user.getId());
        jdbcTemplate.update(
                "UPDATE books SET book_name = ?, book_author = ? WHERE user_id = ?",
                user.getBook().getName(), user.getBook().getAuthor(), user.getId());

    }

}
