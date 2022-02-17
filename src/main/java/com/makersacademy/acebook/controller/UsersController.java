package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Authority;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.AuthoritiesRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.services.FileUploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @Autowired
    FileUploadService fileUploadService;


    @GetMapping("/users/new")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping("/users")
    public RedirectView signup(@ModelAttribute User user) {
        userRepository.save(user);
        Authority authority = new Authority(user.getUsername(), "ROLE_USER");
        authoritiesRepository.save(authority);
        return new RedirectView("/login");
    }

    @GetMapping("/users/profile")
    public String profile(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).get(0);
        model.addAttribute("picturePath", user.getAvatarpath());
        return "users/profile";
    }

    @PutMapping("/users") 
    public RedirectView updateAvatar(MultipartFile file, Authentication auth) {
        String newPath = fileUploadService.storeAvatar(file, auth.getName());      
        User activeUser = userRepository.findByUsername(auth.getName()).get(0);
        activeUser.setAvatarpath(newPath);
        userRepository.save(activeUser);
        return new RedirectView("/users/profile");
    }
}
