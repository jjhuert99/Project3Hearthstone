package com.example.project3hearthstone.classscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project3hearthstone.R

class ClassFragment : Fragment() {

    companion object {
        fun newInstance() = ClassFragment()
    }

    private lateinit var viewModel: ClassViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.class_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClassViewModel::class.java)
        // TODO: Use the ViewModel
    }

}