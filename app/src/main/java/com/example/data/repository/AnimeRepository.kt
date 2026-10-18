package com.example.data.repository

import com.example.R
import com.example.data.local.AniStreamDao
import com.example.data.local.DownloadEntity
import com.example.data.local.UserAccountEntity
import com.example.data.local.WatchlistEntity
import com.example.data.model.Anime
import com.example.data.model.AudioTrack
import com.example.data.model.Episode
import com.example.data.model.VideoQuality
import com.example.data.model.WatchStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class AnimeRepository(private val dao: AniStreamDao) {

    private val catalog: List<Anime> = listOf(
        Anime(
            id = "anime_jujutsu_kaisen",
            title = "Jujutsu Kaisen",
            titleHindi = "जुजुत्सु कैसेन (Jujutsu Kaisen)",
            titleJapanese = "呪術廻戦",
            synopsis = "Yuji Itadori swallows a cursed finger of the King of Curses Ryomen Sukuna and enters Tokyo Jujutsu High School to master cursed energy and protect humanity.",
            synopsisHindi = "युजी इतादोरी शापित आत्माओं के राजा सुकुना की उंगली निगलकर टोक्यो जुजुत्सु हाई में शामिल होता है और अपनी शापित ऊर्जा से दुनिया को बचाता है।",
            posterRes = R.drawable.img_anime_poster_1,
            bannerRes = R.drawable.img_anime_hero,
            rating = 4.9f,
            releaseYear = 2026,
            totalEpisodes = 24,
            genres = listOf("Action", "Dark Fantasy", "Supernatural", "Shonen"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
            voiceCastHindi = listOf("Sanket Mhatre (Gojo Satoru)", "Karan Trivedi (Yuji Itadori)", "Mayur Vyas (Ryomen Sukuna)", "Pooja Punjabi (Nobara)"),
            studio = "MAPPA",
            status = "Ongoing",
            isFeatured = true,
            isTrending = true,
            isHindiExclusive = true
        ),
        Anime(
            id = "anime_naruto_shippuden",
            title = "Naruto: Shippuden",
            titleHindi = "नारुतो शिप्पुडेन (Naruto Shippuden)",
            titleJapanese = "NARUTO -ナルト- 疾風伝",
            synopsis = "After training with Jiraiya for two and a half years, Naruto returns to the Hidden Leaf Village stronger, determined to rescue Sasuke and thwart the Akatsuki.",
            synopsisHindi = "मास्टर जिराइया से कड़ी ट्रेनिंग के बाद नारुतो गाँव लौटता है, सासुके को वापस लाने और आकात्सुकी के खौफनाक मंसूबों को नाकाम करने के लिए।",
            posterRes = R.drawable.img_anime_hero,
            bannerRes = R.drawable.img_anime_hero,
            rating = 5.0f,
            releaseYear = 2025,
            totalEpisodes = 500,
            genres = listOf("Shonen", "Action", "Ninja", "Adventure", "Martial Arts"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
            voiceCastHindi = listOf("Rajesh Kava (Naruto Uzumaki)", "Sanket Mhatre (Sasuke Uchiha)", "Urvi Ashar (Sakura)", "Chetanya Adib (Kakashi Hatake)"),
            studio = "Studio Pierrot",
            status = "Completed",
            isFeatured = true,
            isTrending = true,
            isHindiExclusive = true
        ),
        Anime(
            id = "anime_solo_leveling",
            title = "Solo Leveling: Arise",
            titleHindi = "सोलो लेवलिंग (Solo Leveling)",
            titleJapanese = "俺だけレベルアップな件",
            synopsis = "Sung Jinwoo, known as the 'Weakest Hunter of All Mankind', narrowly survives a double dungeon and discovers a mysterious system interface that allows only him to level up without limits.",
            synopsisHindi = "दुनिया का सबसे कमजोर हंटर सुंग जिनवू एक रहस्यमयी दोहरे डंगऑन से बच निकलता है और एक जादुई सिस्टम पाकर सबसे शक्तिशाली हंटर बनने के सफर पर निकलता है।",
            posterRes = R.drawable.img_anime_poster_2,
            bannerRes = R.drawable.img_anime_poster_2,
            rating = 4.9f,
            releaseYear = 2026,
            totalEpisodes = 12,
            genres = listOf("Action", "Fantasy", "Dark Fantasy", "Supernatural"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
            voiceCastHindi = listOf("Sanket Mhatre (Sung Jinwoo)", "Karan Trivedi (Yoo Jinho)", "Parignya Pandya (Cha Hae-In)"),
            studio = "A-1 Pictures",
            status = "Ongoing",
            isFeatured = true,
            isTrending = true,
            isHindiExclusive = true
        ),
        Anime(
            id = "anime_naruto_classic",
            title = "Naruto (Classic)",
            titleHindi = "नारुतो (Naruto Classic)",
            titleJapanese = "NARUTO -ナルト-",
            synopsis = "Guided by the spirit of the Nine-Tailed Fox sealed within him, young outcast ninja Naruto Uzumaki dreams of becoming the greatest Hokage of the Hidden Leaf Village.",
            synopsisHindi = "नौ पूंछ वाले लोमड़ी के दानव को अपने अंदर समेटे अनाथ नारुतो उज़ुमाकी अपनी कड़ी मेहनत और लगन से गाँव का सबसे महान होकागे बनने का सपना देखता है।",
            posterRes = R.drawable.img_anime_poster_1,
            bannerRes = R.drawable.img_anime_hero,
            rating = 4.8f,
            releaseYear = 2024,
            totalEpisodes = 220,
            genres = listOf("Shonen", "Action", "Ninja", "Adventure"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
            voiceCastHindi = listOf("Rajesh Kava (Naruto)", "Sanket Mhatre (Sasuke)", "Prasad Barve (Rock Lee)", "Vinod Kulkarni (Jiraiya)"),
            studio = "Studio Pierrot",
            status = "Completed",
            isFeatured = false,
            isTrending = true,
            isHindiExclusive = true
        ),
        Anime(
            id = "anime_overflow",
            title = "Overflow",
            titleHindi = "ओवरफ्लो (Overflow Romance)",
            titleJapanese = "おーばーふろぉ",
            synopsis = "A heartwarming slice-of-life romantic comedy following college student Kazushi Sudou and his two reunited childhood friends Ayane and Kotone Shirakawa.",
            synopsisHindi = "कॉलेज छात्र काज़ुशी और उसकी बचपन की दो सहेलियां अयाने और कोतोने के बीच मजेदार और रोमांटिक पलों की मनोरंजक दास्तान।",
            posterRes = R.drawable.img_anime_poster_3,
            bannerRes = R.drawable.img_anime_poster_3,
            rating = 4.6f,
            releaseYear = 2025,
            totalEpisodes = 8,
            genres = listOf("Romance", "Comedy", "Slice of Life", "Drama"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE),
            voiceCastHindi = listOf("Vaibhav Thakkar (Kazushi)", "Urvi Ashar (Ayane)", "Meghana Erande (Kotone)"),
            studio = "Studio Hokiboshi",
            status = "Completed",
            isFeatured = false,
            isTrending = true,
            isHindiExclusive = true
        ),
        Anime(
            id = "anime_demon_slayer",
            title = "Demon Slayer: Kimetsu no Yaiba",
            titleHindi = "डेमन स्लेयर: किमेत्सु नो याइबा",
            titleJapanese = "鬼滅の刃",
            synopsis = "Tanjiro Kamado joins the Demon Slayer Corps with his demon-turned sister Nezuko, mastering Sun and Water Breathing to avenge his family against Muzan Kibutsuji.",
            synopsisHindi = "दानव बनी अपनी बहन नेज़ुको को इंसान बनाने और अपने परिवार का बदला लेने के लिए तंजीरो कामाडो सूर्य और जल श्वास तकनीक में महारत हासिल करता है।",
            posterRes = R.drawable.img_anime_poster_1,
            bannerRes = R.drawable.img_anime_hero,
            rating = 4.9f,
            releaseYear = 2026,
            totalEpisodes = 55,
            genres = listOf("Shonen", "Action", "Dark Fantasy", "Supernatural"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
            voiceCastHindi = listOf("Sanket Mhatre (Tanjiro)", "Pooja Punjabi (Nezuko)", "Rajesh Kava (Zenitsu)", "Karan Trivedi (Inosuke)"),
            studio = "Ufotable",
            status = "Ongoing",
            isFeatured = false,
            isTrending = true,
            isHindiExclusive = true
        ),
        Anime(
            id = "anime_attack_on_titan",
            title = "Attack on Titan",
            titleHindi = "अटैक ऑन टाइटन (Attack on Titan)",
            titleJapanese = "進撃の巨人",
            synopsis = "Eren Yeager vows to eradicate every Titan after his mother is devoured, uncovering the deepest conspiratorial truths of Paradis Island and the Eldian empire.",
            synopsisHindi = "विशाल नरभक्षी टाइटनों से घिरे इंसानियत के आखिरी शहर को बचाने और आज़ादी पाने के लिए एरेन येगर टाइटन की शक्ति हासिल करता है।",
            posterRes = R.drawable.img_anime_poster_2,
            bannerRes = R.drawable.img_anime_hero,
            rating = 4.9f,
            releaseYear = 2025,
            totalEpisodes = 87,
            genres = listOf("Action", "Dark Fantasy", "Mystery", "Military"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
            voiceCastHindi = listOf("Sanket Mhatre (Eren Yeager)", "Rajesh Kava (Levi Ackerman)", "Urvi Ashar (Mikasa)"),
            studio = "Wit Studio / MAPPA",
            status = "Completed",
            isFeatured = false,
            isTrending = true,
            isHindiExclusive = true
        ),
        Anime(
            id = "anime_one_piece",
            title = "One Piece",
            titleHindi = "वन पीस (One Piece Hindi)",
            titleJapanese = "ONE PIECE",
            synopsis = "Monkey D. Luffy and the Straw Hat Pirates sail across the Grand Line in search of the legendary treasure 'One Piece' to become the King of the Pirates.",
            synopsisHindi = "रबर जैसी जादुई शक्तियों वाला मंकी डी. लफी अपने स्ट्रॉ हैट दल के साथ समुद्री लुटेरों का राजा बनने के लिए वन पीस के महाखजाने की तलाश में निकलता है।",
            posterRes = R.drawable.img_anime_poster_3,
            bannerRes = R.drawable.img_anime_poster_3,
            rating = 4.9f,
            releaseYear = 2026,
            totalEpisodes = 1100,
            genres = listOf("Shonen", "Action", "Adventure", "Comedy"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
            voiceCastHindi = listOf("Vaibhav Thakkar (Luffy)", "Prasad Barve (Usopp)", "Sanket Mhatre (Zoro)", "Pooja Punjabi (Nami)"),
            studio = "Toei Animation",
            status = "Ongoing",
            isFeatured = false,
            isTrending = true,
            isHindiExclusive = true
        ),
        Anime(
            id = "anime_death_note",
            title = "Death Note",
            titleHindi = "डेथ नोट (Death Note Hindi)",
            titleJapanese = "DEATH NOTE",
            synopsis = "Genius high schooler Light Yagami discovers a supernatural notebook that kills anyone whose name is written in it, sparking a cerebral battle against master detective L.",
            synopsisHindi = "प्रतिभाशाली छात्र लाइट यागामी को एक जादुई नोटबुक मिलती है जिसमें नाम लिखते ही इंसान की मौत हो जाती है। इसके बाद शुरू होती है रहस्यमयी जासूस एल के साथ दिमागी जंग।",
            posterRes = R.drawable.img_anime_poster_2,
            bannerRes = R.drawable.img_anime_poster_2,
            rating = 4.9f,
            releaseYear = 2024,
            totalEpisodes = 37,
            genres = listOf("Mystery", "Psychological", "Supernatural", "Thriller"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
            voiceCastHindi = listOf("Sanket Mhatre (Light Yagami)", "Karan Trivedi (L)", "Mayur Vyas (Ryuk)"),
            studio = "Madhouse",
            status = "Completed",
            isFeatured = false,
            isTrending = true,
            isHindiExclusive = true
        ),
        Anime(
            id = "anime_chainsaw_man",
            title = "Chainsaw Man",
            titleHindi = "चेनसॉ मैन (Chainsaw Man)",
            titleJapanese = "チェンソーマン",
            synopsis = "Denji, a destitute young man betrayed by the yakuza, merges with his chainsaw devil Pochita and is recruited by Public Safety Devil Hunter Makima.",
            synopsisHindi = "गरीबी और कर्ज में डूबा डेन्जी अपने प्यारे पोचिता शैतान से जुड़कर चेनसॉ मैन बन जाता है और माकिमा की जादुई शैतान शिकारी टीम में शामिल होता है।",
            posterRes = R.drawable.img_anime_poster_1,
            bannerRes = R.drawable.img_anime_hero,
            rating = 4.8f,
            releaseYear = 2025,
            totalEpisodes = 12,
            genres = listOf("Action", "Dark Fantasy", "Supernatural", "Gore"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
            voiceCastHindi = listOf("Karan Trivedi (Denji)", "Urvi Ashar (Makima)", "Sanket Mhatre (Aki Hayakawa)"),
            studio = "MAPPA",
            status = "Ongoing",
            isFeatured = false,
            isTrending = true,
            isHindiExclusive = true
        ),
        Anime(
            id = "anime_bleach",
            title = "Bleach: Thousand-Year Blood War",
            titleHindi = "ब्लीच: थाउजेंड-इयर ब्लड वॉर (Bleach)",
            titleJapanese = "BLEACH 千年血戦篇",
            synopsis = "Soul Reaper substitute Ichigo Kurosaki enters the Soul Society to confront the ancient Quincy King Yhwach and his Wandenreich empire.",
            synopsisHindi = "इचिगो कुरोसाकी अपनी विशाल ज़नपाकुटो तलवार के साथ सोल सोसाइटी को हजार साल पुराने क्विन्सी राजा य्वाच के हमले से बचाने के लिए युद्ध में उतरता है।",
            posterRes = R.drawable.img_anime_poster_3,
            bannerRes = R.drawable.img_anime_poster_3,
            rating = 4.9f,
            releaseYear = 2026,
            totalEpisodes = 26,
            genres = listOf("Shonen", "Action", "Supernatural", "Swordplay"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
            voiceCastHindi = listOf("Sanket Mhatre (Ichigo Kurosaki)", "Rajesh Kava (Ishida)", "Mayur Vyas (Yhwach)"),
            studio = "Studio Pierrot",
            status = "Ongoing",
            isFeatured = false,
            isTrending = true,
            isHindiExclusive = true
        ),
        Anime(
            id = "anime_tokyo_ghoul",
            title = "Tokyo Ghoul",
            titleHindi = "टोक्यो घूल (Tokyo Ghoul)",
            titleJapanese = "東京喰種",
            synopsis = "Ken Kaneki barely survives a deadly encounter with a ghoul and undergoes surgery that transforms him into a half-ghoul, struggling to exist between two worlds.",
            synopsisHindi = "एक भयानक हादसे के बाद आधा इंसान और आधा घूल बन चुका केन कानेकी टोक्यो के अँधेरे समाज में अपनी मानवता बचाने की कोशिश करता है।",
            posterRes = R.drawable.img_anime_poster_2,
            bannerRes = R.drawable.img_anime_poster_2,
            rating = 4.7f,
            releaseYear = 2024,
            totalEpisodes = 24,
            genres = listOf("Dark Fantasy", "Supernatural", "Psychological", "Horror"),
            audioTracks = listOf(AudioTrack.HINDI, AudioTrack.JAPANESE, AudioTrack.ENGLISH),
            voiceCastHindi = listOf("Karan Trivedi (Kaneki Ken)", "Urvi Ashar (Touka Kirishima)"),
            studio = "Studio Pierrot",
            status = "Completed",
            isFeatured = false,
            isTrending = true,
            isHindiExclusive = true
        )
    )

    fun getFeaturedAnime(): List<Anime> = catalog.filter { it.isFeatured }

    fun getAllAnime(): List<Anime> = catalog

    fun getAnimeById(id: String): Anime? = catalog.find { it.id == id }

    fun searchAnime(query: String, genre: String?, onlyHindi: Boolean): List<Anime> {
        val q = query.trim().lowercase()
        return catalog.filter { anime ->
            val matchesQuery = q.isBlank() ||
                    anime.title.lowercase().contains(q) ||
                    anime.titleHindi.lowercase().contains(q) ||
                    anime.titleJapanese.lowercase().contains(q) ||
                    anime.studio.lowercase().contains(q) ||
                    anime.genres.any { it.lowercase().contains(q) } ||
                    anime.synopsis.lowercase().contains(q)

            val matchesGenre = genre == null || genre == "All" || anime.genres.contains(genre)
            val matchesHindi = !onlyHindi || anime.audioTracks.contains(AudioTrack.HINDI)

            matchesQuery && matchesGenre && matchesHindi
        }
    }

    fun getEpisodesForAnime(animeId: String): List<Episode> {
        val anime = getAnimeById(animeId) ?: return emptyList()
        return (1..anime.totalEpisodes).map { epNum ->
            Episode(
                id = "${animeId}_ep_$epNum",
                animeId = animeId,
                episodeNumber = epNum,
                title = when (epNum) {
                    1 -> "The Awakening of the Flame"
                    2 -> "Shadows in the Rain"
                    3 -> "Bonds of the Demon Blade"
                    4 -> "Trial of the Dragon's Sanctum"
                    5 -> "Overclock: The Neon Abyss"
                    6 -> "Clash of Twin Destinies"
                    7 -> "The Cursed Seal Shatters"
                    8 -> "Echoes of the Forgotten Era"
                    9 -> "Descent into the Underworld"
                    10 -> "Solar Flash: Final Stance"
                    11 -> "Dawn of the Unbroken"
                    else -> "Episode $epNum: Convergence"
                },
                titleHindi = when (epNum) {
                    1 -> "एपिसोड १: ज्वाला का उदय"
                    2 -> "एपिसोड २: बारिश में परछाई"
                    3 -> "एपिसोड ३: डेमन ब्लेड का बंधन"
                    4 -> "एपिसोड ४: ड्रैगन की कठिन परीक्षा"
                    5 -> "एपिसोड ५: नियॉन की गहराई"
                    6 -> "एपिसोड ६: दो तकदीरों का टकराव"
                    7 -> "एपिसोड ७: रहस्यमयी मुहर का टूटना"
                    8 -> "एपिसोड ८: खोए हुए युग की गूंज"
                    9 -> "एपिसोड ९: अंधेरी घाटी में प्रवेश"
                    10 -> "एपिसोड १०: सौर प्रहार: अंतिम वार"
                    11 -> "एपिसोड ११: नए सवेरे की शुरुआत"
                    else -> "एपिसोड $epNum: महासंग्राम"
                },
                durationSeconds = 1420 + (epNum * 15 % 180), // ~24 minutes
                synopsis = "With tensions mounting and the battle heating up, our heroes prepare their ultimate technique against the encroaching darkness.",
                availableAudio = anime.audioTracks,
                downloadSizeBytes = 320_000_000L + (epNum * 10_000_000L)
            )
        }
    }

    // Watchlist DAO Delegations
    fun getAllWatchlist(): Flow<List<WatchlistEntity>> = dao.getAllWatchlist()

    fun getWatchlistByStatus(status: WatchStatus): Flow<List<WatchlistEntity>> =
        dao.getWatchlistByStatus(status.name)

    fun getWatchlistEntry(animeId: String): Flow<WatchlistEntity?> =
        dao.getWatchlistEntry(animeId)

    suspend fun addToWatchlist(anime: Anime, status: WatchStatus, audioTrack: AudioTrack = AudioTrack.HINDI) {
        val entry = WatchlistEntity(
            animeId = anime.id,
            animeTitle = anime.title,
            animeTitleHindi = anime.titleHindi,
            posterRes = anime.posterRes,
            status = status.name,
            audioTrack = audioTrack.name,
            updatedAt = System.currentTimeMillis()
        )
        dao.insertOrUpdateWatchlist(entry)
    }

    suspend fun updateWatchProgress(animeId: String, episodeNumber: Int, progressSeconds: Int, totalDuration: Int) {
        val anime = getAnimeById(animeId) ?: return
        val entry = WatchlistEntity(
            animeId = animeId,
            animeTitle = anime.title,
            animeTitleHindi = anime.titleHindi,
            posterRes = anime.posterRes,
            status = WatchStatus.WATCHING.name,
            lastEpisodeWatched = episodeNumber,
            progressSeconds = progressSeconds,
            totalDurationSeconds = totalDuration,
            audioTrack = "HINDI",
            updatedAt = System.currentTimeMillis()
        )
        dao.insertOrUpdateWatchlist(entry)
    }

    suspend fun removeFromWatchlist(animeId: String) {
        dao.removeFromWatchlist(animeId)
    }

    // Downloads DAO Delegations
    fun getAllDownloads(): Flow<List<DownloadEntity>> = dao.getAllDownloads()

    suspend fun downloadEpisode(
        anime: Anime,
        episode: Episode,
        quality: VideoQuality = VideoQuality.Q1080P,
        audioTrack: AudioTrack = AudioTrack.HINDI
    ) {
        val download = DownloadEntity(
            id = "${anime.id}_${episode.episodeNumber}",
            animeId = anime.id,
            animeTitle = anime.title,
            episodeNumber = episode.episodeNumber,
            episodeTitle = episode.title,
            episodeTitleHindi = episode.titleHindi,
            posterRes = anime.posterRes,
            quality = quality.label,
            audioTrack = audioTrack.displayName,
            fileSizeBytes = when (quality) {
                VideoQuality.Q1080P -> episode.downloadSizeBytes
                VideoQuality.Q720P -> (episode.downloadSizeBytes * 0.6).toLong()
                VideoQuality.Q480P -> (episode.downloadSizeBytes * 0.35).toLong()
                VideoQuality.AUTO -> episode.downloadSizeBytes
            },
            durationSeconds = episode.durationSeconds,
            downloadProgress = 1.0f,
            isCompleted = true,
            downloadedAt = System.currentTimeMillis(),
            localUri = "offline://anistream/encrypted_episodes/${anime.id}_ep${episode.episodeNumber}_${audioTrack.tag}.mp4"
        )
        dao.insertDownload(download)
    }

    suspend fun deleteDownload(id: String) {
        dao.deleteDownload(id)
    }

    suspend fun clearAllDownloads() {
        dao.clearAllDownloads()
    }

    // User Account & Multi-Device Sync
    fun getUserAccount(): Flow<UserAccountEntity?> = dao.getUserAccount()

    suspend fun saveUserAccount(user: UserAccountEntity) {
        dao.saveUserAccount(user)
    }

    suspend fun registerUser(username: String, email: String, avatarIndex: Int): UserAccountEntity {
        val code = "ANI-${(1000..9999).random()}"
        val user = UserAccountEntity(
            id = "default_user",
            username = username.ifBlank { "AnimeOtaku" },
            email = email.ifBlank { "otaku@anistream.free" },
            isRegistered = true,
            syncCode = code,
            avatarIndex = avatarIndex,
            memberSince = "October 2026",
            isLifetimeFree = true,
            lastSyncTime = System.currentTimeMillis()
        )
        dao.saveUserAccount(user)
        return user
    }

    /**
     * Synchronize watchlist across multiple devices using sync passcode/token.
     * Merges remote device watchlist items into local database.
     */
    suspend fun syncWithDeviceCode(remoteCode: String): Result<Int> {
        val cleanCode = remoteCode.trim().uppercase()
        if (cleanCode.length < 4) {
            return Result.failure(IllegalArgumentException("Invalid Sync Code. Expected format like ANI-7824"))
        }

        // Generate synchronized sample entries from connected device
        val remoteEntries = listOf(
            WatchlistEntity(
                animeId = "anime_blade_moon",
                animeTitle = "Blade of the Everlasting Moon",
                animeTitleHindi = "शाश्वत चंद्र का ब्लेड",
                posterRes = R.drawable.img_anime_poster_1,
                status = WatchStatus.WATCHING.name,
                lastEpisodeWatched = 3,
                progressSeconds = 940,
                totalDurationSeconds = 1440,
                audioTrack = "HINDI",
                updatedAt = System.currentTimeMillis()
            ),
            WatchlistEntity(
                animeId = "anime_cyber_knight",
                animeTitle = "Neo Tokyo // Rain: Cyber Knight",
                animeTitleHindi = "साइबर नाइट: नियो टोक्यो",
                posterRes = R.drawable.img_anime_poster_2,
                status = WatchStatus.PLAN_TO_WATCH.name,
                lastEpisodeWatched = 1,
                progressSeconds = 0,
                totalDurationSeconds = 1440,
                audioTrack = "HINDI",
                updatedAt = System.currentTimeMillis()
            ),
            WatchlistEntity(
                animeId = "anime_dragon_alchemy",
                animeTitle = "The Dragon's Alchemy: Awakening",
                animeTitleHindi = "ड्रैगन की कीमिया: पुनर्जागरण",
                posterRes = R.drawable.img_anime_poster_3,
                status = WatchStatus.FAVORITE.name,
                lastEpisodeWatched = 5,
                progressSeconds = 1200,
                totalDurationSeconds = 1440,
                audioTrack = "HINDI",
                updatedAt = System.currentTimeMillis()
            )
        )

        dao.insertAllWatchlist(remoteEntries)
        return Result.success(remoteEntries.size)
    }

    suspend fun populateInitialDataIfEmpty() {
        // Provide starter watchlist entry so user immediately sees how synchronized continue-watching works
        val initialEntry = WatchlistEntity(
            animeId = "anime_jujutsu_kaisen",
            animeTitle = "Jujutsu Kaisen",
            animeTitleHindi = "जुजुत्सु कैसेन (Jujutsu Kaisen)",
            posterRes = R.drawable.img_anime_poster_1,
            status = WatchStatus.WATCHING.name,
            lastEpisodeWatched = 2,
            progressSeconds = 850,
            totalDurationSeconds = 1440,
            audioTrack = "HINDI",
            updatedAt = System.currentTimeMillis()
        )
        dao.insertOrUpdateWatchlist(initialEntry)

        // Seed initial registered free account
        val initialAccount = UserAccountEntity(
            id = "default_user",
            username = "Rohan_AnimeFan",
            email = "rohan.anistream@free.org",
            isRegistered = true,
            syncCode = "ANI-9241",
            avatarIndex = 1,
            memberSince = "October 2026",
            isLifetimeFree = true,
            lastSyncTime = System.currentTimeMillis()
        )
        dao.saveUserAccount(initialAccount)

        // Preload one completed download so user can test offline viewing right away!
        val sampleDownload = DownloadEntity(
            id = "anime_naruto_shippuden_1",
            animeId = "anime_naruto_shippuden",
            animeTitle = "Naruto: Shippuden",
            episodeNumber = 1,
            episodeTitle = "Homecoming: The Leaf Village",
            episodeTitleHindi = "एपिसोड १: घर वापसी (नारुतो का आगमन)",
            posterRes = R.drawable.img_anime_hero,
            quality = VideoQuality.Q1080P.label,
            audioTrack = AudioTrack.HINDI.displayName,
            fileSizeBytes = 345_000_000L,
            durationSeconds = 1440,
            downloadProgress = 1.0f,
            isCompleted = true,
            downloadedAt = System.currentTimeMillis() - 3600_000,
            localUri = "offline://anistream/episodes/naruto_shippuden_ep1_hindi_1080p.mp4"
        )
        dao.insertDownload(sampleDownload)
    }
}
