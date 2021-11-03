package com.example.project3hearthstone.classscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project3hearthstone.R
import com.example.project3hearthstone.databinding.ClassFragmentBinding

class ClassFragment : Fragment() {

    private val viewModel: ClassViewModel by lazy{
        ViewModelProvider(this).get(ClassViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = ClassFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        //received Args
        val passedClass = ClassFragmentArgs.fromBundle(requireArguments()).classPass
        val viewModelFactory = ClassViewModelFactory(passedClass, application)


        binding.backArrow.setOnClickListener{view: View->
            view.findNavController().navigate(R.id.action_classFragment_to_homeScreenFragment)
        }
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(ClassViewModel::class.java)
        binding.classRecyclerView.adapter = CardsByClassAdapter(CardsByClassAdapter.OnClickListener{
            viewModel.passCardName(it)
        })
        viewModel.navigateToOverView.observe(this, Observer{
            this.findNavController().navigate(ClassFragmentDirections.actionClassFragmentToCardOverviewFragment(it))
        })
        return binding.root
    }
}