package com.dherediat97.birdly.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dherediat97.birdly.databinding.FragmentRandomRecordedSoundsBinding
import com.dherediat97.birdly.ui.viewmodel.RecentBirdsViewModel
import com.dherediat97.birdly.ui.viewmodel.SoundRecordedViewModel
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomRecordedSoundsFragment : AppCompatActivity() {

    private var _binding: FragmentRandomRecordedSoundsBinding? = null
    private val recentBirdsViewModel: RecentBirdsViewModel by viewModel()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentRandomRecordedSoundsBinding.inflate(layoutInflater)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recentBirdsViewModel.uiState.collect { records ->
                    binding.recordedSoundsRV.adapter =
                        RecentRecordSoundAdapter(this@RandomRecordedSoundsFragment, records.records)
                    binding.recordedSoundsRV.layoutManager = CarouselLayoutManager()
                    val snapHelper = CarouselSnapHelper()
                    snapHelper.attachToRecyclerView(binding.recordedSoundsRV)
                }
            }
        }
        setContentView(binding.root)
    }
}