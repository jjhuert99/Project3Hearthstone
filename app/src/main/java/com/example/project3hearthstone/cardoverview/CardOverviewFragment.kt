package com.example.project3hearthstone.cardoverview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.project3hearthstone.R
import com.example.project3hearthstone.databinding.CardOverviewFragmentBinding
import com.example.project3hearthstone.favoritesdatabase.FavoritesDatabase

class CardOverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = CardOverviewFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        val dataSource = FavoritesDatabase.getInstance(application).favoritesDatabaseDao
        val cardNamePassed = CardOverviewFragmentArgs.fromBundle(requireArguments()).cardNamePassed
        val viewModelFactory = CardOverviewViewModelFactory(cardNamePassed, application, dataSource)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(CardOverviewViewModel::class.java)

        binding.backArrow.setOnClickListener{view: View->
            view.findNavController().navigate(R.id.action_cardOverviewFragment_to_homeScreenFragment)
        }
        return binding.root
    }

}