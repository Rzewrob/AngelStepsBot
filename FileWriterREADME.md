# File Writer Readme

## Config

File: FileWriterConfigV2.json

### Header

```json
{
  "filesPrefix": "Dump_",
  "filesLocation": "",
  "fileDefinitions": [],
  "extraLogs": true
}
```

* filePrefix: Prefix of created log files
  * Note: this was manually added to the gitignore
* fileLocation: *Not Hooked Up* - This will be where you can save the files to
* fileDefintion: List of File Definitions, see that section for more information
* extraLogs: Does not do anything yet

### File Definition
```json
{
  "id": "Dono",
  "fileName": "Dono-Log",
  "fileExtension": "txt",
  "active": true,
  "patterns": [],
  "writeFileHeader": true,
  "includeTimeStamp": true
},
```

* id: Unique ID for the File, this is used to trace back to this file or manually write to it
* fileName: name of the File, appears after the prefix and before the time stamp
* fileExtension: file extension
* active: If the file will create/write
* patterns: List of File Patterns, see that section for more information
* writeFileHeader: Adds a head to the file with a time stamp
* includeTimeStamp: Adds a timestamp to each line written. 

### File Pattern
```json
{
  "findPattern": "bits=",
  "messagePattern": "~USERNAME~ - New Bits: ~BITS~ - ~USER_MESSAGE~"
},
```
* findPattern: Pattern to look for in the message, determines if a line will attempt to write
* messagePattern: Message to write to the line, Words with tildes - ~(WORD)~ - Are replaced with the values defined in ParserMap.kt

## File Writer V2

FileWriterV2.kt

### Open Methods

* init()
  * Params
    * Config: FileWriterConfigV2
      * can take a FileWriterConfigV2 data class as override if you don't want to define a json file
  * Description: creates the files and does some setup 
* listen()
  * Params: 
    * Message: String
      * The twirk twitch log message
  * Description: This method sits in Twirk and listens on each message that comes in.
* writeLineToFile()
  * Params:
    * fileId: String
      * Hardcoded fileId (defined in the config) that you want to write a message to
    * textToWrite: String
      * text to write to file as is, no translation from parser will be done
  * Description: Used if you want to write to a file outside of the listener. FileId must be harded coded and match a defined Id in the config. 

## Parser Map

ParserMap.kt

This file contains a list of Pairs
```kotlin
Pair("USERNAME", ParserOptions("display-name")),
```

This list used for the File Patterns defined in the FileWriterConfigV2.json File.

Pair:
* The pair first is the tilda word to look for 
Ex: "This Message was posted from user ~USERNAME~"
* The pair second is a ParserOptions Object 

ParserOption
* text: is what to look for in the twitch message.
  * Ex: display-name=AUserName;
* ParserOption.overrideMethod: Is if you need to do extra special logic, you can add a method to the FileWriterV2 and add it here. Hopefully not common. 

So the writer will replace ~USERNAME~ with "AUserName"