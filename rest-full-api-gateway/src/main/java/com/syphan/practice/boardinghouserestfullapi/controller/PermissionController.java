package com.syphan.practice.boardinghouserestfullapi.controller;

import com.syphan.practice.common.rest.util.EntityValidationUtils;
import com.syphan.practice.common.rest.util.response.OpenApiWithDataResponse;
import com.syphan.practice.registration.api.PermissionService;
import com.syphan.practice.registration.api.dto.PermissionCreateDto;
import com.syphan.practice.registration.api.model.Permission;
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

@Api(tags = "Permission Management V1")
@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    @Reference
    private PermissionService service;

    @ApiOperation("Create New Permission")
    @PreAuthorize("hasAuthority('UPMS_PERMISSION_EDIT')")
    @PostMapping
    public ResponseEntity<OpenApiWithDataResponse<Permission>> create(@Valid @RequestBody PermissionCreateDto reqPram,
                                                                      BindingResult bindingResult) {
        EntityValidationUtils.processBindingResults(bindingResult);
        return ResponseEntity.ok(new OpenApiWithDataResponse<Permission>(service.create(reqPram)));
    }

}
