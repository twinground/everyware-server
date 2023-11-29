package com.everyware.model.expo.booth.boothmeet;

import com.everyware.common.entity.BaseEntity;
import com.everyware.model.expo.booth.booth.entity.Booth;
import com.everyware.model.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "booth_meet")
public class BoothMeet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booth_meet_id")
    private Long id;

    @Column(name = "meet_reserve_time", nullable = false, updatable = false)
    private LocalDateTime meetReserveTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id", nullable = false)
    private Booth booth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    public BoothMeet(Booth booth, LocalDateTime meetReserveTime, Member member){
        this.meetReserveTime = meetReserveTime;
        this.booth = booth;
        this.member = member;
    }
}
