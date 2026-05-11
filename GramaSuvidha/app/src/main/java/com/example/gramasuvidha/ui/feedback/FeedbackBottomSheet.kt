package com.example.gramasuvidha.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.gramasuvidha.databinding.BottomSheetFeedbackBinding
import com.example.gramasuvidha.viewmodel.ProjectViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FeedbackBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetFeedbackBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProjectViewModel by activityViewModels()

    companion object {
        fun newInstance(projectId: String, isKannada: Boolean): FeedbackBottomSheet {
            return FeedbackBottomSheet().apply {
                arguments = Bundle().apply {
                    putString("PROJECT_ID", projectId)
                    putBoolean("IS_KANNADA", isKannada)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val projectId = arguments?.getString("PROJECT_ID") ?: ""
        val isKannada = arguments?.getBoolean("IS_KANNADA") ?: false

        if (isKannada) {
            binding.tvTitle.text = "ಸಮಸ್ಯೆ ವರದಿ ಮಾಡಿ"
            binding.tvRatingLabel.text = "ಯೋಜನೆಯನ್ನು ರೇಟ್ ಮಾಡಿ"
            binding.etIssue.hint = "ಸಮಸ್ಯೆ ವಿವರಿಸಿ..."
            binding.btnSubmit.text = "ಸಲ್ಲಿಸಿ"
        }

        binding.btnSubmit.setOnClickListener {
            val rating = binding.ratingBar.rating.toInt()
            val issue = binding.etIssue.text.toString().trim()

            if (rating == 0) {
                Toast.makeText(
                    requireContext(),
                    if (isKannada) "ದಯವಿಟ್ಟು ರೇಟಿಂಗ್ ನೀಡಿ" else "Please give a rating",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.submitFeedback(projectId, rating, issue)
            Toast.makeText(
                requireContext(),
                if (isKannada) "ಧನ್ಯವಾದಗಳು!" else "Feedback submitted!",
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}