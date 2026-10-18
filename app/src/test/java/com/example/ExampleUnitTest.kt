package com.example

import com.example.data.model.AudioTrack
import com.example.data.model.Episode
import com.example.data.model.VideoQuality
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testEpisodeDurationFormatting() {
        val episode = Episode(
            id = "ep1",
            animeId = "anime_1",
            episodeNumber = 1,
            title = "Awakening",
            titleHindi = "उदय",
            durationSeconds = 1445, // 24m 5s
            synopsis = "Pilot episode"
        )
        assertEquals("24:05", episode.durationFormatted)
    }

    @Test
    fun testAudioTrackValues() {
        val hindi = AudioTrack.HINDI
        assertEquals("Hindi Dubbed", hindi.displayName)
        assertEquals("हिन्दी डब", hindi.nativeName)
        assertEquals("HI", hindi.tag)
    }

    @Test
    fun testVideoQuality() {
        val fhd = VideoQuality.Q1080P
        assertEquals("1080p FHD", fhd.label)
        assertEquals("1920x1080", fhd.resolution)
    }
}
