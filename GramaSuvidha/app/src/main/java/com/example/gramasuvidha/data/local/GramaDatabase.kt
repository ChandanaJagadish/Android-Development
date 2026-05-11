package com.example.gramasuvidha.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gramasuvidha.data.local.dao.FeedbackDao
import com.example.gramasuvidha.data.local.dao.ProjectDao
import com.example.gramasuvidha.data.local.entity.Converters
import com.example.gramasuvidha.data.local.entity.FeedbackEntity
import com.example.gramasuvidha.data.local.entity.ProjectEntity

@Database(
    entities = [ProjectEntity::class, FeedbackEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GramaDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao
    abstract fun feedbackDao(): FeedbackDao

    companion object {
        @Volatile
        private var INSTANCE: GramaDatabase? = null

        fun getInstance(context: Context): GramaDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GramaDatabase::class.java,
                    "grama_suvidha_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
    }
}