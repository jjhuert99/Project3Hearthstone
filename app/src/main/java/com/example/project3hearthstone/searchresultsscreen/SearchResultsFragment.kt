package com.example.project3hearthstone.searchresultsscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project3hearthstone.R
import com.example.project3hearthstone.classscreen.ClassViewModel
import com.example.project3hearthstone.databinding.SearchResultsFragmentBinding

class SearchResultsFragment : Fragment() {
    private val viewModel: SearchResultsViewModel by lazy{
        ViewModelProvider(this).get(SearchResultsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = SearchResultsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        val passedSearch = SearchResultsFragmentArgs.fromBundle(requireArguments()).searchString
        val viewModelFactory = SearchViewModelFactory(passedSearch, application)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(SearchResultsViewModel::class.java)
        binding.searchResultsRV.adapter = SearchResultsAdapter()

        return binding.root
    }
}