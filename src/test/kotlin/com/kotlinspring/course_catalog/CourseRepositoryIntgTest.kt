package com.kotlinspring.course_catalog

import com.kotlinspring.course_catalog.entity.Course
import com.kotlinspring.course_catalog.repository.CourseRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntgTest {


    @Autowired
    private lateinit var courseRepository: CourseRepository

    fun coursesList() = listOf(
        Course(id = null,
            name = "Course Java",
            category = "Dev"),
        Course(id = null,
            name = "Course Kotlin",
            category = "Dev"),
        Course(id = null,
            name = "Spring with Kotlin",
            category = "Dev"),
        Course(id = null,
            name = "Spring with Java",
            category = "Dev")
    )

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()

        courseRepository.saveAll(coursesList())
    }


    @Test
    fun findCourseByCourseNameContaining() {
        val courses = courseRepository.findCourseByNameContaining("Java")

        Assertions.assertNotNull(courses)
        Assertions.assertEquals(2, courses.size)
    }

    @Test
    fun findCourseByCourseName() {
        val courses = courseRepository.findCoursesByName("Java")

        Assertions.assertNotNull(courses)
        Assertions.assertEquals(2, courses.size)
    }




    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCoursesByName2(name: String, expectedSize: Int) {
        val courses = courseRepository.findCoursesByName("Java")

        Assertions.assertNotNull(courses)
        Assertions.assertEquals(expectedSize, courses.size)

    }


    companion object {

        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments("Java", 2),
                Arguments.arguments("Kotlin", 2))
        }
    }
}