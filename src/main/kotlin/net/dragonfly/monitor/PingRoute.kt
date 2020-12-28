@file:Suppress("BlockingMethodInNonBlockingContext")

package net.dragonfly.monitor

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.ping() {
    post("ping") {
        val userAgent = call.request.header("User-Agent")
        require(userAgent == "FreshpingWebhookSender/1.0 (+https://freshping.io/)")

        val jacksonObjectMapper = jacksonObjectMapper()
        val json = jacksonObjectMapper.readTree(call.receiveText()) as ObjectNode
        require(json.get("organization_name").textValue() == "playdragonfly-org")
        require(json.get("organization_id").numberValue() == 130428)
        require(json.get("webhook_id").numberValue() == 22569)

        println(json.toPrettyString())

        val event = jacksonObjectMapper.treeToValue<WebhookEvent>(json.get("webhook_event_data"))
        println(event)

        call.respond(HttpStatusCode.OK, "OK")
    }
}