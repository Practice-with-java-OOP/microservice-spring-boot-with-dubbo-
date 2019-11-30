//package com.syphan.practice.boardinghouserestfullapi.controller;
//
//import com.syphan.practice.commonservice.security.CurrentUser;
//import com.syphan.practice.commonservice.security.UserPrincipal;
//import com.syphan.practice.commonservice.util.EntityValidationUtils;
//import com.syphan.practice.commonservice.util.response.OpenApiWithDataResponse;
//import com.syphan.practice.dto.house.BoardingHouseCreateDto;
//import com.syphan.practice.house.service.api.model.BoardingHouse;
//import com.syphan.practice.house.service.api.service.BoardingHouseService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.apache.dubbo.config.annotation.Reference;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.annotations.ApiIgnore;
//
//import javax.validation.Valid;
//
//@Api(tags = "BoardingHouse Management V1")
//@RestController
//@RequestMapping("/api/v1/boarding-houses")
//public class BoardingHouseController {
//
//    @Reference
//    private BoardingHouseService service;
//
//    @ApiOperation("Create New House")
//    @PreAuthorize("hasAnyAuthority('BOARDING_HOUSE_CREATE')")
//    @PostMapping
//    public ResponseEntity<OpenApiWithDataResponse<BoardingHouse>> create(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
//                                                                         @Valid @RequestBody BoardingHouseCreateDto reqPram,
//                                                                         BindingResult bindingResult) {
//        EntityValidationUtils.processBindingResults(bindingResult);
//        return ResponseEntity.ok(new OpenApiWithDataResponse<>(service.create(userPrincipal.getId(), reqPram)));
//    }
//}
