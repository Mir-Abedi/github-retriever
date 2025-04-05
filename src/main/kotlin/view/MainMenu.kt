package view

import org.example.view.*

object MainMenu {
    private val commandMapping = mapOf(
        "get" to GetUserDataCommand,
        "all" to ListAllCachedCommand,
        "searchu" to SearchUserCommand,
        "searchr" to SearchRepositoryCommand,
        "help" to HelpCommand,
        "exit" to ExitCommand,
        null to ExitCommand,
    )

    fun run() {
        println("Welcome to github retriever app. write help for a list of commands. enjoy!")
        var userInput = readlnOrNull()
        while (true) {
            (commandMapping[userInput]?: HelpCommand).execute()
            userInput = readlnOrNull()
        }
    }
}