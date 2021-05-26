package com.decagon.android.sq007.network

import com.decagon.android.sq007.repository.model.AllComments
import com.decagon.android.sq007.repository.model.AllPosts
import com.decagon.android.sq007.repository.model.Posts
import retrofit2.Response
import retrofit2.http.*

interface RetroService {

    // network call for All posts
    @GET("posts/")
    suspend fun getPost(): AllPosts

    // network call for a particular post using the post Id
    @GET("posts/{postNumber}")
    suspend fun getSearchPost(@Path("postNumber") number: Int): Response<Posts>

    // network call for a user posts using the usersId
    @POST("posts")
    suspend fun pushPost(@Body post: Posts): Response<Posts>

    // network call for comments using the postId
    @GET("posts/{postNumber}/comments")
    suspend fun getComments(@Path("postNumber") postNumber: Int): AllComments
}
