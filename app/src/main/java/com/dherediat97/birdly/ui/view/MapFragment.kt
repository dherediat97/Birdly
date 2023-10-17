package com.dherediat97.birdly.ui.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dherediat97.birdly.R
import com.dherediat97.birdly.databinding.FragmentMapBinding
import com.dherediat97.birdly.ui.viewmodel.NearBirdsViewModel
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

/**
 * An Fragment that shows points that represent near bird sounds of user location in a map
 */
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val nearBirdsViewModel: NearBirdsViewModel by viewModels()

    private lateinit var mMap: MapView
    private lateinit var controller: IMapController
    private lateinit var mMyLocationOverlay: MyLocationNewOverlay

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
        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mMap)
        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.runOnFirstFix {
            controller.setCenter(mMyLocationOverlay.myLocation)
        }
        controller.animateTo(GeoPoint( 37.8915500, -4.7727500))
        controller.zoomTo(14,1600)
        mMap.overlays.add(mMyLocationOverlay)
        mMap.setMultiTouchControls(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}