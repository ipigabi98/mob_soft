package com.example.mobsoftapp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mobsoftapp.databinding.UpdateProductDialogFragmentBinding;
import com.example.mobsoftapp.model.Product
import com.example.mobsoftapp.model.Rating
import com.example.mobsoftapp.ui.details.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProductDialogFragment(private var product: Product, private var callbackListener: MyCallbackListener) : DialogFragment() {

    private val detailViewModel: DetailViewModel by viewModels()
    private var _binding: UpdateProductDialogFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = UpdateProductDialogFragmentBinding.inflate(inflater, container, false)

        binding.updateTitle.setText(product.title)
        binding.updatePrice.setText(product.price.toString())
        binding.updateDescription.setText(product.description)
        binding.updateCategory.setText(product.category)
        binding.updateImage.setText(product.image)
        binding.updateRate.setText(product.rating.rate.toString())
        binding.updateCount.setText(product.rating.count.toString())

        binding.updateButton.setOnClickListener {
            var title = binding.updateTitle.text.toString()
            var price = binding.updatePrice.text.toString().toDouble()
            var category = binding.updateCategory.text.toString()
            var description = binding.updateDescription.text.toString()
            var image = binding.updateImage.text.toString()
            var rate = binding.updateRate.text.toString().toDouble()
            var count = binding.updateCount.text.toString().toInt()

            var productToUpdate = Product(product.id, title, price, description, category, image, rating = Rating(rate, count))

            var result = detailViewModel.updateProduct(productToUpdate)
            dismiss()
            callbackListener.onDismiss()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}