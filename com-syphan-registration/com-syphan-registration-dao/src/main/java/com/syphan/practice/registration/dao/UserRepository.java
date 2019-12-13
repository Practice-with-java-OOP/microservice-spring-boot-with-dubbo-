package com.syphan.practice.registration.dao;

import com.syphan.practice.common.dao.JpaQueryRepository;
import com.syphan.practice.registration.api.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaQueryRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByPhoneNum(String phoneNum);

    @Query("select u from User u join fetch u.roles")
    List<User> findAllByLazy();
}
