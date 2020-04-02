package com.theapache64.travail

import com.theapache64.travail.core.ConfigManager
import com.theapache64.travail.core.ReportGenerator
import com.theapache64.travail.models.Task
import com.theapache64.travail.utils.DateUtils
import com.theapache64.travail.utils.InputUtils
import com.theapache64.travail.utils.MailHelper
import com.theapache64.travail.utils.TimeUtils.intToTime
import com.theapache64.travail.utils.TimeUtils.timeToInt

class Main


private const val DAY_END = 2359

fun main(args: Array<String>) {

    // consts
    val tasks = mutableListOf<Task>()
    while (true) {

        val from = if (tasks.isEmpty()) {
            intToTime(verifyTime { InputUtils.getInt("From", 0, DAY_END) }) // 23:59
        } else {
            tasks.last().to
        }

        val toMsg = if (tasks.isEmpty()) {
            "➡️ To"
        } else {
            "➡️ From $from to"
        }

        val to = verifyTime { InputUtils.getInt(toMsg, removeTrailingZero(timeToInt(from)), DAY_END, arrayOf(-1)) }

        if (to == -1) {
            println("Ending day")
            break
        } else {
            val task = InputUtils.getString("Task", true)
            tasks.add(Task(from, intToTime(to), task))
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

private val TRAIL_ZERO_REGEX = "[0]+\$".toRegex()

fun removeTrailingZero(int: Int): Int {
    return int.toString().replace(TRAIL_ZERO_REGEX, "").toInt()
}

fun verifyTime(verifyMethod: () -> Int): Int {
    val time = verifyMethod()

    if (time == -1) {
        return time
    }

    val timeString = time.toString()
    if (timeString.length == 2 && time >= 24) {
        return hourError(verifyMethod)
    } else if (timeString.length == 3) {
        val minutes = timeString.substring(1).toInt()
        if (minutes >= 60) {
            return minutesError(verifyMethod)
        }
    } else if (timeString.length == 4) {

        val hrs = timeString.substring(0, 2).toInt()
        val minutes = timeString.substring(3).toInt()

        if (hrs >= 24) {
            return hourError(verifyMethod)
        } else if (minutes >= 60) {
            return minutesError(verifyMethod)
        }
    }

    return time
}

private fun minutesError(verifyMethod: () -> Int): Int {
    println("Minutes must be less than 60")
    return verifyTime(verifyMethod)
}

private fun hourError(verifyMethod: () -> Int): Int {
    println("Hour must be less than 24")
    return verifyTime(verifyMethod)
}