package com.decagon.android.sq007.ui

import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.decagon.android.sq007.R
import com.decagon.android.sq007.adapter.PostRecyclerViewAdapter
import com.decagon.android.sq007.databinding.ActivityMainBinding
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.network.NetworkStatusChecker
import com.decagon.android.sq007.repository.Repository
import com.decagon.android.sq007.viewmodel.MainActivityViewModeFactory
import com.decagon.android.sq007.viewmodel.MainActivityViewModel

@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity(), ClickListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener, AddPostDialogFragment.UploadDialogListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerviewAdapter: PostRecyclerViewAdapter
    lateinit var recyclerview: RecyclerView

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(this, getSystemService(ConnectivityManager::class.java))
    }

    private val repository = Repository()
    private val viewModelFactory = MainActivityViewModeFactory(repository)
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var addCommentFab = binding.fabAddComment
        addCommentFab.setOnClickListener { // opening a new activity on below line.
            val fragment = AddPostDialogFragment(this)
            fragment.show(supportFragmentManager, "addPostDialogfragment")
        }

        // initialize recyclerview
        recyclerview = binding.recyclerview
        recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)

        // setting a decoration between each recycler view item
        val decoration = DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.addItemDecoration(decoration)
        recyclerviewAdapter = PostRecyclerViewAdapter(this)
        recyclerview.adapter = recyclerviewAdapter
        networkStatusChecker.performIfConnectedToInternet {
            viewModel.getPost()
        }

        viewModel.myResponse.observe(
            this,
            Observer { response ->
                if (response != null) {
                    recyclerviewAdapter.addPost(response)
                } else {
                    Toast.makeText(this, "No result to display", Toast.LENGTH_SHORT).show()
                    Log.d("MainActiviy", "Empty Response")
                }
            }
        )
    }

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

        // searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        searchView?.setOnCloseListener(this)
        searchView?.onActionViewCollapsed()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // on query submit we are clearing the focus for our search view.
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // on changing the text in our search view we are calling
        // a filter method to filter our array list.
        if (newText != null && newText.isNotEmpty()) {
            SearchLiveData(newText.toLowerCase())
        }
        return true
    }

    private fun SearchLiveData(text: String) {
        // in this method we are filtering our array list.
        // on below line we are creating a new filtered array list.
        Log.d("SearchLiveDataText", "SearchLiveData:$text ")
        Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT).show()
        var searchResult = viewModel.getSearchPost(text)
//        Log.d(TAG, "SearchLiveData: ")
        Log.d("SearchResult", "$searchResult")
        recyclerviewAdapter.updateSearch(searchResult)
        recyclerviewAdapter.notifyDataSetChanged()
    }

    override fun onClose(): Boolean {
        viewModel.getPost()
        return true
    }

    override fun sendPost(post: Post) {
        viewModel.pushPost(post)
        recyclerviewAdapter.notifyDataSetChanged()
    }
}
