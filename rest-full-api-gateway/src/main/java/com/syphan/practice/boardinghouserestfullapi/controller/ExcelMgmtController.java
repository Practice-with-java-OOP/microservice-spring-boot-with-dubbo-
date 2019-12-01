package com.syphan.practice.boardinghouserestfullapi.controller;

import com.syphan.practice.registration.api.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@RestController
@Api(tags = "Excel Management Controller Api")
@RequestMapping("/api/v1/admin/excel")
public class ExcelMgmtController {

    @Reference
    private UserService userService;

    @ApiOperation("Export excel for discount offline")
    @GetMapping
    public ResponseEntity<Resource> exportExcel() {
        ByteArrayResource resource = userService.exportExcel();
        HttpHeaders headers = new HttpHeaders();
        String filename = String.format("%s%s%s", "attachment; filename=export_user_",
                new SimpleDateFormat("yyMMdd").format(System.currentTimeMillis()), ".xls");
        headers.add("Content-Disposition", filename);
        headers.add("Content-Type", "text/html");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(resource);
    }
}
