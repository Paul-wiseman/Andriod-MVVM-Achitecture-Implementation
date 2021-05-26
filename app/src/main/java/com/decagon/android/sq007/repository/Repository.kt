package com.decagon.android.sq007.repository

import com.decagon.android.sq007.network.RetroInstance
import com.decagon.android.sq007.repository.model.AllComments
import com.decagon.android.sq007.repository.model.AllPosts
import com.decagon.android.sq007.repository.model.Posts
import retrofit2.Response

class Repository {

    // this will contain all the post in the API
    suspend fun getPost(): AllPosts {
        return RetroInstance.api.getPost()
    }

    // this is for a specific post for which the post id is known
    suspend fun getSearchPost(number: Int): Response<Posts> {
        return RetroInstance.api.getSearchPost(number)
    }

    // this is for getting a list of posts by a user using the userId
    suspend fun pushPost(post: Posts): Response<Posts> {
        return RetroInstance.api.pushPost(post)
    }

    suspend fun getComments(postNumber: Int): AllComments {
        return RetroInstance.api.getComments(postNumber)
    }
}
