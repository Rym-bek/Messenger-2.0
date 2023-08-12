package com.example.messenger.methods

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

object CustomDateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTimestamp(timestamp: String): String? {
        val localZonedDateTime = ZonedDateTime.parse(timestamp.dropLast(1)+"-00:00")
            .withZoneSameInstant(ZoneId.systemDefault())
        return localZonedDateTime.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }
}