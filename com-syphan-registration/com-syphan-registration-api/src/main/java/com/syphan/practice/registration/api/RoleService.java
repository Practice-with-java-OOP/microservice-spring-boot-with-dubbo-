package com.syphan.practice.registration.api;

import com.syphan.practice.common.api.BaseService;
import com.syphan.practice.common.api.exception.BIZException;
import com.syphan.practice.registration.api.dto.RoleCreateDto;
import com.syphan.practice.registration.api.model.Role;

public interface RoleService extends BaseService<Role, Integer> {

    Role create(RoleCreateDto data) throws BIZException;
}
