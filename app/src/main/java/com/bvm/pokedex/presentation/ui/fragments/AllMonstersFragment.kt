package com.bvm.pokedex.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bvm.pokedex.databinding.FragmentAllMonstersBinding
import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.presentation.ui.adapters.AllMonstersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMonstersFragment : Fragment() {

    private lateinit var binding: FragmentAllMonstersBinding
    private lateinit var adapter: AllMonstersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMonstersBinding.inflate(layoutInflater)
        adapter = AllMonstersAdapter()

        val list = arguments?.getParcelableArrayList<MonsterDetailsModel>("Monsters")

        adapter.submitList(list)

        binding.monsterList.adapter = adapter

        return binding.root
    }
}