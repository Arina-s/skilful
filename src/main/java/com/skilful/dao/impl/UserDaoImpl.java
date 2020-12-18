package com.skilful.dao.impl;

import com.skilful.dao.UserDao;
import com.skilful.model.Book;
import com.skilful.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    private static int identifier = 1;
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(identifier++, "Anna",34, new Book("Koalio", "Veronika")));
        users.add(new User(identifier++, "Kenadi",21, new Book("Mark", "World")));
    }


    @Override
    public List<User> getAllUsers() {
        Collections.sort(users, Comparator.comparingInt(User::getId));
        return users;
    }

    @Override
    public User getNewUser() {
        User user = new User();
        user.setId(identifier++);
        return user;
    }

}
