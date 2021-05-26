package com.decagon.android.sq007.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        private fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api: RetroService by lazy {
            getRetroInstance().create(RetroService::class.java)
        }
    }
}
