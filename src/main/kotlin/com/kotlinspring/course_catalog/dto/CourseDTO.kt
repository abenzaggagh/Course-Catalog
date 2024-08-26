package com.kotlinspring.course_catalog.dto

import jakarta.validation.constraints.NotBlank

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "Name must not blank")
    var name: String,
    @get:NotBlank(message = "Name must not blank")
    val category: String,
)
