package com.example.funE.Entities;

import com.example.funE.Utils.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String avatar;
    private String cover_avatar;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private String bankAccountHolderName;
    @Column(columnDefinition = "integer default 0")
    private int bankAccountNumber;

    private String bankIdentifierCode;

    @Column(columnDefinition = "bit default 0")
    private boolean isSeller = false;

    @OneToMany(mappedBy = "user")
    private List<Address> address = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "user")
    private List<Payment> payments;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private Set<GroupPost> groupPosts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<LikePost> likePosts;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @ManyToMany(mappedBy = "groupFollowers", fetch = FetchType.EAGER)
    private Set<Group> groups;

    @OneToMany(mappedBy = "toUser")
    private List<Invitation> invitationRequest;

    @OneToMany(mappedBy = "fromUser")
    private List<Invitation> invitationResponse;

    public void addGroup(Group group) {
        groups.add(group);
        group.getGroupFollowers().add(this);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
        group.getGroupFollowers().remove(this);
    }
}
