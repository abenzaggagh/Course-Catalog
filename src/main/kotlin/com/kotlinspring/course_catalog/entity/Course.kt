package com.kotlinspring.course_catalog.entity

import jakarta.persistence.*

@Entity
@Table(name = "courses")
class Course(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,
    var category: String,)
