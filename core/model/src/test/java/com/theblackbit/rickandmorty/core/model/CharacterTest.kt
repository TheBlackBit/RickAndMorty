package com.theblackbit.rickandmorty.core.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class CharacterTest {

    @Test
    fun `test encodedImageUrl`() {
        val character = Character(id = 1, image = "https://example.com/image.jpg")

        val encodedUrl = character.encodedImageUrl()

        val expectedEncodedUrl = URLEncoder.encode("https://example.com/image.jpg", StandardCharsets.UTF_8.toString())
        assertTrue(encodedUrl == expectedEncodedUrl)
    }

    @Test
    fun `test isAlive`() {
        val deadCharacter = Character(id = 1, status = "Dead")

        val aliveCharacter = Character(id = 2, status = "Alive")

        assertFalse(deadCharacter.isAlive())
        assertTrue(aliveCharacter.isAlive())
    }

    @Test
    fun `test isMale`() {
        val maleCharacter = Character(id = 1, gender = "Male")

        val femaleCharacter = Character(id = 2, gender = "Female")

        assertTrue(maleCharacter.isMale())
        assertFalse(femaleCharacter.isMale())
    }
}
