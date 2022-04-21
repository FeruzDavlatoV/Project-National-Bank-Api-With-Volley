package com.example.projectsecondwithvolley.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsecondwithvolley.databinding.ItemCurrencyViewBinding
import com.example.projectsecondwithvolley.model.Currency

class CurrencyAdapter(var list: List<Currency>): RecyclerView.Adapter<CurrencyAdapter.Vh>() {

    lateinit var onClick: (Currency) -> Unit

    inner class Vh(var itemCurrencyViewBinding: ItemCurrencyViewBinding):RecyclerView.ViewHolder(itemCurrencyViewBinding.root)
    {
        fun onBind(currency: Currency){
            itemCurrencyViewBinding.tvCurrency.text = currency.Ccy
            itemCurrencyViewBinding.linearLayout.setOnClickListener {
                onClick.invoke(currency)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCurrencyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int  = list.size
}