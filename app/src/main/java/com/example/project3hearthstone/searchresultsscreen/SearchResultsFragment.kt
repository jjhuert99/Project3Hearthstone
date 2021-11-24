package com.example.project3hearthstone.searchresultsscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.project3hearthstone.R
import com.example.project3hearthstone.classscreen.ClassFragmentDirections
import com.example.project3hearthstone.classscreen.ClassViewModel
import com.example.project3hearthstone.databinding.SearchResultsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultsFragment : Fragment() {

    val viewModel: SearchResultsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = SearchResultsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        val passedSearch = SearchResultsFragmentArgs.fromBundle(requireArguments()).searchString
        binding.viewModel = viewModel
        viewModel.passArgs(passedSearch)
        viewModel.getSearchResults()

        binding.searchResultsRV.adapter = SearchResultsAdapter(SearchResultsAdapter.OnClickListener{
            viewModel.justNav()
            viewModel.passCardName(it)
        })
        viewModel.navigateToOverView.observe(viewLifecycleOwner) {
            if (viewModel.navYet.value == true) {
                this.findNavController().navigate(
                    SearchResultsFragmentDirections.actionSearchResultsFragmentToCardOverviewFragment(it)
                )
            }
            viewModel.doneNav()
        }

        return binding.root
    }
}
