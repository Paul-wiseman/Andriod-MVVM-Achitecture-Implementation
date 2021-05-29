package com.decagon.android.sq007.repository

import android.util.Log
import com.decagon.android.sq007.model.AllComments
import com.decagon.android.sq007.model.Comment
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.network.RetroInstance
import retrofit2.Response

class Repository {

    // this will contain all the post in the API
    suspend fun getPost(): ArrayList<Post> {
        return RetroInstance.api.getPost()
    }

    // this is for a specific post for which the post id is known
    suspend fun getSearchPost(number: Int): Response<Post> {
        return RetroInstance.api.getSearchPost(number)
    }

    // this is for getting a list of posts by a user using the userId
    suspend fun pushPost(post: Post): Response<Post> {
        Log.d("Retrofit", "$post")

        return RetroInstance.api.pushPost(post)
    }

    suspend fun pushComment(comment: Comment): Response<Comment> {
        return RetroInstance.api.pushComment(comment)
    }

    suspend fun getComments(postNumber: Int): AllComments {
        return RetroInstance.api.getComments(postNumber)
    }
}
