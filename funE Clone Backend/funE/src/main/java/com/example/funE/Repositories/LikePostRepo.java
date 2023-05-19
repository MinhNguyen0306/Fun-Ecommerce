package com.example.funE.Repositories;

import com.example.funE.Entities.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikePostRepo extends JpaRepository<LikePost, Integer> {
}
