package com.kotlinspring.course_catalog.repository

import com.kotlinspring.course_catalog.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository


interface CourseRepository: CrudRepository<Course, Int> {

    fun findCourseByNameContaining(courseName: String): List<Course>

    @Query("select * from COURSES c where c.name like %?1%", nativeQuery = true)
    fun findCoursesByName(courseName: String): List<Course>
}