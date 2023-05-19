package com.example.funE.Repositories;

import com.example.funE.Entities.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPostRepo extends JpaRepository<GroupPost, Integer> {
}
