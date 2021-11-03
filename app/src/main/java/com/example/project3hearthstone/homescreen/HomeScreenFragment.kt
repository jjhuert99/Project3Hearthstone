package com.example.project3hearthstone.homescreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.project3hearthstone.R
import com.example.project3hearthstone.databinding.HomeScreenFragmentBinding

class HomeScreenFragment : Fragment() {

    private val viewModel: HomeScreenViewModel by lazy{
        ViewModelProvider(this).get(HomeScreenViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //set databinding
        val binding = HomeScreenFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        val adapter = HomeScreenAdapter(ClassListener {
            classesA -> viewModel.navigateToClass(classesA)
        })

        viewModel.navigateToClassScreen.observe(this, Observer{
            this.findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToClassFragment(it))
        })

        binding.classesList.adapter = adapter
        viewModel.cardClass.observe(viewLifecycleOwner, Observer{
            adapter.submitList(it)
        })

        viewModel.navigateToSearchScreen.observe(this, Observer {
            this.findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToSearchResultsFragment(it))
        })

        return binding.root
    }
}