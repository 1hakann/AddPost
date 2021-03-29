package com.badlogic.androidgames.addpost

import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*
import java.lang.Exception


class ListFragment : Fragment() {

    val postIdListesi = ArrayList<Int>()
    val postBaslikListesi = ArrayList<String>()
    private lateinit var listAdapter: ListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = ListRecyclerAdapter(postBaslikListesi, postIdListesi)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = listAdapter

        sqlVeriAlma()
    }

    fun sqlVeriAlma() {
        try {

            activity?.let {
                val db = it.openOrCreateDatabase("posts", Context.MODE_PRIVATE, null)
                val cursor = db.rawQuery("SELECT * FROM posts", null)
                val postBaslikIndex = cursor.getColumnIndex("postBaslik")
                val postIdIndex = cursor.getColumnIndex("id")

                postIdListesi.clear()
                postBaslikListesi.clear()

                while (cursor.moveToNext()) {

                    postIdListesi.add(cursor.getInt(postIdIndex))
                    postBaslikListesi.add(cursor.getString(postBaslikIndex))

                }

                cursor.close()
            }

        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
}