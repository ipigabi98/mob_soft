package com.example.mobsoftapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobsoftapp.adapters.ProductListAdapter
import com.example.mobsoftapp.databinding.FragmentProductListBinding
import com.example.mobsoftapp.model.Product
import com.example.mobsoftapp.ui.dialogs.CreateProductDialogFragment
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

        binding.fab.setOnClickListener { view ->
            /*nackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            activity?.let { showCreateProductDialogFragment(it) }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var productListAdapter = ProductListAdapter(productList, findNavController())
        var recyclerView = _binding?.productRecycleView
        if (recyclerView != null) {
            recyclerView.adapter = productListAdapter
        }
        if (recyclerView != null) {
            recyclerView.layoutManager = GridLayoutManager(view.context, 2)
        }

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    private fun showCreateProductDialogFragment(activity: FragmentActivity) {
        var createProductDialogFragment = CreateProductDialogFragment()
        createProductDialogFragment.show(activity.supportFragmentManager, "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}