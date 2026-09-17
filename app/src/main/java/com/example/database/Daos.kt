package com.example.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfileEntity)
}

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks WHERE chapterId = :chapterId")
    fun getBookmarksForChapter(chapterId: String): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity): Long

    @Query("DELETE FROM bookmarks WHERE id = :id")
    suspend fun deleteBookmark(id: Long)

    @Query("DELETE FROM bookmarks WHERE chapterId = :chapterId AND title = :title")
    suspend fun deleteBookmarkByChapterAndTitle(chapterId: String, title: String)

    @Query("SELECT COUNT(*) > 0 FROM bookmarks WHERE chapterId = :chapterId AND title = :title")
    suspend fun isBookmarked(chapterId: String, title: String): Boolean
}

@Dao
interface ProgressDao {
    @Query("SELECT * FROM chapter_progress")
    fun getAllProgress(): Flow<List<ChapterProgressEntity>>

    @Query("SELECT * FROM chapter_progress WHERE chapterId = :chapterId")
    fun getProgressForChapter(chapterId: String): Flow<ChapterProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(progress: ChapterProgressEntity)

    @Query("SELECT COUNT(*) FROM chapter_progress WHERE isCompleted = 1")
    fun getCompletedCount(): Flow<Int>
}

@Dao
interface QuizDao {
    @Query("SELECT * FROM quiz_records ORDER BY timestamp DESC LIMIT 20")
    fun getRecentQuizRecords(): Flow<List<QuizRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizRecord(record: QuizRecordEntity): Long
}

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageEntity): Long

    @Query("DELETE FROM chat_messages")
    suspend fun clearMessages()
}
