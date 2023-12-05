package com.example.practicaltaskfour

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.practicaltaskfour.adapter.AdapterOption
import com.example.practicaltaskfour.databinding.ActivityCreatePollBinding
import com.example.practicaltaskfour.data.PollOption
import com.example.practicaltaskfour.data.PollOptionDatabase
import com.example.practicaltaskfour.model.MyOption
import com.example.practicaltaskfour.model.Poll
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreatePollActivity : AppCompatActivity() {

    private lateinit var binding:  ActivityCreatePollBinding

    private lateinit var mAdapter: AdapterOption

    private lateinit var database: PollOptionDatabase
    private var dataSet =  ArrayList<Poll>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = PollOptionDatabase.getDataBase(this)

        binding.back.setOnClickListener{
            onBackPressed()
        }

        binding.buttonAddOption.setOnClickListener {
           optionAdd()
        }

        binding.buttonCreate.setOnClickListener {
            if(binding.editTextQue.text?.isEmpty() == true || dataSet.size < 2){
                Toast.makeText(this,"Please enter a valid question and at least two option.",Toast.LENGTH_SHORT).show()
            }else {
                var isBlank = false
                dataSet.forEach{
                    if(it.poll.isEmpty()){
                       isBlank = true
                        return@forEach
                    }
                }
                if(isBlank){
                    Toast.makeText(this,"Please enter a valid option.",Toast.LENGTH_SHORT).show()
                }else{
                    GlobalScope.launch {
                        database.pollOptionDao().insertPollOption(
                            PollOption(
                                binding.editTextQue.text.toString(), dataSet
                            )
                        )
                    }
                    onBackPressed()
                }
            }
        }

        mAdapter = AdapterOption(dataSet,{
            optionAdd()
        },{data, position ->// delete
            dataSet.removeAt(position)
            mAdapter.notifyItemRemoved(position)
            mAdapter.notifyItemRangeChanged(position, dataSet.size)
            optionCount()
            Log.i("LOG",position.toString())
        },{data,position -> // update text
            dataSet[position].poll = data
        })
        binding.recyclerView.adapter = mAdapter


        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }

    private fun optionCount(){
        if(dataSet.size == 5){
            binding.textViewOptionCount.visibility = View.GONE
        }else{
            binding.textViewOptionCount.visibility = View.VISIBLE
        }
        binding.textViewOptionCount.text = "You can add ${5-dataSet.size} more options."
    }

    private fun optionAdd(){
        if(dataSet.size < 5){
            dataSet.add(Poll("",false))
            mAdapter.notifyItemInserted(dataSet.size)
        }
        optionCount()
    }


    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
            0
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                mAdapter.moveItem(fromPosition, toPosition)
                mAdapter.notifyItemMoved(fromPosition, toPosition)
                Log.i("LOG","move called")

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                mAdapter.notifyDataSetChanged()
            }
        }

        ItemTouchHelper(simpleItemTouchCallback)
    }

}

