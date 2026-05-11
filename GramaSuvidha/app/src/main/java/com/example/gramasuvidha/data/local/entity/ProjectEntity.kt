package com.example.gramasuvidha.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.gramasuvidha.model.ProjectUpdate

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromUpdateList(list: List<ProjectUpdate>?): String =
        gson.toJson(list ?: emptyList<ProjectUpdate>())

    @TypeConverter
    fun toUpdateList(json: String?): List<ProjectUpdate> {
        if (json.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<ProjectUpdate>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}

@Entity(tableName = "projects")
@TypeConverters(Converters::class)
data class ProjectEntity(
    @PrimaryKey val id: String,
    val title_en: String,
    val title_kn: String,
    val description_en: String,
    val description_kn: String,
    val category: String,
    val status: String,
    val progress_percent: Int,
    val budget_inr: Long,
    val amount_spent_inr: Long,
    val contractor: String,
    val start_date: String,
    val expected_end_date: String,
    val ward_number: Int,
    val image_before_url: String,
    val image_after_url: String,
    val updates: List<ProjectUpdate> = emptyList(),
    val cached_at: Long = System.currentTimeMillis()
)