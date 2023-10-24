package com.crazy

import com.crazy.models.ParserOptions

val parserMap = mutableMapOf<String, ParserOptions>(
    Pair("USERNAME", ParserOptions("display-name")),
    Pair("BITS", ParserOptions("bits")),
    Pair("SUBS", ParserOptions("msg-param-sub-plan")),
    Pair("USER_MESSAGE", ParserOptions("user-type", "getPrivateMessage")),
    Pair("ALL", ParserOptions("all-text"))
)