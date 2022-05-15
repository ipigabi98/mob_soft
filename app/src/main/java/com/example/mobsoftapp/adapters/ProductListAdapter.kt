package com.example.mobsoftapp.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobsoftapp.R
import com.example.mobsoftapp.model.Product
import java.util.concurrent.Executors

class ProductListAdapter(private var productList: List<Product>, private var navController: NavController) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.product_title)
        val imageView: ImageView = view.findViewById(R.id.product_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_row_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        var product = productList[position]

        holder.titleTextView.text = product.title
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("itemId" to product.id)
            navController.navigate(R.id.action_ProductListFragment_to_ProductDetailFragment, bundle)
        }
        getImageBitMap(product.image, holder)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    private fun getImageBitMap(imageUrl: String, holder: ProductViewHolder) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            val inputStream = java.net.URL(imageUrl).openStream()
            val image = BitmapFactory.decodeStream(inputStream)

            handler.post {
                holder.imageView.setImageBitmap(image)
            }
        }
    }
}