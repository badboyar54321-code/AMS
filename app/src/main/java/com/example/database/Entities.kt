package com.example.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val email: String,
    val grade: String,
    val board: String,
    val stream: String,
    val streakDays: Int,
    val lastActiveDate: String
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val type: String, // NOTE, FORMULA, QUESTION, CONCEPT
    val subjectId: String,
    val chapterId: String,
    val snippet: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "chapter_progress")
data class ChapterProgressEntity(
    @PrimaryKey val chapterId: String,
    val subjectId: String,
    val currentStep: Int, // 1 to 7
    val isCompleted: Boolean,
    val bestQuizScore: Int,
    val totalQuizQuestions: Int,
    val lastStudiedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_records")
data class QuizRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val chapterId: String,
    val subjectId: String,
    val score: Int,
    val total: Int,
    val percentage: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val role: String, // "user" or "assistant"
    val content: String,
    val subjectContext: String,
    val timestamp: Long = System.currentTimeMillis()
)
