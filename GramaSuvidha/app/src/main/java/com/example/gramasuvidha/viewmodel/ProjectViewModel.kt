package com.example.gramasuvidha.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gramasuvidha.data.repository.ProjectRepository
import com.example.gramasuvidha.model.Project
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProjectRepository(application)
    val allProjects: LiveData<List<Project>> = repository.allProjects

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isKannada = MutableLiveData(false)
    val isKannada: LiveData<Boolean> = _isKannada

    init { loadProjects() }

    private fun loadProjects() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.loadAndCacheProjects()
            _isLoading.value = false
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.refreshProjects()
            _isLoading.value = false
        }
    }

    fun setLanguage(isKannada: Boolean) { _isKannada.value = isKannada }
    fun toggleLanguage() { _isKannada.value = _isKannada.value != true }
    fun getProjectById(id: String) = repository.getProjectById(id)
    fun getAverageRating(projectId: String) = repository.getAverageRating(projectId)

    fun submitFeedback(projectId: String, rating: Int, issue: String) {
        viewModelScope.launch {
            repository.submitFeedback(projectId, rating, issue)
        }
    }
}