package com.syphan.practice.common.rest.util.response;

import com.syphan.practice.common.api.wrapper.PageWrapper;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OpenApiWithPageResponse<T extends Serializable> extends OpenApiWithDataResponse {
    private Long totalElements;

    private int totalPages;

    private int number;

    private int size;

    public OpenApiWithPageResponse(PageWrapper<T> page) {
        this.data = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.number = page.getNumber();
        this.size = page.getSize();
    }
}
