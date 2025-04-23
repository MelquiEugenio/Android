package com.example.customtip
import org.junit.Assert.assertEquals
import org.junit.Test

class TipCalculatorTest {
    @Test
    fun calculateTip_15PercentNoRoundup() {
        val actualTip = getTip(billAmount = "100", tipPercentage = 0.15, roundUp = false)
        assertEquals(15.0, actualTip, 0.01)
    }
}