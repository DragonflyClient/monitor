package net.dragonfly.monitor

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WebhookEvent(
    @JsonProperty("check_state_name")
    val checkStateName: String,
    @JsonProperty("check_name")
    val checkName: String,
    @JsonProperty("request_url")
    val requestUrl: String
)
