package com.example.ptechforum.service;

import com.example.ptechforum.model.MemberAdapter;
import com.example.ptechforum.model.Role;
import com.example.ptechforum.model.Member;
import com.example.ptechforum.model.enums.Author;
import com.example.ptechforum.repository.RoleRepository;
import com.example.ptechforum.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void initialize() {
        Member admin = memberRepository.findByEmail("admin@admin.com");
        if (Objects.isNull(admin)) {
            admin = Member.builder()
                    .email("admin@admin.com")
                    .encryptedPassword(bCryptPasswordEncoder.encode("admin"))
                    .username("admin").build();
            memberRepository.save(admin);
            Role adminRole = Role.builder()
                    .author(Author.ADMIN)
                    .member(admin).build();
            roleRepository.save(adminRole);
        }

        Member testMember = memberRepository.findByEmail("test@test.com");
        if (Objects.isNull(testMember)) {
            testMember = Member.builder()
                    .email("test@test.com")
                    .encryptedPassword(bCryptPasswordEncoder.encode("test"))
                    .username("test").build();
            memberRepository.save(testMember);
            Role testUserRole = Role.builder()
                    .author(Author.MEMBER)
                    .member(testMember).build();
            roleRepository.save(testUserRole);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (Objects.isNull(member)) {
            throw new UsernameNotFoundException("Member not found");
        }
        return new MemberAdapter(member);
    }
}
