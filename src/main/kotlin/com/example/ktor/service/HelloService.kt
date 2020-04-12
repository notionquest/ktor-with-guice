package com.example.ktor.service

import javax.inject.Inject
import javax.inject.Named

internal class HelloService @Inject constructor(@Named("hello-message") private val helloMessage: String){

    fun getMessage() = helloMessage

}