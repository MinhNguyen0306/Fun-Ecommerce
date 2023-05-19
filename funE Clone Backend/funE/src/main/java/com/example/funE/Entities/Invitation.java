package com.example.funE.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invitation")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private int id;

    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date inviteAt;

    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "user_id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", referencedColumnName = "user_id")
    private User toUser;
}
