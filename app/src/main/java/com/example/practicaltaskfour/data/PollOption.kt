package com.example.practicaltaskfour.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.practicaltaskfour.model.Poll

@Entity(tableName = "pollOption")
data class PollOption(

    val title: String,

    val optionList: List<Poll>,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val optionSelectedIndex: Int = 0,
    val isSelected: Boolean = false


)