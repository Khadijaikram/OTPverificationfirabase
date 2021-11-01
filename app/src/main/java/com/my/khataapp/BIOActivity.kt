package com.my.khataapp

import android.os.Bundle
import android.widget.Toast
import com.my.khataapp.databinding.ActivityBioBinding

import com.my.khataapp.utils.MyCodeHalper
import com.my.khataapp.utils.Utils.Companion.gettext

import com.my.khataapp.utils.Utils.Companion.mSubmitJasonfiledata
import com.my.khataapp.utils.Utils.Companion.myJASONObj
import org.json.JSONObject


class BIOActivity : MyCodeHalper() {

    lateinit var mJSONObject: JSONObject
    private lateinit var binding: ActivityBioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mSubmitJasonfiledata(this, "First Create", "new")
        binding.createBusiness.setOnClickListener {
            if (gettext(this, binding.name, "Please Enter your  Name") && gettext(
                    this,
                    binding.number,
                    "Please Enter your Number"
                ) && gettext(this, binding.gender, "Please Enter your Gender Name")
            ) {
                if (!mFilepath.exists()) {
                    Toast.makeText(this@BIOActivity, "file not created", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    mJSONObject = myJASONObj(mFilepath)!!
                    mJSONObject.put("name", binding.name.text.toString())
                    mJSONObject.put("gender", binding.gender.text.toString())
                    mJSONObject.put("number", binding.number.text.toString())
                    mJSONObject.put("Business", JSONObject())
                    mSubmitJasonfiledata(this, mJSONObject.toString(), "")
//                    val MBusinessobj = mJSONObject.getJSONObject("myBusiness")
//                    MBusinessobj.put(binding.number.text.toString(), binding.namebussiness.text.toString())
//                    mJSONObject.put("myBusiness", MBusinessobj)
//                    mSubmitJasonfiledata(this, mJSONObject.toString(), "")
                }
            }

        }

    }

}