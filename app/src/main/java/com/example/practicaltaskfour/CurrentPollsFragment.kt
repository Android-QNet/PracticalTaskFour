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
import com.example.practicaltaskfour.databinding.FragmentCurrentPollsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentPollsFragment : BaseFragment() {
    private lateinit var binding: FragmentCurrentPollsBinding
    private lateinit var dataAdapter: AdapterPollsParent
    private lateinit var database: PollOptionDatabase
    private var optionList = ArrayList<PollOption>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentPollsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = PollOptionDatabase.getDataBase(requireContext())

        database.pollOptionDao().getPollOptions().observe(viewLifecycleOwner, Observer {
            optionList.addAll(it)
            if(optionList.size == 0){
                binding.recyclerViewCurrentPoll.visibility = View.GONE
                binding.textViewPlaceHolder.visibility = View.VISIBLE
            }else{

                var finalOptionList = optionList.filter{pollOption -> !pollOption.isSelected }.reversed()

                if(finalOptionList.isEmpty()) {
                    binding.recyclerViewCurrentPoll.visibility = View.GONE
                    binding.textViewPlaceHolder.visibility = View.VISIBLE
                }else {

                    binding.recyclerViewCurrentPoll.visibility = View.VISIBLE
                    binding.textViewPlaceHolder.visibility = View.GONE
                    dataAdapter = AdapterPollsParent(finalOptionList) { data, poll, pos ->
                        data.optionList.forEachIndexed { index, poll ->
                            poll.pollSelect = index == pos
                        }
                        GlobalScope.launch(Dispatchers.IO) {
                            database.pollOptionDao().update(
                                pos, true, data.optionList, data.title
                            )
                        }
                    }

                    binding.recyclerViewCurrentPoll.apply {
                        adapter = dataAdapter
                    }
                }
            }


        })



    }
}