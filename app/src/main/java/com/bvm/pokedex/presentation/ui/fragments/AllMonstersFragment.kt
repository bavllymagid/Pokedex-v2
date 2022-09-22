package com.bvm.pokedex.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.bvm.pokedex.R
import com.bvm.pokedex.databinding.FragmentAllMonstersBinding
import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.RequestPaginate
import com.bvm.pokedex.presentation.ui.adapters.AllMonstersAdapter
import com.bvm.pokedex.presentation.viewmodels.PokedexViewModel
import com.bvm.pokedex.utils.CurrentList.currentList
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
        requireActivity().window.statusBarColor = requireActivity().getColor(R.color.off_white)

        mShimmerViewContainer = binding.shimmerViewContainer
        val builder = Shimmer.AlphaHighlightBuilder()
        builder.setDirection(Shimmer.Direction.TOP_TO_BOTTOM)
        mShimmerViewContainer.setShimmer(builder.build())

        if(currentList.isEmpty()) getAllPokemon(0)
        else{
            binding.dataHolder.visibility = View.VISIBLE
            binding.shimmerViewContainer.visibility = View.GONE
            adapter.submitList(currentList.map { it.copy() })
        }

        binding.monsterList.adapter = adapter

        binding.monsterList.setOnScrollChangeListener { _, _, _, _, _ ->
            if (!binding.monsterList.canScrollVertically(1)) {
                binding.monsterProgress.visibility = View.VISIBLE
                binding.monsterList.scrollToPosition(adapter.currentList.size - 1)
                getAllPokemon(1)
            }
        }

        binding.refresh.setOnRefreshListener {
            currentList.clear()
            getAllPokemon(0)
            binding.monsterList.scrollToPosition(0)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        mShimmerViewContainer.startShimmer()
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onMonsterClicked(item: MonsterDetailsModel , position: Int) {
        val fragment = MonsterDetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable("Monster", item)
        fragment.arguments = bundle
        transferTo(fragment)
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
                currentList.addAll(list)
                println("MYAPP ${currentList.size}")
                withContext(Dispatchers.Main) {
                    when(state){
                        1 -> adapter.submitList(adapter.currentList + list)
                        else -> adapter.submitList(list)
                    }
                    mShimmerViewContainer.startShimmer()
                    binding.dataHolder.visibility = View.VISIBLE
                    binding.shimmerViewContainer.visibility = View.GONE
                    binding.monsterProgress.visibility = View.GONE
                    binding.refresh.isRefreshing = false
                }
            } catch (e: Exception) {
                e.toString()
                withContext(Dispatchers.Main){
                    Snackbar.make(requireView(), "Something Went Wrong", Snackbar.LENGTH_SHORT).show()
                    binding.monsterProgress.visibility = View.GONE
                    binding.refresh.isRefreshing = false
                }
            }
        }
    }

    private fun transferTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.commit {
            addToBackStack("frag")
            setCustomAnimations(
                androidx.appcompat.R.anim.abc_slide_in_bottom,
                androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_slide_out_bottom
            )
            replace(R.id.nav_container, fragment)
        }
    }
}