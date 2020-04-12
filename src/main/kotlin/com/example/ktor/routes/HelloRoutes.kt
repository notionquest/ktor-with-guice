package com.example.ktor.routes

import com.example.ktor.User
import com.example.ktor.injector
import com.example.ktor.service.HelloService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import javax.inject.Inject
import javax.inject.Named

class HelloRoutes @Inject constructor(application: Application, @Named("hello-message") message: String) {
    init {
        application.routing {
            get ("/helloguice2"){
                val user = User(call.injector.getInstance(HelloService::class.java).getMessage(), "helloemail@example.com")
                call.respond(FreeMarkerContent("home.ftl", mapOf("user" to user)))
            }
        }
    }
}