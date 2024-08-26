package com.kotlinspring.course_catalog.unit

import com.kotlinspring.course_catalog.controller.CourseController
import com.kotlinspring.course_catalog.controller.GreetingController
import com.kotlinspring.course_catalog.dto.CourseDTO
import com.kotlinspring.course_catalog.entity.Course
import com.kotlinspring.course_catalog.service.CourseService
import com.kotlinspring.course_catalog.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseService: CourseService

    private fun courseDTO() = CourseDTO(1, "Spring with Kotlin", "Dev")

    @Test
    fun testAddCourse() {

        every {
            courseService.addCourse(any())
        } returns courseDTO()

        val saveCourseDTO = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO())
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue(saveCourseDTO!!.id == 1)

    }

    @Test
    fun testGetAllCourses() {

        every {
            courseService.retrieveAllCourses()
        } returns listOf(courseDTO())


        val listCourseDTO = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(1, listCourseDTO!!.size)
    }

    @Test
    fun testUpdateCourse() {

        every {
            courseService.updateCourse(any(), any())
        } returns courseDTO().apply { name = "Spring Boot with Java" }

        val updatedCourseDTO = webTestClient.put()
            .uri("/v1/courses/{course_id}", 1)
            .bodyValue(courseDTO())
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("Spring Boot with Java", updatedCourseDTO!!.name)
    }

    @Test
    fun testDeleteCourse() {

        every {
            courseService.deleteById(any())
        } just runs

        webTestClient.delete()
            .uri("/v1/courses/{course_id}", 1)
            .exchange()
            .expectStatus().isNoContent

    }

}