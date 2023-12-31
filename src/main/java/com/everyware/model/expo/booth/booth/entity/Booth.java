package com.everyware.model.expo.booth.booth.entity;

import com.everyware.common.entity.BaseEntity;
import com.everyware.model.expo.booth.boothmeet.BoothMeet;
import com.everyware.model.expo.booth.comment.entity.Comment;
import com.everyware.model.expo.booth.like.entity.Like;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

import com.everyware.model.expo.entity.Expo;
import lombok.Getter;

@Entity
@Getter
@Table(name = "booth")
public class Booth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booth_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "meet_link")
    private String meetLink;

    @Column(name = "count")
    private Integer likeCount = 0;

    @Column(name = "admin_phone_number")
    private String adminPhoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expo_id", nullable = false)
    private Expo expo;

    @OneToMany(mappedBy = "booth", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<BoothMaterial> boothMaterials = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<BoothMeet> boothMeets = new ArrayList<>();



    public void setLikeCount(Integer likeCount) {
        if (likeCount == null) {
            this.likeCount = 0;
        } else {
            this.likeCount = likeCount;
        }
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public String getTopLogo(){
        return getMaterialContentByType(BoothMaterialType.TOP_LOGO);
    }
    public String getBottomLogo(){
        return getMaterialContentByType(BoothMaterialType.BOTTOM_LOGO);
    }

    public List<String> getImages() {
        return getMaterialContentsByType(BoothMaterialType.IMAGE);
    }
    private String getMaterialContentByType(BoothMaterialType type) {
        return boothMaterials.stream()
                .filter(material -> material.getType() == type)
                .findFirst()
                .map(BoothMaterial::getLink)
                .orElse(null);
    }

    private List<String> getMaterialContentsByType(BoothMaterialType type) {
        return boothMaterials.stream()
                .filter(material -> material.getType() == type)
                .map(BoothMaterial::getLink)
                .collect(Collectors.toList());
    }
}
