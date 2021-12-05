package com.project.githubuser.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class User(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("follower")
    val follower: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("repository")
    val repository: Int,
    @SerializedName("username")
    val username: String
) : Parcelable