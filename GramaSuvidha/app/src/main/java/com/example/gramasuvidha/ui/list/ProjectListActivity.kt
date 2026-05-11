package com.example.gramasuvidha.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gramasuvidha.R
import com.example.gramasuvidha.databinding.ActivityProjectListBinding
import com.example.gramasuvidha.ui.detail.ProjectDetailActivity
import com.example.gramasuvidha.util.PrefsManager
import com.example.gramasuvidha.viewmodel.ProjectViewModel

class ProjectListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectListBinding
    private val viewModel: ProjectViewModel by viewModels()
    private lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val isKannada = PrefsManager.isKannada(this)
        viewModel.setLanguage(isKannada)

        setupRecyclerView()
        observeViewModel()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupRecyclerView() {
        adapter = ProjectAdapter(
            isKannada = viewModel.isKannada.value ?: false
        ) { project ->
            val intent = Intent(this, ProjectDetailActivity::class.java)
            intent.putExtra("PROJECT_ID", project.id)
            startActivity(intent)
        }
        binding.rvProjects.layoutManager = LinearLayoutManager(this)
        binding.rvProjects.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.allProjects.observe(this) { projects ->
            binding.swipeRefresh.isRefreshing = false
            if (projects.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvProjects.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvProjects.visibility = View.VISIBLE
                adapter.submitList(projects)
            }
        }

        viewModel.isLoading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.isKannada.observe(this) { isKannada ->
            adapter.updateLanguage(isKannada)
            binding.toolbar.title = if (isKannada) "ಗ್ರಾಮ ಸುವಿಧಾ" else "Grama Suvidha"
            supportActionBar?.subtitle = if (isKannada)
                "ಹೊಸಹಳ್ಳಿ ಗ್ರಾಮ ಪಂಚಾಯತ್" else "Hosahalli Grama Panchayat"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle_language -> {
                viewModel.toggleLanguage()
                val isKannada = viewModel.isKannada.value ?: false
                PrefsManager.setKannada(this, isKannada)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}