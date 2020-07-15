package com.preproject.springbootcrud.controller;

import com.preproject.springbootcrud.model.Role;
import com.preproject.springbootcrud.model.User;
import com.preproject.springbootcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/")
    public String startingPage(){
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/userlist")
    public String getUsers(ModelMap model){
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("user", new User());
        return "users";
    }

    @GetMapping(value = "/userpage")
    public String getUserPage(ModelMap model, Authentication authentication){
        model.addAttribute("userData", userService.getUserByName(authentication.getName()));
        return "userpage";
    }

    @GetMapping(value = "/admin")
    public String getAdminPage(ModelMap model, Authentication authentication){
        model.addAttribute("userData", userService.getUserByName(authentication.getName()));
        return "admin";
    }

    @PostMapping(value = "/adduser")
    public String addUser( @ModelAttribute("user") User user){ //@Valid
        user.setIsAccountNonExpired();
        user.setIsAccountNonLocked();
        user.setIsCredentialsNonExpired();
        user.setIsEnabled();
        user.addRole(new Role("ROLE_USER", user));
        userService.saveUser(user);
        return "redirect:/userlist";
    }

    @GetMapping(value = "/update/{id}")
    public String updateUser(@PathVariable long id, ModelMap model){
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRole());
        return "update";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable long id,  @ModelAttribute User user){ //@Valid
        Role userRole = new Role("ROLE_USER", user);
        user.addRole(userRole);
        userService.updateUserById(id, user);
        return "redirect:/userlist";
    }

    @GetMapping(value = "/deleteuser/{id}")
    public String deleteUser(@PathVariable long id, ModelMap model){
        userService.deleteUserById(id);
        return "redirect:/userlist";
    }

}
