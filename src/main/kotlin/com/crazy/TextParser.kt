package com.crazy

open class TextParser {

    open lateinit var usernameText: String
    open lateinit var valueText: String

    fun parseMessage(message: String): List<Pair<String, String>> {
        return message.split(";").map {
            val map = it.split("=")
            Pair(map[0], map[1])
        }
    }

    fun getValue(text: String, map: List<Pair<String, String>>): String? {
        return map.find { it.first == text }?.second
    }

    fun getStuff(message: String): Pair<String, String> {
        val map = parseMessage(message)
        val username = getValue(usernameText, map) ?: "UNKNOWN"
        val value = getValue(valueText, map) ?: "UNKNOWN"
        return Pair(username, value)
    }
}

class CheerParser: TextParser() {
    override var usernameText: String = "display-name"
    override var valueText: String = "bits"
}

class SubParser: TextParser() {
    override var usernameText: String = "display-name"
    override var valueText: String = "msg-param-sub-plan"
}