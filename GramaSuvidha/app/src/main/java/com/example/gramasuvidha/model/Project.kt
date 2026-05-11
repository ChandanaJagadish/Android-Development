package com.example.gramasuvidha.model

data class ProjectUpdate(
    val date: String,
    val note_en: String,
    val note_kn: String
)

data class Project(
    val id: String,
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
    val updates: List<ProjectUpdate> = emptyList()
) {
    fun getTitle(isKannada: Boolean) = if (isKannada) title_kn else title_en
    fun getDescription(isKannada: Boolean) = if (isKannada) description_kn else description_en
    fun budgetFormatted(): String = "₹${String.format("%,d", budget_inr)}"
    fun spentFormatted(): String = "₹${String.format("%,d", amount_spent_inr)}"
    fun spentPercent(): Int = if (budget_inr > 0) ((amount_spent_inr * 100) / budget_inr).toInt() else 0
}

data class ProjectsData(
    val version: String,
    val panchayat: String,
    val district: String,
    val state: String,
    val last_updated: String,
    val projects: List<Project>
)