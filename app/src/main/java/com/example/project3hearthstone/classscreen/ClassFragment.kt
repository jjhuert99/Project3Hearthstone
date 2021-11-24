package com.example.project3hearthstone.classscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project3hearthstone.R
import com.example.project3hearthstone.databinding.ClassFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassFragment : Fragment() {

    val viewModel: ClassViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ClassFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val passedClass = ClassFragmentArgs.fromBundle(requireArguments()).classPass
        binding.viewModel = viewModel
        viewModel.passArgs(passedClass)
        viewModel.getCardsByClass()


        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.classRecyclerView.adapter = CardsByClassAdapter(CardsByClassAdapter.OnClickListener{
            viewModel.justNav()
            viewModel.passCardName(it)
        })
        viewModel.navigateToOverView.observe(viewLifecycleOwner) {
            if (viewModel.navYet.value == true) {
                this.findNavController()
                    .navigate(ClassFragmentDirections.actionClassFragmentToCardOverviewFragment(it))
            }
            viewModel.doneNav()
        }
        return binding.root
    }
}
