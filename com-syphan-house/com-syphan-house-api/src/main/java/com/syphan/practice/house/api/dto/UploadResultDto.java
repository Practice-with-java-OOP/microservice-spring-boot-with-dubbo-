package com.syphan.practice.house.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResultDto implements Serializable {
  private String id;
  private Long productId;
  private String fileName;
  private Long fileSize;
  private Integer totalFilePart;
  private String checksum;
  private String featureAlias;
  private Integer partIndex;
  private Long sliceSize;
  private String filePath;
  private String status;
}
