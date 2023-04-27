package com.project.mvvmproject.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.mvvmproject.databinding.RowForQuotesRecyclerViewBinding
import com.project.mvvmproject.model.CatsFacts

class QuotesAdaptor(var context: Context) : RecyclerView.Adapter<QuotesAdaptor.viewHolder>() {
    lateinit var binding:RowForQuotesRecyclerViewBinding
    private var list:List<CatsFacts> = ArrayList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        binding=RowForQuotesRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.quotesText.text=list[position].text
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    fun setQuotesList(list:List<CatsFacts>){
        this.list=list
        notifyDataSetChanged()
    }

    class viewHolder(var binding: RowForQuotesRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

}