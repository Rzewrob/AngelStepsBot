package com.crazy

import com.crazy.models.FileWriterConfigV2
import com.crazy.models.FileWriterType
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.time.LocalDateTime
import java.util.regex.Matcher
import java.util.regex.Pattern


class FileWriterV2 { // This is just intended to play around, so don't mind the mess

    private val now = LocalDateTime.now().toString().replace(":", "")
    private lateinit var config: FileWriterConfigV2

    private var patterns = mutableListOf<String>()

    private var parser = ParserV2()

    fun initFiles(configOverride: FileWriterConfigV2? = null) {
        config = configOverride ?: getConfigFileOrDefaults()

        config.fileDefinitions.removeIf { !it.active }

        config.fileDefinitions.forEach {
            if (it.active) {
                val newFileName = createFile(it.fileName, it.fileExtension, it.writeFileHeader)
                it.fullFileName = newFileName
            }
        }

        buildPatterns()
    }

    private fun setFileName(name: String, extension: String = "txt"): String {
        return "Dump_${name}_$now.$extension"
    }

    private fun getConfigFileOrDefaults(): FileWriterConfigV2 {

        return try {
            val fileContents = File("FileWriterConfigV2.json").readText().trim()
            val config = ObjectMapper().readValue(fileContents, FileWriterConfigV2::class.java)
            writeLog("** [ERROR]: Successfully read the FileWriterConfig file")
            config
        } catch (e: Exception) {
            writeLog("** [ERROR]: Failed to read config")
            FileWriterConfigV2("Failed_", "", mutableListOf())
        }
    }
    
    private fun createFile(fileName: String, fileExtension: String, addFileHeader: Boolean): String {
        val newFileName = setFileName(fileName, fileExtension)
        val newFileNameWithPath = getFileWithPath(newFileName, config.filesLocation)
        try {
            writeLog("Creating File $newFileName")
            if (!File(newFileNameWithPath).exists()) {
                var text = ""
                if (addFileHeader) {
                    text = "File $newFileName Started at $now"
                }
                File(newFileNameWithPath).writeText(text)
                writeLog("Created File $newFileName")
            } else {
                writeLog("File $newFileName already exists, will write to it instead")
            }
        } catch (e: Exception) {
            writeLog("** [ERROR]: Failed to create file $newFileName - due to: $e")
        }
        return newFileName
    }

    private fun getFileWithPath(fileName: String, path: String): String {
        val pathToUse = if (path.isNotEmpty()) "$path/" else ""
        return "$pathToUse$fileName"
    }

    private fun buildPatterns() {
        patterns = this.config.fileDefinitions.flatMap { it.patterns }.map { it.findPattern }.distinct().toMutableList()
    }

    fun listen(message: String) {
        val matchList = patterns.filter { it in message }
        val matchFound = matchList.isNotEmpty()
        if (matchFound) {
            // Temp to just make progress, redo
//            val files = config.fileDefinitions.filter {
//                it.patterns.any { it2 -> matchList.contains(it2.messagePattern) }
//            }
//
//            files.forEach {
//                File(it.fullFileName).writeText(message)
//            }


            //Temp Crap
            config.fileDefinitions.forEach {file ->
                file.patterns.forEach {
                    val timeStamp = if (file.includeTimeStamp) "${LocalDateTime.now()} " else ""
//                    var fileMessage = ""
//                    if (it.findPattern == "") {
//                        fileMessage = message
//                    }
                    if (matchList.contains(it.findPattern)) {
                        val fileMessage = getMessage(message, it.messagePattern)
                        File(getFileWithPath(file.fullFileName, config.filesLocation)).appendText("\n$timeStamp$fileMessage")
                    }

//                    if (fileMessage.isNotEmpty()) {
//                        File(getFileWithPath(file.fullFileName, config.filesLocation)).appendText("\n$timeStamp$fileMessage")
//                    }
                }
            }

            println("Bananas!!!!!")
        } else {
            println("NO BANANAS!")
        }
    }

    private fun getMessage(message: String, findPattern: String): String {

        val matchList: MutableList<String> = ArrayList()
        val regex: Pattern = Pattern.compile("~(.*?)~")
        val regexMatcher: Matcher = regex.matcher(findPattern)

        while (regexMatcher.find()) { //Finds Matching Pattern in String
            matchList.add(regexMatcher.group(1)) //Fetching Group from String
        }

        var newMessage = findPattern

        for (str in matchList) {
            val parserMap = parserMap[str] ?: throw Exception() // TODO Fix
            val map = parser.parseMessage(message)
            val stuff = parser.getValue(map, parserMap)
            newMessage = newMessage.replace("~$str~", stuff)
            println(str)
        }

//        println(newMessage)
        return newMessage
    }


    private fun writeLog(textToWrite: String) {
        println(textToWrite)
    }

    fun writeLineToFile(textToWrite: String, fileToWrite: FileWriterType) {
        try {
//            val file = getFileNameByType(fileToWrite)
//            File(file).appendText("\n${LocalDateTime.now()} - $textToWrite")
        } catch (e: Exception) {
            println("***** ERROR: Failed to write to file due to: $e")
        }
    }

    fun writeToBitCheerFile(type: String, username: String?, value: String?, privateMessage: String?) {
        if (value != null && (value.toIntOrNull() ?: 0) > 0) {
            writeLineToFile("$username - New $type: $value - $privateMessage", FileWriterType.BitCheerFile)
//            if (config.writeCsvFile) {
//                writeCsvFile(type, username, value, privateMessage)
//            }
        }
    }

    fun writeToMessageFile(username: String?, privateMessage: String?) {
        if (!privateMessage.isNullOrBlank()) {
            writeLineToFile("$username - $privateMessage", FileWriterType.MessageFile)
        }
    }

    private fun writeCsvFile(type: String, username: String?, value: String?, privateMessage: String?) {
        val message = "$type,$username,$value,$privateMessage"
        writeLineToFile(message, FileWriterType.BitCheerFileCsv)
    }
}

