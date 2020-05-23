package com.jianastrero.yelpr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.adapter.SquareImageAdapter
import com.jianastrero.yelpr.databinding.FragmentBusinessBinding
import com.jianastrero.yelpr.dialog.showImage
import com.jianastrero.yelpr.fragment.base.BaseFragment
import com.jianastrero.yelpr.viewmodel.MainViewModel
import com.jianastrero.yelpr.viewmodel.factory.YelprViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BusinessFragment : BaseFragment() {

    private val args: BusinessFragmentArgs by navArgs()
    private val adapter = SquareImageAdapter().apply {
        setOnItemClickListener {
            showImage(childFragmentManager, currentList[it]) { }
        }
    }

    private lateinit var binding: FragmentBusinessBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::binding.isInitialized || binding.root != container) {
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_business,
                container,
                false
            )
        }

        viewModel =
            activity?.let {
                ViewModelProvider(it, YelprViewModelFactory.getInstance())
                    .get(MainViewModel::class.java)
            } ?: ViewModelProvider(viewModelStore, YelprViewModelFactory.getInstance())
                .get(MainViewModel::class.java)

        binding.status = 0
        binding.viewModel = viewModel

        binding.recyclerView.also {
            it.layoutManager = object : GridLayoutManager(requireContext(), 2) {
                override fun canScrollVertically(): Boolean = false
            }
            it.adapter = adapter
        }

        CoroutineScope(Dispatchers.IO).launch {
            val businessFull = viewModel.getBusinessFull(args.businessId)

            if (!isVisible) {
                return@launch
            }

            withContext(Dispatchers.Main) {
                viewModel.businessFull.postValue(businessFull)
                businessFull?.let {
                    adapter.submitList(it.photos)
                }
            }

            binding.status = if (businessFull == null) 1 else 2
            binding.viewModel = viewModel
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.businessFull.value = null
    }
}