package com.crazy

import com.crazy.models.ParserOptions
import jdk.internal.org.objectweb.asm.commons.Method

class ParserV2 {
    open var usernameText: String = "display-name"
    open lateinit var valueText: String
    open var userMessage1: String = "vip"
    open var userMessage2: String = "user-type"

    fun parseMessage(message: String): List<Pair<String, String>> {
        return message.split(";").map {
            val map = it.split("=")
            Pair(map[0], map[1])
        }
    }

    fun getValue(map: List<Pair<String, String>>, parserOption: ParserOptions): String {
        return if (parserOption.overrideMethod != null) {
//            val pr2 = ParserV2::class.java.getMethod("pr2", Int::class.java).kotlinFunction

            ""
        } else {
            getValueInternal(parserOption.text, map) ?: "[CNF]"
        }
    }

    private fun getValueInternal(text: String, map: List<Pair<String, String>>): String? {
        return map.find { it.first == text }?.second
    }

    fun getPrivateMessage(map: List<Pair<String, String>>): String? {
        var privateMessage = getValueInternal(userMessage1, map)
        if (privateMessage.isNullOrBlank()) {
            privateMessage = getValueInternal(userMessage2, map)
        }

        return privateMessage?.substringAfter("#angel_steps")?.trim()
    }

    fun getStuff(message: String): Triple<String, String, String> {
        val map = parseMessage(message)
        val username = getValueInternal(usernameText, map) ?: "UNKNOWN"
        val value = getValueInternal(valueText, map) ?: "UNKNOWN"
        val privateMessage = getPrivateMessage(map) ?: "UNKNOWN"
        return Triple(username, value, privateMessage)
    }
}