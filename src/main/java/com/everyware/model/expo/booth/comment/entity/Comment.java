package com.everyware.model.expo.booth.comment.entity;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id", nullable = false)
    private Booth booth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    private Long rate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Builder
    private Comment(
            String content, Member member, Booth booth, Long rate) {
        this.content = content;
        this.member = member;
        this.booth = booth;
        this.rate = rate;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setBooth(Booth booth) {
        this.booth = booth;
    }
}
