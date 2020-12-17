package com.skilful.controllers;

import com.skilful.model.Book;
import com.skilful.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    private static List<User> users = generate();

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/create")
    public String getCreateUserForm(User user) {
        return "createUserForm";
    }

    @PostMapping("/users/create")
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }

    public static List<User> generate() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Anna", new Book("Koalio", "Veronika")));
        users.add(new User(2, "Ken", new Book("Mark", "World")));
        return users;
    }

}
