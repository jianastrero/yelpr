package com.jianastrero.yelpr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.databinding.FragmentSearchResultBinding
import com.jianastrero.yelpr.fragment.base.BaseFragment

class SearchResultFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchResultBinding

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

        return binding.root
    }
}