package net.dragonfly.monitor

import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

object DragonflyMonitor {

    @JvmStatic
    fun main(args: Array<String>) {
        val port = System.getenv("PORT")
        println("Launching on port $port")
        embeddedServer(Netty, port = port.toInt()) {
            routing {
                ping()
                status()
            }
        }.start(wait = true)
    }
}