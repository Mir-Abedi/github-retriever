package utils

import kotlinx.coroutines.runBlocking
import retrofit.*

object GithubRetriever {
    private val cache: Cache = Cache("github-cache/data/")

    fun getUserData(user: String): GitHubUserWithRepos = runBlocking {
        if (cache.has(user)) {
            cache.get(user)!!
        } else {
            val githubData =GitHubUserWithRepos(Dependencies.gitHub.getUserInfo(user), Dependencies.gitHub.getUserRepositories(user))
            cache.put(user, githubData)
            githubData
        }
    }

    fun getAllCachedUsers(): Set<String> = runBlocking {
        cache.getAllKeys()
    }

    fun searchUsername(username: String): List<String> = runBlocking {
        cache.searchUser(username)
    }

    fun searchRepo(repoName: String): Map<String, String> = runBlocking {
        cache.searchRepos(repoName)
    }
}