package com.bvm.pokedex.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bvm.pokedex.databinding.FragmentAllMonstersBinding
import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.RequestPaginate
import com.bvm.pokedex.presentation.ui.adapters.AllMonstersAdapter
import com.bvm.pokedex.presentation.viewmodels.PokedexViewModel
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class AllMonstersFragment : Fragment() , AllMonstersAdapter.OnMonsterSelected {

    private lateinit var binding: FragmentAllMonstersBinding
    private lateinit var adapter: AllMonstersAdapter
    private lateinit var pokemonViewModel: PokedexViewModel
    private lateinit var mShimmerViewContainer: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMonstersBinding.inflate(layoutInflater)
        pokemonViewModel = ViewModelProvider(this)[PokedexViewModel::class.java]
        adapter = context?.let { it.applicationContext?.let { it1 -> AllMonstersAdapter(it1,this) } }!!

        mShimmerViewContainer = binding.shimmerViewContainer
        val builder = Shimmer.AlphaHighlightBuilder()
        builder.setDirection(Shimmer.Direction.TOP_TO_BOTTOM)
        mShimmerViewContainer.setShimmer(builder.build())

        getAllPokemon(0)

        binding.monsterList.adapter = adapter

        binding.monsterList.setOnScrollChangeListener { _, _, _, _, _ ->
            if (!binding.monsterList.canScrollVertically(1)) {
                binding.monsterProgress.visibility = View.VISIBLE
                binding.monsterList.scrollToPosition(adapter.currentList.size - 1)
                getAllPokemon(1)
            }
        }

        binding.refresh.setOnRefreshListener {
            getAllPokemon(0)
            binding.refresh.isRefreshing = false
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        mShimmerViewContainer.startShimmer()
    }

    override fun onMonsterClicked(item: MonsterDetailsModel) {
        println("Clicked ${item.name}")
    }

    private fun getAllPokemon(state:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when(state){
                    1 -> RequestPaginate.offset += RequestPaginate.limit
                    else -> RequestPaginate.offset = 0
                }
                val response = pokemonViewModel.getAllPokemon(RequestPaginate.offset, RequestPaginate.limit)
                val list = ArrayList<MonsterDetailsModel>()
                for (item in response?.results ?: ArrayList()) {
                    pokemonViewModel.getPokemonByName(item.name)?.let { list.add(it) }
                }
                withContext(Dispatchers.Main) {
                    when(state){
                        1 -> adapter.submitList(adapter.currentList + list)
                        else -> adapter.submitList(list)
                    }
                    mShimmerViewContainer.startShimmer();
                    binding.dataHolder.visibility = View.VISIBLE
                    binding.shimmerViewContainer.visibility = View.GONE
                    binding.monsterProgress.visibility = View.GONE
                }
            } catch (e: Exception) {
                e.toString()
                withContext(Dispatchers.Main){
                    Snackbar.make(requireView(), "Something went Wrong", Snackbar.LENGTH_SHORT).show()
                    binding.monsterProgress.visibility = View.GONE
                }
            }
        }
    }
}