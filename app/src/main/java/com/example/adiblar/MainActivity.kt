package com.example.adiblar

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.adiblar.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.karumi.dexter.PermissionToken

import com.karumi.dexter.listener.PermissionDeniedResponse

import com.karumi.dexter.listener.PermissionGrantedResponse

import com.karumi.dexter.listener.single.PermissionListener

import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionRequest


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var animation: Animation
    lateinit var firebaseFireStore:FirebaseFirestore
    lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        navController = Navigation.findNavController(this,R.id.my_nav_host_fragment)
//        NavigationUI.setupActionBarWithNavController(this,navController)
        animation = AnimationUtils.loadAnimation(this,R.anim.my_animation)
        binding.appImageView.startAnimation(animation)
//        AnimationSleep().start()

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                binding.appImageView.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.appImageView.visibility = View.GONE
                binding.myConstraint.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })




        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) { /* ... */
                }
            }).check()

        firebaseFireStore = FirebaseFirestore.getInstance()





    }

//    inner class AnimationSleep:Thread(){
//        override fun run() {
//            super.run()
//            sleep(3000)
//            binding.appImageView.startAnimation(animation)
//        }
//    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp()
//    }
}