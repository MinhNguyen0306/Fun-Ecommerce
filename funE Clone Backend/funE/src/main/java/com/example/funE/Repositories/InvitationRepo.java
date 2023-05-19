package com.example.funE.Repositories;

import com.example.funE.Entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepo extends JpaRepository<Invitation, Integer> {
}
