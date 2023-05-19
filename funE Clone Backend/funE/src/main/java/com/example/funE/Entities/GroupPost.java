package com.example.funE.Entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "group_post")
@RequiredArgsConstructor
@Setter @Getter
public class GroupPost {
    @Id
    @Column(name = "group_post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String message;

    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm:ss")
    private LocalDateTime datePost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "groupPost")
    private List<Comment> comments;

    @OneToMany(mappedBy = "groupPost")
    private List<LikePost> likePosts;

    @OneToMany(mappedBy = "groupPost", fetch = FetchType.LAZY)
    private List<Photo> photos;

    @OneToMany(mappedBy = "groupPost", fetch = FetchType.LAZY)
    private List<Video> videos;

    @OneToMany(mappedBy = "groupPost", fetch = FetchType.LAZY)
    private List<Audio> audioSet;


    @Transient
    public Integer getNumberLikeOfPost() {
        return this.likePosts.size();
    }
}
