package com.pipi.pipix.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

@Database(entities = [TestData::class], version = 1, exportSchema = false)
abstract class TDDatabase: RoomDatabase() {

    abstract fun testDataDao(): TestDataDao

    companion object{
        private var INSTANCE: TDDatabase? = null

        fun getDatabase(context: Context): TDDatabase{

            val tempInstance= INSTANCE
            if( tempInstance != null) {
                return tempInstance
            }
            // safe multithreading
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TDDatabase::class.java,
                    "td_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}