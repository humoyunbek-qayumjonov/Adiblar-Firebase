package com.example.adiblar.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.adiblar.WindowFragment
import com.example.adiblar.models.PagerModel

class TabAdapter(var list:ArrayList<PagerModel>,fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return WindowFragment.newInstance(list[position].titleName)
    }
}