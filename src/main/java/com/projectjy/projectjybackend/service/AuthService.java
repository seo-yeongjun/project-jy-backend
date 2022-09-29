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
        if (requestDto.getMemberId() == null || requestDto.getMemberId().equals(""))
            return MemberResponseDto.message("아이디를 입력해주세요.");
        if (requestDto.getNickname() == null || requestDto.getNickname().equals(""))
            return MemberResponseDto.message("닉네임을 입력해주세요.");
        if (requestDto.getPassword() == null || requestDto.getPassword().equals(""))
            return MemberResponseDto.message("비밀번호를 입력해주세요.");
        if (requestDto.getEmail() == null || requestDto.getEmail().equals(""))
            return MemberResponseDto.message("이메일을 입력해주세요.");
        if(requestDto.getPassword().length() < 10)
            return MemberResponseDto.message("비밀번호는 10자 이상이어야 합니다.");
        if(!requestDto.getPassword().matches("^.*(?=^.{5,10}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$"))
            return MemberResponseDto.message("비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.");
        if (memberRepository.existsByMemberId(requestDto.getMemberId())) {
            return MemberResponseDto.message("이미 존재하는 아이디입니다.");
        }
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            return MemberResponseDto.message("이미 존재하는 이메일입니다.");
        }
        Member member = requestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    public TokenDto login(MemberRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        return tokenProvider.generateTokenDto(managerBuilder.getObject().authenticate(authenticationToken));
    }

    public Boolean checkMemberId(String memberId) {
        return memberRepository.existsByMemberId(memberId);
    }

    public Boolean checkEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public Boolean checkNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
