package com.skilful.dao;

import com.skilful.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    void save(User user);

    void delete(int id);

    void update(User user);

}
