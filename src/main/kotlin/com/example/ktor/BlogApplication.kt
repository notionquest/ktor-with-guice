package com.example.ktor

import com.example.ktor.guice.modules.CallModule
import com.example.ktor.guice.modules.HelloModule
import com.example.ktor.guice.modules.MainModule
import com.example.ktor.service.HelloService
import com.google.inject.Guice
import com.google.inject.Injector
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.AttributeKey

// attribute key for storing injector in a call
val InjectorKey = AttributeKey<Injector>("injector")

// accessor for injector from a call
val ApplicationCall.injector: Injector get() = attributes[InjectorKey]

fun main(args: Array<String>) {

    embeddedServer(Netty, 8080) {
        // Create main injector
        val injector = Guice.createInjector(HelloModule(), MainModule(this))

        // Intercept application call and put child injector into attributes
        intercept(ApplicationCallPipeline.Features) {
            call.attributes.put(InjectorKey, injector.createChildInjector(CallModule(call)))
        }

        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        }
        routing {
            get("/") {
                call.respondText("My Example Blog", ContentType.Text.Html)
            }
            get("/home") {
                val user = User("Hello World!", "helloemail@example.com")
                call.respond(FreeMarkerContent("home.ftl", mapOf("user" to user)))
            }
            get("/contact") {
                val user = User("Hello World!", "helloemail@example.com")
                call.respond(FreeMarkerContent("contact.ftl", mapOf("user" to user)))
            }
            get ("/helloguice"){
                val user = User(call.injector.getInstance(HelloService::class.java).getMessage(), "helloemail@example.com")
                call.respond(FreeMarkerContent("home.ftl", mapOf("user" to user)))
            }
        }
    }.start(wait = true)
}

data class User(val name: String, val email: String)