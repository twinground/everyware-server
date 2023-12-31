package com.everyware.model.expo.booth.boothmeet;

import com.everyware.model.expo.booth.booth.entity.Booth;
import com.everyware.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface BoothMeetRepository extends JpaRepository<BoothMeet,Long> {
    boolean existsByBoothAndMeetReserveTimeAfter(Booth booth,LocalDateTime now);
    BoothMeet findTopByBoothOrderByMeetReserveTimeDesc(Booth booth);

    boolean existsByBoothAndMember(Booth booth, Member member);

    boolean existsByMemberAndMeetReserveTimeBetween(Member member, LocalDateTime startTime, LocalDateTime endTime);

}
