package com.bvm.pokedex.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bvm.pokedex.R
import com.bvm.pokedex.databinding.FragmentMonsterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonsterDetailsFragment : Fragment() {

    lateinit var binding: FragmentMonsterDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonsterDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

}