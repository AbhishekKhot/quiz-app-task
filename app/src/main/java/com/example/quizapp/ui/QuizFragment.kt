package com.example.quizapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.adapter.QuizAdapter
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.repository.QuizRepository
import com.example.quizapp.utils.Resource
import com.google.android.material.snackbar.Snackbar
import java.util.*


class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuizViewModel
    private lateinit var quizAdapter: QuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentQuizBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        val repository = QuizRepository()
        val viewModelProviderFactory = QuizViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(QuizViewModel::class.java)

        viewModel.quizData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    it.data?.let {
                        quizAdapter.differ.submitList(it.results)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        })

        binding.spDifficulty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                viewModel.getDifficultyWiseQuizData(binding.spDifficulty.selectedItem.toString()
                    .lowercase(
                        Locale.getDefault()))
                getDifficultyWiseQuizData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (binding.spCategory.selectedItem.toString()) {
                    "General" -> viewModel.getCategoryWiseQuizData("9")
                    "History" -> viewModel.getCategoryWiseQuizData("23")
                    "Geography" -> viewModel.getCategoryWiseQuizData("22")
                    "Politics" -> viewModel.getCategoryWiseQuizData("24")
                    "Art" -> viewModel.getCategoryWiseQuizData("25")
                    "Maths" -> viewModel.getCategoryWiseQuizData("19")
                    "Science" -> viewModel.getCategoryWiseQuizData("17")
                    "Comics" -> viewModel.getCategoryWiseQuizData("29")
                }

                getCategoriesWiseQuizData()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.btnSubmit.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("score", quizAdapter.score.toString())
            findNavController().navigate(R.id.action_quizFragment_to_resultFragment,bundle)
        }
    }

    private fun getCategoriesWiseQuizData() {
        viewModel.categoryData.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    it.data?.let {
                        quizAdapter.differ.submitList(it.results)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun getDifficultyWiseQuizData() {
        viewModel.difficultyData.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    it.data?.let {
                        quizAdapter.differ.submitList(it.results)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun setUpRecyclerView() = binding.recyclerView.apply {
        quizAdapter = QuizAdapter(requireActivity())
        adapter = quizAdapter
        layoutManager = LinearLayoutManager(requireActivity())

    }

}