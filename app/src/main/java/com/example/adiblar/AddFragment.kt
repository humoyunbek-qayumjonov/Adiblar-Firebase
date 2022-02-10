package com.example.adiblar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.adiblar.databinding.FragmentAddBinding
import com.example.adiblar.models.AdiblarModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
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
    lateinit var binding: FragmentAddBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list:ArrayList<AdiblarModel>
    lateinit var fireBAseStorage:FirebaseStorage
    lateinit var reference:StorageReference
    lateinit var tip:String
    var imgUrl:String? = null
    @SuppressLint("FragmentBackPressedCallback")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(LayoutInflater.from(context))
        tip = ""
        firebaseFirestore = FirebaseFirestore.getInstance()
        fireBAseStorage = FirebaseStorage.getInstance()
        reference = fireBAseStorage.getReference("images")
        list = ArrayList()

        binding.saveBtn.setOnClickListener {
            val name = binding.adibFio.text.toString().trim()
            val birthday = binding.adibBirthday.text.toString().trim()
            val died = binding.adibVafot.text.toString().trim()
            val about = binding.aboutAdib.text.toString().trim()
            val turi = binding.spinTur.selectedItemPosition
            if (turi == 0){
                tip = "Uzbek"
            }else if (turi == 1){
                tip = "Mumtoz"
            }else if (turi == 2){
                tip = "Jahon"
            }
            if (name!="" && birthday!= "" && died!="" && about!="" && tip !=""){
                 val adib = AdiblarModel(imgUrl,name,birthday,died,tip,about)
                firebaseFirestore.collection("adibs")
                    .add(adib)
                    .addOnSuccessListener {
                        Toast.makeText(context, it.id, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
            }else{
                Toast.makeText(context, "Ma`lumot yetarli emas", Toast.LENGTH_SHORT).show()
            }


        }
        binding.adibImg.setOnClickListener {
            getImageContent.launch("image/*")
        }
        binding.adibRasmiBtn.setOnClickListener {
            getImageContent.launch("image/*")
        }

//        val callback: OnBackPressedCallback =
//            object : OnBackPressedCallback(true /* enabled by default */) {
//                override fun handleOnBackPressed() {
//                  findNavController().popBackStack()
//                }
//            }
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback)

        return binding.root
    }
    private var getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()){uri->
        binding.adibImg.setImageURI(uri)
        val m = System.currentTimeMillis()
        if (uri != null){
            val uploadTask = reference.child(m.toString()).putFile(uri)

            uploadTask.addOnSuccessListener {
                if (it.task.isSuccessful){
                    val downloadUrl = it.metadata?.reference?.downloadUrl
                    downloadUrl?.addOnSuccessListener {imgUri->
                        imgUrl = imgUri.toString()
                    }
                }
            }.addOnFailureListener {

            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}