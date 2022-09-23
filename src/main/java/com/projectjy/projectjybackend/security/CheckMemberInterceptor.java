package com.projectjy.projectjybackend.security;

import com.projectjy.projectjybackend.repository.MemberRepository;
import com.projectjy.projectjybackend.security.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class CheckMemberInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String memberIdString = request.getParameter("memberId");
        Long memberIdLong = SecurityUtil.getCurrentId();
        Member member = memberRepository.findById(memberIdLong).orElseThrow(() -> new RuntimeException("해당 회원이 없습니다."));
        return member.getMemberId().equals(memberIdString);
    }
}
