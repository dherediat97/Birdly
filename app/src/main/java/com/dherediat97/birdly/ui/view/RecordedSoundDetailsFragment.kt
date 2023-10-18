package com.dherediat97.birdly.ui.view

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dherediat97.birdly.databinding.FragmentSoundRecordedBinding
import com.dherediat97.birdly.domain.model.RecordedSound
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RecordedSoundDetailsFragment(private val soundRecorded: RecordedSound) : BottomSheetDialogFragment() {

    private var _binding: FragmentSoundRecordedBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSoundRecordedBinding.inflate(inflater, container, false)
        binding.birdNameTV.text = soundRecorded.en
//        Glide
//            .with(this)
//            .load(soundRecorded.url)
//            .centerCrop()
//            .into(binding.birdSpecieIV)
        binding.btnPlayRecordedSoundFAB.setOnClickListener {
            val player = MediaPlayer.create(
                context, Uri.parse("https://xeno-canto.org/${soundRecorded.id}/download")
            )
            player.start()
        }
        return binding.root
    }

    companion object {
        const val TAG = "RecordedSoundDetailsFragment"
    }
}