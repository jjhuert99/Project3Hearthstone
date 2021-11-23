package com.example.project3hearthstone.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.project3hearthstone.databinding.HomeScreenFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    /*private val viewModel: HomeScreenViewModel by lazy{
        ViewModelProvider(this).get(HomeScreenViewModel::class.java)
    }*/
    val viewModel: HomeScreenViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //set databinding
        val binding = HomeScreenFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        val adapter = HomeScreenAdapter(ClassListener {
            classesA -> viewModel.navigateToClass(classesA)
        })

        viewModel.navigateToClassScreen.observe(viewLifecycleOwner, Observer{
            this.findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToClassFragment(it))
        })

        binding.classesList.adapter = adapter
        viewModel.cardClass.observe(viewLifecycleOwner, Observer{
            adapter.submitList(it)
        })

        viewModel.navigateToSearchScreen.observe(viewLifecycleOwner, Observer {
            this.findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToSearchResultsFragment(it))
        })

        return binding.root
    }
}
