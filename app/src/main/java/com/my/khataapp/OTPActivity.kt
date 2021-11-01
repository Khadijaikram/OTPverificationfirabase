package com.my.khataapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.my.khataapp.databinding.ActivityOptBinding
import com.my.khataapp.utils.MyCodeHalper
import com.my.khataapp.utils.Utils.Companion.Curentuser
import java.util.concurrent.TimeUnit


class OTPActivity : MyCodeHalper() {
    private lateinit var binding: ActivityOptBinding
    private var verificationId: String? = null
    private var mytkn: ForceResendingToken? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        binding.ccp.registerPhoneNumberTextView(binding.number)
        binding.btn.setOnClickListener {
            if (TextUtils.isEmpty(binding.number.text.toString())) {
                Toast.makeText(
                    this@OTPActivity,
                    "Please enter a valid phone number.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val phone = binding.ccp.fullNumberWithPlus.toString()
                val phone1 = binding.ccp.fullNumber.toString()
                val phone2 = binding.ccp.number.toString()
                val phone3 = binding.ccp.fullNumberWithPlus.toString()
                Log.d("TAG", "onCreate: ${phone}$phone1$phone2$phone3")

                sendVerificationCode(phone2)
            }
        }
        binding.senbtn.setOnClickListener {
            resendVerificationCode(binding.ccp.number.toString(), mytkn!!)
        }
        binding.gerbtn.setOnClickListener {
            if (TextUtils.isEmpty(binding.code.text.toString())) {
                Toast.makeText(
                    this@OTPActivity,
                    "Please enter a valid Code.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                verifyCode(binding.code.text.toString())
            }
        }


    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Curentuser = mAuth!!.currentUser!!.uid
//                    rootRef.addListenerForSingleValueEvent()
                } else {
                    Toast.makeText(
                        this@OTPActivity,
                        task.exception?.message,
                        Toast.LENGTH_LONG
                    )
                        .show()
                    Log.d("TAG", "signInWithCredential:" + task.exception?.message)
                }
            }
    }

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBack)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallBack: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                verificationId = s
                mytkn = forceResendingToken
                Log.d("TAG", "onCodeSent:$s")
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                super.onCodeAutoRetrievalTimeOut(p0)
                Log.d("TAG", "onCodetimeout:$p0)")
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                Log.d("TAG", "onCodeSentveri:$code")
                if (code != null) {
                    binding.code.setText(code)
                    verifyCode(code)
                    Log.d("TAG", "onCodeSentveri:$code")
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@OTPActivity, e.message, Toast.LENGTH_LONG).show()
                Log.d("TAG", "onCodeSentFailed:" + e.message)
            }
        }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: ForceResendingToken
    ) {
        PhoneAuthProvider.verifyPhoneNumber(
            PhoneAuthOptions
                .newBuilder(FirebaseAuth.getInstance())
                .setActivity(this)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(mCallBack)
                .setForceResendingToken(token)
                .build()
        );// ForceResendingToken from callbacks
    }
}