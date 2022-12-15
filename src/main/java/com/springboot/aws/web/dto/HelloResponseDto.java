package com.springboot.aws.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;

    @Override
    public String toString() {
        return "HelloResponseDto{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
