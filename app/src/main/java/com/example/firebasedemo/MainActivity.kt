package com.example.firebasedemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseCrashlytics: FirebaseCrashlytics
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appPackageName()

        //init
        firebaseCrashlytics = FirebaseCrashlytics.getInstance()
//        crashReportCustomize()
        initUI()
    }

    private fun appPackageName() {
        val packageName = applicationContext.packageName
        Log.d(TAG, packageName)
    }

    private fun initUI() {
        findViewById<Button>(R.id.btn_crash).run {
            setOnClickListener {
                throw RuntimeException("Test crash")
//                setUpCrash()
//                setUpCrashForNonFatalException()
            }
        }
    }

    private fun setUpCrash() {
        val array = arrayOf(1, 2, 3, 4)
        array[5]
    }

    private fun setUpCrashForNonFatalException() {
        try {
            val a = 10
            val b = 0
            val someNum = a / b
        } catch (e: ArithmeticException) {
            firebaseCrashlytics.recordException(e)
            Log.d(TAG, "Divided by zero operation cannot possible")
        }
    }

    fun crashReportCustomize() {
        //Add custom keys
        firebaseCrashlytics.setCustomKey("DATE", getCurrentTime())

        //Add custom log messages
        firebaseCrashlytics.log("ToTheNew trainee firebase crashlytics demo app")

        //Set user identifiers
        firebaseCrashlytics.setUserId("0123")
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.ENGLISH)
        return sdf.format(Date())
    }
}