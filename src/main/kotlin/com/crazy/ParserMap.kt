package com.crazy

import com.crazy.models.ParserOptions

val parserMap = mutableMapOf<String, ParserOptions>(
    Pair("USERNAME", ParserOptions("display-name")),
    Pair("BITS", ParserOptions("bits")),
    Pair("USER_MESSAGE", ParserOptions("user-type", "getPrivateMessage"))
)