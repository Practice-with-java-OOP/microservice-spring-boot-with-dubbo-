package com.syphan.practice.boardinghouserestfullapi.controller;

import com.syphan.practice.common.rest.security.CurrentUser;
import com.syphan.practice.common.rest.security.UserPrincipal;
import com.syphan.practice.common.rest.util.EntityValidationUtils;
import com.syphan.practice.common.rest.util.response.OpenApiWithDataResponse;
import com.syphan.practice.house.api.BoardingHouseService;
import com.syphan.practice.house.api.dto.BoardingHouseCreateDto;
import com.syphan.practice.house.api.model.BoardingHouse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(tags = "BoardingHouse Management V1")
@RestController
@RequestMapping("/api/v1/boarding-houses")
public class BoardingHouseController {

    @Reference
    private BoardingHouseService service;

    @ApiOperation("Create New House")
    @PreAuthorize("hasAnyAuthority('BOARDING_HOUSE_CREATE')")
    @PostMapping
    public ResponseEntity<OpenApiWithDataResponse<BoardingHouse>> create(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                                                         @Valid @RequestBody BoardingHouseCreateDto reqPram,
                                                                         BindingResult bindingResult) {
        EntityValidationUtils.processBindingResults(bindingResult);
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(service.create(userPrincipal.getId(), reqPram)));
    }

    /**
     * Print
     */
    @ApiOperation("Print house demo")
    @PreAuthorize("hasAnyAuthority('BOARDING_HOUSE_CREATE')")
    @PostMapping("{id}/print")
    public ResponseEntity<OpenApiWithDataResponse<String>> printHouse(@PathVariable Integer id) {
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(service.printHouse(id)));
    }
}
