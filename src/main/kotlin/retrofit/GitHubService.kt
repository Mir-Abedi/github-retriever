package retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("/users/{user}")
    suspend fun getUserInfo(@Path("user") user: String): GitHubUser
    @GET("/users/{user}/repos")
    suspend fun getUserRepositories(@Path("user") user: String): List<GitHubRepository>
}