package com.example.ptechforum.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSaveRequestVo {
    private String email;

    private String password;

    private String username;
}
