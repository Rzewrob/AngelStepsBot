package com.crazy

import java.io.File
import java.time.LocalDateTime

class FileWriter { // TODO: This is just jammed in here for testing purposes.

    private val filename = "dumpTest${LocalDateTime.now()}.txt"

    fun createFile() {
        println("Creating File $filename")
        try {
            File(filename).writeText("File $filename Started at ${LocalDateTime.now()}")
            println("Created File $filename")
        } catch (e: Exception) {
            println("***** ERROR: Failed to create file due to: $e")
        }
    }

    fun writeLineToFile(textToWrite: String) {
        try {
            File(filename).appendText("\n$textToWrite")
        } catch (e: Exception) {
            println("***** ERROR: Failed to write to file due to: $e")
        }
    }
}
