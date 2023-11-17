package com.everyware.model.member.repository;

import com.everyware.model.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
    Optional<Member>findByNickname(String nickname);

}
