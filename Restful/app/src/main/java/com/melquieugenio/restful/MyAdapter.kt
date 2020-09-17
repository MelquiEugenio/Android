package com.melquieugenio.restful

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*

class MyAdapter(private val dataSet: List<Repo>, private val context: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindContentOnItems(repo: Repo) {

            itemView.repo_title.text = repo.name

            if (repo.description != "null")
                itemView.repo_description.text = repo.description
            else
                itemView.repo_description.text = ""

            if (repo.language != "null")
                itemView.repo_language.text = repo.language
            else
                itemView.repo_language.text = ""
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyAdapter.MyViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row, parent, false)
        return MyViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bindContentOnItems(dataSet[position])

        // On repo click
        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Clicked.", Toast.LENGTH_SHORT).show()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}