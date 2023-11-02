package com.crazy

import com.crazy.models.ParserOptions
import kotlin.reflect.KCallable

class ParserV2 {

    companion object {
        val methods: Map<String, KCallable<*>> = ParserV2::class.members
            .associateBy { it.name }
    }

    fun parseMessage(message: String): List<Pair<String, String>> {
        val map = message.split(";").map {
            val map = it.split("=")
            if (map.size > 1) {
                Pair(map[0], map[1])
            } else {
                Pair("", "")
            }
        }.toMutableList()
        map.add(Pair("all-text", message))
        return map
    }

    fun getValue(map: List<Pair<String, String>>, parserOption: ParserOptions): String {
        return if (parserOption.overrideMethod != null) {
            callOverrideMethod(parserOption.overrideMethod, map).toString()
        } else {
            getValueInternal(parserOption.text, map) ?: "[CNF]"
        }
    }

    private fun callOverrideMethod(overrideMethodName: String, vararg args: Any?): Any? {
        val method = methods[overrideMethodName]
            ?: throw IllegalArgumentException("Unknown method")
        return method.call(this, *args)
    }

    private fun getValueInternal(text: String, map: List<Pair<String, String>>): String? {
        return map.find { it.first == text }?.second
    }

    fun getPrivateMessage(map: List<Pair<String, String>>): String? {

        val userMessage1 = "vip"
        val userMessage2 = "user-type"
        var privateMessage = getValueInternal(userMessage1, map)
        if (privateMessage.isNullOrBlank()) {
            privateMessage = getValueInternal(userMessage2, map)
        }

        return privateMessage?.substringAfter("#angel_steps")?.trim()
    }

//    fun getStuff(message: String): Triple<String, String, String> {
//        val map = parseMessage(message)
//        val username = getValueInternal(usernameText, map) ?: "UNKNOWN"
//        val value = getValueInternal(valueText, map) ?: "UNKNOWN"
//        val privateMessage = getPrivateMessage(map) ?: "UNKNOWN"
//        return Triple(username, value, privateMessage)
//    }
}