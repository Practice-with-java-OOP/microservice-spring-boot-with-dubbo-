package com.syphan.practice.registration.dao;

import com.syphan.practice.common.dao.JpaQueryRepository;
import com.syphan.practice.registration.api.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaQueryRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByPhoneNum(String phoneNum);
}
