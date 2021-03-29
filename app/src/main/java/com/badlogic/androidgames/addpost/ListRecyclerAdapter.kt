package com.badlogic.androidgames.addpost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_context.view.*
import kotlinx.android.synthetic.main.recycler_row.view.*

class ListRecyclerAdapter(val postListesi : ArrayList<String>, val idListesi : ArrayList<Int>) : RecyclerView.Adapter<ListRecyclerAdapter.PostHolder>() {



    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
       val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row, parent, false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.itemView.tvTest.text = postListesi[position]
    }

    override fun getItemCount(): Int {
        return postListesi.size
    }
}