package com.submission.mytax.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.submission.mytax.databinding.ItemPenjualanBinding
import com.submission.mytax.model.Penjualan

class PenjualanAdapter(
    private var penjualanList: MutableList<Penjualan>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<PenjualanAdapter.PenjualanViewHolder>() {

    inner class PenjualanViewHolder(private val binding: ItemPenjualanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val penjualan = penjualanList[position]
                    listener.onItemClick(penjualan)
                }
            }
            binding.ivDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position)
                }
            }
        }

        fun bind(penjualan: Penjualan) {
            binding.tvNama.text = penjualan.namaPembeli
            binding.tvTanggal.text = penjualan.tanggal
            binding.tvTotal.text = penjualan.total.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(penjualan: Penjualan)
        fun onDeleteClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenjualanViewHolder {
        val binding =
            ItemPenjualanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PenjualanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PenjualanViewHolder, position: Int) {
        holder.bind(penjualanList[position])
    }

    override fun getItemCount(): Int = penjualanList.size

    fun updateData(newPenjualanList: List<Penjualan>) {
        penjualanList.clear()
        penjualanList.addAll(newPenjualanList)
        notifyDataSetChanged()
    }
}
