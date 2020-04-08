package com.theapache64.travail

import com.theapache64.travail.core.ConfigManager
import com.theapache64.travail.core.ReportGenerator
import com.theapache64.travail.models.Task
import com.theapache64.travail.utils.DateUtils
import com.theapache64.travail.utils.InputUtils
import com.theapache64.travail.utils.MailHelper
import com.theapache64.travail.utils.TimeUtils
import com.theapache64.travail.utils.TimeUtils.intToTime
import com.theapache64.travail.utils.TimeUtils.stringToTime
import com.theapache64.travail.utils.TimeUtils.timeToInt
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

class Main


private const val DAY_END = 2359
private const val MODE_FROM_TO = 1
private const val MODE_FROM_TIME = 2

fun main(args: Array<String>) {

    val mode = InputUtils.getInt(
        """
            Choose mode
            $MODE_FROM_TO) From To Mode
            $MODE_FROM_TIME) From Hour Mode
            
            Choose
        """.trimIndent(),
        1,
        2
    )

    val tasks = when (mode) {
        MODE_FROM_TO -> {
            startFromToMode()
        }

        MODE_FROM_TIME -> {
            startFromTimeMode()
        }

        else -> {
            throw IllegalArgumentException("Undefined mode `$mode`")
        }
    }

    if (tasks.isNotEmpty()) {
        println("Generating tasks...")
        val config = ConfigManager.getConfig()
        val report = ReportGenerator.generateReport(config, tasks)

        // Preview report
        println("----------------------------------------------------------------------------")
        println(report.replace("            ", ""))
        println("----------------------------------------------------------------------------")

        val isSend = InputUtils.getString("Do you want to sent? (y/N)", true).toLowerCase()[0] == 'y'

        if (isSend) {
            println("Sending...")
            val subject = "Work Status Report - ${DateUtils.getToday()}"
            MailHelper.sendMail(
                config,
                config.superior.email,
                subject,
                report
            )
            println("Sent 👍")
        } else {
            println("Cancelled")
        }

    } else {
        println("No tasks reported. Skipping work report generation...")
    }
}

fun startFromTimeMode(): List<Task> {
    val tasks = mutableListOf<Task>()
    while (true) {
        val from = if (tasks.isEmpty()) {
            stringToTime(validateTimeString { InputUtils.getString("From", true) })
        } else {
            tasks.last().to
        }

        val toMsg = if (tasks.isEmpty()) {
            "➡️ Time took"
        } else {
            "➡️ From $from, time took"
        }

        val timeTook = validateTimeString {
            InputUtils.getString(
                toMsg,
                true
            )
        }

        if (timeTook == "-1") {
            println("Ending day")
            break
        } else {
            val to = TimeUtils.add(from, stringToTime(timeTook))
            val task = InputUtils.getString("Task", true)
            tasks.add(Task(from, to, task))
        }
    }
    return tasks
}


fun validateTimeString(readTime: () -> String): String {

    val timeString = readTime()
    if (timeString == "-1") {
        return timeString
    }

    try {
        if (timeString.contains(".")) {
            // has minutes
            val timeSplit = timeString.split(".")
            val hourInt = timeSplit[0].toInt()
            if (hourInt >= 24) {
                return hourErrorString { readTime() }
            }
        } else {
            // only hour
            val hourInt = timeString.toInt()
            if (hourInt >= 24) {
                return hourErrorString { readTime() }
            }
        }

        return timeString.trim()

    } catch (e: NumberFormatException) {
        println("Invalid time")
        return validateTimeString { readTime() }
    }
}

private fun startFromToMode(): List<Task> {

    // consts
    val tasks = mutableListOf<Task>()
    while (true) {

        val from = if (tasks.isEmpty()) {
            intToTime(verifyTimeInt { InputUtils.getInt("From", 0, DAY_END) }) // 23:59
        } else {
            tasks.last().to
        }

        val toMsg = if (tasks.isEmpty()) {
            "➡️ To"
        } else {
            "➡️ From $from to"
        }

        val to = verifyTimeInt { InputUtils.getInt(toMsg, removeTrailingZero(timeToInt(from)), DAY_END, arrayOf(-1)) }

        if (to == -1) {
            println("Ending day")
            break
        } else {
            val task = InputUtils.getString("Task", true)
            tasks.add(Task(from, intToTime(to), task))
        }
    }

    return tasks
}

private val TRAIL_ZERO_REGEX = "[0]+\$".toRegex()

fun removeTrailingZero(int: Int): Int {
    return int.toString().replace(TRAIL_ZERO_REGEX, "").toInt()
}

fun verifyTimeInt(readTime: () -> Int): Int {
    val time = readTime()

    if (time == -1) {
        return time
    }

    val timeString = time.toString()
    try {
        if (timeString.length == 2 && time >= 24) {
            return hourError(readTime)
        } else if (timeString.length == 3) {
            val minutes = timeString.substring(1).toInt()
            if (minutes >= 60) {
                return minutesError(readTime)
            }
        } else if (timeString.length == 4) {

            val hrs = timeString.substring(0, 2).toInt()
            val minutes = timeString.substring(3).toInt()

            if (hrs >= 24) {
                return hourError(readTime)
            } else if (minutes >= 60) {
                return minutesError(readTime)
            }
        }
        return time
    } catch (e: NumberFormatException) {
        println("Invalid time")
        return verifyTimeInt { readTime() }
    }
}

private fun minutesError(verifyMethod: () -> Int): Int {
    println("Minutes must be less than 60")
    return verifyTimeInt(verifyMethod)
}

private fun hourError(verifyMethod: () -> Int): Int {
    println("Hour must be less than 24")
    return verifyTimeInt(verifyMethod)
}

private fun hourErrorString(verifyMethod: () -> String): String {
    println("Hour must be less than 24")
    return validateTimeString(verifyMethod)
}