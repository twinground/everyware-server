package com.everyware.model.expo.entity;

import java.util.ArrayList;
import java.util.List;
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

@Entity
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expo_id", nullable = false)
    private Expo expo;

    @OneToMany(mappedBy = "booth")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "booth")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "booth")
    private List<BoothMaterial> boothMaterials = new ArrayList<>();
}
