package com.example.funE.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "comment")
@RequiredArgsConstructor
@Setter @Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int id;
    private String message;

    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm:ss")
    private LocalDateTime createAt;

    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm:ss")
    private LocalDateTime editAt;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(referencedColumnName = "comment_id")
    @JsonBackReference
    private Comment parentComment;

    @ManyToOne
    @JoinColumn(name = "group_post_id")
    private GroupPost groupPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
