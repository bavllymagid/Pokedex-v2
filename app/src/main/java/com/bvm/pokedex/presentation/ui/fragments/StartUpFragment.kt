package com.bvm.pokedex.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.bvm.pokedex.R
import com.bvm.pokedex.databinding.FragmentStartUpBinding
import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.Pokemon
import com.bvm.pokedex.domain.models.RequestPaginate
import com.bvm.pokedex.presentation.viewmodels.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class StartUpFragment : Fragment() {

    private lateinit var binding: FragmentStartUpBinding
    private lateinit var pokemonViewModel: PokedexViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartUpBinding.inflate(layoutInflater)
        pokemonViewModel = ViewModelProvider(this)[PokedexViewModel::class.java]

        getAllPokemon()

        return binding.root
    }


    private fun getAllPokemon() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                pokemonViewModel.getAllPokemon(RequestPaginate.offset, RequestPaginate.limit)
                withContext(Dispatchers.Main) {
                    transferTo(AllMonstersFragment())
                }
            } catch (e: Exception) {
                e.toString()
            }
        }
    }

    private fun transferTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.nav_container, fragment)
        }
    }

}