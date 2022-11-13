package com.ptc.challenge.data.remote.dto

data class ConfigurationData(
    val currency: Currency,
    val languages: List<Language>,
    val support: Support
)