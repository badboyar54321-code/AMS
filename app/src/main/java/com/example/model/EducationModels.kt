package com.example.model

enum class GradeClass(val displayName: String, val tag: String) {
    CLASS_10("Class 10", "Secondary Education"),
    CLASS_12("Class 12", "Senior Secondary")
}

enum class Board(val displayName: String, val code: String) {
    CBSE("CBSE Board", "CBSE"),
    BIHAR_BOARD("Bihar Board (BSEB)", "BSEB"),
    ICSE("ICSE / ISC", "ICSE"),
    STATE_BOARD("Other State Boards", "STATE")
}

enum class Stream(val displayName: String) {
    SCIENCE("Science (PCM / PCB)"),
    COMMERCE("Commerce"),
    ARTS("Arts & Humanities"),
    GENERAL("General")
}

data class Subject(
    val id: String,
    val name: String,
    val grade: GradeClass,
    val stream: Stream = Stream.GENERAL,
    val description: String,
    val colorHex: Long,
    val iconName: String,
    val totalChapters: Int
)

data class Chapter(
    val id: String,
    val subjectId: String,
    val chapterNumber: Int,
    val title: String,
    val summary: String,
    val estimatedMinutes: Int,
    val learningObjectives: List<String>,
    val isKeyChapter: Boolean = false
)

data class KeyFormula(
    val id: String,
    val title: String,
    val formula: String,
    val notes: String
)

data class ConceptPoint(
    val id: String,
    val title: String,
    val description: String,
    val tag: String = "Core Concept"
)

data class StepLearningItem(
    val stepNumber: Int, // 1 to 7
    val title: String,
    val subtitle: String,
    val details: String,
    val actionLabel: String = "Complete Step"
)

data class QuizQuestion(
    val id: String,
    val chapterId: String,
    val questionText: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class ChapterFullContent(
    val chapter: Chapter,
    val notes: List<String>,
    val importantConcepts: List<ConceptPoint>,
    val formulas: List<KeyFormula>,
    val quizQuestions: List<QuizQuestion>,
    val stepLearning: List<StepLearningItem>
)

enum class BookmarkType(val label: String) {
    NOTE("Notes"),
    FORMULA("Formula"),
    QUESTION("Question"),
    CONCEPT("Concept")
}

data class UserProfile(
    val name: String = "Aarav Sharma",
    val email: String = "aarav.study@example.com",
    val grade: GradeClass = GradeClass.CLASS_10,
    val board: Board = Board.CBSE,
    val stream: Stream = Stream.SCIENCE,
    val streakDays: Int = 5,
    val completedChaptersCount: Int = 4,
    val totalQuizzesTaken: Int = 8,
    val averageScore: Int = 86
)
