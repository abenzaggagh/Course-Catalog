package com.kotlinspring.course_catalog.controller

import com.kotlinspring.course_catalog.dto.CourseDTO
import com.kotlinspring.course_catalog.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/v1/courses")
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDTO: CourseDTO) = courseService.addCourse(courseDTO)

    @GetMapping
    fun retrieveAllCourses(@RequestParam("courseName", required = false) courseName: String): List<CourseDTO> = courseService.retrieveAllCourses(courseName)

    @PutMapping("/{course_id}")
    fun updateCourse(@RequestBody courseDTO: CourseDTO,
                     @PathVariable("course_id") courseId: Int) = courseService.updateCourse(courseId, courseDTO)

    @DeleteMapping("/{course_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("course_id") course_id: Int) = courseService.deleteById(course_id)
}