package com.rcalencar.coroutinevolley.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rcalencar.coroutinevolley.R
import com.rcalencar.coroutinevolley.viewmodel.TodoViewModel
import com.rcalencar.coroutinevolley.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private val todoViewModel: TodoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFirstBinding.bind(view)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        todoViewModel.liveData.observe(requireActivity(), {
            it?.let {
                binding.textviewFirst.text = it.data?.title ?: "Error"
            }
        })
    }
}



