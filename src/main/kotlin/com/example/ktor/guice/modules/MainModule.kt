package com.example.ktor.guice.modules

import com.example.ktor.routes.HelloRoutes
import com.google.inject.AbstractModule
import io.ktor.application.Application

class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        bind(HelloRoutes::class.java).asEagerSingleton()
        bind(Application::class.java).toInstance(application)
    }
}