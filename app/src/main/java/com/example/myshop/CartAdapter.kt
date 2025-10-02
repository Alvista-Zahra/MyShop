package com.example.myshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemCartPlaceholderBinding

class CartAdapter(
    private val items: MutableList<CartItem>,
    private val onDeleteClick: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartPlaceholderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            with(binding) {
                txtProductName.text = item.name
                txtProductPrice.text = formatPrice(item.price)
                txtQuantity.text = item.quantity.toString()
                txtTotalPrice.text = formatPrice(item.price * item.quantity)

                btnDelete.setOnClickListener { onDeleteClick(item) }

                btnIncrease.setOnClickListener {
                    item.quantity++
                    notifyItemChanged(adapterPosition)
                }

                btnDecrease.setOnClickListener {
                    if (item.quantity > 1) {
                        item.quantity--
                        notifyItemChanged(adapterPosition)
                    }
                }
            }
        }

        private fun formatPrice(price: Int): String {
            return "Rp ${String.format("%,d", price).replace(',', '.')}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartPlaceholderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
