package com.example.practicaltaskfour.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltaskfour.databinding.RowOptionBinding
import com.example.practicaltaskfour.data.PollOption
import com.example.practicaltaskfour.model.MyOption
import com.example.practicaltaskfour.model.Poll

class AdapterOption(
    private var list: ArrayList<Poll> = ArrayList(),
    private val onItemAdd: () -> Unit,
    private val onItemDelete: (data: Poll, position:Int) -> Unit,
    private val onItemTextUpdate: (data: String, position:Int) -> Unit) :
    RecyclerView.Adapter<AdapterOption.MyViewHolder>() {

    lateinit var context: Context

    class MyViewHolder(val binding: RowOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        return MyViewHolder(
            RowOptionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder.binding) {
            var data = list[position]

            if(list.size == 5){
                editTextOption.setImeOptions(EditorInfo.IME_ACTION_DONE)
                editTextOption.requestFocus()
            }else{
                editTextOption.setImeOptions(EditorInfo.IME_ACTION_NEXT)
                editTextOption.requestFocus()
            }

            editTextOption.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    onItemTextUpdate.invoke(str.toString(),holder.absoluteAdapterPosition)
                }

                override fun afterTextChanged(str: Editable?) {

                }

            })

            editTextOption.setOnEditorActionListener(TextView.OnEditorActionListener{ _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    onItemAdd.invoke()
                    return@OnEditorActionListener true
                }
                false
            })


            imageViewDelete.setOnClickListener {
                onItemDelete.invoke(data,holder.absoluteAdapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val currentItem: Poll = list[fromPosition]
        list.removeAt(fromPosition)
        list.add(toPosition,currentItem)

    }
}