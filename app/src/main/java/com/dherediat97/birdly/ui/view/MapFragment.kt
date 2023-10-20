package com.dherediat97.birdly.ui.view

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.location.GpsStatus
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dherediat97.birdly.R
import com.dherediat97.birdly.databinding.FragmentMapBinding
import com.dherediat97.birdly.ui.viewmodel.SoundRecordedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


/**
 * An Fragment that shows points that represent near bird sounds of user location in a map
 */
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val soundRecordedViewModel: SoundRecordedViewModel by activityViewModel()

    private lateinit var mMap: MapView
    private lateinit var controller: IMapController

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        Configuration.getInstance().load(
            context,
            context?.getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
        mMap = _binding!!.mapView
        controller = mMap.controller
        mMap.isVerticalMapRepetitionEnabled = false
        mMap.isHorizontalMapRepetitionEnabled = false
        mMap.setExpectedCenter(GeoPoint(37.8915500, -4.7727500))
        controller.animateTo(GeoPoint(37.8915500, -4.7727500))
        controller.zoomTo(14, 1600)
        mMap.setMultiTouchControls(true)
        binding.searchBirdFAB.setOnClickListener {
            startActivity(Intent(requireContext(), RandomRecordedSoundsFragment::class.java))
        }
        val mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mMap)
        mLocationOverlay.enableMyLocation()
        mMap.overlays.add(mLocationOverlay)
        val locationManager =
            requireContext().getSystemService(LOCATION_SERVICE) as LocationManager?

        if (checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                locationManager!!.isLocationEnabled
            } else {
                TODO("VERSION.SDK_INT < P")
            }


            mLocationOverlay.runOnFirstFix {
                soundRecordedViewModel.fetchNearRecords(
                    mLocationOverlay.myLocation.latitude.toString(),
                    mLocationOverlay.myLocation.longitude.toString()
                )
            }
        }
        soundRecordedViewModel.fetchNearRecords(
            mMap.mapCenter.latitude.toString(),
            mMap.mapCenter.longitude.toString()
        )
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                soundRecordedViewModel.uiStateRecords.collect { soundRecorders ->
                    if (soundRecorders.recordings.isNotEmpty()) {
                        val recordings = soundRecorders.recordings
                        recordings.forEach { soundRecorded ->
                            val soundRecordedMarker = Marker(mMap)
                            soundRecordedMarker.icon = AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.ic_recorded_sound
                            )
                            soundRecordedMarker.position =
                                GeoPoint(soundRecorded.lat, soundRecorded.lng)
                            soundRecordedMarker.setOnMarkerClickListener { _, _ ->
                                val recordedSoundDetailsFragment =
                                    RecordedSoundDetailsFragment(soundRecorded)
                                recordedSoundDetailsFragment.show(
                                    parentFragmentManager,
                                    RecordedSoundDetailsFragment.TAG
                                )
                                return@setOnMarkerClickListener true
                            }
                            mMap.overlays.add(soundRecordedMarker)
                            if (soundRecorded == recordings.last())
                                mMap.invalidate()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}