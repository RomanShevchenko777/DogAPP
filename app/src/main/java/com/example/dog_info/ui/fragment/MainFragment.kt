package com.example.dog_info.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dog_info.databinding.FragmentMainBinding
import com.example.dog_info.ui.BreedListAdapter
import com.example.dog_info.ui.viewmodel.HomeViewModel

class MainFragment : Fragment(), BreedListAdapter.ItemAction {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: BreedListAdapter

    private val homeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private val recyclerViewScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val visibleItemCount = recyclerView.layoutManager?.childCount ?: 0
            val pastVisibleItem =
                (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            val total = adapter.itemCount

            if (!homeViewModel.isLoading() && (visibleItemCount + pastVisibleItem) >= total) {
                homeViewModel.increasePage()
                homeViewModel.getPage()
            }

            super.onScrolled(recyclerView, dx, dy)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(layoutInflater).apply {
            viewModel = homeViewModel
            rvBreed.apply {
                addItemDecoration(
                    DividerItemDecoration(requireActivity(), RecyclerView.VERTICAL)
                )
                addOnScrollListener(recyclerViewScrollListener)
            }
        }

        homeViewModel.apply {
            subscribeToHomeViewModel(this)
            getBreeds()
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        homeViewModel.resetPage()
    }

    override fun onItemListener(dogEntity: String) {
        homeViewModel.navigateToImages(dogEntity)
    }

    private fun subscribeToHomeViewModel(homeViewModel: HomeViewModel) {
        homeViewModel.apply {
            loadBreedsCommand.observe(viewLifecycleOwner) {
                createAdapter(it)
            }
            loadRangeBreedsCommand.observe(viewLifecycleOwner) {
                adapter.addItems(it)
            }
            loadErrorCommand.observe(viewLifecycleOwner) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
            goToImagesCommand.observe(viewLifecycleOwner) {
                val action = MainFragmentDirections.actionMainFragmentToBreedImageFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    private fun createAdapter(listAdapter: List<String>) {
        adapter = BreedListAdapter(this@MainFragment, listAdapter.toMutableList())
        binding.rvBreed.adapter = adapter
    }

}
