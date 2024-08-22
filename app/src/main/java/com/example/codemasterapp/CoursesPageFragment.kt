package com.example.codemasterapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton


class CoursesPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_courses, container, false)

        // Find buttons by ID
        val button1: ImageButton = view.findViewById(R.id.btn_1)
        val button2: ImageButton = view.findViewById(R.id.btn_2)
        val button3: ImageButton = view.findViewById(R.id.btn_3)
        val button4: ImageButton = view.findViewById(R.id.btn_4)
        val button5: ImageButton = view.findViewById(R.id.btn_5)
        val button6: ImageButton = view.findViewById(R.id.btn_6)

        // Set onClickListeners for buttons
        button1.setOnClickListener { openVideoPlayerFragment("oC_Fc2NtrnI") }
        button2.setOnClickListener { openVideoPlayerFragment("1FkA1DEs01E") }
        button3.setOnClickListener { openVideoPlayerFragment("rozMHGw_p5Y") }
        button4.setOnClickListener { openVideoPlayerFragment("quD8YaCih3s") }
        button5.setOnClickListener { openVideoPlayerFragment("nHLvOqcTbIY") }
        button6.setOnClickListener { openVideoPlayerFragment("ad79nYk2keg") }

        return view
    }

    private fun openVideoPlayerFragment(videoId: String) {
        val fragment = VideoPlayerFragment()
        val bundle = Bundle()
        bundle.putString("videoId", videoId)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .addToBackStack(null)
            .commit()
    }
}
