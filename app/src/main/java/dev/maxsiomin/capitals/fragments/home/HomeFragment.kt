package dev.maxsiomin.capitals.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.capitals.R
import dev.maxsiomin.capitals.databinding.FragmentHomeBinding
import dev.maxsiomin.capitals.extensions.openInBrowser
import dev.maxsiomin.capitals.util.BaseViewModel
import dev.maxsiomin.capitals.util.SharedPrefsConfig.PARTS_OF_WORLD

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<BaseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = viewModel.getString(R.string.app_name)

        binding.apply {
            buttonNewGame.setOnClickListener {
                if (viewModel.sharedPrefs.getStringSet(PARTS_OF_WORLD, setOf())!!.size > 0)
                    findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
                else
                    viewModel.toast(R.string.need_more_parts_of_world, Toast.LENGTH_SHORT)
            }

            buttonSettings.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
            }

            buttonWebsite.setOnClickListener {
                requireActivity().openInBrowser("https://maxsiomin.dev")
            }
        }
    }
}
