package com.syphan.practice.boardinghouserestfullapi.controller;

import com.syphan.practice.common.rest.util.EntityValidationUtils;
import com.syphan.practice.common.rest.util.response.OpenApiWithDataResponse;
import com.syphan.practice.registration.api.RoleService;
import com.syphan.practice.registration.api.dto.RoleCreateDto;
import com.syphan.practice.registration.api.model.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Role Management V1")
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Reference
    private RoleService roleService;

    @ApiOperation("Create New Role")
    @PreAuthorize("hasAuthority('UPMS_ROLE_READ')")
    @PostMapping
    public ResponseEntity<OpenApiWithDataResponse<Role>> addRole(@Valid @RequestBody RoleCreateDto reqParam,
                                                                 BindingResult bindingResult) {
        EntityValidationUtils.processBindingResults(bindingResult);
        return ResponseEntity.ok(new OpenApiWithDataResponse<Role>(roleService.create(reqParam)));
    }
}
