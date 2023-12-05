package com.example.practicaltaskfour.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [PollOption::class], version = 1)
@TypeConverters(Converters::class)
 abstract class PollOptionDatabase: RoomDatabase() {

    abstract fun pollOptionDao(): PollOptionDao

    companion object {
        @Volatile
        private var INSTANCE: PollOptionDatabase? = null
        fun getDataBase(context: Context): PollOptionDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PollOptionDatabase::class.java,
                        "pollOptionDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}