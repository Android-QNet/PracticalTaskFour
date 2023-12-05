package com.example.practicaltaskfour.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltaskfour.data.PollOption
import com.example.practicaltaskfour.databinding.RowPollParentBinding
import com.example.practicaltaskfour.model.Poll

class AdapterPollsParent(
    private var list: List<PollOption> = ArrayList(),
    private val onItemSelected: (data: PollOption,poll: Poll,pos: Int) -> Unit) :
    RecyclerView.Adapter<AdapterPollsParent.MyParentViewHolder>() {


    lateinit var context: Context


    class MyParentViewHolder(val binding: RowPollParentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PollOption, position: Int, callbacks: (data: PollOption,poll: Poll,pos: Int) -> Unit) {

            var dataAdapter = AdapterPolls(data.optionList,data.isSelected) { poll,pos ->
                callbacks.invoke(data,poll,pos)
            }

            binding.recyclerView.apply {
                    adapter = dataAdapter
                }
            }





        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyParentViewHolder {
        context = parent.context
        return MyParentViewHolder(
            RowPollParentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyParentViewHolder, position: Int) {
        with(holder.binding) {
            var data = list[position]
            holder.bind(data,position, onItemSelected)

            textViewPollTitle.text = data.title


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}