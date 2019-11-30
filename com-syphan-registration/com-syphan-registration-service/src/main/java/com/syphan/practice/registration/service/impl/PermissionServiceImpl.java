package com.syphan.practice.registration.service.impl;

import com.syphan.practice.common.api.enumclass.ErrType;
import com.syphan.practice.common.api.exception.BIZException;
import com.syphan.practice.common.service.base.BaseServiceImpl;
import com.syphan.practice.registration.api.PermissionService;
import com.syphan.practice.registration.api.dto.PermissionCreateDto;
import com.syphan.practice.registration.api.model.Permission;
import com.syphan.practice.registration.dao.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@org.apache.dubbo.config.annotation.Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Integer> implements PermissionService {

    private PermissionRepository repository;

    @Autowired
    protected PermissionServiceImpl(PermissionRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Transactional
    @Override
    public Permission create(PermissionCreateDto data) throws BIZException {
        if (repository.findByCode(data.getCode().trim()) != null) throw BIZException.buildBIZException(
                ErrType.CONFLICT, "code.already.existed", String.format("%s%s%s", "Code [ ", data.getCode(), " ] already existed.")
        );

        Permission permission = new Permission();
        permission.setName(data.getName());
        permission.setCode(data.getCode());
        return repository.save(permission);
    }
}
