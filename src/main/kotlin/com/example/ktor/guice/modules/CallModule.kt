package com.example.ktor.guice.modules

import com.example.ktor.service.HelloService
import com.google.inject.AbstractModule
import io.ktor.application.ApplicationCall

class CallModule(private val call: ApplicationCall): AbstractModule() {
    override fun configure() {
        bind(ApplicationCall::class.java).toInstance(call)
        bind(HelloService::class.java)
    }
}