package com.example.project3hearthstone.cardoverview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project3hearthstone.R
import com.example.project3hearthstone.databinding.CardOverviewFragmentBinding
import com.example.project3hearthstone.homescreen.HomeScreenViewModel

class CardOverviewFragment : Fragment() {
    private val viewModel: CardOverviewViewModel by lazy{
        ViewModelProvider(this).get(CardOverviewViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = CardOverviewFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        return binding.root
    }

}