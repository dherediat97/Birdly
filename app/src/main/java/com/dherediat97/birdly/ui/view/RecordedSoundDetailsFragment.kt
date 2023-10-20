package com.dherediat97.birdly.ui.view

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dherediat97.birdly.BirdlyApp
import com.dherediat97.birdly.databinding.FragmentSoundRecordedBinding
import com.dherediat97.birdly.domain.model.RecordedSound
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RecordedSoundDetailsFragment(private val soundRecorded: RecordedSound) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentSoundRecordedBinding? = null

    private val binding get() = _binding!!
    private lateinit var player: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSoundRecordedBinding.inflate(inflater, container, false)
        binding.birdNameTV.text = "${soundRecorded.en} - ${soundRecorded.gen}"
        binding.birdRecorderNameTV.text = soundRecorded.rec
        player = MediaPlayer.create(
            context, Uri.parse("${BirdlyApp.baseURLSound}${soundRecorded.id}/download")
        )
        binding.btnPlayRecordedSoundFAB.setOnClickListener {
            player.start()
        }
        return binding.root
    }

    override fun onDestroyView() {
        player.stop()
        super.onDestroyView()
    }


    companion object {
        const val TAG = "RecordedSoundDetailsFragment"
    }
}