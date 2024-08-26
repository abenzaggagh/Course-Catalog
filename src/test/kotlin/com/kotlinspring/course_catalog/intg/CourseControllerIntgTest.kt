package com.kotlinspring.course_catalog.intg

import com.kotlinspring.course_catalog.dto.CourseDTO
import com.kotlinspring.course_catalog.entity.Course
import com.kotlinspring.course_catalog.repository.CourseRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setup() {
        courseRepository.deleteAll()

        courseRepository.save(Course(id = null, "Java", "Dev"))
    }

    @Test
    fun addCourse() {
        val courseDTO = CourseDTO(null, "Build Rest API with Kotlin", "Dev")

        val result = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue { result!!.id != null }
    }

    @Test
    fun retrieveCourse() {
        val result = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(1, result!!.size)
        Assertions.assertEquals("Java", result[0].name)
    }

    @Test
    fun retrieveCourse_Name() {

        val uri = UriComponentsBuilder
            .fromUriString("/v1/courses")
            .queryParam("courseName", "Java")
            .toUriString()

        val result = webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(1, result!!.size)
        Assertions.assertEquals("Java", result[0].name)
    }

    @Test
    fun updateCourse() {

        val updateCourseDTO = CourseDTO(null, "Kotlin", "Dev")

        val updateCourse = webTestClient
            .put()
            .uri("/v1/courses/1")
            .bodyValue(updateCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        updateCourse?.run {
            Assertions.assertEquals("Kotlin", updateCourse.name)
        }
    }

    @Test
    fun deleteCourse() {
        webTestClient
            .delete()
            .uri("/v1/courses/{course_id}", 1)
            .exchange()
            .expectStatus().isNoContent
    }

}