package com.soulai.portal.service

import org.junit.Assert.assertEquals
import org.junit.Test

class SoulAIAccessibilityServiceTest {

    @Test
    fun testCalculateInputText_Replace() {
        // clear=true should always return newText
        val result = SoulAIAccessibilityService.calculateInputText("old", "hint", "new", true)
        assertEquals("new", result)
    }

    @Test
    fun testCalculateInputText_Append() {
        // clear=false should append
        val result = SoulAIAccessibilityService.calculateInputText("old", "hint", "new", false)
        assertEquals("oldnew", result)
    }

    @Test
    fun testCalculateInputText_AppendWithNulls() {
        val result = SoulAIAccessibilityService.calculateInputText(null, null, "new", false)
        assertEquals("new", result)
    }

    @Test
    fun testCalculateInputText_SmartHint() {
        // Case: Text equals Hint -> Treat as empty (Prevent "Searchsome-text")
        val result = SoulAIAccessibilityService.calculateInputText("Search", "Search", "query", false)
        assertEquals("query", result)
    }

    @Test
    fun testCalculateInputText_SmartHintMismatch() {
        // Case: Text does NOT equal Hint -> Append normally
        val result = SoulAIAccessibilityService.calculateInputText("Existing", "Search", "query", false)
        assertEquals("Existingquery", result)
    }
}
