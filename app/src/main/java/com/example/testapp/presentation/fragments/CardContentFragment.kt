package com.example.testapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.databinding.FragmentCardContentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardContentFragment : Fragment() {
    private var _binding: FragmentCardContentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CardContentFragment()
    }

    //private lateinit var viewModel: CardContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardContentBinding.inflate(inflater, container, false)

        return binding.root
    }
}