package com.theapache64.travail.core

import com.theapache64.travail.models.Config
import com.theapache64.travail.models.Task
import java.lang.StringBuilder
import java.text.SimpleDateFormat

object ReportGenerator {

    private val inputTimeFormat = SimpleDateFormat("HH:mm")
    private val outputTimeFormat = SimpleDateFormat("hh:mm aa")

    fun generateReport(config: Config, tasks: List<Task>): String {
        val tasksString = generateTasks(tasks)
        return """
            Hi ${config.superior.name}
            
            Below given my work status report
            
            $tasksString
            
            Regards
            ${config.yourName}
        """.trimIndent()
    }

    private fun generateTasks(tasks: List<Task>): String {
        val sb = StringBuilder()
        for (task in tasks) {
            val fromTime = to12Hours(task.from)
            val toTime = to12Hours(task.to)
            sb.append("$fromTime - $toTime : ${task.task}\n")
        }
        return sb.toString()
    }

    private fun to12Hours(time: String): String {
        return outputTimeFormat.format(inputTimeFormat.parse(time))
    }
}