package com.example.gramasuvidha.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gramasuvidha.R
import com.example.gramasuvidha.databinding.ItemProjectBinding
import com.example.gramasuvidha.model.Project

class ProjectAdapter(
    private var isKannada: Boolean,
    private val onClick: (Project) -> Unit
) : ListAdapter<Project, ProjectAdapter.ProjectViewHolder>(DiffCallback()) {

    fun updateLanguage(kannada: Boolean) {
        isKannada = kannada
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = ItemProjectBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(getItem(position), isKannada, onClick)
    }

    class ProjectViewHolder(private val binding: ItemProjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(project: Project, isKannada: Boolean, onClick: (Project) -> Unit) {
            binding.tvProjectTitle.text = project.getTitle(isKannada)
            binding.tvCategory.text = project.category
            binding.tvWard.text = if (project.ward_number == 0)
                "All Wards" else "Ward ${project.ward_number}"
            binding.progressBar.progress = project.progress_percent
            binding.tvProgress.text = "${project.progress_percent}%"
            binding.tvBudget.text = project.budgetFormatted()

            val (statusText, colorRes) = when (project.status) {
                "COMPLETED" -> Pair(
                    if (isKannada) "ಪೂರ್ಣಗೊಂಡಿದೆ" else "Completed",
                    R.color.status_completed
                )
                "IN_PROGRESS" -> Pair(
                    if (isKannada) "ಪ್ರಗತಿಯಲ್ಲಿದೆ" else "In Progress",
                    R.color.status_in_progress
                )
                "NOT_STARTED" -> Pair(
                    if (isKannada) "ಆರಂಭವಾಗಿಲ್ಲ" else "Not Started",
                    R.color.status_not_started
                )
                else -> Pair("On Hold", R.color.status_on_hold)
            }
            binding.tvStatus.text = statusText
            binding.tvStatus.setBackgroundColor(
                ContextCompat.getColor(binding.root.context, colorRes)
            )
            binding.root.setOnClickListener { onClick(project) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(a: Project, b: Project) = a.id == b.id
        override fun areContentsTheSame(a: Project, b: Project) = a == b
    }
}