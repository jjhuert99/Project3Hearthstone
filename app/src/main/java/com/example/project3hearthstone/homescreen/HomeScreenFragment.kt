package com.example.project3hearthstone.homescreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.project3hearthstone.R
import com.example.project3hearthstone.databinding.HomeScreenFragmentBinding

class HomeScreenFragment : Fragment() {

    private val viewModel: HomeScreenViewModel by lazy{
        ViewModelProvider(this).get(HomeScreenViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //set databinding
        val binding = HomeScreenFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        val adapter = HomeScreenAdapter()
        binding.classesList.adapter = adapter
        viewModel.cardClass.observe(viewLifecycleOwner, Observer{
            adapter.dataClasses = it
        })

        return binding.root
    }
}