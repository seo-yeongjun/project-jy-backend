package com.projectjy.projectjybackend.service;

import com.projectjy.projectjybackend.repository.MemberRepository;
import com.projectjy.projectjybackend.security.MemberResponseDto;
import com.projectjy.projectjybackend.security.TokenDto;
import com.projectjy.projectjybackend.security.TokenProvider;
import com.projectjy.projectjybackend.security.entity.Member;
import com.projectjy.projectjybackend.security.entity.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//로그인 회원가입 서비스
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberResponseDto join(MemberRequestDto requestDto) {
        if (memberRepository.existsByMemberId(requestDto.getMemberId())) {
            throw new RuntimeException("이미 존재 하는 아이디 입니다");
        }
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 존재 하는 이메일 입니다");
        }
        Member member = requestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    public TokenDto login(MemberRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        return tokenProvider.generateTokenDto( managerBuilder.getObject().authenticate(authenticationToken));
    }

}
