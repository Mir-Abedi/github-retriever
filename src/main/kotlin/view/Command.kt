package org.example.view

import utils.GithubRetriever
import kotlin.system.exitProcess

interface Command {
    fun execute()
}

object GetUserDataCommand: Command {
    override fun execute() {
        println("write username:")
        val username = readlnOrNull()?:""
        val githubData = GithubRetriever.getUserData(username)
        println("user data for ${githubData.user.name}:\nfollowers: ${githubData.user.followers}\nfollowing: ${githubData.user.following}\ncreated at: ${githubData.user.created_at}\nrepositories: ${githubData.repositories.joinToString(", ") { it.name }}")
    }
}

object ListAllCachedCommand: Command {
    override fun execute() {
        println("Cached users:")
        println(GithubRetriever.getAllCachedUsers().joinToString(","))
    }
}

object SearchUserCommand: Command {
    override fun execute() {
        println("Write part of username or a regex for finding usernames:")
        val username = readlnOrNull()?:""

        val matchedUsernames = GithubRetriever.searchUsername(username)
        if (matchedUsernames.isEmpty()) {
            println("No Matched usernames")
        } else {
            println("Matched usernames: ${matchedUsernames.joinToString(", ")}")
        }
    }
}

object SearchRepositoryCommand: Command {
    override fun execute() {
        println("Write part of repository name or a regex for finding names:")
        val repoName = readlnOrNull()?:""

        val matchedRepos = GithubRetriever.searchRepo(repoName)
        if (matchedRepos.keys.isEmpty()) {
            println("No Matched repos")
        } else {
            println("Matched repos:")
            matchedRepos.forEach {
                println("reponame: ${it.key} -> username: ${it.value}")
            }
        }
    }
}

object HelpCommand: Command {
    private val helpDescription = """
        list of commands:
        get: Get information about username.
        all: List all cached users.
        searchu: Search among cached users.
        searchr: Search among cached repositories.
        exit: gracefully exit the program.
    """.trimIndent()
    override fun execute() {
        println(helpDescription)
    }
}

object ExitCommand: Command {
    override fun execute() {
        println("See you again")
        exitProcess(0)
    }
}