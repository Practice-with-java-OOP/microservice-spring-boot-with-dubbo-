package com.syphan.practice.mns.api.common;

import lombok.Getter;

@Getter
public enum SendPipeline {
    ONLINE_CHAT(1),
    MAIL(2),
    SMS(3),
    PUSH_NOTIFICATION(4);

    private int value;
    SendPipeline(int value) {
        this.value = value;
    }
}

