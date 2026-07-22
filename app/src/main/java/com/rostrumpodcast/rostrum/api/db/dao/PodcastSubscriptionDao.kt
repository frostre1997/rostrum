package app.rostrumpodcast.rostrum.api.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import app.rostrumpodcast.rostrum.api.db.model.podcastubscriptionBundle
import app.rostrumpodcast.rostrum.api.db.model.podcastubscriptionModel
import kotlinx.coroutines.flow.Flow

@Dao
interface podcastubscriptionDao {
    @Query("SELECT * FROM podcastubscription ORDER BY newEpisodes DESC")
    fun allByNewEpisodes(): PagingSource<Int, podcastubscriptionBundle>

    @Query("SELECT * FROM podcastubscription ORDER BY newEpisodes DESC LIMIT :limit OFFSET :offset")
    suspend fun getByNewEpisodes(limit: Int, offset: Int): List<podcastubscriptionBundle>

    @Query("SELECT * FROM podcastubscription WHERE origin=:origin")
    fun get(origin: String): Flow<podcastubscriptionModel?>

    @Query("SELECT * FROM podcastubscription WHERE origin=:origin")
    suspend fun getSync(origin: String): podcastubscriptionModel?

    @Query("SELECT * FROM podcastubscription ORDER BY lastUpdate ASC")
    suspend fun allSortedByLastUpdate(): List<podcastubscriptionBundle>

    @Query("SELECT origin FROM podcastubscription")
    suspend fun allOrigins(): List<String>

    @Query("SELECT SUM(p.fileSize) FROM podcastubscription s, podcast p WHERE p.origin = s.origin")
    suspend fun getEstimatedUpdateDataUsage(): Long?

    @Query("SELECT AVG(p.fileSize) FROM podcastubscription s, podcast p WHERE p.origin = s.origin")
    suspend fun getAverageDataUsage(): Long?

    @Query("UPDATE podcastubscription SET cacheLastModified=:lastModified, cacheETag=:eTag, cacheContentLength=:contentLength WHERE origin=:origin")
    suspend fun storeCacheValues(
        origin: String,
        lastModified: String,
        eTag: String,
        contentLength: String
    )

    @Query("UPDATE podcastubscription SET lastUpdate=:timestamp WHERE origin=:origin")
    suspend fun logUpdate(origin: String, timestamp: Long = System.currentTimeMillis())

    @Query("INSERT INTO podcastubscription (origin, enableNotifications, enableAutoDownload, lastUpdate, newEpisodes, cacheETag, cacheLastModified, cacheContentLength) VALUES (:origin, 0, 0, 0, 0, '', '', '')")
    suspend fun subscribe(origin: String)

    @Query("DELETE FROM podcastubscription WHERE origin=:origin")
    suspend fun unsubscribe(origin: String)

    @Query("UPDATE podcastubscription SET enableNotifications = 1 WHERE origin=:origin")
    suspend fun enableNotifications(origin: String)

    @Query("UPDATE podcastubscription SET enableNotifications = 0 WHERE origin=:origin")
    suspend fun disableNotifications(origin: String)

    @Query("UPDATE podcastubscription SET enableAutoDownload = 1 WHERE origin=:origin")
    suspend fun enableAutoDownload(origin: String)

    @Query("UPDATE podcastubscription SET enableAutoDownload = 0 WHERE origin=:origin")
    suspend fun disableAutoDownload(origin: String)
}