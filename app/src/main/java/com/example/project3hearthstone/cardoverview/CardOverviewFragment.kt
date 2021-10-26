package com.example.project3hearthstone.cardoverview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project3hearthstone.R
import com.example.project3hearthstone.classscreen.ClassViewModel
import com.example.project3hearthstone.databinding.CardOverviewFragmentBinding
import com.example.project3hearthstone.homescreen.HomeScreenViewModel

class CardOverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = CardOverviewFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val cardNamePassed = CardOverviewFragmentArgs.fromBundle(requireArguments()).cardNamePassed
        val viewModelFactory = CardOverviewViewModelFactory(cardNamePassed, application)
        //binding.setLifecycleOwner(this)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(CardOverviewViewModel::class.java)

        return binding.root
    }

}