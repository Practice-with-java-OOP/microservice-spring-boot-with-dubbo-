package com.syphan.practice.common.rest.controller;

import cn.hutool.core.util.IdUtil;
import com.syphan.practice.common.rest.config.AwsS3Properties;
import com.syphan.practice.common.rest.util.response.OpenApiWithDataResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Api(tags = {"Upload Api"})
@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {

    private final Logger logger = LoggerFactory.getLogger(StorageController.class);

    @Autowired
    private S3Client s3Client;

    @Autowired
    private AwsS3Properties awsS3Properties;

    @ApiOperation("Upload")
    @PutMapping
    public ResponseEntity<OpenApiWithDataResponse<String>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", required = false) String folder,
            @RequestParam(value = "needUniqueName", defaultValue = "true") Boolean needUniqueName,
            @RequestParam(value = "username", required = false) String username) {

        logger.debug(String.format("%s%s%s%s", "upload file contentType: ", file.getContentType(),
                ", originalFilename: ", file.getOriginalFilename()));
        String fileName = null;
        if (!file.isEmpty()) {
            try {
                fileName = String.format("%s%s%s%s%s", username != null ? String.format("%s%s", username, "/") : "",
                        folder != null ? String.format("%s%s", folder, "/") : "",
                        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        "/", needUniqueName ? IdUtil.fastSimpleUUID() + file.getOriginalFilename() : file.getOriginalFilename());
                logger.debug(String.format("%s%s", "fileName: ", fileName));
                PutObjectResponse response = s3Client.putObject(
                        PutObjectRequest.builder().bucket(awsS3Properties.getBucket()).key(fileName).build(),
                        RequestBody.fromInputStream(file.getInputStream(), file.getSize())
                );
                logger.debug(String.format("%s%s", "Upload files to AWS S3 response: ", response));
            } catch (Exception e) {
                logger.error(String.format("%s%s", "An error occurred uploading files to AWS S3: ", e.getMessage()));
                fileName = null;
            }
        }
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(fileName));
    }

    @ApiOperation("Get access endpoint")
    @GetMapping("endpoint")
    public ResponseEntity<OpenApiWithDataResponse<String>> getEndpoint() {
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(awsS3Properties.getEndpoint()));
    }
}
