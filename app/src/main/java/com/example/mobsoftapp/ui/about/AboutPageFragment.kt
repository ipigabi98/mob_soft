package com.example.mobsoftapp.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobsoftapp.R
import com.example.mobsoftapp.databinding.AboutPageBinding
import com.example.mobsoftapp.databinding.FragmentProductDetailBinding

class AboutPageFragment : Fragment() {

    private var _binding: AboutPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AboutPageBinding.inflate(inflater, container, false)

        binding.logo.setImageDrawable(resources.getDrawable(R.drawable.bme_logo))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}