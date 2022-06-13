package com.example.ptechforum.controller;

import com.example.ptechforum.model.vo.MemberVo;
import com.example.ptechforum.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "app/members/login";
    }

    @GetMapping("/new")
    public String newMember() {
        return "app/members/new";
    }

    @PostMapping("")
    public String create(@ModelAttribute MemberVo vo) {
        memberService.save(vo);
        return "redirect:/";
    }
}
