package com.projectjy.projectjybackend.controller;

import com.projectjy.projectjybackend.dto.ChangeResponseDto;
import com.projectjy.projectjybackend.security.MemberResponseDto;
import com.projectjy.projectjybackend.security.entity.MemberRequestDto;
import com.projectjy.projectjybackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMemberInfo();
        System.out.println(myInfoBySecurity.getNickname());
        return ResponseEntity.ok((myInfoBySecurity));
        // return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }

    @PostMapping("/changeNickname")
    public ResponseEntity<ChangeResponseDto> setMemberNickname(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberNickname(request.getNickname()));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ChangeResponseDto> setMemberPassword(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
    }

    @PostMapping("/changeEmail")
    public ResponseEntity<ChangeResponseDto> setMemberEmail(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeEmail(request.getEmail()));
    }
}
