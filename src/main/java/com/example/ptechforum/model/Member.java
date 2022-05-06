package com.example.ptechforum.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String encryptedPassword;

    private String username;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "member")
    private Set<Role> roles = new HashSet<>();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @Builder
    public Member(String email, String encryptedPassword, String username) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.username = username;
    }
}
