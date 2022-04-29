package com.example.mobsoftapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobsoftapp.adapters.ProductListAdapter
import com.example.mobsoftapp.databinding.FragmentProductListBinding
import com.example.mobsoftapp.model.Product
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: FragmentProductListBinding? = null
    private var productList: List<Product> = listOf()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        productList= mainViewModel.getProductList()

        _binding = FragmentProductListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var productListAdapter = ProductListAdapter(productList)
        var recyclerView = _binding?.productRecycleView
        if (recyclerView != null) {
            recyclerView.adapter = productListAdapter
        }
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(view.context)
        }

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}