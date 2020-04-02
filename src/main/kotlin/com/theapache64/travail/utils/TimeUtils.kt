package com.theapache64.travail.utils

import java.lang.IllegalArgumentException

object TimeUtils {
    fun intToTime(i: Int): String {

        if (i == 0) {
            return "00:00"
        } else {
            val iString = i.toString()
            return when (iString.length) {
                1 -> {
                    "0$i:00"
                }
                2 -> {
                    "$i:00"
                }
                3 -> {
                    val h = iString[0]
                    "0$h:${iString.substring(1)}"
                }
                4 -> {
                    val hh = iString.substring(0, 2)
                    val mm = iString.substring(2)
                    return "$hh:$mm"
                }

                else -> throw IllegalArgumentException("Invalid time : $iString")
            }
        }
    }

    fun timeToInt(timeString: String): Int {
        return timeString.replace(":", "").toInt()
    }

}