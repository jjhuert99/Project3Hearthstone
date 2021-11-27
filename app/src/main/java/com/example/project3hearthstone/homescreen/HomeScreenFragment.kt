package com.example.project3hearthstone.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project3hearthstone.databinding.HomeScreenFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    val viewModel: HomeScreenViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //set databinding
        val binding = HomeScreenFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        val adapter = HomeScreenAdapter(ClassListener {
            viewModel.justNav()
            viewModel.navigateToClass(it)
        })

        viewModel.navigateToClassScreen.observe(viewLifecycleOwner) {
            if (viewModel.navYet.value == true) {
                this.findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToClassFragment(it))
            }
            viewModel.doneNav()
        }

        binding.classesList.adapter = adapter
        viewModel.cardClass.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.navigateToSearchScreen.observe(viewLifecycleOwner) {
            if (viewModel.navYet.value == true) {
                this.findNavController().navigate(
                    HomeScreenFragmentDirections.actionHomeScreenFragmentToSearchResultsFragment(it)
                )
            }
            viewModel.doneNav()
        }

        return binding.root
    }
}
