package com.example.project3hearthstone.cardoverview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project3hearthstone.R

class CardOverviewFragment : Fragment() {

    companion object {
        fun newInstance() = CardOverviewFragment()
    }

    private lateinit var viewModel: CardOverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.card_overview_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CardOverviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}