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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.searchResultsRV.adapter = SearchResultsAdapter()

        return binding.root
    }
}