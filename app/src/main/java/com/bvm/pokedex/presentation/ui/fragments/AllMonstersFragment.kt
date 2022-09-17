package com.bvm.pokedex.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bvm.pokedex.R
import com.bvm.pokedex.databinding.FragmentAllMonstersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMonstersFragment : Fragment() {

    lateinit var binding: FragmentAllMonstersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMonstersBinding.inflate(layoutInflater)
        return binding.root
    }
}