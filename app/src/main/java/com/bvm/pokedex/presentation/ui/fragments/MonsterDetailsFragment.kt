package com.bvm.pokedex.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import com.bvm.pokedex.R
import com.bvm.pokedex.databinding.FragmentMonsterDetailsBinding
import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.Type
import com.bvm.pokedex.presentation.ui.adapters.MonsterTypeAdapter
import com.bvm.pokedex.presentation.ui.adapters.PagerAdapter
import com.bvm.pokedex.utils.ImageLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonsterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMonsterDetailsBinding
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var adapter:MonsterTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonsterDetailsBinding.inflate(layoutInflater)
        val monster = arguments?.getParcelable<MonsterDetailsModel>("Monster")

        adapter = MonsterTypeAdapter(typeListOfMonster(monster?.types!!), 1)

        binding.apply {
            background.setBackgroundColor(ContextCompat.getColor(requireContext(), monster?.color!!))
            ImageLoader.loadImageIntoImageView600(monster.sprites.other.officialArtwork.front_default, monsterImg)
            monsterName.text = monster.name
            typeRc.adapter = adapter
//            pager.adapter = pagerAdapter
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            })

        return binding.root
    }

    private fun typeListOfMonster(types: List<Type>):ArrayList<String>{
        val list = ArrayList<String>()
        for (item in types){
            list.add(item.type.name)
        }

        return list
    }
}

