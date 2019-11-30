package com.syphan.practice.registration.api;

import com.syphan.practice.common.api.BaseService;
import com.syphan.practice.common.api.exception.BIZException;
import com.syphan.practice.registration.api.dto.PermissionCreateDto;
import com.syphan.practice.registration.api.model.Permission;

public interface PermissionService extends BaseService<Permission, Integer> {

    Permission create(PermissionCreateDto data) throws BIZException;
}
