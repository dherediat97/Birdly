package com.dherediat97.birdly.ui.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dherediat97.birdly.R
import com.dherediat97.birdly.databinding.FragmentMapBinding
import com.dherediat97.birdly.ui.viewmodel.NearBirdsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

/**
 * An Fragment that shows points that represent near bird sounds of user location in a map
 */
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val nearBirdsViewModel: NearBirdsViewModel by activityViewModel()

    private lateinit var mMap: MapView
    private lateinit var controller: IMapController

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        nearBirdsViewModel.fetchBirdSound(178261)
        Configuration.getInstance().load(
            context,
            context?.getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
        mMap = _binding!!.mapView
        controller = mMap.controller
        mMap.isVerticalMapRepetitionEnabled = false
        mMap.isHorizontalMapRepetitionEnabled = false
        controller.animateTo(GeoPoint(37.8915500, -4.7727500))
        controller.zoomTo(14, 1600)
        mMap.setMultiTouchControls(true)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                nearBirdsViewModel.uiState.collect {
                    val soundRecorded = Marker(mMap)
                    soundRecorded.position = GeoPoint(it.record.lat, it.record.lng)
                    soundRecorded.title =
                        "${it.record.en}\nEncontrado en: ${it.record.loc} por ${it.record.rec}"
                    if (it.record.id != 0)
                        mMap.overlays.add(soundRecorded)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}