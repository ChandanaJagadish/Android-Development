package com.example.gramasuvidha.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.gramasuvidha.R
import com.example.gramasuvidha.databinding.ActivityProjectDetailBinding
import com.example.gramasuvidha.model.Project
import com.example.gramasuvidha.ui.feedback.FeedbackBottomSheet
import com.example.gramasuvidha.util.PrefsManager
import com.example.gramasuvidha.viewmodel.ProjectViewModel

class ProjectDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectDetailBinding
    private val viewModel: ProjectViewModel by viewModels()
    private var projectId: String = ""
    private var isKannada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        isKannada = PrefsManager.isKannada(this)
        projectId = intent.getStringExtra("PROJECT_ID") ?: ""

        viewModel.getProjectById(projectId).observe(this) { project ->
            project?.let { bindProject(it) }
        }

        viewModel.getAverageRating(projectId).observe(this) { avg ->
            val rating = avg ?: 0f
            binding.ratingBar.rating = rating
            binding.tvAvgRating.text = String.format("%.1f / 5.0", rating)
        }

        binding.btnReportIssue.setOnClickListener {
            val sheet = FeedbackBottomSheet.newInstance(projectId, isKannada)
            sheet.show(supportFragmentManager, "feedback")
        }
    }

    private fun bindProject(p: Project) {
        supportActionBar?.title = p.getTitle(isKannada)
        binding.tvDescription.text = p.getDescription(isKannada)
        binding.tvContractor.text = p.contractor
        binding.tvStartDate.text = p.start_date
        binding.tvEndDate.text = p.expected_end_date
        binding.tvBudget.text = p.budgetFormatted()
        binding.tvSpent.text = p.spentFormatted()
        binding.progressBar.progress = p.progress_percent
        binding.tvProgress.text = "${p.progress_percent}% Complete"
        binding.tvWard.text = if (p.ward_number == 0) "All Wards" else "Ward ${p.ward_number}"
        binding.tvStatus.text = p.status.replace("_", " ")

        binding.imgBefore.load(p.image_before_url) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
            transformations(RoundedCornersTransformation(16f))
        }
        binding.imgAfter.load(p.image_after_url) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
            transformations(RoundedCornersTransformation(16f))
        }

        binding.layoutUpdates.removeAllViews()
        p.updates.forEach { update ->
            val tv = android.widget.TextView(this)
            val note = if (isKannada) update.note_kn else update.note_en
            tv.text = "• ${update.date}: $note"
            tv.setPadding(0, 8, 0, 8)
            tv.setTextColor(getColor(R.color.text_secondary))
            binding.layoutUpdates.addView(tv)
        }
        if (p.updates.isEmpty()) {
            binding.tvNoUpdates.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}