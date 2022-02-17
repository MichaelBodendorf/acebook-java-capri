package com.makersacademy.acebook.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    private String content;
    private String author;
    private Short likes;
    private Short dislikes;
   
    public Post() { this.likes = 0; this.dislikes = 0;}

    public Post(String content) {
        this.content = content;
    }

    public Post (Long id, Short likes) { 
    this.id = id;
    this.likes = likes;    
    }

    public Post (Long id, Short likes, String content , String author) { 
        this.id = id;
        this.likes = likes; 
        this.content = content;
        this.author = author;
    }
    
   
    public void counter(String like){
        
      if (like.equals("likesup")) { ++ this.likes; } 
      if (like.equals("dislikesup")) {++ this.dislikes; } 

    }    

    // -----getter and setter provided by lombok.Data-----
     public String getContent() { return this.content; }
     public void setContent(String content) { this.content = content; }
     public Long getId() { return this.id; }
     public void setId(Long id) { this.id = id; }
     public String getAuthor() { return this.author; }
     public void setAuthor(String author) { this.author = author; }
     public void setLikes(Short likes) {this.likes = likes; }
     public Short getLikes() { return this.likes; }
     public void setDislikes(Short dislikes) {this.dislikes = dislikes; }
     public Short getDislikes() { return this.dislikes; }
     
}
