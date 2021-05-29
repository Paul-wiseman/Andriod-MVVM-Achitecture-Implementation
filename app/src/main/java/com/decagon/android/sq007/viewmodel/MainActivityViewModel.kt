package com.decagon.android.sq007.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.collections.ArrayList

class MainActivityViewModel(private val repository: Repository) : ViewModel() {

    // the response for Allposts network call is stored in this Live Data
    private val _myResponse: MutableLiveData<ArrayList<Post>> = MutableLiveData()
    private var _myResponseQueryList: ArrayList<Post>? = null
    val myResponse: LiveData<ArrayList<Post>>
        get() = _myResponse

    // this function function triggers the network call for Allposts
    fun getPost() {
        viewModelScope.launch {
            try {
                val response = repository.getPost()
                _myResponse.postValue(response)
                _myResponseQueryList = response
            } catch (e: Exception) {
                Log.d("getPost", "$e")
            }
        }
    }

    // this function triggers the network call for specific post provided by the user

    fun getSearchPost(queryText: String): ArrayList<Post> {
        Log.d("viewModel", "SearchLiveData:$queryText ")

        var newReponseList = _myResponseQueryList
        try {
            var filteredList = newReponseList?.filter { it.title.startsWith(queryText) || it.title.contains(queryText) || it.id.toString().startsWith(queryText) }
            _myResponse.value = filteredList as ArrayList<Post>
        } catch (e: Exception) {
            Log.d("getSearchPost", "$e")
        }
        return _myResponse.value as ArrayList<Post>
    }
//        Log.d("searchData", "${searchData.value}")

    fun pushPost(post: Post) {
        viewModelScope.launch {
            try {
                val response = repository.pushPost(post)
                _myResponse.value?.add(response.body()!!)
            } catch (e: Exception) {
                _myResponse.value = null
            }
        }
    }
}
