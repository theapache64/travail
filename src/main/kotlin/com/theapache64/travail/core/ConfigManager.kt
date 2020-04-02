package com.theapache64.travail.core

import com.theapache64.travail.models.Config
import com.theapache64.travail.utils.GsonUtils
import com.theapache64.travail.utils.JarUtils
import java.io.File

object ConfigManager {

    private val configFile = File("${JarUtils.getJarDir()}config.json")

    fun getConfig(): Config {
        val configJson = configFile.readText()
        return GsonUtils.gson.fromJson(configJson, Config::class.java)
    }

}