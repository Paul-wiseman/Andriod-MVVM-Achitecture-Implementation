package com.decagon.android.sq007.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.android.sq007.model.AllComments
import com.decagon.android.sq007.model.Comment
import com.decagon.android.sq007.repository.Repository
import kotlinx.coroutines.launch

class CommentsFragmentViewModel(var repository: Repository) : ViewModel() {

    // the response for Allposts network call is stored in this Live Data
    private val _commentLiveData: MutableLiveData<AllComments> = MutableLiveData()
    val commentLiveData: LiveData<AllComments>
        get() = _commentLiveData

    // this function triggers the network call for specific post provided by the user
    fun getComments(postNumber: Int) {
        viewModelScope.launch {
            val response = repository.getComments(postNumber)
            _commentLiveData.postValue(response)
            Log.d("Viewmodel", "getPost: $response")
        }
    }

    fun pushComment(comment: Comment) {
        viewModelScope.launch {
            val response = repository.pushComment(comment)
            _commentLiveData.value?.add(comment)
            Log.d("Viewmodel", "getPost: $response")
        }
    }
}
