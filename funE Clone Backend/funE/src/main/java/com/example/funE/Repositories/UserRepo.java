package com.example.funE.Repositories;

import com.example.funE.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    @Query(value = "select email from users where email=:email", nativeQuery = true)
    List<String> checkUserEmail(@Param("email") String email);

    @Query(value = "select password from users where email=:email", nativeQuery = true)
    String checkUserPasswordByEmail(@Param("email") String email);

    @Query(value = "select * from users where email=:email", nativeQuery = true)
    User getUserDetailByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "insert into users(name, email, password) values(:name, :email, :password)", nativeQuery = true)
    int registerNewUser(@Param("email") String email,
                        @Param("password") String password,
                        @Param("name") String name);

    @Query(value = "select * from users where name like :key or email like :key", nativeQuery = true)
    List<User> searchUsers(@Param("key") String key);
}
