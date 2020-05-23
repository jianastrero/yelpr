package com.jianastrero.yelpr.activity

import android.Manifest
import android.animation.ValueAnimator
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.REQUEST_PERMISSION_LOCATION
import com.jianastrero.yelpr.databinding.ActivityMainBinding
import com.jianastrero.yelpr.dialog.askConfirmation
import com.jianastrero.yelpr.extension.dp
import com.jianastrero.yelpr.extension.into
import com.jianastrero.yelpr.extension.log
import com.jianastrero.yelpr.viewmodel.MainViewModel
import com.jianastrero.yelpr.viewmodel.factory.YelprViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var defaultConstraintSet: ConstraintSet
    private lateinit var detailConstraintSet: ConstraintSet
    private lateinit var currentConstraintSet: ConstraintSet

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private var lastBackClick = 0L
    private var backdropTopMargin = 0
    private var ghostStartTime = 0L
    private var isManualExpanded = false
    private var startScrollY = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this, YelprViewModelFactory.getInstance())
            .get(MainViewModel::class.java)

        binding.viewModel = viewModel

        defaultConstraintSet = ConstraintSet()
        defaultConstraintSet.clone(binding.constraintLayout)

        detailConstraintSet = ConstraintSet()
        detailConstraintSet.clone(this, R.layout.activity_main_detail_view)

        currentConstraintSet = defaultConstraintSet

        viewModel.businessFull.observe(
            this,
            Observer {
                if (it != null) {
                    animateToDetailView()
                    it.imageUrl?.into(binding.ivImage)
                } else {
                    animateToDefaultView()
                }
            }
        )

        binding.navHostHolder.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            "scrollY: $scrollY".log()
            if (startScrollY == -1) {
                startScrollY = scrollY
            }
            if (scrollY == 0 && viewModel.isDetailViewExpanded.value == true) {
                binding.navHostHolder.scrollable = false
                isManualExpanded = true
            }
        }

        binding.navHostHolder.setOnGhostStartListener { _, direction ->
            "setOnGhostStartListener".log()
            if (
                viewModel.isDetailViewExpanded.value == true &&
                binding.navHostHolder.scrollY == 0 &&
                direction < 0
            ) {
                binding.navHostHolder.scrollable = false
                isManualExpanded = true
            }

            if (!isManualExpanded && viewModel.isDetailViewExpanded.value == true) {
                binding.navHostHolder.scrollable = true
            }
            ghostStartTime = System.currentTimeMillis()
        }

        binding.navHostHolder.setOnGhostDragListener { startY, moveY ->
            val lp = binding.navHostHolder.layoutParams as ConstraintLayout.LayoutParams

            if (backdropTopMargin == 0) {
                binding.navHostHolder.scrollable = moveY - startY < 0
            }

            if (!binding.navHostHolder.scrollable) {

                lp.topMargin = backdropTopMargin + (moveY - startY).toInt()

                if (lp.topMargin > 400.dp) {
                    lp.topMargin = 400.dp.toInt()
                } else if (lp.topMargin < 60.dp) {
                    lp.topMargin = 60.dp.toInt() - startScrollY
                } else {
                    if (lp.topMargin > 280.dp) {
                        binding.ivImage.scaleX = 1f + (1f - 280.dp / lp.topMargin) * 2f
                        binding.ivImage.scaleY = 1f + (1f - 280.dp / lp.topMargin) * 2f
                        binding.ivImage.translationY = (lp.topMargin - 320.dp).coerceAtLeast(0f)
                    }
                }

                if (binding.ivImage.scaleX < 1f || binding.ivImage.scaleY < 1f) {
                    binding.ivImage.scaleX = 1f
                    binding.ivImage.scaleY = 1f
                }

                binding.navHostHolder.layoutParams = lp
            }
        }

        binding.navHostHolder.setOnGhostReleaseListener {
            val lp = binding.navHostHolder.layoutParams as ConstraintLayout.LayoutParams
            val isManualExpanded: Boolean
            val end =
                if (System.currentTimeMillis() - ghostStartTime < 200) {
                    if (it == 1) {
                        isManualExpanded = true
                        280.dp.toInt()
                    } else {
                        isManualExpanded = false
                        60.dp.toInt()
                    }
                } else {
                    if (lp.topMargin > (280 + 60).dp / 2) {
                        isManualExpanded = true
                        280.dp.toInt()
                    } else {
                        isManualExpanded = false
                        60.dp.toInt()
                    }
                }
            val anim = ValueAnimator.ofInt(lp.topMargin, end)
            anim.addUpdateListener {
                lp.topMargin = it.animatedValue as Int
                binding.navHostHolder.layoutParams = lp

                if (it.animatedFraction == 1f) {
                    backdropTopMargin = lp.topMargin
                    binding.navHostHolder.isEnabled = true
                    this.isManualExpanded = isManualExpanded
                }
            }

            anim.start()
            ValueAnimator.ofFloat(binding.ivImage.scaleX, 1f)
                .also {
                    it.addUpdateListener {
                        binding.ivImage.scaleX = (it.animatedValue as Float)
                        binding.ivImage.scaleY = (it.animatedValue as Float)
                    }
                }
                .start()
            ValueAnimator.ofFloat(binding.ivImage.translationY, 0f)
                .also {
                    it.addUpdateListener {
                        binding.ivImage.translationY = (it.animatedValue as Float)
                    }
                }
                .start()
            binding.navHostHolder.isEnabled = false
            startScrollY = -1
        }

        binding.setOnBackClickedListener {
            if (System.currentTimeMillis() - lastBackClick > 1000) {
                onBackPressed()
                lastBackClick = System.currentTimeMillis()
            }
        }

        if (
            locationPermissions.all {
                checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
            }
        ) {
            getLatestLocation()
        } else {
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLatestLocation()
            } else {
                requestPermission()
            }
        }
    }

    private fun requestPermission() {
        CoroutineScope(Dispatchers.Default).launch {
            val confirmed = askConfirmation(supportFragmentManager) {
                setTitle("Location Permission")
                setMessage("Yelpr needs your permission to allow getting last location and location updates. Pressing cancel will close the app.")
                setPositiveText("Ok")
                setNegativeText("Cancel")
            }

            "Confirmed: $confirmed".log()

            if (confirmed) {
                withContext(Dispatchers.Main) {
                    requestPermissions(locationPermissions, REQUEST_PERMISSION_LOCATION)
                }
            } else {
                withContext(Dispatchers.Main) {
                    finish()
                }
            }
        }
    }

    private fun getLatestLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                if (it != null) {
                    viewModel.latitude = it.latitude
                    viewModel.longitude = it.longitude

                    viewModel.search()
                }
            }
    }

    private fun animateToDetailView() {
        if (currentConstraintSet == defaultConstraintSet) {
            TransitionManager.beginDelayedTransition(binding.constraintLayout)
            currentConstraintSet = detailConstraintSet
            detailConstraintSet.applyTo(binding.constraintLayout)
            binding.viewModel = viewModel
            binding.navHostHolder.scrollable = false
            viewModel.isDetailViewExpanded.postValue(true)
            isManualExpanded = true
            backdropTopMargin =
                (binding.navHostHolder.layoutParams as ConstraintLayout.LayoutParams).topMargin
        }
    }

    private fun animateToDefaultView() {
        if (currentConstraintSet == detailConstraintSet) {
            TransitionManager.beginDelayedTransition(binding.constraintLayout)
            currentConstraintSet = defaultConstraintSet
            defaultConstraintSet.applyTo(binding.constraintLayout)
            binding.viewModel = viewModel
            binding.navHostHolder.scrollable = true
            viewModel.isDetailViewExpanded.postValue(false)
            backdropTopMargin =
                (binding.navHostHolder.layoutParams as ConstraintLayout.LayoutParams).topMargin
        }
    }
}
