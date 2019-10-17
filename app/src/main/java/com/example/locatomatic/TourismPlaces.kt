package com.example.locatomatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_tourism_places.*
import org.json.JSONObject

class TourismPlaces : AppCompatActivity() {
    var country = ""
    var province = ""
    var city = ""
    lateinit var adapter: Adapter
    var places: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourism_places)
        val intent = getIntent()
        val country = intent.getStringExtra("country")
        val province = intent.getStringExtra("province")
        val city = intent.getStringExtra("city")
        Log.i("Places", country+province+city)
        var url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + city + "+" + province + "+" + country + "+point+of+interest&language=en&key=AIzaSyD2DDliqRX9DDUCSsEvRhCU8YVrya7vBa8"
        val queue = Volley.newRequestQueue(this)
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var jsonObject = JSONObject(response)
                var result = jsonObject.getJSONArray("results")
                for (i in 0..result.length() - 1) {
                    var jsonObject1 = result.getJSONObject(i)
                    places.add(jsonObject1.getString("name"))
                    //Log.i("Places", places.get(i))
                }
                if(places.size == 0) places.add("Nothing Found!!!")
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = Adapter(this, places)
                adapter = Adapter(this, places)

                //Set up recyclerview with Vertical LayoutManager and the adapter

                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                recyclerView.setAdapter(adapter)
            },
            Response.ErrorListener { error ->
                Log.i("Places", error.message)
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}
