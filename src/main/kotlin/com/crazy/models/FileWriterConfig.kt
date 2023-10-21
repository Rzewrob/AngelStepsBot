package com.crazy.models

data class FileWriterConfig(
    var writeFullFileDump: Boolean = true,
    var fullFileDumpName: String = "FullDump",
    var writeBitCheerFile: Boolean = true,
    var bitCheerFileName: String = "dumpTest",
    var writeMessageFile: Boolean = true,
    var messageFileName: String = "MessageDump",
    var writeCsvFile: Boolean = false
)
