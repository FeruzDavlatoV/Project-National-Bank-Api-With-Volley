package com.example.projectsecondwithvolley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.projectsecondwithvolley.adapter.CurrencyAdapter
import com.example.projectsecondwithvolley.databinding.ActivityMainBinding
import com.example.projectsecondwithvolley.model.Currency
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var requestQueue: RequestQueue

    private var url ="https://cbu.uz/uz/arkhiv-kursov-valyut/json/"
    private var TAG = "MainActivity"

    private lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)
        VolleyLog.DEBUG = true

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,url, null, object : Response.Listener<JSONArray>{
            override fun onResponse(response: JSONArray?) {
                val type =object: TypeToken<List<Currency>>(){}.type

                val list: List<Currency> = Gson().fromJson(response.toString(), type)
                Log.d("@@@@", list.toString())

                currencyAdapter = CurrencyAdapter(list)
                binding.rvMain.adapter = currencyAdapter
                val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                binding.rvMain.layoutManager = linearLayoutManager
                currencyAdapter.onClick = {
                    showBottomSheet(it)
                }
            }
        },
        object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                Log.d("@@@@@",error.toString())
            }

        })

        requestQueue.add(jsonArrayRequest)
    }
    private fun showBottomSheet(currency: Currency) {
        val view: View = layoutInflater.inflate(R.layout.currency_selection_bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val ccyName: TextView = view.findViewById(R.id.tvCcyNm_EN)
        val tvRate: TextView = view.findViewById(R.id.tvRate)
        val tvDiff: TextView = view.findViewById(R.id.tvDiff)
        val tvDate: TextView = view.findViewById(R.id.tvDate)

        ccyName.text = currency.CcyNm_UZ
        tvRate.text = currency.Rate
        tvDiff.text = currency.Diff
        tvDate.text = currency.Date
        dialog.show()
    }
}