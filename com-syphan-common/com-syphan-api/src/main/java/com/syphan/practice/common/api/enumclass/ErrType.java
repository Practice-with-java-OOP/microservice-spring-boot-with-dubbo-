package com.syphan.practice.common.api.enumclass;

import lombok.Getter;

@Getter
public enum ErrType {
    CONSTRAINT,
    NOT_FOUND,
    CONFLICT,
    UNSUPPORTED_OPERATION,
    BAD_REQUEST
}
