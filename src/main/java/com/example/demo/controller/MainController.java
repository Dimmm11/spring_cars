package com.example.demo.controller;

import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/menu")
    @Transactional(readOnly = true)
    public String menu() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null && auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ADMIN"))){
            return "redirect:/admin/panel";
        }
        return "menu";
    }

}
