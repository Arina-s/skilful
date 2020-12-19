package com.skilful.controllers;

import com.skilful.model.User;
import com.skilful.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users",userService.getAll());
        return "users";
    }

    @GetMapping("/users/create")
    public String getCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUserForm";
    }

    @PostMapping("/users/create")
    public String createUser(User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createUserForm";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("user/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        User userUpdate = userService.updateById(id);
        model.addAttribute("user", userUpdate);
        return "updateUser";
    }

}
