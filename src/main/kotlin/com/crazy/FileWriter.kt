package com.crazy

import com.crazy.models.FileWriterConfig
import com.crazy.models.FileWriterType
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.time.LocalDateTime

class FileWriter { // This is just intended to play around, so don't mind the mess

    private val now = LocalDateTime.now().toString().replace(":", "")
    private val defaultFileName = "Dump_FallbackName"

    private var bitCheerFileName: String = "${defaultFileName}_DonoFile_${now}.txt"
    private var bitCheerFileNameCsv: String = "${defaultFileName}_DonoCsVFile_${now}.csv"
    private var messageFileName: String = "${defaultFileName}_MessageFile_${now}.txt"
    private var fullDumpFileName: String = "${defaultFileName}_FullFile_${now}.txt"

    private lateinit var config: FileWriterConfig

    fun initFiles() {
        config = getConfigFileOrDefaults()

        if (config.writeBitCheerFile) {
            bitCheerFileName = setFileName(config.bitCheerFileName)
            createFile(bitCheerFileName)
            if (config.writeCsvFile) {
                bitCheerFileNameCsv = setFileName(config.bitCheerFileName, "csv")
                createFile(bitCheerFileNameCsv)
            }
        }
        if (config.writeFullFileDump) {
            fullDumpFileName = setFileName(config.fullFileDumpName)
            createFile(fullDumpFileName)
        }
        if (config.writeMessageFile) {
            messageFileName = setFileName(config.messageFileName)
            createFile(messageFileName)
        }
    }

    private fun setFileName(name: String, extension: String = "txt"): String {
        return "Dump_${name}_$now.$extension"
    }

    private fun getConfigFileOrDefaults(): FileWriterConfig {

        return try {
            val fileContents = File("FileWriterConfig.json").readText().trim()
            val config = ObjectMapper().readValue(fileContents, FileWriterConfig::class.java)
            println("**** Successfully read the FileWriterConfig file")
            config
        } catch (e: Exception) {
            println("**** Failed to read config, using defaults")
            FileWriterConfig()
        }
    }
    
    fun createFile(filenameToCreate: String) {
        try {
            println("Creating File $filenameToCreate")
            if (!File(filenameToCreate).exists()) {
                File(filenameToCreate).writeText("File $filenameToCreate Started at $now")
                println("Created File $filenameToCreate")
            } else {
                println("File $filenameToCreate already exists")
            }
        } catch (e: Exception) {
            println("***** ERROR: Failed to create file due to: $e")
        }
    }

    fun writeLineToFile(textToWrite: String, fileToWrite: FileWriterType) {
        try {
            val file = getFileNameByType(fileToWrite)
            File(file).appendText("\n${LocalDateTime.now()} - $textToWrite")
        } catch (e: Exception) {
            println("***** ERROR: Failed to write to file due to: $e")
        }
    }

    fun writeToBitCheerFile(type: String, username: String?, value: String?, privateMessage: String?) {
        if (value != null && (value.toIntOrNull() ?: 0) > 0) {
            writeLineToFile("$username - New $type: $value - $privateMessage", FileWriterType.BitCheerFile)
            if (config.writeCsvFile) {
                writeCsvFile(type, username, value, privateMessage)
            }
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

    private fun getFileNameByType(type: FileWriterType): String {
        return when (type) {
            FileWriterType.BitCheerFile -> bitCheerFileName
            FileWriterType.MessageFile -> messageFileName
            FileWriterType.FullDump -> fullDumpFileName
            FileWriterType.BitCheerFileCsv -> bitCheerFileNameCsv
        }
    }
}

