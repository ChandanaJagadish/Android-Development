package com.example.gramasuvidha.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gramasuvidha.data.local.entity.FeedbackEntity

@Dao
interface FeedbackDao {

    @Query("SELECT * FROM feedback WHERE project_id = :projectId ORDER BY submitted_at DESC")
    fun getFeedbackForProject(projectId: String): LiveData<List<FeedbackEntity>>

    @Query("SELECT AVG(rating) FROM feedback WHERE project_id = :projectId")
    fun getAverageRating(projectId: String): LiveData<Float?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(feedback: FeedbackEntity)
}