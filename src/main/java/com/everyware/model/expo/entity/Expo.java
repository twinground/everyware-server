package com.everyware.model.expo.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.everyware.common.entity.BaseEntity;
import com.everyware.model.expo.booth.booth.entity.Booth;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "expo")
public class Expo extends BaseEntity {

    @Id
    @Column(name = "expo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expo_image_url")
    private String expoImageUrl;

    @Column(name = "title")
    private String title;

    @Column(name = "introduction")
    private String introduction;

    //URL 추가하기
    @OneToMany(mappedBy = "expo")
    private List<Booth> booths = new ArrayList<>();
}
