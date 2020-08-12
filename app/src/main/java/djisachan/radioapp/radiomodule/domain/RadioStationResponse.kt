package djisachan.radioapp.radiomodule.domain

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Markova Ekaterina on 05-Aug-20
 */

data class RadioStationResponse(
    @JsonProperty("changeuuid")
    val changeuuid: String? = null,
    @JsonProperty("stationuuid")
    val stationuuid: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("url_resolved")
    val url_resolved: String? = null,
    @JsonProperty("homepage")
    val homepage: String? = null,
    @JsonProperty("favicon")
    val favicon: String,
    @JsonProperty("tags")
    val tags: String? = null,
    @JsonProperty("country")
    val country: String? = null,
    @JsonProperty("countrycode")
    var countrycode: String? = null,
    @JsonProperty("state")
    var state: String? = null,
    @JsonProperty("language")
    var language: String? = null,
    @JsonProperty("votes")
    var votes: Float? = 0f,
    @JsonProperty("lastchangetime")
    var lastchangetime: String? = null,
    @JsonProperty("codec")
    var codec: String? = null,
    @JsonProperty("bitrate")
    var bitrate: Float? = 0f,
    @JsonProperty("hls")
    var hls: Float? = 0f,
    @JsonProperty("lastcheckok")
    var lastcheckok: Float? = 0f,
    @JsonProperty("lastchecktime")
    var lastchecktime: String? = null,
    @JsonProperty("lastcheckoktime")
    var lastcheckoktime: String? = null,
    @JsonProperty("lastlocalchecktime")
    var lastlocalchecktime: String? = null,
    @JsonProperty("clicktimestamp")
    var clicktimestamp: String? = null,
    @JsonProperty("clickcount")
    var clickcount: Float? = 0f,
    @JsonProperty("clicktrend")
    var clicktrend: Float? = 0f
)