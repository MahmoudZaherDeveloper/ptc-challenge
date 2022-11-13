package com.ptc.challenge.data.remote.dto

data class Session(
    val YII_CSRF_TOKEN: String,
    val expire: Any,
    val id: String
)