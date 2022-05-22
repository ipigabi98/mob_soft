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
import com.example.mobsoftapp.adapters.ProductListAdapter
import com.example.mobsoftapp.databinding.FragmentProductListBinding
import com.example.mobsoftapp.model.Product
import com.example.mobsoftapp.ui.dialogs.CreateProductDialogFragment
import com.example.mobsoftapp.ui.dialogs.MyCallbackListener
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ProductListFragment : Fragment(), MyCallbackListener {

    private val mainViewModel: MainViewModel by viewModels()
    private var productListAdapter: ProductListAdapter? = null
    private var _binding: FragmentProductListBinding? = null
    private var productList: List<Product> = listOf()
    private val binding get() = _binding!!

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = activity?.let { FirebaseAnalytics.getInstance(it) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        productList= mainViewModel.getProductList()

        _binding = FragmentProductListBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener { view ->

            //throw RuntimeException("Test Crash")

            activity?.let { showCreateProductDialogFragment(it) }
//            var bundle = Bundle()
//            bundle.putString(FirebaseAnalytics.Param.METHOD, "on create item clicked")
//            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle)
//
//            val bundle1 = Bundle()
//            bundle.putString(FirebaseAnalytics.Param.METHOD, "login")
//            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle1)

            /*
            adb shell setprop log.tag.FA VERBOSE
            adb shell setprop log.tag.FA-SVC VERBOSE
            adb logcat -v time -s FA FA-SVC

            adb shell setprop log.tag.FirebaseCrashlytics DEBUG
            adb logcat -s FirebaseCrashlytics
            */
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productListAdapter = ProductListAdapter(productList, findNavController())
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
        var createProductDialogFragment = CreateProductDialogFragment(this)
        createProductDialogFragment.show(activity.supportFragmentManager, "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDismiss() {
        productList = mainViewModel.getProductList()
        productListAdapter!!.update(productList)
    }
}