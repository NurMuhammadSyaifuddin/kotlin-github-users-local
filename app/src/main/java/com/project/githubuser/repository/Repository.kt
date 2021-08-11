package com.project.githubuser.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.githubuser.model.User
import java.io.IOException

object Repository {

    fun getUsers(context: Context?): MutableList<User>? {
        val json: String
        try {
            val inputStream = context?.assets?.open("json/users.json")
            json = inputStream?.bufferedReader().use { it?.readText().toString() }
        } catch (e: IOException) {
            e.printStackTrace()

            return null
        }

        val type = object : TypeToken<MutableList<User>>() {}.type
        val users = Gson().fromJson<MutableList<User>>(json, type)

        users?.let { return it }

        return null
    }

}