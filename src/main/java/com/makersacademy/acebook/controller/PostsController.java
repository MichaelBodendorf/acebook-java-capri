package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class PostsController {

    @Autowired
    PostRepository repository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    


    @GetMapping("/posts")
    public String index(Model model) {
        Iterable<Post> posts = repository.findAll();
        model.addAttribute("posts", this.orderByIdDESC(posts));
        model.addAttribute("post", new Post());
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", this.commentRepository.findAll());
        model.addAttribute("username", this.userRepository.findAll());
        return "posts/index";
    }

    @PostMapping("/posts")
    public RedirectView create(@ModelAttribute Post post, Authentication auth) {
        post.setAuthor(auth.getName());
        repository.save(post);
        return new RedirectView("/posts");
    }

    private List<Post> orderByIdDESC(Iterable<Post> posts) {
        List<Post> listOfPosts = new ArrayList<>();
        posts.forEach(listOfPosts::add);

        return listOfPosts.stream()
            .sorted(Comparator.comparingLong(Post::getId).reversed())
            .collect(Collectors.toList());
    }

    @PutMapping("/posts/like")
     public RedirectView update( Long id, String like ) {
        
        Post post = repository.findById(id).get();
        post.counter(like);

        repository.save(post);
        return new RedirectView("/posts");
        
    }

}
