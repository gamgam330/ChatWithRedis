package com.example.redis.Repository;

import com.example.redis.domain.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(String id);
    Optional<String> findByEmail(String Email);
    @Query(value = "SELECT nickname FROM user u WHERE u.id = :id", nativeQuery = true)
    Optional<String> findNicknameById(@Param("id") String id);

    @Query(value = "SELECT nickname FROM user u WHERE u.userid = :userid", nativeQuery = true)
    Optional<String> findNicknameByUserId(@Param("userid") long userid);

}