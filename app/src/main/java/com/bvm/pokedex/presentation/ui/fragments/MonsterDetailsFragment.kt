package com.bvm.pokedex.presentation.ui.fragments

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bvm.pokedex.R
import com.bvm.pokedex.databinding.FragmentMonsterDetailsBinding
import com.bvm.pokedex.domain.models.*
import com.bvm.pokedex.presentation.ui.adapters.AllMonstersAdapter
import com.bvm.pokedex.presentation.ui.adapters.MonsterTypeAdapter
import com.bvm.pokedex.presentation.viewmodels.PokedexViewModel
import com.bvm.pokedex.utils.ImageLoader
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MonsterDetailsFragment : Fragment(), AllMonstersAdapter.OnMonsterSelected {

    private lateinit var binding: FragmentMonsterDetailsBinding
    private lateinit var adapter:MonsterTypeAdapter
    private lateinit var pokemonViewModel: PokedexViewModel
    private lateinit var evolutionAdapter: AllMonstersAdapter

    private var snackBar: Snackbar? = null

    private var monster:MonsterDetailsModel? = null
    private var species:PokeSpecies? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonsterDetailsBinding.inflate(layoutInflater)
        pokemonViewModel = ViewModelProvider(this)[PokedexViewModel::class.java]
        monster = arguments?.getParcelable("Monster")

        if(monster != null) {
            requireActivity().window.statusBarColor = requireActivity().getColor(monster!!.color)
            requireActivity().window.decorView.systemUiVisibility = 0
            binding.selector.setSelectedTabIndicatorColor(requireActivity().getColor(monster!!.color))
            evolutionAdapter = AllMonstersAdapter(context?.applicationContext!!, this)
            binding.monsterEvolution.adapter = evolutionAdapter

            getSpeciesDetails(monster!!.id)

            adapter = MonsterTypeAdapter(typeListOfMonster(monster!!.types), 1)

            binding.backBtn.setOnClickListener {
                snackBar?.dismiss()
                requireActivity().supportFragmentManager.popBackStack()
                requireActivity().supportFragmentManager.beginTransaction().remove(this@MonsterDetailsFragment).commitAllowingStateLoss()
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

        binding.selector.isEnabled = false


        binding.selector.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        binding.selector.isEnabled = false
                        binding.dataPlacer.visibility = View.VISIBLE
                        binding.baseStat.visibility = View.GONE
                        binding.monsterEvolution.visibility = View.GONE
                        bindAbout()
                    }
                    1 -> {
                        binding.dataPlacer.visibility = View.GONE
                        binding.baseStat.visibility = View.VISIBLE
                        binding.monsterEvolution.visibility = View.GONE
                        bindBaseStat()
                    }
                    2 -> {
                        binding.dataPlacer.visibility = View.GONE
                        binding.baseStat.visibility = View.GONE
                        binding.selector.isEnabled = false
                        getEvolutionList()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    snackBar?.dismiss()
                    requireActivity().supportFragmentManager.popBackStack()
                    requireActivity().supportFragmentManager.beginTransaction().remove(this@MonsterDetailsFragment).commitAllowingStateLoss()
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
                withContext(Dispatchers.IO){
                    species = pokemonViewModel.getPokeSpecies(id)!!
                }
                binding.aboutProgress.visibility = View.GONE
                binding.dataPlacer.visibility = View.VISIBLE

                binding.apply {
                    idTv.text = String.format("#%03d", monster?.id)
                    speciesTv.text = monster?.species?.name
                    heightTv.text = "${monster?.height?.toFloat()?.div(10)} m"
                    weightTv.text = "${monster?.weight?.toFloat()?.div(10)} kg"
                    abilityTv.text = monster?.abilities?.let { getPokeAbilities(it) }
                    briefTv.text = species?.flavor_text_entries?.let { getFlavourText(it) }
                }
                binding.selector.isEnabled = true
            }catch (e:Exception){
                snackBar = Snackbar.make(requireView(), "Something went Wrong", Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(android.graphics.Color.RED)
                    .setAction("Refresh") {
                        getSpeciesDetails(id)
                    }
                snackBar!!.show()
            }
        }
    }

    private fun getPokeAbilities(list:List<Ability>):String{
        var s = ""
        repeat(list.size-1){
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

    @SuppressLint("SetTextI18n")
    private fun bindAbout(){
        if(species != null){
            binding.apply {
                idTv.text = String.format("#%03d", monster?.id)
                speciesTv.text = monster?.species?.name
                heightTv.text = "${monster?.height?.toFloat()?.div(10)} m"
                weightTv.text = "${monster?.weight?.toFloat()?.div(10)} kg"
                abilityTv.text = monster?.abilities?.let { getPokeAbilities(it) }
                briefTv.text = species?.flavor_text_entries?.let { getFlavourText(it) }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindBaseStat(){
        if(monster != null){
            val hp = getStat("hp")
            val atk = getStat("attack")
            val def = getStat("defense")
            val spAtk = getStat("special-attack")
            val spDef = getStat("special-defense")
            val speed = getStat("speed")
            binding.apply {
                bindStatView(hpTv, hpIndicator, hp)
                bindStatView(atkTv, atkIndicator, atk)
                bindStatView(defTv, defIndicator, def)
                bindStatView(spAttackTv, spAtkIndicator, spAtk)
                bindStatView(spDefTv, spDefIndicator, spDef)
                bindStatView(speedTv, speedIndicator, speed)

                totalTv.text = (hp.second+atk.second+def.second+spAtk.second+spDef.second+speed.second).toString()
            }
        }
    }

    private fun bindStatView(textView: TextView, linearProgressIndicator: LinearProgressIndicator, pair :Pair<String,Int>){
        textView.text = pair.first
        linearProgressIndicator.progress = pair.second
        if(pair.second >= 85)
            linearProgressIndicator.setIndicatorColor(requireActivity().getColor(R.color.green))
        else if (pair.second in 65..85)
            linearProgressIndicator.setIndicatorColor(requireActivity().getColor(R.color.yellow))
        else
            linearProgressIndicator.setIndicatorColor(requireActivity().getColor(R.color.red))
    }

    private fun getStat(stateName:String):Pair<String,Int>{
        for (item in monster?.stats!!){
            if(item.stat.name == stateName){
                return Pair(item.base_stat.toString(),item.base_stat)
            }
        }
        return Pair("",0)
    }

    private fun getEvolutionList() {
        var mons:MonsterDetailsModel?
        binding.aboutProgress.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            try {
                withContext(Dispatchers.IO) {
                    mons = species?.evolves_from_species?.let { it.name.let { it1 ->
                        pokemonViewModel.getPokemonByName(
                            it1
                        )
                    } }
                }
                if(mons != null)
                    evolutionAdapter.submitList(listOf(mons))
                binding.aboutProgress.visibility = View.GONE
                binding.monsterEvolution.visibility = View.VISIBLE
                binding.selector.isEnabled = true

            } catch (e: Exception) {
                snackBar =
                    Snackbar.make(requireView(), "Something went Wrong", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(android.graphics.Color.RED)
                        .setAction("Refresh") {
                            getEvolutionList()
                        }
                snackBar!!.show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        snackBar = null
    }

    override fun onMonsterClicked(item: MonsterDetailsModel, position: Int) {

    }

}

