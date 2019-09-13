package com.matthewbartos.emailer.model

import java.time.LocalDateTime

data class Email(

    val sender: String,

    val subject: String,

    val content: String,

    val sent: LocalDateTime,

    val isRead: Boolean,

    val isDeleted: Boolean
)
