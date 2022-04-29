package com.example.mobsoftapp.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobsoftapp.R
import com.example.mobsoftapp.model.Product
import java.util.concurrent.Executors

class ProductListAdapter(private var productList: List<Product>) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

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