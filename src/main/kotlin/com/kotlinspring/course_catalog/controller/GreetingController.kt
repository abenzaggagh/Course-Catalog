package com.kotlinspring.course_catalog.controller

import com.kotlinspring.course_catalog.service.GreetingService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/greetings")
class GreetingController(val greetingService: GreetingService) {

    @GetMapping("/{name}")
    fun retrieveGreetings(@PathVariable("name") name: String) = greetingService.retrieveGreetings(name);


}