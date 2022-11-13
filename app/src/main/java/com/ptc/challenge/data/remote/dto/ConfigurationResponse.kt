package com.ptc.challenge.data.remote.dto

data class ConfigurationResponse(
    val metadata: ConfigurationData,
    val session: Session,
    val success: Boolean
)