package com.example.funE.Services.Impl;

import com.example.funE.Dtos.InvitationDto;
import com.example.funE.Entities.Invitation;
import com.example.funE.Entities.User;
import com.example.funE.Exceptions.ResourceNotFoundException;
import com.example.funE.Repositories.InvitationRepo;
import com.example.funE.Repositories.UserRepo;
import com.example.funE.Services.InvitationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    private InvitationRepo invitationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InvitationDto requestInvitation(Integer fromUserId,Integer toUserId, InvitationDto invitationDto) {
        Invitation invitation = new Invitation();
        User fromUser = this.userRepo.findById(fromUserId)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", fromUserId));
        User toUser = this.userRepo.findById(toUserId)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", toUserId));
        invitation.setToUser(toUser);
        invitation.setFromUser(fromUser);
        invitation.setStatus("pending");
        LocalDateTime localDateTime =  LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        invitation.setInviteAt(Date.from(instant));
        return this.modelMapper.map(invitation, InvitationDto.class);
    }

    @Override
    public List<Invitation> getAllInvitationOfUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        List<Invitation> invitationResponses = user.getInvitationResponse();
        List<InvitationDto> invitationDtos = invitationResponses.stream()
                .map(invitation -> this.modelMapper.map(invitation, InvitationDto.class)).collect(Collectors.toList());
        return invitationResponses;
    }

    @Override
    public String acceptInvitation(Integer invitationId) {
        Invitation invitation = this.invitationRepo.findById(invitationId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation", "Id", invitationId));
        invitation.setStatus("accept");
        return "success";
    }

    @Override
    public String rejectInvitation(Integer invitationId) {
        Invitation invitation = this.invitationRepo.findById(invitationId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation", "Id", invitationId));
        invitation.setStatus("reject");
        return "success";
    }

    @Override
    public InvitationDto getInvitationById(Integer invitationId) {
        return null;
    }
}
