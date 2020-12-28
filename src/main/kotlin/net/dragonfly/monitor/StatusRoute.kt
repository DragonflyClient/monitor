package net.dragonfly.monitor

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.status() {
    get("status") {
        call.respond(DragonflyMonitor.servicesWithStatus)
    }
}