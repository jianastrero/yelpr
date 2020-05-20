package com.jianastrero.yelpr.fragment.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jianastrero.yelpr.extension.log

open class BaseFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        "${this::class} -> onActivityCreated".log()

        super.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        "${this::class} -> onAttach".log()

        super.onAttach(context)
    }

    override fun onAttachFragment(childFragment: Fragment) {
        "${this::class} -> onAttachFragment".log()

        super.onAttachFragment(childFragment)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        "${this::class} -> onConfigurationChanged".log()

        super.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        "${this::class} -> onCreateOptionsMenu".log()

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        "${this::class} -> onCreate".log()

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        "${this::class} -> onCreateView".log()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDetach() {
        "${this::class} -> onDetach".log()

        super.onDetach()
    }

    override fun onDestroy() {
        "${this::class} -> onDestroy".log()

        super.onDestroy()
    }

    override fun onDestroyView() {
        "${this::class} -> onDestroyView".log()

        super.onDestroyView()
    }

    override fun onLowMemory() {
        "${this::class} -> onLowMemory".log()

        super.onLowMemory()
    }

    override fun onPause() {
        "${this::class} -> onPause".log()

        super.onPause()
    }

    override fun onResume() {
        "${this::class} -> onResume".log()

        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        "${this::class} -> onSaveInstanceState".log()

        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        "${this::class} -> onStart".log()

        super.onStart()
    }

    override fun onStop() {
        "${this::class} -> onStop".log()

        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        "${this::class} -> onViewCreated".log()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        "${this::class} -> onViewStateRestored".log()

        super.onViewStateRestored(savedInstanceState)
    }
}