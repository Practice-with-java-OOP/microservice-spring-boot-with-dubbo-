package com.syphan.practice.registration.dao;

import com.syphan.practice.common.dao.JpaQueryRepository;
import com.syphan.practice.registration.api.model.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaQueryRepository<Permission, Integer> {
    Permission findByCode(String code);
}
