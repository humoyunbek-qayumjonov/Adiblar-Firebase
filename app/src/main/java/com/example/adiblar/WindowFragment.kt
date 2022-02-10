package com.example.adiblar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.adiblar.adapter.AdibAdapter
import com.example.adiblar.databinding.FragmentWindowBinding
import com.example.adiblar.models.AdiblarModel
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [WindowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WindowFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    lateinit var binding: FragmentWindowBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var adibAdapter: AdibAdapter
    lateinit var list:ArrayList<AdiblarModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWindowBinding.inflate(LayoutInflater.from(context))
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
                    if (param1 == "Mumtoz adabiyoti"){
                        val sortedList = ArrayList<AdiblarModel>()
                        for (model in list) {
                            if (model.poetTip == "Mumtoz"){
                                sortedList.add(model)
                            }
                        }
                        adibAdapter = AdibAdapter(context,sortedList,object : AdibAdapter.RvItemClick {
                            override fun onMyItemClick(adiblarModel: AdiblarModel) {
                                binding.root.findNavController().navigate(R.id.aboutFragment, bundleOf("keyadib" to adiblarModel))
                            }

                        })
                        binding.rvAdib.adapter = adibAdapter

                    }

                    if (param1 == "O'zbek adabiyoti"){
                        val sortedList = ArrayList<AdiblarModel>()
                        for (model in list) {
                            if (model.poetTip == "Uzbek"){
                                sortedList.add(model)
                            }
                        }
                        adibAdapter = AdibAdapter(context,sortedList,object : AdibAdapter.RvItemClick {
                            override fun onMyItemClick(adiblarModel: AdiblarModel) {
                                binding.root.findNavController().navigate(R.id.aboutFragment, bundleOf("keyadib" to adiblarModel))
                            }

                        })
                        binding.rvAdib.adapter = adibAdapter
                    }

                    if (param1 == "Jahon adabiyoti"){
                        val sortedList = ArrayList<AdiblarModel>()
                        for (model in list) {
                            if (model.poetTip == "Jahon"){
                                sortedList.add(model)
                            }
                        }
                        adibAdapter = AdibAdapter(context,sortedList,object : AdibAdapter.RvItemClick {
                            override fun onMyItemClick(adiblarModel: AdiblarModel) {
                                binding.root.findNavController().navigate(R.id.aboutFragment, bundleOf("keyadib" to adiblarModel))
                            }

                        })
                        binding.rvAdib.adapter = adibAdapter
                    }


                }
            }






        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WindowFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            WindowFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}