package com.example.funE.Repositories;

import com.example.funE.Entities.Audio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioRepo extends JpaRepository<Audio, Integer> {
}
