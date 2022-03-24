package dev.maxsiomin.capitals.fragments.gameresults

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.capitals.R
import dev.maxsiomin.capitals.databinding.FragmentGameResultsBinding
import dev.maxsiomin.capitals.util.BaseViewModel

@AndroidEntryPoint
class GameResultsFragment : Fragment(R.layout.fragment_game_results) {

    private lateinit var binding: FragmentGameResultsBinding

    private val viewModel by viewModels<BaseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = viewModel.getString(R.string.score)

        binding = FragmentGameResultsBinding.bind(view)

        binding.apply {
            scoreTextView.text = getString(R.string.score_value, requireArguments().getInt(ARG_SCORE))
        }
    }

    companion object {
        const val ARG_SCORE = "score"
    }
}
