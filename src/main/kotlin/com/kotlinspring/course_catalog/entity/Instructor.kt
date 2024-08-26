package com.kotlinspring.course_catalog.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "instructors")
data class Instructor(

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    @get:NotBlank(message = "Name cannot be blank")
    val name: String,
    @OneToMany(mappedBy = "instructor",
        cascade = [(CascadeType.ALL)],
        orphanRemoval = true)
    val courses: List<Course> = mutableListOf()
)