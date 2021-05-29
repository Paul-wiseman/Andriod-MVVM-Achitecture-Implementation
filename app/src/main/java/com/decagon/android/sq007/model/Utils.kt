package com.decagon.android.sq007.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("userId")
    var userID: Int,
    var id: Int,
    var title: String,
    var body: String
)
class AllComments : ArrayList<Comment>()

data class Comment(
    var postId: Int,
    var id: Int,
    var name: String,
    var email: String,
    var body: String

)

