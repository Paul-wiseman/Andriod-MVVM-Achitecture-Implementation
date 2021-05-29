package com.decagon.android.sq007.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.decagon.android.sq007.databinding.FragmentAddPostDialogBinding
import com.decagon.android.sq007.model.Post
import java.lang.ClassCastException

class AddPostDialogFragment(private val listener: UploadDialogListener) : DialogFragment() {

    private var _binding: FragmentAddPostDialogBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentAddPostDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button_add = binding.buttonAdd

        button_add.setOnClickListener {
            val id = binding.editTextId.text.toString().trim().toInt()
            val commentTitle = binding.editTextTitle.text.toString()
            val comment = binding.editTextComment.text.toString()
            Log.d("buttonid", "$id")
            Log.d("commentTitle", commentTitle)
            Log.d("comment", comment)

            val post = Post(1, id, commentTitle, comment)
            listener.sendPost(post)
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implemet UploadDialogListener")
        }
    }

    interface UploadDialogListener {
        fun sendPost(post: Post)
    }
}
