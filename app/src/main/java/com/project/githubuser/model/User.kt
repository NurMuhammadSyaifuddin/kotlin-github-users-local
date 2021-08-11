package com.project.githubuser.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class User(
    @SerializedName("avatar")
    var avatar: String? = null,
    @SerializedName("company")
    var company: String? = null,
    @SerializedName("follower")
    var follower: Int? = null,
    @SerializedName("following")
    var following: Int? = null,
    @SerializedName("location")
    var location: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("repository")
    var repository: Int? = null,
    @SerializedName("username")
    var username: String? = null
) : Parcelable