package com.example.mobsoftapp.ui.details

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobsoftapp.R
import com.example.mobsoftapp.databinding.FragmentProductDetailBinding
import com.example.mobsoftapp.model.Product
import com.example.mobsoftapp.ui.dialogs.DeleteProductDialogFragment
import com.example.mobsoftapp.ui.dialogs.MyCallbackListener
import com.example.mobsoftapp.ui.dialogs.UpdateProductDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class ProductDetailFragment : Fragment(), MyCallbackListener {

    private val detailViewModel: DetailViewModel by viewModels()
    private var product: Product? = null
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val productId = arguments?.getLong("itemId")!!
        product = detailViewModel.loadProduct(productId)
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        setBindings()

        binding.fabDelete.setOnClickListener {
            activity?.let { showDeleteDialog(it) }
        }

        binding.fabUpdate.setOnClickListener {
            activity?.let { it1 -> showUpdateDialog(it1) }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setBindings() {
        _binding!!.detailTitle.text = product?.title
        _binding!!.detailDescription.text = product?.description
        _binding!!.detailCategory.text = "Category: " + product?.category
        _binding!!.detailPrice.text = "$" + product?.price.toString()
        _binding!!.detailRating.numStars = 5
        _binding!!.detailRatingRate.text = product!!.rating.rate.toString()
        _binding!!.detailRatingCount.text = "(" + product!!.rating.count.toString() + ")"
        _binding!!.detailRating.rating = product!!.rating.rate.toFloat()
        product?.image?.let { loadImage(it) }
    }

    private fun showDeleteDialog(activity: FragmentActivity) {
        var deleteProductDialogFragment = product?.id?.let { DeleteProductDialogFragment(it) }
        if (deleteProductDialogFragment != null) {
            deleteProductDialogFragment.show(activity.supportFragmentManager, "")
        }
    }

    private fun showUpdateDialog(activity: FragmentActivity) {
        var updateProductDialogFragment = product?.let { UpdateProductDialogFragment(it, this) }
        updateProductDialogFragment!!.show(activity.supportFragmentManager, "")
    }

    private fun loadImage(imageUrl: String) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            try {
                val inputStream = java.net.URL(imageUrl).openStream()
                val image = BitmapFactory.decodeStream(inputStream)

                handler.post {
                    _binding!!.detailImage.setImageBitmap(image)
                }
            } catch (e: Exception) {

            }
        }
    }

    override fun onDismiss() {
        val productId = arguments?.getLong("itemId")!!
        product = detailViewModel.loadProduct(productId)
        setBindings()
    }
}