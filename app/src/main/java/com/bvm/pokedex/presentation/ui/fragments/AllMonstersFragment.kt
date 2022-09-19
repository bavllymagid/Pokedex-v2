package com.bvm.pokedex.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bvm.pokedex.databinding.FragmentAllMonstersBinding
import com.bvm.pokedex.domain.models.MonsterDetailsModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMonstersFragment : Fragment() {

    private lateinit var binding: FragmentAllMonstersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMonstersBinding.inflate(layoutInflater)

        val list = arguments?.getParcelableArrayList<MonsterDetailsModel>("Monsters")

        for (item in list?: ArrayList()){
            println("MineApp ${item.types}")
        }

        return binding.root
    }
}