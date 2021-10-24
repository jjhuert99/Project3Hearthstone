package com.example.project3hearthstone.classscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project3hearthstone.R
import com.example.project3hearthstone.databinding.ClassFragmentBinding
import com.example.project3hearthstone.homescreen.HomeScreenViewModel

class ClassFragment : Fragment() {

    private val viewModel: ClassViewModel by lazy{
        ViewModelProvider(this).get(ClassViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ClassFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel


        return binding.root
    }


}