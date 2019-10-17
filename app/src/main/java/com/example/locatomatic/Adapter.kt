package com.example.locatomatic

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.recyclerview_list.view.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent



class Adapter(var context: Context, var dataList: ArrayList<String>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return dataList.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recyclerview_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataList.get(position)
        holder.textView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val gmmIntentUri = Uri.parse("geo:0,0?q="+holder.textView.text)
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                context.startActivity(mapIntent)
            }

        })
    }


    class ViewHolder(item: View?) : RecyclerView.ViewHolder(item!!) {
        val textView = item!!.places
    }
}