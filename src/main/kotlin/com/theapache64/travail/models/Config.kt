package com.theapache64.travail.models

import com.google.gson.annotations.SerializedName

data class Config(
    @SerializedName("smtp_config")
    val smtpConfig: SmtpConfig,
    @SerializedName("superior")
    val superior: Superior,
    @SerializedName("your_name")
    val yourName: String // Shifar
) {
    data class SmtpConfig(
        @SerializedName("host")
        val host: String, // smtp-mail.outlook.com
        @SerializedName("password")
        val password: String, // Admin@123
        @SerializedName("port")
        val port: String, // 587
        @SerializedName("username")
        val username: String // shifar.m@thinkpalm.com
    )

    data class Superior(
        @SerializedName("email")
        val email: String, // vinod.r@thinkpalm.com
        @SerializedName("name")
        val name: String // Vinod
    )
}