package com.example.quizapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.databinding.QuizItemBinding
import com.example.quizapp.model.Result

class QuizAdapter(val context: Context) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    var score = 0

    inner class QuizViewHolder(val binding: QuizItemBinding) : RecyclerView.ViewHolder(binding.root)

    val diffCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.question == newItem.question
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = QuizItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = differ.currentList[position]
        holder.binding.apply {
            tvQuestion.text = quiz.question
            tvOption1.text = quiz.correct_answer
            val totalOptions = quiz.incorrect_answers.size

            if (totalOptions >= 3) {
                tvOption2.text = quiz.incorrect_answers[0]
                tvOption3.text = quiz.incorrect_answers[1]
                tvOption4.text = quiz.incorrect_answers[2]
            } else {
                tvOption2.text = "None"
                tvOption3.text = "None"
                tvOption4.text = "None"
            }
        }
        holder.binding.checkbox1.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                score++
                holder.binding.tvOption1.background =
                    ContextCompat.getDrawable(context, R.drawable.correct_option_background)
            }
        }
        holder.binding.checkbox2.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                holder.binding.tvOption2.background =
                    ContextCompat.getDrawable(context, R.drawable.wrong_option_background)
                holder.binding.tvOption3.background =
                    ContextCompat.getDrawable(context, R.drawable.option_background)
                holder.binding.tvOption4.background =
                    ContextCompat.getDrawable(context, R.drawable.option_background)
            }
        }
        holder.binding.checkbox3.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                holder.binding.tvOption2.background =
                    ContextCompat.getDrawable(context, R.drawable.option_background)
                holder.binding.tvOption3.background =
                    ContextCompat.getDrawable(context, R.drawable.wrong_option_background)
                holder.binding.tvOption4.background =
                    ContextCompat.getDrawable(context, R.drawable.option_background)
            }
        }
        holder.binding.checkbox4.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                holder.binding.tvOption2.background =
                    ContextCompat.getDrawable(context, R.drawable.option_background)
                holder.binding.tvOption3.background =
                    ContextCompat.getDrawable(context, R.drawable.option_background)
                holder.binding.tvOption4.background =
                    ContextCompat.getDrawable(context, R.drawable.wrong_option_background)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}