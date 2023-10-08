package com.crazy

import java.io.File
import java.time.LocalDateTime

class FileWriter { // This is just intended to play around, so don't mind the mess

    private val now = LocalDateTime.now().toString().replace(":", "")
    private val defaultFileName = "dumpTest$now"
    private var filename = defaultFileName
    private var csvFilename = defaultFileName

    fun initFiles(filenameOverride: String? = null) {
        val file = filenameOverride ?: defaultFileName
        filename = "$file.txt"
        csvFilename = "$file.csv"

        createFile(filename)
        createFile(csvFilename)
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

    fun writeLineToFile(textToWrite: String, fileToWrite: String? = null) {
        try {
            val file = fileToWrite ?: filename
            File(file).appendText("\n$textToWrite")
        } catch (e: Exception) {
            println("***** ERROR: Failed to write to file due to: $e")
        }
    }

    fun writeToFileFancier(type: String, username: String?, value: String?, privateMessage: String?) {
        if (value != null && value.toInt() > 0) {
            writeLineToFile("$username - New $type: $value - $privateMessage")
            writeCsvFile(type, username, value, privateMessage)
        }
    }

    fun writeCsvFile(type: String, username: String?, value: String?, privateMessage: String?) {
        val message = "$type,$username,$value,$privateMessage"
        writeLineToFile(message, csvFilename)
    }
}
