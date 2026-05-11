package com.example.gramasuvidha.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gramasuvidha.data.local.entity.ProjectEntity

@Dao
interface ProjectDao {

    @Query("SELECT * FROM projects ORDER BY status ASC, progress_percent DESC")
    fun getAllProjects(): LiveData<List<ProjectEntity>>

    @Query("SELECT * FROM projects WHERE id = :projectId")
    fun getProjectById(projectId: String): LiveData<ProjectEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(projects: List<ProjectEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: ProjectEntity)

    @Query("DELETE FROM projects")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM projects")
    suspend fun getCount(): Int
}