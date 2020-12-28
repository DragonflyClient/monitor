package net.dragonfly.monitor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

private const val RESET = "\u001B[0m"
private const val RED = "\u001B[31m"
private const val GREEN = "\u001B[32m"
private const val YELLOW = "\u001B[33m"

object DragonflyMonitor {

    val servicesWithStatus = listOf("API", "Blog", "Dashboard", "Dragonfly Main Page", "Ideas Platform", "Kernel")
        .associateWith { Status.AVAILABLE }.toMutableMap()

    @JvmStatic
    fun main(args: Array<String>) {
        val port = System.getenv("PORT")
        println("Launching on port $port")
        embeddedServer(Netty, port = port.toInt()) {
            install(ContentNegotiation) {
                gson {  }
            }

            routing {
                ping()
                status()
            }
        }.start(wait = true)
    }

    fun handleEvent(event: WebhookEvent) {
        val serviceName = event.checkName
        val available = event.checkStateName == "Available" || event.httpStatusCode == 200 || event.httpStatusCode == 1

        val newStatus = if (available) Status.AVAILABLE else Status.OFFLINE
        val previousStatus = servicesWithStatus[serviceName]!!
        val statusColor = if (available) GREEN else RED

        if (previousStatus != newStatus) {
            println("${YELLOW}${serviceName} ${RESET}changed status from $previousStatus to ${statusColor}${newStatus} $RESET")
            servicesWithStatus[serviceName] = newStatus
        } else {
            println("${YELLOW}${serviceName} ${RESET}keeps being ${statusColor}${newStatus} $RESET")
        }
    }
}