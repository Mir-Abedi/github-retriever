package utils

import java.io.File
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit.GitHubUserWithRepos

class Cache(private val storagePath: String) {
    private val cacheFile = File(storagePath, "cache.json")
    private var cacheMap: MutableMap<String, GitHubUserWithRepos> = mutableMapOf()

    init {
        loadFromFile()
    }

    fun put(key: String, value: GitHubUserWithRepos) {
        cacheMap[key] = value
        saveToFile()
    }

    fun get(key: String): GitHubUserWithRepos? {
        return cacheMap[key]
    }

    fun has(key: String): Boolean {
        return cacheMap.containsKey(key)
    }

    fun getAllKeys(): Set<String> {
        return cacheMap.keys
    }

    fun searchUser(query: String): List<String> {
        return try {
            val regex = query.toRegex()
            cacheMap.keys.filter { key -> regex.containsMatchIn(key) }
        } catch (e: Exception) {
            cacheMap.keys.filter { key -> key.contains(query) }
        }
    }

    fun searchRepos(query: String): Map<String, String> {
        return try {
            val regex = query.toRegex()
            cacheMap.flatMap { (username, userWithRepos) ->
                userWithRepos.repositories
                    .filter { repo -> regex.containsMatchIn(repo.name) }
                    .map { repo -> repo.name to username }
            }.toMap()
        } catch (e: Exception) {
            cacheMap.flatMap { (username, userWithRepos) ->
                userWithRepos.repositories
                    .filter { repo -> repo.name.contains(query) }
                    .map { repo -> repo.name to username }
            }.toMap()
        }
    }

    private fun loadFromFile() {
        if (cacheFile.exists()) {
            val jsonString = cacheFile.readText()
            if (jsonString.isNotBlank()) {
                val type = object : TypeToken<Map<String, GitHubUserWithRepos>>() {}.type
                cacheMap = Gson().fromJson(jsonString, type) ?: mutableMapOf()
            }
        }
    }

    private fun saveToFile() {
        cacheFile.parentFile?.mkdirs()
        cacheFile.writeText(Gson().toJson(cacheMap))
    }

    companion object {
        fun create(storagePath: String): Cache {
            return Cache(storagePath)
        }
    }
}