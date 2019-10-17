package com.example.locatomatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home_page.*
import org.json.JSONObject

class HomePage : AppCompatActivity() {
    var country: String? = null
    var city: String? = null
    var province: String? = null
    var spinnerCountry: Spinner? = null
    var spinnerProvince: Spinner? = null
    var spinnerCity: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        spinnerCountry = this.countrySpinner
        spinnerProvince = this.provinceSpinner
        spinnerCity = this.citySpinner
        llProgressBar.visibility = View.VISIBLE
        getCountries()
        countrySpinner()
        provinceSpinner()
        citySpinner()

        btnSearch!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@HomePage, TourismPlaces :: class.java);
                intent.putExtra("country", country)
                intent.putExtra("province", province)
                intent.putExtra("city", city)
                startActivity(intent)
            }

        })
    }

    private fun countrySpinner() {
        spinnerCountry!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                country = spinnerCountry!!.getItemAtPosition(position).toString()
                if(!country.equals("Please select a Country", true)){
                    getProvinces(country!!)
                }
                else{
                    spinnerProvince!!.visibility = View.INVISIBLE
                    spinnerCity!!.visibility = View.INVISIBLE
                    btnSearch.visibility = View.INVISIBLE
                }
            }

        }
    }

    private fun provinceSpinner() {
        spinnerProvince!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                province = spinnerProvince!!.getItemAtPosition(position).toString()
                if(!province.equals("Please select a Province", ignoreCase = true)){
                    getCities(province!!)
                }
                else{
                    spinnerCity!!.visibility = View.INVISIBLE
                    btnSearch.visibility = View.INVISIBLE
                }
            }

        }
    }

    private fun citySpinner() {
        spinnerCity!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                city = spinnerCity!!.getItemAtPosition(position).toString()
                if(!city.equals("Please select a City", true)){
                    btnSearch.visibility = View.VISIBLE
                }
                else{
                    btnSearch.visibility = View.INVISIBLE
                }
            }

        }
    }

    private fun getCountries() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://www.avtargill.com/CountryList.php"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var countryData = JSONObject(response);
                var countries = countryData.getJSONArray("countries");
                var countryArray = arrayListOf<String>()
                countryArray.add("Please select a Country")
                for (x in 0..(countries.length() - 1)) {
                    var countryDetail = countries.getJSONObject(x)
                    countryArray.add(countryDetail.getString("name"))
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryArray)
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked)
                spinnerCountry!!.setAdapter(adapter)
                spinnerCountry!!.visibility = View.VISIBLE
                llProgressBar.visibility = View.GONE
            },
            Response.ErrorListener { error ->
                Log.i("country", error.message)
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun getProvinces(country: String) {
        llProgressBar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "http://www.avtargill.com/ProvinceList.php?country="+country
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var provinceData = JSONObject(response);
                var provinces = provinceData.getJSONArray("provinces");
                var provinceArray = arrayListOf<String>()
                provinceArray.add("Please select a Province")
                for (x in 0..(provinces.length() - 1)) {
                    var provinceDetail = provinces.getJSONObject(x)
                    provinceArray.add(provinceDetail.getString("name"))
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinceArray)
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked)
                spinnerProvince!!.setAdapter(adapter)
                spinnerProvince!!.visibility = View.VISIBLE
                llProgressBar.visibility = View.GONE
            },
            Response.ErrorListener { error ->
                Log.i("country", error.message)
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun getCities(province: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://www.avtargill.com/CityList.php?province="+province
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var cityData = JSONObject(response);
                var cities = cityData.getJSONArray("cities");
                var cityArray = arrayListOf<String>()
                cityArray.add("Please select a City")
                for (x in 0..(cities.length() - 1)) {
                    var cityDetail = cities.getJSONObject(x)
                    cityArray.add(cityDetail.getString("name"))
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cityArray)
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked)
                spinnerCity!!.setAdapter(adapter)
                spinnerCity!!.visibility = View.VISIBLE
                llProgressBar.visibility = View.GONE
            },
            Response.ErrorListener { error ->
                Log.i("country", error.message)
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}
