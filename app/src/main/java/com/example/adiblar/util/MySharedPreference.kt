package com.example.adiblar.util

import android.content.Context
import android.content.SharedPreferences
import com.example.adiblar.models.AdiblarModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

object MySharedPrefarance {
    private const val NAME = "KeshXotiragaAdib"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context?) {
        preferences = context?.getSharedPreferences(NAME, MODE)!!
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var obektString: ArrayList<AdiblarModel>
        get() = gsonStringToArray(preferences.getString("obekt", "[]")!!)
        set(value) = preferences.edit {
            if (value != null) {
                it.putString("obekt", arrayToGsonString(value))
            }
        }

    fun arrayToGsonString(arrayList: ArrayList<AdiblarModel>): String {
        return Gson().toJson(arrayList)
    }

    fun gsonStringToArray(gsonString: String): ArrayList<AdiblarModel> {
        val typeToken = object : TypeToken<ArrayList<AdiblarModel>>() {}.type
        return Gson().fromJson(gsonString, typeToken)
    }
}