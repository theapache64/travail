package com.theapache64.travail.utils

import com.theapache64.travail.core.ConfigManager
import com.theapache64.travail.core.ReportGenerator
import com.theapache64.travail.models.Task
import org.junit.Assert.*
import org.junit.Test

class MailHelperTest {
    @Test
    fun testSendMailSuccess() {
        val config = ConfigManager.getConfig()
        MailHelper.sendMail(
            config,
            "theapache64@gmail.com",
            "Test",
            "Test subject"
        )
    }

    @Test
    fun testSendReportSuccess() {

        val tasks = mutableListOf(
            Task("10:00", "12:00", "Working with PayPal SDK"),
            Task("12:00", "01:00", "Lunch"),
            Task("01:00", "06:00", "Bug fixing in project ABCD")
        )

        val config = ConfigManager.getConfig()
        val report = ReportGenerator.generateReport(config, tasks)
        val subject = "Work Status Report - ${DateUtils.getToday()}"
        MailHelper.sendMail(
            config,
            config.superior.email,
            subject,
            report
        )
    }
}