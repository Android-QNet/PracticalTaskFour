package com.example.practicaltaskfour.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.practicaltaskfour.model.Poll

@Dao
interface PollOptionDao {

    @Insert
    suspend fun insertPollOption(option: PollOption)

    @Update
    suspend fun updatePollOption(option: PollOption)

    @Delete
    suspend fun deletePollOption(option: PollOption)

    @Query("SELECT * FROM pollOption")
    fun getPollOptions(): LiveData<List<PollOption>>

    @Query("UPDATE pollOption SET optionSelectedIndex=:selectedIndex,isSelected=:selected,optionList=:mlist WHERE title LIKE :mtitle")
    suspend fun update(selectedIndex: Int, selected: Boolean, mlist: List<Poll>, mtitle: String)


}