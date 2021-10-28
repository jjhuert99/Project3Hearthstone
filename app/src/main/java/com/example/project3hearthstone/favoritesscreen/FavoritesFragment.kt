package com.example.project3hearthstone.favoritesscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project3hearthstone.R
import com.example.project3hearthstone.cardoverview.CardOverviewViewModel
import com.example.project3hearthstone.cardoverview.CardOverviewViewModelFactory
import com.example.project3hearthstone.classscreen.ClassViewModel
import com.example.project3hearthstone.databinding.FavoritesFragmentBinding
import com.example.project3hearthstone.favoritesdatabase.FavoritesDatabase

class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by lazy{
        ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FavoritesFragmentBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        binding.setLifecycleOwner(this)

        val dataSource = FavoritesDatabase.getInstance(application).favoritesDatabaseDao
        val viewModelFactory = FavoritesViewModelFactory(application, dataSource)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)

        binding.favsRecyclerView.adapter = FavoritesAdapter()
        return binding.root
    }


}