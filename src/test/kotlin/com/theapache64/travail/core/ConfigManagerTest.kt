package com.theapache64.travail.core

import com.winterbe.expekt.should
import org.junit.Test

class ConfigManagerTest {
    @Test
    fun testGetConfigSuccess() {
        val config = ConfigManager.getConfig()
        config.yourName.should.equal("Shifar")
    }
}