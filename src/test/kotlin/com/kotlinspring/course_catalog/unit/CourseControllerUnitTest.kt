package com.kotlinspring.course_catalog.unit

import com.kotlinspring.course_catalog.controller.CourseController
import com.kotlinspring.course_catalog.controller.GreetingController
import com.kotlinspring.course_catalog.dto.CourseDTO
import com.kotlinspring.course_catalog.service.CourseService
import com.kotlinspring.course_catalog.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
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

    @Test
    fun testAddCourse() {
        var course: CourseDTO? = null
        every {
            courseService.addCourse(any())
        }.let {
            course = CourseDTO(1, "Spring with Kotlin", "Dev")
        }

        webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(course!!)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

    }

}