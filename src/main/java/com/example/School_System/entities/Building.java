package com.example.School_System.entities;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "buildings")
@Filter(name = "deletedFilter",condition = "is_deleted = :isDeleted")
public class Building extends SoftDeletableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id",nullable = false)
    private Faculty faculty;

    @Column(name = "building_name",nullable = false,length = 100)
    private String buildingName;

    @Column(name = "building_code",nullable = false)
    private String buildingCode;

    @CreationTimestamp
    @Column(name= "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Building(Faculty faculty,String buildingName,String buildingCode){
        this.faculty = faculty;
        this.buildingName = buildingName;
        this.buildingCode = buildingCode;
    }

    public Building() {
    }

    public Long getId() {
        return id;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
