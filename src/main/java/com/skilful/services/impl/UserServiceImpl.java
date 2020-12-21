package com.skilful.services.impl;

import com.skilful.dao.UserDao;
import com.skilful.exceptions.UserException;
import com.skilful.model.User;
import com.skilful.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAllUsers();
    }

    @Override
    public void deleteById(int id) {
        userDao.delete(id);
    }

    @Override
    public void save(User user) {
        checkValidUserName(user);
        userDao.save(user);
    }

    private void checkValidUserName(User user) {
        List<User> users = userDao.getAllUsers().stream()
                .filter(userDB -> userDB.getName().equals(user.getName()))
                .collect(Collectors.toList());
        if (!users.isEmpty()) {
            throw new UserException("User with that name is already exist");
        }
        if (user.getName().length() < 4) {
            throw new UserException("Minimum name length must be 4 characters");
        }
    }

    @Override
    public User getUserById(int id) {
        User user = userDao.getAllUsers().stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        return user;
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

}