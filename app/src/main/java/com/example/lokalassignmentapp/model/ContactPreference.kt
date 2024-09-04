package com.example.lokalassignmentapp.model

data class ContactPreference(
    val preference: Int?= null,
    val preferred_call_end_time: String? = null,
    val preferred_call_start_time: String? =null,
    val whatsapp_link: String?= null
)