package com.example.gramasuvidha.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feedback")
data class FeedbackEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val project_id: String,
    val rating: Int,
    val issue_description: String,
    val submitted_at: Long = System.currentTimeMillis(),
    val is_synced: Boolean = false
)