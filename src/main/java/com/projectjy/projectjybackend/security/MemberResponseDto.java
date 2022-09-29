package com.projectjy.projectjybackend.security;

import com.projectjy.projectjybackend.security.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private String email;
    private String memberId;
    private String nickname;
    private  String message;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .build();
    }

    public static MemberResponseDto message(String s) {
        return MemberResponseDto.builder()
                .message(s)
                .build();
    }
}
