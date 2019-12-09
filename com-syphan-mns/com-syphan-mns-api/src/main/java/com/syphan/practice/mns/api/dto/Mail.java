package com.syphan.practice.mns.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Mail {
    private List<String> tos;
    private List<String> ccs;
    private List<String> bccs;
    private String subject;
    private String content;
    private Boolean isHtml;
}
