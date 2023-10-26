package com.crazy

open class TextParser {

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

    fun getValue(text: String, map: List<Pair<String, String>>): String? {
        return map.find { it.first == text }?.second
    }

    fun getPrivateMessage(map: List<Pair<String, String>>): String? {
        var privateMessage = getValue(userMessage1, map)
        if (privateMessage.isNullOrBlank()) {
            privateMessage = getValue(userMessage2, map)
        }

        return privateMessage?.substringAfter("#angel_steps")?.trim()
    }

    fun getStuff(message: String): Triple<String, String, String> {
        val map = parseMessage(message)
        val username = getValue(usernameText, map) ?: "UNKNOWN"
        val value = getValue(valueText, map) ?: "UNKNOWN"
        val privateMessage = getPrivateMessage(map) ?: "UNKNOWN"
        return Triple(username, value, privateMessage)
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

class GenericParser: TextParser() {
    override var usernameText: String = "display-name"
    override var valueText: String = ""
}