package com.projectjy.projectjybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeResponseDto {
    private String message;

    public static ChangeResponseDto of(String message) {
        return new ChangeResponseDto(message);
    }
}
