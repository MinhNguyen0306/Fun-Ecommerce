package com.example.funE.Dtos;

import com.example.funE.Entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class InvitationDto {
    private int id;
    private boolean status;
    private Date inviteAt;

    private User fromUser;
    private User toUser;
}
