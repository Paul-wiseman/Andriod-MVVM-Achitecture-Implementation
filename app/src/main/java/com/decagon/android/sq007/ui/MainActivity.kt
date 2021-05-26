package com.decagon.android.sq007.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.ActivityMainBinding
import com.decagon.android.sq007.repository.Repository
import com.decagon.android.sq007.ui.adapter.PostRecyclerViewAdapter
import com.decagon.android.sq007.viewmodel.MainActivityViewModeFactory
import com.decagon.android.sq007.viewmodel.MainActivityViewModel
import java.util.*

class MainActivity : AppCompatActivity(), ClickListener, SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerviewAdapter: PostRecyclerViewAdapter
    lateinit var recyclerview: RecyclerView
    private lateinit var viewModel: MainActivityViewModel
    lateinit var etSearchText: EditText
    lateinit var btnSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var addCommentFab = binding.fabAddComment
        addCommentFab.setOnClickListener { // opening a new activity on below line.
            val fragment = AddCommentDialogFragment()
            fragment.show(supportFragmentManager, "addcommentDialogfragment")
        }

        // initialize recyclerview
        recyclerview = binding.recyclerview
        recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)

        // setting a decoration between each recycler view item
        val decoration = DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.addItemDecoration(decoration)

        val repository = Repository()
        val viewModelFactory = MainActivityViewModeFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(
            this,
            Observer { response ->
                if (response != null) {
                    recyclerviewAdapter = PostRecyclerViewAdapter(response, this)
                    recyclerviewAdapter.notifyDataSetChanged()
                    recyclerview.adapter = recyclerviewAdapter
                    Log.d("Response", response[0].userID.toString())
                    Log.d("Response", response[0].id.toString())
                    Log.d("Response", response[0].title)
                    Log.d("Response", response[0].body)
                } else {
                    Log.d("MainActiviy", "Empty Response")
                }
            }
        )

//        btnSearch.setOnClickListener {
//            var number = etSearchText.text.toString()
//
// //            viewModel.getsearchPost(Integer.parseInt(number))
//            viewModel.getCustomPosts(Integer.parseInt(number))
//            viewModel.customSearchResponse.observe(
//                this,
//                Observer { response ->
//                    if (response.isSuccessful) {
//
// //                        recyclerviewAdapter.setPost(response)
//                    }
//                }
//            )
//        }
    }

//    private fun iniRecyclerView() {
//        recyclerview.apply {
//            layoutManager = LinearLayoutManager(this@MainActivity)
//            adapter = recyclerviewAdapter
//
//            // the code below creates a division between each recycler view
//            val decoration = DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
//            addItemDecoration(decoration)
//        }
//    }

    override fun onItemClicked(postNumber: Int) {
        var bundle = Bundle()
        bundle.putInt("position", postNumber)
        var commentsFragment = CommentsFragment()
        commentsFragment.arguments = bundle
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_fragment, commentsFragment).addToBackStack(null).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // in this on create options menu we are calling
        // a menu inflater and inflating our menu file.
        var inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        // on below line we are getting our menu item as search view item
        var searchViewItem = menu.findItem(R.id.app_bar_search)
        // on below line we are creating a variable for our search view.
        var searchView = searchViewItem?.actionView as? SearchView
        // on below line we are setting on query text listener for our search view.

        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    // Override onQueryTextSubmit method
    // which is call
    // when submitquery is searched

    override fun onQueryTextSubmit(query: String?): Boolean {
        // on query submit we are clearing the focus for our search view.
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // on changing the text in our search view we are calling
        // a filter method to filter our array list.
        if (newText != null) {
            SearchLiveData(newText.toLowerCase(Locale.ROOT))
        }
        return false
    }
    fun SearchLiveData(text: String) {
        // in this method we are filtering our array list.
        // on below line we are creating a new filtered array list.
        viewModel.getsearchPost(text)
        viewModel.searchData.observe(
            this,
            Observer {
                if (it.isEmpty()) {
                    Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT).show()
                } else {
                    recyclerviewAdapter.filterList(it)
                    recyclerviewAdapter.notifyDataSetChanged()
                }
            }
        )
    }
}
