package com.jianastrero.yelpr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.databinding.FragmentBusinessBinding
import com.jianastrero.yelpr.databinding.FragmentSearchResultBinding
import com.jianastrero.yelpr.fragment.base.BaseFragment
import com.jianastrero.yelpr.viewmodel.MainViewModel
import com.jianastrero.yelpr.viewmodel.factory.YelprViewModelFactory

class BusinessFragment : BaseFragment() {

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
            } ?: ViewModelProvider(this, YelprViewModelFactory.getInstance())
                .get(MainViewModel::class.java)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.businessLiveData.postValue(null)
    }
}