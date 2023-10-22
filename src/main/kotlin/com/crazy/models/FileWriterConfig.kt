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

data class FileWriterConfigV2(
    val filesPrefix: String = "Dump_",
    val filesLocation: String = "",
    val fileDefinitions: MutableList<FileDefinition>,
    val extraLogs: Boolean = false,
)

data class FileDefinition (
    val id: String,
    val fileName: String,
    val fileExtension: String = "txt",
    val active: Boolean = true,
    val patterns: MutableList<FilePatterns> = mutableListOf(),
    val writeFileHeader: Boolean = true,
    val includeTimeStamp: Boolean = true,
    var fullFileName: String = ""
)

data class FilePatterns (
    val findPattern: String,
    val messagePattern: String,
)