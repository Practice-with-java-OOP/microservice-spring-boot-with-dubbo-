package com.syphan.practice.registration.dao;

import com.syphan.practice.common.dao.JpaQueryRepository;
import com.syphan.practice.registration.api.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaQueryRepository<Role, Integer> {
    Role findByCode(String code);
}
