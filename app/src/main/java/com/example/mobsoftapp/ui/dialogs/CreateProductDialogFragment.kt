package com.example.mobsoftapp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mobsoftapp.databinding.CreateProductDialogFragmentBinding
import com.example.mobsoftapp.model.Product
import com.example.mobsoftapp.model.Rating
import com.example.mobsoftapp.ui.details.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProductDialogFragment(private var callbackListener: MyCallbackListener) : DialogFragment() {

    private val detailViewModel: DetailViewModel by viewModels()
    private var _binding: CreateProductDialogFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*if (activity is SaveProductCallbackListener) {
            callbackListener = activity as SaveProductCallbackListener
        }*/
        setStyle(STYLE_NORMAL, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = CreateProductDialogFragmentBinding.inflate(inflater, container, false)

        binding.saveButton.setOnClickListener {
            var title = binding.saveTitle.text.toString()
            var price = binding.savePrice.text.toString().toDouble()
            var category = binding.saveCategory.text.toString()
            var description = binding.saveDescription.text.toString()
            var image = binding.saveImage.text.toString()
            var rate = binding.saveRate.text.toString().toDouble()
            var count = binding.saveCount.text.toString().toInt()

            var newProduct = Product(null, title, price, description, category, image, rating = Rating(rate, count))

            var result = detailViewModel.saveProduct(newProduct)
            dismiss()
            callbackListener.onDismiss()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}