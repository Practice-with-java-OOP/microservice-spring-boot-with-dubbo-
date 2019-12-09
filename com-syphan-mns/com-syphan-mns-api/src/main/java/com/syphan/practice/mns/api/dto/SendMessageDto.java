package com.syphan.practice.mns.api.dto;

import com.syphan.practice.mns.api.common.SendPipeline;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageDto {
    private SendPipeline sendPipeline;
    private String content;
    private String destination;
    private Boolean isHtml = false;
}
