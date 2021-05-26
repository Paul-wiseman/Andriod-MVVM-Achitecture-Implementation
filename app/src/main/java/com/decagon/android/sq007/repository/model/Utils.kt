package com.decagon.android.sq007.repository.model

import com.google.gson.annotations.SerializedName

class AllPosts : ArrayList<Posts>()
data class Posts(
    @SerializedName("userId")
    var userID: Int,
    var id: Int,
    var title: String,
    var body: String
)
class AllComments : ArrayList<Comments>()

data class Comments(
    var postId: Int,
    var id: Int,
    var name: String,
    var email: String,
    var body: String

)

