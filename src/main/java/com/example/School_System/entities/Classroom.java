package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.ClassroomType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classrooms")
@Filter(name = "deletedFilter",condition = "is_deleted = :isDeleted")
public class Classroom extends SoftDeletableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id",nullable = false)
    private Building building;

    @Column(name = "classroom_number",nullable = false)
    private String classroomNumber;

    @Column(name = "classroom_name",nullable = false)
    private String classroomName;

    @Enumerated(EnumType.STRING)
    @Column(name = "classroom_type",nullable = false)
    private ClassroomType classroomType;

    @Column(name = "capacity",nullable = false)
    private Integer capacity;

    @Column(name = "has_projector")
    private Boolean hasProjector = false;

    @Column(name = "has_audio_system")
    private Boolean hasAudioSystem = false;

    @Column(name = "is_accessible")
    private Boolean isAccessible = true;

    @OneToMany(mappedBy = "classroom",fetch = FetchType.LAZY)
    private Set<Schedule> schedules = new HashSet<>();

    @OneToMany(mappedBy = "classroom",fetch = FetchType.LAZY)
    private Set<ClassSession> classSessions = new HashSet<>();

    @CreationTimestamp
    @Column(name="created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Classroom(){}

    public Classroom(Building building, String classroomNumber, String classroomName, ClassroomType classroomType, Integer capacity, Boolean hasProjector, Boolean hasAudioSystem, Boolean isAccessible) {
        this.building = building;
        this.classroomNumber = classroomNumber;
        this.classroomName = classroomName;
        this.classroomType = classroomType;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
        this.hasAudioSystem = hasAudioSystem;
        this.isAccessible = isAccessible;
    }

    public Long getId() {
        return id;
    }

    public Building getBuilding() {
        return building;
    }

    public String getClassroomNumber() {
        return classroomNumber;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public ClassroomType getClassroomType() {
        return classroomType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Boolean getHasProjector() {
        return hasProjector;
    }

    public Boolean getHasAudioSystem() {
        return hasAudioSystem;
    }

    public Boolean getAccessible() {
        return isAccessible;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public Set<ClassSession> getClassSessions() {
        return classSessions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
