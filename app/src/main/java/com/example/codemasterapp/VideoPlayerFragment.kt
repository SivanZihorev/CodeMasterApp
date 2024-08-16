package com.example.codemasterapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import com.example.codemasterapp.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class VideoPlayerFragment : Fragment() {
    private lateinit var resultTextView: TextView
    private lateinit var youtubeWebView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_video_player, container, false)

        resultTextView = view.findViewById(R.id.result_text_view)
        youtubeWebView = view.findViewById(R.id.youtube_webview)

        fetchVideoDetails()

        return view
    }

    private fun fetchVideoDetails() {
        val apiKey = "AIzaSyADXOd1UVu2nDBvrTRZl1h3EUl-j4x_3W8"
        val videoId = "XV7ow8VUSxU"

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitInstance.api.getVideoDetails("snippet", videoId, apiKey)
                if (response.isSuccessful) {
                    val videoItem = response.body()?.items?.firstOrNull()
                    val title = videoItem?.snippet?.title
                    resultTextView.text = title ?: "No title found"

                    // Display the video
                    val videoUrl = "https://www.youtube.com/watch?v=$videoId"
                    youtubeWebView.settings.javaScriptEnabled = true
                    youtubeWebView.loadUrl("https://www.youtube.com/embed/$videoId")
                    youtubeWebView.visibility = View.VISIBLE
                } else {
                    resultTextView.text = "API call failed. Status code: ${response.code()}"
                }
            } catch (e: Exception) {
                resultTextView.text = "API call failed. Exception: ${e.message}"
            }
        }
    }
}

