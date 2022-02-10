package com.example.adiblar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.adiblar.adapter.AdibAdapter
import com.example.adiblar.databinding.FragmentSearchBinding
import com.example.adiblar.models.AdiblarModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
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
    lateinit var binding: FragmentSearchBinding
    lateinit var firebaseFirestore:FirebaseFirestore
    lateinit var list:ArrayList<AdiblarModel>
    lateinit var adibAdapter: AdibAdapter
    @SuppressLint("FragmentBackPressedCallback")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(LayoutInflater.from(context))
        binding.serachView.setQuery("",true)
        binding.serachView.isFocusable = true
        binding.serachView.isIconified = false
        binding.serachView.requestFocusFromTouch()
        firebaseFirestore = FirebaseFirestore.getInstance()
        list = ArrayList()
        firebaseFirestore.collection("adibs")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val result = it.result
                    result?.forEach {queryDocumentSnapshot ->
                    val model = queryDocumentSnapshot.toObject(AdiblarModel::class.java)
                        list.add(model)
                    }
                    adibAdapter = AdibAdapter(context,list ,object : AdibAdapter.RvItemClick {
                        override fun onMyItemClick(adiblarModel: AdiblarModel) {
                            binding.root.findNavController().navigate(R.id.aboutFragment, bundleOf("keyadib" to adiblarModel))
                        }

                    })
                    binding.rvSearch.adapter = adibAdapter
                    binding.serachView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return true
                            }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            var sortedList = ArrayList<AdiblarModel>()
                            binding.serachView.clearFocus()
                            for (listModel in list) {
                                for (i in listModel.fio!!.indices){
                                    if (listModel.fio!!.subSequence(0,i).toString().lowercase(Locale.getDefault()) == newText!!.toLowerCase()){
                                        sortedList.add(listModel)
                                    }
                                }
                            }
                            adibAdapter = AdibAdapter(context,sortedList,object : AdibAdapter.RvItemClick {
                                override fun onMyItemClick(adiblarModel: AdiblarModel) {
                                    binding.root.findNavController().navigate(R.id.aboutFragment, bundleOf("key" to adiblarModel))
                                }

                            })
                            binding.rvSearch.adapter = adibAdapter
                            return true
                        }

                    })
                }
            }
//
//        val callback: OnBackPressedCallback =
//            object : OnBackPressedCallback(true /* enabled by default */) {
//                override fun handleOnBackPressed() {
//                    findNavController().popBackStack()
//                }
//            }
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback)
        return binding.root

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}