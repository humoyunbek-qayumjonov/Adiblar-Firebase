package com.example.adiblar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.adiblar.adapter.TabAdapter
import com.example.adiblar.databinding.FragmentHomeBinding
import com.example.adiblar.models.PagerModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_item_category.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentHomeBinding
    lateinit var categoryList:ArrayList<PagerModel>
    lateinit var tabAdapter:TabAdapter
    var tabSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context))
        categoryList = ArrayList()
        binding.searchButton.setOnClickListener {
            binding.root.findNavController().navigate(R.id.searchFragment)
        }
        categoryList.add(PagerModel("Mumtoz adabiyoti"))
        categoryList.add(PagerModel("O'zbek adabiyoti"))
        categoryList.add(PagerModel("Jahon adabiyoti"))
        tabAdapter = TabAdapter(categoryList,context as FragmentActivity)
        binding.viewPager.adapter = tabAdapter

        TabLayoutMediator(binding.tabLayout,binding.viewPager, object :TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = categoryList[position].titleName
            }

        }).attach()

        binding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tabSelected = tab?.position!!
                    val customview = tab?.customView
//                    tabSelected= customview?.id!!
                    customview?.txt_tab_item?.background =
                        resources.getDrawable(R.drawable.ic_tab_bacground)
                    customview?.txt_tab_item?.setTextColor(resources.getColor(R.color.white))


                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val customView = tab?.customView
                    customView?.txt_tab_item?.background =
                        resources.getDrawable(R.drawable.ic_tab_bacground_2)
                    customView?.txt_tab_item?.setTextColor(resources.getColor(R.color.un_selected))
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            }
        )

        val tabCount = binding.tabLayout.tabCount
        for (i in 0 until tabCount){
            val tabView = LayoutInflater.from(context).inflate(R.layout.tab_item_category, null, false)
            val tab = binding.tabLayout.getTabAt(i)
            tab?.customView = tabView
            tabView.txt_tab_item.text = categoryList[i].titleName
            if (i == 0){
                tabView.txt_tab_item?.background =
                    resources.getDrawable(R.drawable.ic_tab_bacground)
                tabView?.txt_tab_item?.setTextColor(resources.getColor(R.color.white))
            }else{

                tabView?.txt_tab_item?.background =
                    resources.getDrawable(R.drawable.ic_tab_bacground_2)
                tabView?.txt_tab_item?.setTextColor(resources.getColor(R.color.un_selected))
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.tabLayout.getTabAt(tabSelected)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}