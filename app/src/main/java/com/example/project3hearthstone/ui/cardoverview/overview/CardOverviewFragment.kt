package com.example.project3hearthstone.ui.cardoverview.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project3hearthstone.databinding.CardOverviewFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardOverviewFragment : Fragment() {
    val viewModel: CardOverviewViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = CardOverviewFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        val cardNamePassed = CardOverviewFragmentArgs.fromBundle(requireArguments()).cardNamePassed
        binding.viewModel = viewModel
        viewModel.passArgs(cardNamePassed)
        viewModel.getCardOverview()

        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }
        return binding.root
    }

}
