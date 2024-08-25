package com.kotlinspring.course_catalog.service

import com.kotlinspring.course_catalog.dto.CourseDTO
import com.kotlinspring.course_catalog.entity.Course
import com.kotlinspring.course_catalog.exception.CourseNotFoundException
import com.kotlinspring.course_catalog.repository.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    fun addCourse(courseDTO: CourseDTO): CourseDTO {
        val course = courseDTO.let {
            Course(null, it.name, it.category)
        }

        courseRepository.save(course);

        return course.let { CourseDTO(it.id, it.name, it.category) }
    }

    fun retrieveAllCourses(): List<CourseDTO> {
        return courseRepository.findAll().map { CourseDTO(it.id, it.name, it.category) }
    }

    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO {
        val courseEntity = courseRepository.findById(courseId)

        return if (courseEntity.isPresent) {
            courseEntity.get().let {
                it.name = courseDTO.name
                it.category = courseDTO.category

                courseRepository.save(it)

                CourseDTO(it.id, it.name, it.category)
            }
        } else {
            throw CourseNotFoundException("Not Found Course with id $courseId")
        }
    }

    fun deleteById(courseId: Int) {
        val courseEntity = courseRepository.findById(courseId)

        if (courseEntity.isPresent) {
            courseEntity.get().let {
                courseRepository.deleteById(courseId)
            }
        } else {
            throw CourseNotFoundException("Not Found Course with id $courseId")
        }
    }
}