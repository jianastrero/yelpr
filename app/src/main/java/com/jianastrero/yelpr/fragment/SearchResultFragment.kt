package com.jianastrero.yelpr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.adapter.BusinessAdapter
import com.jianastrero.yelpr.databinding.FragmentSearchResultBinding
import com.jianastrero.yelpr.extension.log
import com.jianastrero.yelpr.fragment.base.BaseFragment
import com.jianastrero.yelpr.viewmodel.MainViewModel
import com.jianastrero.yelpr.viewmodel.factory.YelprViewModelFactory

class SearchResultFragment : BaseFragment() {

    private val adapter = BusinessAdapter()
    private val layoutManager: GridLayoutManager by lazy {
        GridLayoutManager(requireContext(), 1)
    }
    private val toggleCallback =
        object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                layoutManager.spanCount = if (viewModel.isListView.get()) 1 else 2
                adapter.isListView = viewModel.isListView.get()
            }
        }


    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::binding.isInitialized || binding.root != container) {
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_search_result,
                container,
                false
            )
        }

        binding.status = 0

        viewModel =
            activity?.let {
                ViewModelProvider(it, YelprViewModelFactory.getInstance())
                    .get(MainViewModel::class.java)
            } ?: ViewModelProvider(this, YelprViewModelFactory.getInstance())
                .get(MainViewModel::class.java)

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.adapter = adapter
            it.clipToOutline = true
        }

        viewModel.searchResultLiveData.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {
                    adapter.submitList(it.businesses)
                    binding.status = if (it.businesses.isEmpty()) 1 else 2
                }
            }
        )

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.isListView.addOnPropertyChangedCallback(toggleCallback)
    }

    override fun onPause() {
        super.onPause()

        viewModel.isListView.removeOnPropertyChangedCallback(toggleCallback)
    }
}