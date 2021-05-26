package com.decagon.android.sq007.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.android.sq007.repository.Repository
import com.decagon.android.sq007.repository.model.AllComments
import com.decagon.android.sq007.repository.model.Posts
import kotlinx.coroutines.launch

class CommentsFragmentViewModel(var repository: Repository) : ViewModel() {

    // the response for Allposts network call is stored in this Live Data
    private val _commentLiveData: MutableLiveData<AllComments> = MutableLiveData()
    val commentLiveData: LiveData<AllComments>
        get() = _commentLiveData

    private val _postLiveData: MutableLiveData<Posts> = MutableLiveData()
    val postLiveData: LiveData<Posts>
        get() = _postLiveData

    // this function triggers the network call for specific post provided by the user
    fun getComments(postNumber: Int) {
        viewModelScope.launch {
            val response = repository.getComments(postNumber)
            _commentLiveData.postValue(response)
            Log.d("Viewmodel", "getPost: $response")
        }
    }


}
