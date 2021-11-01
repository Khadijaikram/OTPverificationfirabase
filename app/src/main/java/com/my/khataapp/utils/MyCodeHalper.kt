package com.my.khataapp.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.my.khataapp.R
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.*

open class MyCodeHalper : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    lateinit var mFilepath: File
    lateinit var sharedPref: SharePrefnce

    lateinit var firestoreref: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        sharedPref = SharePrefnce(this)
        firestoreref = Firebase.firestore
        mFilepath = File(
            getExternalFilesDir(null),
            getString(R.string.app_name) + getString(R.string.jsonext)
        )
    }


}