package com.example.thymeleafonetomany.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @GetMapping("/users")
    public String listUsers(Model model)
    {
        List<User>  listUsers = userRepo.findAll();
        model.addAttribute("listU", listUsers);
        return "users";
    }
    @GetMapping("/users/new")
    public String showUserForm(Model model)
    {
        List<Role> listRoles = roleRepo.findAll();
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user_form";
    }
    @PostMapping("/users/save")
    public String saveUser(Model model,User user)
    {
        userRepo.save(user);
        return "redirect:/users";
    }
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model)
    {
        User user = userRepo.findById(id).get();
        List<Role> listRoles = roleRepo.findAll();

        model.addAttribute("user",user);
        model.addAttribute("listRoles", listRoles);

        return "user_form";
    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model)
    {
        userRepo.deleteById(id);
        return "redirect:/users";
    }
}
