package com.example.locatomatic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class TourismPlaces : AppCompatActivity() {
    var country = ""
    var province = ""
    var city = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourism_places)

        country = "India"
        province = "Rajasthan"
        city = "Alwar"

    }
}
