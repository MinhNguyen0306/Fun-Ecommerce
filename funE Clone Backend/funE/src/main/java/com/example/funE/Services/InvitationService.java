package com.example.funE.Services;

import com.example.funE.Dtos.InvitationDto;
import com.example.funE.Entities.Invitation;

import java.util.List;

public interface InvitationService {
    InvitationDto requestInvitation(Integer fromUserId, Integer toUserId, InvitationDto invitationDto);
    List<Invitation> getAllInvitationOfUser(Integer userId);
    String acceptInvitation(Integer invitationId);
    String rejectInvitation(Integer invitationId);
    InvitationDto getInvitationById(Integer invitationId);
}
