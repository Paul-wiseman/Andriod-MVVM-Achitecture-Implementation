package com.decagon.android.sq007.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.ui.ClickListener

class PostRecyclerViewAdapter(private val listener: ClickListener) : RecyclerView.Adapter<PostRecyclerViewAdapter.PostViewHolder>() {

    private var item = arrayListOf<Post>()

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var postID: TextView = view.findViewById(R.id.tv_id)
        private var commentTitle: TextView = view.findViewById(R.id.tv_title)
        private var comment: TextView = view.findViewById(R.id.tv_body)
        var layout: ConstraintLayout = view.findViewById(R.id.cl_post_item)
        fun bind(post: Post) {
            postID.text = post.id.toString()
            commentTitle.text = post.title
            comment.text = post.body
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(item[position])
        holder.layout.setOnClickListener {
            val postNumber = item[position].userID
            listener.onItemClicked(postNumber)
        }
    }

    fun updateSearch(searchResult: ArrayList<Post>) {
        this.item = searchResult
    }

    fun addPost(allPosts: List<Post>) {
        this.item = allPosts as ArrayList<Post>
        notifyDataSetChanged()
    }
}
