package com.projectjy.projectjybackend.controller;

import com.projectjy.projectjybackend.security.MemberResponseDto;
import com.projectjy.projectjybackend.security.TokenDto;
import com.projectjy.projectjybackend.security.entity.MemberRequestDto;
import com.projectjy.projectjybackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<MemberResponseDto> join(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.join(requestDto));
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
    @GetMapping("/existMemberId")
    public ResponseEntity<Boolean> checkMemberId(@RequestParam String memberId){
        return ResponseEntity.ok(authService.checkMemberId(memberId));
    }

    @GetMapping("/existNickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname){
        return ResponseEntity.ok(authService.checkNickname(nickname));
    }

    @GetMapping("/existEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email){
        return ResponseEntity.ok(authService.checkEmail(email));
    }
}
