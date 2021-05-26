package com.decagon.android.sq007.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.repository.model.AllPosts
import com.decagon.android.sq007.repository.model.Posts
import com.decagon.android.sq007.ui.ClickListener

class PostRecyclerViewAdapter(var item: AllPosts, private val listener: ClickListener) : RecyclerView.Adapter<PostRecyclerViewAdapter.PostViewHolder>() {

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var postID: TextView = view.findViewById(R.id.tv_id)
        private var commentTitle: TextView = view.findViewById(R.id.tv_title)
        private var comment: TextView = view.findViewById(R.id.tv_body)
        var layout: ConstraintLayout = view.findViewById(R.id.cl_post_item)
        fun bind(post: Posts) {
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
    fun filterList(filterlist: ArrayList<Posts>) {
        // on below line we are passing filtered
        // array list in our original array list
        item = filterlist as AllPosts
        notifyDataSetChanged()
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
}
