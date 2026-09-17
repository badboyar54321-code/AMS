package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.CurriculumRepository
import com.example.model.Chapter
import com.example.model.GradeClass
import com.example.model.Subject
import com.example.ui.components.AmsBottomNavigation
import com.example.ui.components.AmsTopBar
import com.example.ui.components.AppDestination
import com.example.ui.screens.AiTutorScreen
import com.example.ui.screens.BookmarksScreen
import com.example.ui.screens.ChapterDetailScreen
import com.example.ui.screens.ChapterListScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.LandingScreen
import com.example.ui.screens.PracticeQuizScreen
import com.example.ui.screens.ProfileAndAuthScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.StepLearningScreen
import com.example.ui.screens.SubjectsScreen
import com.example.ui.theme.MyApplicationTheme

enum class ScreenState {
    LANDING,
    DASHBOARD,
    SUBJECTS,
    CHAPTER_LIST,
    CHAPTER_DETAIL,
    STEP_LEARNING,
    PRACTICE_QUIZ,
    AI_TUTOR,
    PROGRESS,
    BOOKMARKS,
    SEARCH,
    PROFILE
}

@Composable
fun AmsApp(
    viewModel: AmsViewModel = viewModel()
) {
    val selectedGrade by viewModel.selectedGrade.collectAsState()
    val selectedBoard by viewModel.selectedBoard.collectAsState()
    val selectedStream by viewModel.selectedStream.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val allProgress by viewModel.allProgress.collectAsState()
    val recentQuizzes by viewModel.recentQuizzes.collectAsState()
    val chatMessages by viewModel.chatMessages.collectAsState()
    val isAiThinking by viewModel.isAiThinking.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    var currentScreen by remember { mutableStateOf(ScreenState.LANDING) }
    var selectedSubjectId by remember { mutableStateOf("c10_math") }
    var selectedChapterId by remember { mutableStateOf("c10_m_ch1") }
    var aiPrefilledQuery by remember { mutableStateOf("") }

    val currentSubject = CurriculumRepository.getSubjectById(selectedSubjectId)
        ?: CurriculumRepository.subjects.first()
    val currentChapter = CurriculumRepository.getChapterById(selectedChapterId)
        ?: CurriculumRepository.chapters.first()
    val gradeSubjects = CurriculumRepository.getSubjectsForGrade(selectedGrade, selectedStream)

    // Back button handling
    BackHandler(enabled = currentScreen != ScreenState.LANDING) {
        when (currentScreen) {
            ScreenState.DASHBOARD -> currentScreen = ScreenState.LANDING
            ScreenState.CHAPTER_LIST -> currentScreen = ScreenState.SUBJECTS
            ScreenState.CHAPTER_DETAIL -> currentScreen = ScreenState.CHAPTER_LIST
            ScreenState.STEP_LEARNING -> currentScreen = ScreenState.CHAPTER_DETAIL
            ScreenState.PRACTICE_QUIZ -> currentScreen = ScreenState.CHAPTER_DETAIL
            ScreenState.SEARCH, ScreenState.BOOKMARKS, ScreenState.PROFILE -> currentScreen = ScreenState.DASHBOARD
            else -> currentScreen = ScreenState.DASHBOARD
        }
    }

    MyApplicationTheme(darkTheme = isDarkMode) {
        Scaffold(
            topBar = {
                if (currentScreen != ScreenState.LANDING &&
                    currentScreen != ScreenState.CHAPTER_DETAIL &&
                    currentScreen != ScreenState.PRACTICE_QUIZ &&
                    currentScreen != ScreenState.STEP_LEARNING &&
                    currentScreen != ScreenState.SEARCH
                ) {
                    AmsTopBar(
                        selectedGrade = selectedGrade,
                        streakDays = userProfile.streakDays,
                        isDarkMode = isDarkMode,
                        onGradeToggle = {
                            val nextGrade = if (selectedGrade == GradeClass.CLASS_10) GradeClass.CLASS_12 else GradeClass.CLASS_10
                            viewModel.setGrade(nextGrade)
                        },
                        onSearchClick = { currentScreen = ScreenState.SEARCH },
                        onBookmarkClick = { currentScreen = ScreenState.BOOKMARKS },
                        onThemeToggle = { viewModel.toggleDarkMode() }
                    )
                }
            },
            bottomBar = {
                if (currentScreen != ScreenState.LANDING &&
                    currentScreen != ScreenState.CHAPTER_DETAIL &&
                    currentScreen != ScreenState.PRACTICE_QUIZ &&
                    currentScreen != ScreenState.STEP_LEARNING &&
                    currentScreen != ScreenState.SEARCH
                ) {
                    val activeDest = when (currentScreen) {
                        ScreenState.DASHBOARD -> AppDestination.HOME
                        ScreenState.SUBJECTS, ScreenState.CHAPTER_LIST -> AppDestination.SUBJECTS
                        ScreenState.STEP_LEARNING -> AppDestination.STEP_LEARNING
                        ScreenState.AI_TUTOR -> AppDestination.AI_TUTOR
                        ScreenState.PROGRESS -> AppDestination.PROGRESS
                        ScreenState.BOOKMARKS -> AppDestination.BOOKMARKS
                        ScreenState.PROFILE -> AppDestination.PROFILE
                        else -> AppDestination.HOME
                    }

                    AmsBottomNavigation(
                        currentDestination = activeDest,
                        onNavigate = { dest ->
                            currentScreen = when (dest) {
                                AppDestination.HOME -> ScreenState.DASHBOARD
                                AppDestination.SUBJECTS -> ScreenState.SUBJECTS
                                AppDestination.STEP_LEARNING -> ScreenState.STEP_LEARNING
                                AppDestination.PRACTICE -> ScreenState.PRACTICE_QUIZ
                                AppDestination.AI_TUTOR -> ScreenState.AI_TUTOR
                                AppDestination.PROGRESS -> ScreenState.PROGRESS
                                AppDestination.BOOKMARKS -> ScreenState.BOOKMARKS
                                AppDestination.PROFILE -> ScreenState.PROFILE
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    ScreenState.LANDING -> {
                        LandingScreen(
                            onStartLearning = { currentScreen = ScreenState.DASHBOARD },
                            onAskAi = { currentScreen = ScreenState.AI_TUTOR },
                            onSelectClass10 = {
                                viewModel.setGrade(GradeClass.CLASS_10)
                                currentScreen = ScreenState.SUBJECTS
                            },
                            onSelectClass12 = {
                                viewModel.setGrade(GradeClass.CLASS_12)
                                currentScreen = ScreenState.SUBJECTS
                            },
                            onOpenStepLearning = { currentScreen = ScreenState.STEP_LEARNING },
                            onOpenPractice = { currentScreen = ScreenState.PRACTICE_QUIZ },
                            onOpenProgress = { currentScreen = ScreenState.PROGRESS },
                            onOpenBookmarks = { currentScreen = ScreenState.BOOKMARKS },
                            onOpenDashboard = { currentScreen = ScreenState.DASHBOARD }
                        )
                    }

                    ScreenState.DASHBOARD -> {
                        DashboardScreen(
                            userProfile = userProfile,
                            selectedGrade = selectedGrade,
                            selectedBoard = selectedBoard,
                            subjects = gradeSubjects,
                            progressList = allProgress,
                            bookmarks = bookmarks,
                            onSubjectClick = { subject ->
                                selectedSubjectId = subject.id
                                currentScreen = ScreenState.CHAPTER_LIST
                            },
                            onContinueLearningClick = { subjectId, chapterId ->
                                selectedSubjectId = subjectId
                                selectedChapterId = chapterId
                                currentScreen = ScreenState.CHAPTER_DETAIL
                            },
                            onAiTutorClick = { currentScreen = ScreenState.AI_TUTOR },
                            onPracticeClick = { currentScreen = ScreenState.PRACTICE_QUIZ },
                            onStepLearningClick = { currentScreen = ScreenState.STEP_LEARNING },
                            onBookmarksClick = { currentScreen = ScreenState.BOOKMARKS },
                            onProgressClick = { currentScreen = ScreenState.PROGRESS }
                        )
                    }

                    ScreenState.SUBJECTS -> {
                        SubjectsScreen(
                            selectedGrade = selectedGrade,
                            selectedBoard = selectedBoard,
                            selectedStream = selectedStream,
                            subjects = gradeSubjects,
                            progressList = allProgress,
                            onGradeChange = { viewModel.setGrade(it) },
                            onBoardChange = { viewModel.setBoard(it) },
                            onStreamChange = { viewModel.setStream(it) },
                            onSubjectClick = { subject ->
                                selectedSubjectId = subject.id
                                currentScreen = ScreenState.CHAPTER_LIST
                            }
                        )
                    }

                    ScreenState.CHAPTER_LIST -> {
                        val subjectChapters = CurriculumRepository.getChaptersForSubject(selectedSubjectId)
                        ChapterListScreen(
                            subject = currentSubject,
                            chapters = subjectChapters,
                            progressList = allProgress,
                            bookmarks = bookmarks,
                            onBack = { currentScreen = ScreenState.SUBJECTS },
                            onChapterClick = { chapter ->
                                selectedChapterId = chapter.id
                                currentScreen = ScreenState.CHAPTER_DETAIL
                            },
                            onBookmarkToggle = { chapter ->
                                viewModel.toggleBookmark(
                                    title = chapter.title,
                                    type = "Chapter",
                                    subjectId = chapter.subjectId,
                                    chapterId = chapter.id,
                                    snippet = chapter.summary
                                )
                            }
                        )
                    }

                    ScreenState.CHAPTER_DETAIL -> {
                        val chapterContent = CurriculumRepository.getChapterFullContent(selectedChapterId)
                        val cp = allProgress.find { it.chapterId == selectedChapterId }
                        val isCompleted = cp?.isCompleted == true
                        val isBookmarked = bookmarks.any { it.chapterId == selectedChapterId }

                        val subjectChapters = CurriculumRepository.getChaptersForSubject(selectedSubjectId)
                        val currentIndex = subjectChapters.indexOfFirst { it.id == selectedChapterId }
                        val prevChapter = if (currentIndex > 0) subjectChapters[currentIndex - 1] else null
                        val nextChapter = if (currentIndex in 0 until subjectChapters.size - 1) subjectChapters[currentIndex + 1] else null

                        ChapterDetailScreen(
                            chapter = currentChapter,
                            subject = currentSubject,
                            content = chapterContent,
                            isCompleted = isCompleted,
                            isBookmarked = isBookmarked,
                            onBack = { currentScreen = ScreenState.CHAPTER_LIST },
                            onToggleCompleted = {
                                viewModel.toggleChapterCompleted(selectedChapterId, selectedSubjectId)
                            },
                            onToggleBookmark = {
                                viewModel.toggleBookmark(
                                    title = currentChapter.title,
                                    type = "Chapter",
                                    subjectId = selectedSubjectId,
                                    chapterId = selectedChapterId,
                                    snippet = currentChapter.summary
                                )
                            },
                            onOpenStepLearning = { currentScreen = ScreenState.STEP_LEARNING },
                            onOpenQuiz = { currentScreen = ScreenState.PRACTICE_QUIZ },
                            onAskAiForChapter = { topic ->
                                aiPrefilledQuery = "Explain $topic from ${currentSubject.name} for ${selectedGrade.displayName}"
                                currentScreen = ScreenState.AI_TUTOR
                            },
                            onPreviousChapter = prevChapter?.let { prev ->
                                {
                                    selectedChapterId = prev.id
                                }
                            },
                            onNextChapter = nextChapter?.let { next ->
                                {
                                    selectedChapterId = next.id
                                }
                            }
                        )
                    }

                    ScreenState.STEP_LEARNING -> {
                        val chapterContent = CurriculumRepository.getChapterFullContent(selectedChapterId)
                        val cp = allProgress.find { it.chapterId == selectedChapterId }
                        val currentStep = cp?.currentStep ?: 1

                        StepLearningScreen(
                            chapter = currentChapter,
                            subject = currentSubject,
                            steps = chapterContent.stepLearning,
                            currentCompletedStep = currentStep,
                            onBack = { currentScreen = ScreenState.CHAPTER_DETAIL },
                            onStepComplete = { stepNum ->
                                viewModel.updateChapterStep(selectedChapterId, selectedSubjectId, stepNum)
                            },
                            onOpenQuiz = { currentScreen = ScreenState.PRACTICE_QUIZ },
                            onOpenNotes = { currentScreen = ScreenState.CHAPTER_DETAIL }
                        )
                    }

                    ScreenState.PRACTICE_QUIZ -> {
                        val chapterContent = CurriculumRepository.getChapterFullContent(selectedChapterId)
                        PracticeQuizScreen(
                            chapter = currentChapter,
                            subject = currentSubject,
                            questions = chapterContent.quizQuestions,
                            onBack = { currentScreen = ScreenState.CHAPTER_DETAIL },
                            onQuizCompleted = { score, total ->
                                viewModel.recordQuizResult(selectedChapterId, selectedSubjectId, score, total)
                            }
                        )
                    }

                    ScreenState.AI_TUTOR -> {
                        AiTutorScreen(
                            messages = chatMessages,
                            selectedGrade = selectedGrade,
                            isThinking = isAiThinking,
                            onSendMessage = { query, contextChip ->
                                viewModel.sendChatMessage(query, contextChip)
                            },
                            onClearChat = { viewModel.clearChat() },
                            initialQuery = aiPrefilledQuery
                        )
                    }

                    ScreenState.PROGRESS -> {
                        ProgressScreen(
                            userProfile = userProfile,
                            subjects = gradeSubjects,
                            progressList = allProgress,
                            quizHistory = recentQuizzes
                        )
                    }

                    ScreenState.BOOKMARKS -> {
                        BookmarksScreen(
                            bookmarks = bookmarks,
                            onRemoveBookmark = { id -> viewModel.removeBookmark(id) },
                            onOpenChapter = { subId, chapId ->
                                selectedSubjectId = subId
                                selectedChapterId = chapId
                                currentScreen = ScreenState.CHAPTER_DETAIL
                            }
                        )
                    }

                    ScreenState.SEARCH -> {
                        SearchScreen(
                            onBack = { currentScreen = ScreenState.DASHBOARD },
                            onResultClick = { subId, chapId ->
                                selectedSubjectId = subId
                                if (chapId != null) {
                                    selectedChapterId = chapId
                                    currentScreen = ScreenState.CHAPTER_DETAIL
                                } else {
                                    currentScreen = ScreenState.CHAPTER_LIST
                                }
                            }
                        )
                    }

                    ScreenState.PROFILE -> {
                        ProfileAndAuthScreen(
                            userProfile = userProfile,
                            isDarkMode = isDarkMode,
                            onThemeToggle = { viewModel.toggleDarkMode() },
                            onUpdateProfile = { name, email, grade, board, stream ->
                                viewModel.updateProfile(name, email, grade, board, stream)
                            }
                        )
                    }
                }
            }
        }
    }
}
