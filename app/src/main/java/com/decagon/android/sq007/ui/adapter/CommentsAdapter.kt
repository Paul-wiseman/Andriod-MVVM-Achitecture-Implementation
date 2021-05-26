package com.decagon.android.sq007.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.repository.model.AllComments
import com.decagon.android.sq007.repository.model.Comments

class CommentsAdapter(var comments: AllComments) : RecyclerView.Adapter<CommentsAdapter.CommentViewholder>() {
    inner class CommentViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_postId: TextView = view.findViewById(R.id.tv_postId)
        var tv_commentId: TextView = view.findViewById(R.id.tv_commentId)
        var tv_commentName: TextView = view.findViewById(R.id.tv_commentName)
        var tv_commentEmail: TextView = view.findViewById(R.id.tv_commentEmail)
        var tv_commentBody: TextView = view.findViewById(R.id.tv_commentBody)
        fun bind(comment: Comments) {
            tv_postId.text = comment.postId.toString()
            tv_commentId.text = comment.id.toString()
            tv_commentName.text = comment.name
            tv_commentEmail.text = comment.email
            tv_commentBody.text = comment.body
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewholder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentViewholder(inflater)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewholder, position: Int) {
        holder.bind(comments[position])
    }
}
