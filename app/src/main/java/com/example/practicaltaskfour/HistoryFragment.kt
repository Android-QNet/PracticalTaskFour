package com.example.practicaltaskfour

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.practicaltaskfour.adapter.AdapterPollsParent
import com.example.practicaltaskfour.data.PollOption
import com.example.practicaltaskfour.data.PollOptionDatabase
import com.example.practicaltaskfour.databinding.FragmentHistoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryFragment: BaseFragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var dataAdapter: AdapterPollsParent
    private lateinit var database: PollOptionDatabase
    private var optionList = ArrayList<PollOption>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = PollOptionDatabase.getDataBase(requireContext())

        database.pollOptionDao().getPollOptions().observe(viewLifecycleOwner, Observer {
            optionList.addAll(it)
            if(optionList.size == 0){
                binding.recyclerViewHistory.visibility = View.GONE
                binding.textViewPlaceHolder.visibility = View.VISIBLE
            }else{

                var finalOptionList = optionList.filter{pollOption -> pollOption.isSelected }
                Log.d("DATA",finalOptionList.toString())
                if(finalOptionList.isEmpty()) {
                    binding.recyclerViewHistory.visibility = View.GONE
                    binding.textViewPlaceHolder.visibility = View.VISIBLE
                }else {
                    binding.recyclerViewHistory.visibility = View.VISIBLE
                    binding.textViewPlaceHolder.visibility = View.GONE
                    dataAdapter = AdapterPollsParent(finalOptionList) { data, poll, pos ->
                        /* GlobalScope.launch(Dispatchers.IO) {
                        database.pollOptionDao().update(
                            pos,true,data.title
                        )
                    }*/
                    }

                    binding.recyclerViewHistory.apply {
                        adapter = dataAdapter
                    }
                }
            }



        })


    }
}