package dev.maxsiomin.capitals.fragments.game

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.capitals.R
import dev.maxsiomin.capitals.databinding.FragmentGameBinding
import dev.maxsiomin.capitals.extensions.isDarkThemeOn

@AndroidEntryPoint
class GameFragment : Fragment(R.layout.fragment_game) {

    private lateinit var binding: FragmentGameBinding

    private val viewModel by viewModels<GameViewModel>()

    private var isChecked: Boolean = false
    private var checkedButtonId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setGameOverCallback { score ->
            val direction = GameFragmentDirections.actionGameFragmentToGameResultsFragment(score)
            findNavController().navigate(direction)
        }

        binding = FragmentGameBinding.bind(view)

        binding.apply {
            gameViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner

            questionRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                val answer: String = when (checkedId) {
                    R.id.firstAnswerRadioButton -> {
                        if (!firstAnswerRadioButton.isChecked) return@setOnCheckedChangeListener
                        firstAnswerRadioButton.text
                    }

                    R.id.secondAnswerRadioButton -> {
                        if (!secondAnswerRadioButton.isChecked) return@setOnCheckedChangeListener
                        secondAnswerRadioButton.text
                    }

                    R.id.thirdAnswerRadioButton -> {
                        if (!thirdAnswerRadioButton.isChecked) return@setOnCheckedChangeListener
                        thirdAnswerRadioButton.text
                    }

                    R.id.fourthAnswerRadioButton -> {
                        if (!fourthAnswerRadioButton.isChecked) return@setOnCheckedChangeListener
                        fourthAnswerRadioButton.text
                    }

                    else -> null
                }?.toString() ?: return@setOnCheckedChangeListener

                viewModel.onAnswered(answer)
                isChecked = true
                checkedButtonId = checkedId
                group.allViews.toMutableList().subList(1, 5).forEach {
                    (it as RadioButton).isEnabled = false
                }
            }

            nextButton.setOnClickListener {
                if (!isChecked) return@setOnClickListener

                questionRadioGroup.clearCheck()
                checkedButtonId = null
                isChecked = false

                val color = R.color.white//if (requireContext().isDarkThemeOn()) R.color.black else R.color.white
                root.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
                viewModel.updateQuestion()

                questionRadioGroup.allViews.toMutableList().subList(1, 5).forEach {
                    (it as RadioButton).isEnabled = true
                }
            }
        }

        viewModel.backgroundColor.observe(viewLifecycleOwner) { newColor ->
            binding.root.setBackgroundColor(newColor)
        }

        viewModel.actionBarTitle.observe(viewLifecycleOwner) { newTitle ->
            (activity as AppCompatActivity).supportActionBar?.title = newTitle
        }
    }
}
