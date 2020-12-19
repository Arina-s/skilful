package com.skilful.services;

import com.skilful.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    void deleteById(int id);

    void save(User user);

    User updateById(int id);

}
