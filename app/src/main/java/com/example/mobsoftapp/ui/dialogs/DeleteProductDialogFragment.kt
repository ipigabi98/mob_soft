package com.example.mobsoftapp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobsoftapp.R
import com.example.mobsoftapp.databinding.DeleteProductDialogFragmentBinding
import com.example.mobsoftapp.ui.details.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteProductDialogFragment(private val productId: Long) : DialogFragment() {

    private val detailViewModel: DetailViewModel by viewModels()
    private var _binding: DeleteProductDialogFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DeleteProductDialogFragmentBinding.inflate(inflater, container, false)

        binding.deleteItemButton.setOnClickListener {
            Toast.makeText(activity?.applicationContext, "Item successfully deleted", Toast.LENGTH_SHORT).show()
            var result = detailViewModel.deleteProduct(productId)
            findNavController().navigate(R.id.action_ProductDetailFragment_to_ProductListFragment)
            dismiss()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}