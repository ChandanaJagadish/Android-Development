package com.example.gramasuvidha.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.gramasuvidha.data.local.GramaDatabase
import com.example.gramasuvidha.data.local.entity.FeedbackEntity
import com.example.gramasuvidha.data.local.entity.ProjectEntity
import com.example.gramasuvidha.model.Project
import com.example.gramasuvidha.model.ProjectsData
import com.example.gramasuvidha.model.ProjectUpdate
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository(private val context: Context) {

    private val db = GramaDatabase.getInstance(context)
    private val projectDao = db.projectDao()
    private val feedbackDao = db.feedbackDao()

    // Load all projects as LiveData (auto-updates UI)
    val allProjects: LiveData<List<Project>> = projectDao.getAllProjects().map { entities ->
        entities.map { it.toProject() }
    }

    fun getProjectById(id: String): LiveData<Project?> =
        projectDao.getProjectById(id).map { it?.toProject() }

    fun getFeedbackForProject(projectId: String) =
        feedbackDao.getFeedbackForProject(projectId)

    fun getAverageRating(projectId: String) =
        feedbackDao.getAverageRating(projectId)

    // Load from JSON and cache into Room
    suspend fun loadAndCacheProjects() = withContext(Dispatchers.IO) {
        val count = projectDao.getCount()
        if (count == 0) {
            val projects = loadProjectsFromJson()
            projectDao.insertAll(projects.map { it.toEntity() })
        }
    }

    suspend fun refreshProjects() = withContext(Dispatchers.IO) {
        val projects = loadProjectsFromJson()
        projectDao.insertAll(projects.map { it.toEntity() })
    }

    suspend fun submitFeedback(projectId: String, rating: Int, issue: String) =
        withContext(Dispatchers.IO) {
            feedbackDao.insert(
                FeedbackEntity(
                    project_id = projectId,
                    rating = rating,
                    issue_description = issue
                )
            )
        }

    private fun loadProjectsFromJson(): List<Project> {
        val json = context.resources.openRawResource(
            context.resources.getIdentifier("projects", "raw", context.packageName)
        ).bufferedReader().use { it.readText() }
        val data = Gson().fromJson(json, ProjectsData::class.java)
        return data.projects
    }

    // --- Mapping helpers ---
    private fun Project.toEntity() = ProjectEntity(
        id = id, title_en = title_en, title_kn = title_kn,
        description_en = description_en, description_kn = description_kn,
        category = category, status = status,
        progress_percent = progress_percent,
        budget_inr = budget_inr, amount_spent_inr = amount_spent_inr,
        contractor = contractor, start_date = start_date,
        expected_end_date = expected_end_date, ward_number = ward_number,
        image_before_url = image_before_url, image_after_url = image_after_url,
        updates = updates
    )

    private fun ProjectEntity.toProject() = Project(
        id = id, title_en = title_en, title_kn = title_kn,
        description_en = description_en, description_kn = description_kn,
        category = category, status = status,
        progress_percent = progress_percent,
        budget_inr = budget_inr, amount_spent_inr = amount_spent_inr,
        contractor = contractor, start_date = start_date,
        expected_end_date = expected_end_date, ward_number = ward_number,
        image_before_url = image_before_url, image_after_url = image_after_url,
        updates = updates
    )
}