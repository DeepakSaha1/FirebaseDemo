package com.example.firebasedemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAnalytics = Firebase.analytics

        appPackageName()

        findViewById<Button>(R.id.btn_track_event).setOnClickListener {
            setUpUserProperty()
            setUserId()
            logEvent()
        }
    }

    private fun appPackageName() {
        val packageName = applicationContext.packageName
        Log.d(TAG, packageName)
    }

    private fun logEvent() {
        // suppose user click on ecommerce app for purchasing mobile
        // firebase suggested event
        val eventName = FirebaseAnalytics.Event.SELECT_ITEM
        val bundle = Bundle().apply {
            putInt(FirebaseAnalytics.Param.ITEM_ID, 784621)
            putString(FirebaseAnalytics.Param.ITEM_BRAND, "ONE_PLUS")
            putString("MODEL", "ONE_PLUS_8_PRO")
            putDouble(FirebaseAnalytics.Param.PRICE, 39_499.00)
            putBoolean("PURCHASE_BUTTON_CLICK", true)
            putBoolean(FirebaseAnalytics.Param.SUCCESS, true)
        }
        setDefaultParameters()
        firebaseAnalytics.logEvent(eventName, bundle)

        // custom event
        val eventName2 = "share_image"
        val bundle2 = Bundle().apply {
            bundle.putString("image_name", "some_name")
            bundle.putString("image_description", "some_text")
        }

        firebaseAnalytics.logEvent(eventName2, bundle2)
    }

    private fun setDefaultParameters() {
        val parameters = Bundle().apply {
            putString(FirebaseAnalytics.Param.LOCATION, "INDIA")
            putString(FirebaseAnalytics.Param.CURRENCY, "INR")
        }

        firebaseAnalytics.setDefaultEventParameters(parameters)
    }

    private fun setUpUserProperty() {
        firebaseAnalytics.setUserProperty("location", "INDIA")
        firebaseAnalytics.setUserProperty("currency", "INR")
        firebaseAnalytics.setUserProperty("is_subscribe", "false")
    }

    private fun setUserId() {
        firebaseAnalytics.setUserId("64512")
    }
}