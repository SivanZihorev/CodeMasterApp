package com.example.codemasterapp.models

data class YoutubeVideoResponse(
    val items: List<YoutubeVideoItem>
)

data class YoutubeVideoItem(
    val snippet: Snippet
)

data class Snippet(
    val title: String,
    val description: String
    )

