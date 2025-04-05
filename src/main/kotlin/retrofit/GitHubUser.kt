package retrofit

import com.google.gson.annotations.SerializedName

data class GitHubUser(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int,
    @SerializedName("created_at") val created_at: String,
)