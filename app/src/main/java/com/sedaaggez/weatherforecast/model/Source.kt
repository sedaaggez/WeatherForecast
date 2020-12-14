package com.sedaaggez.weatherforecast.model

import com.google.gson.annotations.SerializedName

data class Source(
    val title: String,
    val slug: String,
    val url: String,
    @SerializedName("crawl_rate")
    val crawlRate: Long
)
