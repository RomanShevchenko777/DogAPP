package com.example.dog_info.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dog_info.GlideApp
import com.example.dog_info.R
import com.example.dog_info.databinding.FragmentBreedImageBinding
import com.example.dog_info.ui.ImageAdapter
import com.example.dog_info.ui.MainActivity
import com.example.dog_info.ui.viewmodel.ImageViewModel

class ImageFragment : Fragment(), ImageAdapter.ItemAction {

    private lateinit var binding: FragmentBreedImageBinding
    private val args: ImageFragmentArgs by navArgs()
    private lateinit var adapter: ImageAdapter

    private val imageViewModel by lazy {
        ViewModelProvider(this)[ImageViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBreedImageBinding.inflate(layoutInflater)

        val breedExtra = args.dog

        imageViewModel.apply {
            subscribeToImageViewModel(this)
            getImageLinks(breedExtra)
        }

        binding.rvImage.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        return binding.root
    }

    private fun subscribeToImageViewModel(imageViewModel: ImageViewModel) {
        imageViewModel.apply {
            loadImageLinks.observe(viewLifecycleOwner) {
                it?.let { it1 -> loadFirstImage(it1[0]) }
                createAdapter(it)
            }
            loadErrorCommand.observe(viewLifecycleOwner) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createAdapter(listAdapter: List<String>?) {
        adapter = ImageAdapter(listAdapter, this@ImageFragment)
        binding.rvImage.adapter = adapter
    }

    override fun onItemListener(imageLink: String) {
        loadFirstImage(imageLink)
    }

    private fun loadFirstImage(imageLink: String) {
        with(binding) {
            GlideApp.with(baseImageView.context)
                .load(imageLink)
                .placeholder(R.drawable.android)
                .into(baseImageView)
        }
    }
}