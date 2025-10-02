package com.example.myshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    private val cartItems = mutableListOf<CartItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadCartItems()
        updateTotalPrice()

        binding.btnCheckout.setOnClickListener {
            if (cartItems.isEmpty()) {
                Toast.makeText(requireContext(), "Keranjang belanja kosong", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Lanjut ke pembayaran", Toast.LENGTH_SHORT).show()
                // Navigasi ke halaman pembayaran jika ada
                // findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
            }
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(cartItems) { item ->
            removeItem(item)
        }

        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadCartItems() {
        // Data dummy untuk contoh
        // Ganti dengan data dari database atau repository
        cartItems.clear()
        cartItems.addAll(
            listOf(
                CartItem(1, "Laptop Gaming", 15000000, 1, "https://example.com/laptop.jpg"),
                CartItem(2, "Mouse Wireless", 250000, 2, "https://example.com/mouse.jpg"),
                CartItem(3, "Keyboard Mechanical", 750000, 1, "https://example.com/keyboard.jpg")
            )
        )
        cartAdapter.notifyDataSetChanged()
    }

    private fun removeItem(item: CartItem) {
        cartItems.remove(item)
        cartAdapter.notifyDataSetChanged()
        updateTotalPrice()
        Toast.makeText(requireContext(), "${item.name} dihapus dari keranjang", Toast.LENGTH_SHORT).show()
    }

    private fun updateTotalPrice() {
        val total = cartItems.sumOf { it.price * it.quantity }
        binding.txtProductPrice.text = formatPrice(total)
    }

    private fun formatPrice(price: Int): String {
        return "Rp ${String.format("%,d", price).replace(',', '.')}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Data class untuk item keranjang
data class CartItem(
    val id: Int,
    val name: String,
    val price: Int,
    var quantity: Int,
    val imageUrl: String
)