package com.projectjy.projectjybackend.service;

import com.projectjy.projectjybackend.dto.ChangeResponseDto;
import com.projectjy.projectjybackend.repository.MemberRepository;
import com.projectjy.projectjybackend.security.MemberResponseDto;
import com.projectjy.projectjybackend.security.SecurityUtil;
import com.projectjy.projectjybackend.security.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto getMemberInfo() {
        return memberRepository.findById(SecurityUtil.getCurrentId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

    @Transactional
    public ChangeResponseDto changeMemberNickname(String nickname) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        if (member.getNickname().equals(nickname)) {
            return ChangeResponseDto.of("변경할 닉네임이 현재 닉네임과 같습니다");
        }
        if (memberRepository.existsByNickname(nickname)) {
            return ChangeResponseDto.of("이미 존재하는 닉네임입니다");
        }
        if (nickname.length() < 2 || nickname.length() > 10) {
            return ChangeResponseDto.of("닉네임은 2자 이상 10자 이하로 설정해주세요");
        }
        member.setNickname(nickname);
        memberRepository.save(member);
        return ChangeResponseDto.of("닉네임 변경 성공");
    }

    @Transactional
    public ChangeResponseDto changeMemberPassword(String exPassword, String newPassword) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        if (!passwordEncoder.matches(exPassword, member.getPassword()))
            return ChangeResponseDto.of("현재 비밀번호가 일치하지 않습니다");
        if (passwordEncoder.matches(newPassword, member.getPassword()))
            return ChangeResponseDto.of("현재 비밀번호와 동일한 비밀번호입니다");
        if (newPassword.length() < 10)
            return ChangeResponseDto.of("비밀번호는 10자 이상으로 설정해주세요");
        //비밀번호 영문, 숫자, 특수문자 조합
        if (newPassword.matches("^.*(?=^.{5,10}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$"))
            return ChangeResponseDto.of("비밀번호는 영문, 숫자, 특수문자 조합으로 설정해주세요");

        member.setPassword(passwordEncoder.encode((newPassword)));
        memberRepository.save(member);
        return ChangeResponseDto.of("비밀번호 변경 성공");
    }

    @Transactional
    public ChangeResponseDto changeEmail(String email){
        Member member = memberRepository.findById(SecurityUtil.getCurrentId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        if (member.getEmail().equals(email)) {
            return ChangeResponseDto.of("변경할 이메일이 현재 이메일과 같습니다");
        }
        if (memberRepository.existsByEmail(email)) {
            return ChangeResponseDto.of("이미 존재하는 이메일입니다");
        }
        if(!email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")){
            return ChangeResponseDto.of("이메일 형식이 올바르지 않습니다");
        }
        member.setEmail(email);
        memberRepository.save(member);
        return ChangeResponseDto.of("이메일 변경 성공");
    }
}
