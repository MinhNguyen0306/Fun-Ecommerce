package com.example.funE.Entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_chat")
@RequiredArgsConstructor
@Getter @Setter
public class Group {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String avatar;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "group_follower", joinColumns = @JoinColumn(name = "group_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> groupFollowers = new HashSet<>();

    @OneToMany(mappedBy = "group")
    private Set<GroupPost> groupPosts;

    public void addUser(User user) {
        groupFollowers.add(user);
        user.getGroups().add(this);
    }

    public void removeUser(User user) {
        groupFollowers.remove(user);
        user.getGroups().remove(this);
    }
}
