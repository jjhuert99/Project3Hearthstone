package com.example.project3hearthstone.mapscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project3hearthstone.R

class MapShopsFragment : Fragment() {

    companion object {
        fun newInstance() = MapShopsFragment()
    }

    private lateinit var viewModel: MapShopsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_shops_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapShopsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}