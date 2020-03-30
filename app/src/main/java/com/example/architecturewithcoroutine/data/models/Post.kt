package com.example.architecturewithcoroutine.data.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Post {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    var id = 0
    @SerializedName("title")
    var title: String? = null
    @SerializedName("body")
    var body: String? = null
    @SerializedName("userId")
    var userId = 0
}