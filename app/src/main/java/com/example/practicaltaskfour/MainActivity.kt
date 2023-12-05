package com.example.practicaltaskfour

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.practicaltaskfour.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:  ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(CurrentPollsFragment())

        binding.menuCurrentPolls.setOnClickListener {
            bottomMenuOnClick(1)
        }

        binding.menuHistory.setOnClickListener {
            bottomMenuOnClick(2)
        }

        binding.imageViewCreatePoll.setOnClickListener {
            startActivity(Intent(this@MainActivity,CreatePollActivity::class.java))
        }




    }


    private fun replaceFragment(fragment: Fragment) {
        val bundle = Bundle()
//        bundle.putString(AppConstant.GAME, game)
        fragment.arguments = bundle
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }



    private fun bottomMenuOnClick(pos: Int) {
        binding.menuCurrentPolls.isTabSelected(false)
        binding.menuHistory.isTabSelected(false)

        when(pos){
            1 -> {
                binding.menuCurrentPolls.isTabSelected(true)
                replaceFragment(CurrentPollsFragment())
            }
            2 -> {
                binding.menuHistory.isTabSelected(true)
                replaceFragment(HistoryFragment())
            }
        }

    }
}

