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
                val response = pokemonViewModel.getAllPokemon(0, 20)
                val list = ArrayList<MonsterDetailsModel>()
                for (item in response?.results ?: ArrayList()) {
                    pokemonViewModel.getPokemonByName(item.name)?.let { list.add(it) }
                }
                withContext(Dispatchers.Main) {
                    transferTo(AllMonstersFragment(), list)
                }
            } catch (e: Exception) {
                e.toString()
            }
        }
    }

    private fun transferTo(fragment: Fragment, item: ArrayList<MonsterDetailsModel>) {
        val bundle = Bundle()
        bundle.putParcelableArrayList("Monsters", item)
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.commit {
            replace(R.id.nav_container, fragment)
        }
    }

}