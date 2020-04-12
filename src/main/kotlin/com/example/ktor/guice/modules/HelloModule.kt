package com.example.ktor.guice.modules

import com.google.inject.AbstractModule
import com.google.inject.Provides
import javax.inject.Named
import javax.inject.Singleton

class HelloModule : AbstractModule() {
    override fun configure() {}

    @Provides
    @Singleton
    @Named("hello-message")
    fun helloMessage(): String = "Hello from Ktor Guice sample application"
}