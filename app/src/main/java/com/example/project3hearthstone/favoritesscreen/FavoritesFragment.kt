package com.example.project3hearthstone.favoritesscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.project3hearthstone.R
import com.example.project3hearthstone.databinding.FavoritesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FavoritesFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel



        binding.backArrowF.setOnClickListener{view: View->
            view.findNavController().navigate(R.id.action_favoritesFragment_to_homeScreenFragment)
        }
        binding.favsRecyclerView.adapter = FavoritesAdapter(FavoritesAdapter.OnClickListener{
            viewModel.passCardName(it)
        })

        viewModel.navigateToOverView.observe(viewLifecycleOwner) {
            this.findNavController().navigate(
                FavoritesFragmentDirections.actionFavoritesFragmentToCardOverviewFragment(it)
            )
        }
        return binding.root
    }


}
