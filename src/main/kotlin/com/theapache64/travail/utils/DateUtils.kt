package com.theapache64.travail.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy")
    fun getToday(): String {
        return DATE_FORMAT.format(Date())
    }
}