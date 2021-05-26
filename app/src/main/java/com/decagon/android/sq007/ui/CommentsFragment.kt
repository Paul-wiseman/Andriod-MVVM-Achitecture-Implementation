package com.decagon.android.sq007.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.decagon.android.sq007.databinding.FragmentCommentsBinding
import com.decagon.android.sq007.repository.Repository
import com.decagon.android.sq007.ui.adapter.CommentsAdapter
import com.decagon.android.sq007.viewmodel.CommentsFragmentViewModeFactory
import com.decagon.android.sq007.viewmodel.CommentsFragmentViewModel

class CommentsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!
    lateinit var commentsAdapter: CommentsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        recyclerView = binding.rvCommentFragment
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val decoration = DividerItemDecoration(requireContext(), StaggeredGridLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(decoration)

        // Getting the value of the position of the post sent from the MainActivity
        val position = this.arguments?.getInt("position")

        val repository = Repository()
        val viewModelFactory = CommentsFragmentViewModeFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(CommentsFragmentViewModel::class.java)
        if (position != null) {
            viewModel.getComments(position)
        }
        viewModel.commentLiveData.observe(
            viewLifecycleOwner,
            Observer { response ->
                if (response != null) {
                    commentsAdapter = CommentsAdapter(response)
                    commentsAdapter.notifyDataSetChanged()
                    recyclerView.adapter = commentsAdapter
                } else {
                    Log.d("CommentsFragment", "Empty Response: $response")
                }
            }
        )
    }
}
