package com.bvm.pokedex.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(fragmentManager: FragmentManager, behavior:Int) : FragmentPagerAdapter(fragmentManager) {


    val fragmentList = ArrayList<Fragment>()
    val nameList = ArrayList<String>()

    override fun getCount(): Int  = 100

    override fun getItem(position: Int): Fragment {
        TODO("Not yet implemented")
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}