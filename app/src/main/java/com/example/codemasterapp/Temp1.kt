package com.example.codemasterapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.codemasterapp.api.RetrofitInstance
import com.example.codemasterapp.api.YoutubeApi
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.chromium.base.Callback
import org.chromium.base.Log
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Temp1 : Fragment() {

    private lateinit var resultTextView: TextView
    private lateinit var fetchButton: Button
    private lateinit var youtubeWebView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_temp1, container, false)

        resultTextView = view.findViewById(R.id.result_text_view)
        fetchButton = view.findViewById(R.id.fetch_button)
        youtubeWebView = view.findViewById(R.id.youtube_webview)

        fetchButton.setOnClickListener {
            fetchVideoDetails()
        }

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






