package com.my.khataapp.utils

import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.my.khataapp.R
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.*

class Utils {
    companion object {
        var Curentuser = ""

        fun mSubmitJasonfiledata(context: Context, s: String, n: String) {
            val rootFolder = context.getExternalFilesDir(null)
            val jsonFile =
                File(
                    rootFolder,
                    context.getString(R.string.app_name) + context.getString(R.string.jsonext)
                )
            val writer = FileWriter(jsonFile)
            if (n == "new") {
                writer.write("{}")
            } else {
                writer.write(s)
            }
            writer.close()
        }

        fun gettext(context: Context, mtext: EditText, s: String): Boolean {
            if (mtext.text.toString().isEmpty()) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
                return false
            } else {
                return true
            }

        }

        fun Getmyfiletext(file: File): String? {
            var textfromfile: String? = null
            if (file.exists()) {
                val text = StringBuilder()
                try {
                    val br = BufferedReader(FileReader(file))
                    var line = br.readLine()
                    while (line != null) {
                        text.append(line)
                        text.append('\n')
                        line = br.readLine()
                    }
                    br.close()
                    textfromfile = text.toString()

                } catch (e: IOException) {
                    e.printStackTrace()
                    //You'll need to add proper error handling here
                }
            } else {
                Log.d("TAG", "else: no file  ")
            }
            return textfromfile
        }

        fun myJASONObj(mFilepath: File): JSONObject? {
            val mJSONObject: JSONObject?
            var mobject: Any? = null
            val myTxt = Getmyfiletext(mFilepath)
            try {
                mobject = JSONTokener(myTxt).nextValue()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            mJSONObject = mobject as JSONObject?
            return mJSONObject
        }
    }

}