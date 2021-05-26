package com.decagon.android.sq007.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.android.sq007.repository.Repository
import com.decagon.android.sq007.repository.model.AllPosts
import com.decagon.android.sq007.repository.model.Posts
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainActivityViewModel(private val repository: Repository) : ViewModel() {

    // the response for Allposts network call is stored in this Live Data
    private val _myResponse: MutableLiveData<AllPosts> = MutableLiveData()
    val myResponse: LiveData<AllPosts>
        get() = _myResponse
    val searchData: MutableLiveData<ArrayList<Posts>> = MutableLiveData()

    // this function function triggers the network call for Allposts
    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            _myResponse.postValue(response)
            Log.d("Viewmodel", "getPost: $response")
        }
    }

    // this function triggers the network call for specific post provided by the user
    fun getsearchPost(queryText: String) {
        for (item in _myResponse.value!!) {
            if (item.title.contains(queryText.toLowerCase(Locale.ROOT)) || item.id == queryText.toInt() || item.body.contains(
                    queryText.toLowerCase(Locale.ROOT)
                )
            ) {
                searchData.value?.add(item)
            }
        }
    }
    fun pushPost(posts: Posts) {
        viewModelScope.launch {
            val response = repository.pushPost(posts)
            response.body()?.let { _myResponse.value?.add(it) }
        }
    }
}
