package com.example.adiblar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adiblar.databinding.FragmentPagerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PagerFragment : Fragment() {
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

    lateinit var binding: FragmentPagerBinding
    var son = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPagerBinding.inflate(LayoutInflater.from(context))
        repFunction(HomeFragment())
        binding.bottomBar.setOnItemSelectedListener {
            when(it){
                0->{
                    repFunction(HomeFragment())
                    son = 0
                }
                1->{
                    repFunction(SaqlanganFragment())
                    son = 1
                }
                2->{
                    repFunction(InfoFragment())
                    son = 2
                }
            }
        }

        return binding.root
    }

    private fun repFunction(fragment: Fragment) {
        val beginTransaction = fragmentManager?.beginTransaction()
        beginTransaction?.replace(R.id.frame,fragment)
        beginTransaction?.commit()
    }

    override fun onResume() {
        super.onResume()
        if (son == 0){
            repFunction(HomeFragment())
            binding.bottomBar.itemActiveIndex = 0
        } else if (son == 1){
            repFunction(SaqlanganFragment())
            binding.bottomBar.itemActiveIndex = 1
        }else{
            repFunction(InfoFragment())
            binding.bottomBar.itemActiveIndex = 2
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PagerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PagerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}