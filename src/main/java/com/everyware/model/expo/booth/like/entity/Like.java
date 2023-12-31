package com.everyware.model.expo.booth.like.entity;

import com.everyware.common.entity.BaseEntity;
import com.everyware.model.expo.booth.booth.entity.Booth;
import com.everyware.model.member.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "heart")
@NoArgsConstructor
public class Like extends BaseEntity {
    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id", nullable = false)
    private Booth booth;


    public Like(Member member, Booth booth) {
        this.member = member;
        this.booth = booth;
    }
}
