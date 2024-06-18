package com.submission.mytax.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.submission.mytax.databinding.ItemPembelianBinding
import com.submission.mytax.model.Pembelian

class PembelianAdapter(
    private var pembelianList: MutableList<Pembelian>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<PembelianAdapter.PembelianViewHolder>() {

    inner class PembelianViewHolder(private val binding: ItemPembelianBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val pembelian = pembelianList[position]
                    listener.onItemClick(pembelian)
                }
            }
            binding.ivDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position)
                }
            }
        }

        fun bind(pembelian: Pembelian) {
            binding.tvNama.text = pembelian.namaPenjual
            binding.tvTanggal.text = pembelian.tanggal
            binding.tvTotal.text = pembelian.total.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(pembelian: Pembelian)
        fun onDeleteClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PembelianViewHolder {
        val binding =
            ItemPembelianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PembelianViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PembelianViewHolder, position: Int) {
        holder.bind(pembelianList[position])
    }

    override fun getItemCount(): Int = pembelianList.size

    fun updateData(newPembelianList: List<Pembelian>) {
        pembelianList.clear()
        pembelianList.addAll(newPembelianList)
        notifyDataSetChanged()
    }
}
