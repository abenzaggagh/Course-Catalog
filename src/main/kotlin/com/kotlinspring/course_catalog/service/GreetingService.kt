package com.kotlinspring.course_catalog.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GreetingService {

    @Value("\${message}")
    lateinit var message: String

    fun retrieveGreetings(name: String) = "$message, $name"

}