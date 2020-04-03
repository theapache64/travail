package com.theapache64.travail.utils

import com.winterbe.expekt.should
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class TimeUtilsTest {
    @Test
    fun intToTimeTestSuccess() {
        TimeUtils.intToTime(0).should.equal("00:00")
        TimeUtils.intToTime(1).should.equal("01:00")
        TimeUtils.intToTime(120).should.equal("01:20")
        TimeUtils.intToTime(130).should.equal("01:30")
        TimeUtils.intToTime(1130).should.equal("11:30")
        TimeUtils.intToTime(1230).should.equal("12:30")
        TimeUtils.intToTime(1600).should.equal("16:00")
    }

    @Test
    fun timeToIntTestSuccess() {
        TimeUtils.timeToInt("00:00").should.equal(0)
        TimeUtils.timeToInt("01:00").should.equal(100)
        TimeUtils.timeToInt("01:20").should.equal(120)
        TimeUtils.timeToInt("01:30").should.equal(130)
        TimeUtils.timeToInt("11:30").should.equal(1130)
        TimeUtils.timeToInt("12:30").should.equal(1230)
        TimeUtils.timeToInt("16:00").should.equal(1600)
    }

    @Test
    fun timeStringToDateTestSuccess() {
        TimeUtils.stringToTime("1").should.equal("01:00")
        TimeUtils.stringToTime("1.5").should.equal("01:30")
        TimeUtils.stringToTime("12").should.equal("12:00")
        TimeUtils.stringToTime("16.25").should.equal("16:15")
        TimeUtils.stringToTime("16.5").should.equal("16:30")
    }
}
