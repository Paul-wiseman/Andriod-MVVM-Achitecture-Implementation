package com.decagon.android.sq007.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.decagon.android.sq007.databinding.FragmentAddCommentDialogBinding
import com.decagon.android.sq007.repository.Repository
import com.decagon.android.sq007.repository.model.Posts
import com.decagon.android.sq007.viewmodel.MainActivityViewModeFactory
import com.decagon.android.sq007.viewmodel.MainActivityViewModel

class AddCommentDialogFragment : DialogFragment() {
    private var _binding: FragmentAddCommentDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddCommentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button_add = binding.buttonAdd

        button_add.setOnClickListener {
            val userId = binding.editTextFullUserID.text.toString().trim().toInt()
            val id = binding.editTextId.text.toString().trim().toInt()
            val commentTitle = binding.editTextTitle.text.toString()
            val comment = binding.editTextComment.text.toString()
            Log.d("buttonUserid", "$userId")
            Log.d("buttonid", "$id")
            Log.d("commentTitle", commentTitle)
            Log.d("comment", comment)

            var post = Posts(userId, id, commentTitle, comment)
            val repository = Repository()
            val viewModelFactory = MainActivityViewModeFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
            viewModel.pushPost(post)
            dismiss()
        }
    }
}
