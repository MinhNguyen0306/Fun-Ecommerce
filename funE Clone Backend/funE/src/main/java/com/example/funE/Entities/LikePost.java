package com.example.funE.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "like_post")
@Data
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_post_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_post_id", referencedColumnName = "group_post_id")
    private GroupPost groupPost;
}
