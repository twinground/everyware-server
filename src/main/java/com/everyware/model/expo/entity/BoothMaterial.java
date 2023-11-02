package com.everyware.model.expo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "booth_material")
public class BoothMaterial extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "booth_material_id")
    private Long id;

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
