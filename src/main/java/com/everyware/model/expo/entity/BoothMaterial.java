package com.everyware.model.expo.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "booth_material")
public class BoothMaterial extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booth_material_id")
    private Long id;

    @Column(name = "booth_material_link")
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(name = "booth_material_type")
    private BoothMaterialType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id", nullable = false)
    private Booth booth;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
