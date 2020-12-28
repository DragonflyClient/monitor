package net.dragonfly.monitor

import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*

object DragonflyMonitor {

    @JvmStatic
    fun main(args: Array<String>) {
        embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
            routing {
                get("/") {
                    call.respondHtml(HttpStatusCode.OK, HTML::index)
                }
            }
        }.start(wait = true)
    }
}

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            +"Hello from Ktor"
        }
    }
}