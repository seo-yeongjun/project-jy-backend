package com.projectjy.projectjybackend.security.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public Member(Long id, String email, String password, String nickname, Authority authority, String memberId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
        this.memberId = memberId;
    }
}
