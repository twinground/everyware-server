package com.everyware.model.expo.entity;

import com.everyware.model.comment.entity.Comment;
import com.everyware.model.like.entity.Like;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "booth")
public class Booth extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booth_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "count")
    private Integer likeCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expo_id", nullable = false)
    private Expo expo;

    @OneToMany(mappedBy = "booth",cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "booth",cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "booth",cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private List<BoothMaterial> boothMaterials = new ArrayList<>();

    public void setLikeCount(Integer likeCount) {
        if (likeCount == null){
            this.likeCount = 0;
        }
        else{
            this.likeCount = likeCount;
        }
    }

    public Integer getLikeCount() {
        return likeCount;
    }
}
