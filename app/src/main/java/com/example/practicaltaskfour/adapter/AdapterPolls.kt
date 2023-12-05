package com.example.practicaltaskfour.adapter

import android.R
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltaskfour.databinding.RowPollBinding
import com.example.practicaltaskfour.model.Poll


class AdapterPolls(
    private var list: List<Poll> = ArrayList(),
    private var isSelected: Boolean =  false,
    private val onItemSelected: (data: Poll,position: Int) -> Unit) :
    RecyclerView.Adapter<AdapterPolls.MyViewHolder>() {

    lateinit var context: Context

    class MyViewHolder(val binding: RowPollBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        return MyViewHolder(
            RowPollBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder.binding) {
            var data = list[position]

            textViewPoll.text = data.poll.toString()

            if(isSelected){
                radiobutton.visibility = View.INVISIBLE
                constraintSelected.visibility = View.VISIBLE
                if (data.pollSelect) {
                    textViewPercentage.visibility = View.VISIBLE
                    textViewPercentage.text = "100%"
                    imageViewChecked.visibility = View.VISIBLE
                    val progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
                    progressAnimator.duration = 500
                    progressAnimator.interpolator = LinearInterpolator()
                    progressAnimator.start()
                } else {
                    textViewPercentage.visibility = View.VISIBLE
                    textViewPercentage.text = "0%"
                    imageViewChecked.visibility = View.GONE
                }
            }else {
                if (!data.pollSelect) {
                    radiobutton.visibility = View.VISIBLE
                    constraintSelected.visibility = View.INVISIBLE
                } else {
                    radiobutton.visibility = View.INVISIBLE
                    constraintSelected.visibility = View.VISIBLE
                }
            }

            root.setOnClickListener {
                if(isSelected){

                }else {
                    data.pollSelect = true
                    onItemSelected.invoke(data, holder.absoluteAdapterPosition)
                    val progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
                    progressAnimator.duration = 500
                    progressAnimator.interpolator = LinearInterpolator()
                    progressAnimator.start()
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}