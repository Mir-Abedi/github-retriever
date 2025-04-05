package retrofit

data class GitHubUserWithRepos(
    val user: GitHubUser,
    val repositories: List<GitHubRepository>
)