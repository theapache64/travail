package com.theapache64.travail.utils

import com.theapache64.travail.Main
import java.io.File

object JarUtils {

    fun getJarDir(): String {

        val jarDir = File(
            Main::class.java.protectionDomain.codeSource.location
                .toURI()
        ).parent

        if (jarDir.contains("/out/production") || jarDir.contains("/build/classes/")) {
            return ""
        }

        return "$jarDir/"
    }
}