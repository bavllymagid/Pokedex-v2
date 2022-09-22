package com.bvm.pokedex.presentation.ui.fragments

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bvm.pokedex.databinding.FragmentMonsterDetailsBinding
import com.bvm.pokedex.domain.models.*
import com.bvm.pokedex.presentation.ui.adapters.MonsterTypeAdapter
import com.bvm.pokedex.presentation.viewmodels.PokedexViewModel
import com.bvm.pokedex.utils.ImageLoader
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MonsterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMonsterDetailsBinding
    private lateinit var adapter:MonsterTypeAdapter
    private lateinit var pokemonViewModel: PokedexViewModel

    private var monster:MonsterDetailsModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonsterDetailsBinding.inflate(layoutInflater)
        pokemonViewModel = ViewModelProvider(this)[PokedexViewModel::class.java]
        monster = arguments?.getParcelable<MonsterDetailsModel>("Monster")

        if(monster != null) {
            requireActivity().window.statusBarColor = requireActivity().getColor(monster!!.color)
            requireActivity().window.decorView.systemUiVisibility = 0

            getSpeciesDetails(monster!!.id)

            adapter = MonsterTypeAdapter(typeListOfMonster(monster!!.types), 1)

            binding.backBtn.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            binding.apply {
                background.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        monster!!.color
                    )
                )
                ImageLoader.loadImageIntoImageView600(
                    monster!!.sprites.other.officialArtwork.front_default,
                    monsterImg
                )
                monsterName.text = monster!!.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }

                likeBtn.setOnClickListener{
                    pokemonViewModel.setLikeState(monster!!.id , likeBtn.isChecked)
                }
                likeBtn.isChecked = pokemonViewModel.getLikeState(monster!!.id)

                val colorMatrix = ColorMatrix()
                colorMatrix.setSaturation(1.7f)
                val filter = ColorMatrixColorFilter(colorMatrix)
                monsterImg.colorFilter = filter
                typeRc.adapter = adapter
            }
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

    @SuppressLint("SetTextI18n")
    private fun getSpeciesDetails(id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val species:PokeSpecies
                withContext(Dispatchers.IO){
                    species = pokemonViewModel.getPokeSpecies(id)!!
                }

                binding.aboutProgress.visibility = View.GONE
                binding.dataPlacer.visibility = View.VISIBLE

                binding.apply {
                    speciesTv.text = monster?.species?.name
                    heightTv.text = "${monster?.height} cm"
                    weightTv.text = "${monster?.weight} kg"
                    abilityTv.text = monster?.abilities?.let { getPokeAbilities(it) }

//                    habitatTv.text = species.habitat.name
//                    generationTv.text = species.generation.name
//                    eggGroupTv.text = species.egg_groups[0].name
                    briefTv.text = getFlavourText(species.flavor_text_entries)
                }

            }catch (e:Exception){
                Snackbar.make(requireView(), "Something went Wrong", Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(android.graphics.Color.RED)
                    .setAction("Refresh") {
                        getSpeciesDetails(id)
                    }
                    .show()
            }
        }
    }

    private fun getPokeAbilities(list:List<Ability>):String{
        var s:String = ""
        repeat(list.size-2){
            s += "${list[it].ability.name}, "
        }
        s+= list[list.size-1].ability.name

        return s
    }

    private fun getFlavourText(list: List<FlavorTextEntry>):String{
        for (item in list){
            if(item.language.name == "en"){
                return item.flavor_text.replace("\n","")
            }
        }
        return ""
    }

}

