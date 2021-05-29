package com.decagon.android.sq007.ui // ktlint-disable filename

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.decagon.android.sq007.databinding.FragmentAddCommentDialogBinding
import com.decagon.android.sq007.model.Comment
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.repository.Repository
import com.decagon.android.sq007.viewmodel.CommentsFragmentViewModeFactory
import com.decagon.android.sq007.viewmodel.CommentsFragmentViewModel

class AddCommentDialogFragment(private val listener: UploadDialogListener) : DialogFragment() {
    private var _binding: FragmentAddCommentDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NO_TITLE,
            android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
// Inflate the layout for this fragment
        _binding = FragmentAddCommentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository()
        val viewModelFactory = CommentsFragmentViewModeFactory(repository)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(CommentsFragmentViewModel::class.java)

        val btnadd = binding.buttonAdd
        btnadd.setOnClickListener {
            val postId = binding.editTextPostId.text.toString().trim().toInt()
            val id = binding.editTextId.text.toString().trim().toInt()
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val body = binding.editTextCommentBody.text.toString()
            val comment = Comment(postId, id, name, email, body)
            dismiss()
        }
    }

    interface UploadDialogListener {
        fun sendComment(comment: Comment)
    }
}
