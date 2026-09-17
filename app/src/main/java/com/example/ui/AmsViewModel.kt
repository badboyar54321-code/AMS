package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.GeminiService
import com.example.database.AppDatabase
import com.example.database.BookmarkEntity
import com.example.database.ChapterProgressEntity
import com.example.database.ChatMessageEntity
import com.example.database.QuizRecordEntity
import com.example.database.UserProfileEntity
import com.example.model.Board
import com.example.model.GradeClass
import com.example.model.Stream
import com.example.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AmsViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val userDao = db.userDao()
    private val bookmarkDao = db.bookmarkDao()
    private val progressDao = db.progressDao()
    private val quizDao = db.quizDao()
    private val chatDao = db.chatDao()

    // Active Grade & Board selection state
    private val _selectedGrade = MutableStateFlow(GradeClass.CLASS_10)
    val selectedGrade: StateFlow<GradeClass> = _selectedGrade.asStateFlow()

    private val _selectedBoard = MutableStateFlow(Board.CBSE)
    val selectedBoard: StateFlow<Board> = _selectedBoard.asStateFlow()

    private val _selectedStream = MutableStateFlow(Stream.SCIENCE)
    val selectedStream: StateFlow<Stream> = _selectedStream.asStateFlow()

    // Dark Mode preference (default false = light academic mode)
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    // AI generating state
    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    // Search query state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // User Profile from DB or default
    val userProfile: StateFlow<UserProfile> = userDao.getUserProfile()
        .map { entity ->
            if (entity != null) {
                UserProfile(
                    name = entity.name,
                    email = entity.email,
                    grade = GradeClass.entries.find { it.name == entity.grade } ?: GradeClass.CLASS_10,
                    board = Board.entries.find { it.name == entity.board } ?: Board.CBSE,
                    stream = Stream.entries.find { it.name == entity.stream } ?: Stream.SCIENCE,
                    streakDays = entity.streakDays
                )
            } else {
                UserProfile()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserProfile()
        )

    // Bookmarks Flow
    val bookmarks: StateFlow<List<BookmarkEntity>> = bookmarkDao.getAllBookmarks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Chapter Progress Flow
    val allProgress: StateFlow<List<ChapterProgressEntity>> = progressDao.getAllProgress()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Completed Chapters Count
    val completedChaptersCount: StateFlow<Int> = progressDao.getCompletedCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // Recent Quizzes
    val recentQuizzes: StateFlow<List<QuizRecordEntity>> = quizDao.getRecentQuizRecords()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Chat Messages
    val chatMessages: StateFlow<List<ChatMessageEntity>> = chatDao.getAllMessages()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        // Pre-populate initial profile if empty
        viewModelScope.launch {
            val defaultEntity = UserProfileEntity(
                id = 1,
                name = "Aarav Sharma",
                email = "aarav.study@example.com",
                grade = GradeClass.CLASS_10.name,
                board = Board.CBSE.name,
                stream = Stream.SCIENCE.name,
                streakDays = 5,
                lastActiveDate = "Today"
            )
            userDao.saveUserProfile(defaultEntity)

            // Seed a welcome message for AMS AI
            val welcomeMsg = ChatMessageEntity(
                role = "assistant",
                content = "👋 Hello! I am **AMS AI**, your dedicated academic study tutor for Class 10 & 12.\n\nAsk me any concept, equation, textbook problem, or exam revision question. What would you like to master today?",
                subjectContext = "General"
            )
            chatDao.insertMessage(welcomeMsg)
        }
    }

    fun setGrade(grade: GradeClass) {
        _selectedGrade.value = grade
        viewModelScope.launch {
            val current = userProfile.value
            userDao.saveUserProfile(
                UserProfileEntity(
                    id = 1,
                    name = current.name,
                    email = current.email,
                    grade = grade.name,
                    board = current.board.name,
                    stream = current.stream.name,
                    streakDays = current.streakDays,
                    lastActiveDate = "Today"
                )
            )
        }
    }

    fun setBoard(board: Board) {
        _selectedBoard.value = board
        viewModelScope.launch {
            val current = userProfile.value
            userDao.saveUserProfile(
                UserProfileEntity(
                    id = 1,
                    name = current.name,
                    email = current.email,
                    grade = current.grade.name,
                    board = board.name,
                    stream = current.stream.name,
                    streakDays = current.streakDays,
                    lastActiveDate = "Today"
                )
            )
        }
    }

    fun setStream(stream: Stream) {
        _selectedStream.value = stream
        viewModelScope.launch {
            val current = userProfile.value
            userDao.saveUserProfile(
                UserProfileEntity(
                    id = 1,
                    name = current.name,
                    email = current.email,
                    grade = current.grade.name,
                    board = current.board.name,
                    stream = stream.name,
                    streakDays = current.streakDays,
                    lastActiveDate = "Today"
                )
            )
        }
    }

    fun updateProfile(name: String, email: String, grade: GradeClass, board: Board, stream: Stream) {
        _selectedGrade.value = grade
        _selectedBoard.value = board
        _selectedStream.value = stream
        viewModelScope.launch {
            userDao.saveUserProfile(
                UserProfileEntity(
                    id = 1,
                    name = name,
                    email = email,
                    grade = grade.name,
                    board = board.name,
                    stream = stream.name,
                    streakDays = userProfile.value.streakDays,
                    lastActiveDate = "Today"
                )
            )
        }
    }

    fun toggleBookmark(title: String, type: String, subjectId: String, chapterId: String, snippet: String) {
        viewModelScope.launch {
            val exists = bookmarkDao.isBookmarked(chapterId, title)
            if (exists) {
                bookmarkDao.deleteBookmarkByChapterAndTitle(chapterId, title)
            } else {
                bookmarkDao.insertBookmark(
                    BookmarkEntity(
                        title = title,
                        type = type,
                        subjectId = subjectId,
                        chapterId = chapterId,
                        snippet = snippet
                    )
                )
            }
        }
    }

    fun removeBookmark(id: Long) {
        viewModelScope.launch {
            bookmarkDao.deleteBookmark(id)
        }
    }

    fun updateChapterStep(chapterId: String, subjectId: String, stepNumber: Int) {
        viewModelScope.launch {
            val currentProgress = allProgress.value.find { it.chapterId == chapterId }
            val completed = (stepNumber >= 7) || (currentProgress?.isCompleted == true)
            val entity = ChapterProgressEntity(
                chapterId = chapterId,
                subjectId = subjectId,
                currentStep = maxOf(stepNumber, currentProgress?.currentStep ?: 1),
                isCompleted = completed,
                bestQuizScore = currentProgress?.bestQuizScore ?: 0,
                totalQuizQuestions = currentProgress?.totalQuizQuestions ?: 0,
                lastStudiedTimestamp = System.currentTimeMillis()
            )
            progressDao.saveProgress(entity)
        }
    }

    fun toggleChapterCompleted(chapterId: String, subjectId: String) {
        viewModelScope.launch {
            val currentProgress = allProgress.value.find { it.chapterId == chapterId }
            val nowCompleted = !(currentProgress?.isCompleted ?: false)
            val entity = ChapterProgressEntity(
                chapterId = chapterId,
                subjectId = subjectId,
                currentStep = if (nowCompleted) 7 else (currentProgress?.currentStep ?: 1),
                isCompleted = nowCompleted,
                bestQuizScore = currentProgress?.bestQuizScore ?: 0,
                totalQuizQuestions = currentProgress?.totalQuizQuestions ?: 0,
                lastStudiedTimestamp = System.currentTimeMillis()
            )
            progressDao.saveProgress(entity)
        }
    }

    fun recordQuizResult(chapterId: String, subjectId: String, score: Int, total: Int) {
        viewModelScope.launch {
            val percentage = if (total > 0) (score * 100) / total else 0
            quizDao.insertQuizRecord(
                QuizRecordEntity(
                    chapterId = chapterId,
                    subjectId = subjectId,
                    score = score,
                    total = total,
                    percentage = percentage
                )
            )

            // Update chapter progress with best score
            val currentProgress = allProgress.value.find { it.chapterId == chapterId }
            val bestScore = maxOf(score, currentProgress?.bestQuizScore ?: 0)
            val entity = ChapterProgressEntity(
                chapterId = chapterId,
                subjectId = subjectId,
                currentStep = maxOf(5, currentProgress?.currentStep ?: 5),
                isCompleted = currentProgress?.isCompleted ?: false,
                bestQuizScore = bestScore,
                totalQuizQuestions = total,
                lastStudiedTimestamp = System.currentTimeMillis()
            )
            progressDao.saveProgress(entity)
        }
    }

    fun sendChatMessage(query: String, subjectContext: String = "General") {
        if (query.isBlank()) return
        val studentQuery = query.trim()

        viewModelScope.launch {
            // Save user message
            val userMsg = ChatMessageEntity(
                role = "user",
                content = studentQuery,
                subjectContext = subjectContext
            )
            chatDao.insertMessage(userMsg)

            // AI thinking indicator
            _isAiThinking.value = true

            val gradeDisplayName = selectedGrade.value.displayName
            val chatHistory = chatMessages.value.takeLast(6).map { it.role to it.content }

            val response = GeminiService.askTutor(
                studentQuery = studentQuery,
                gradeContext = gradeDisplayName,
                subjectContext = subjectContext,
                recentChat = chatHistory
            )

            val botMsg = ChatMessageEntity(
                role = "assistant",
                content = response,
                subjectContext = subjectContext
            )
            chatDao.insertMessage(botMsg)
            _isAiThinking.value = false
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            chatDao.clearMessages()
            // Re-seed welcome
            chatDao.insertMessage(
                ChatMessageEntity(
                    role = "assistant",
                    content = "Chat cleared! How can I help you learn today?",
                    subjectContext = "General"
                )
            )
        }
    }
}
