package com.example.ai

import android.util.Log
import com.example.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

// Moshi data structures for Gemini REST API
@JsonClass(generateAdapter = true)
data class GeminiRequest(
    @Json(name = "contents") val contents: List<GeminiContent>,
    @Json(name = "systemInstruction") val systemInstruction: GeminiContent? = null,
    @Json(name = "generationConfig") val generationConfig: GeminiGenerationConfig? = null
)

@JsonClass(generateAdapter = true)
data class GeminiContent(
    @Json(name = "parts") val parts: List<GeminiPart>,
    @Json(name = "role") val role: String? = null
)

@JsonClass(generateAdapter = true)
data class GeminiPart(
    @Json(name = "text") val text: String
)

@JsonClass(generateAdapter = true)
data class GeminiGenerationConfig(
    @Json(name = "temperature") val temperature: Float = 0.7f,
    @Json(name = "topP") val topP: Float = 0.95f,
    @Json(name = "maxOutputTokens") val maxOutputTokens: Int = 1200
)

@JsonClass(generateAdapter = true)
data class GeminiResponse(
    @Json(name = "candidates") val candidates: List<GeminiCandidate>? = null
)

@JsonClass(generateAdapter = true)
data class GeminiCandidate(
    @Json(name = "content") val content: GeminiContent? = null
)

interface GeminiApi {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

object GeminiService {
    private const val TAG = "GeminiService"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val api: GeminiApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeminiApi::class.java)
    }

    /**
     * Ask AMS AI with Class & Subject Context
     */
    suspend fun askTutor(
        studentQuery: String,
        gradeContext: String = "Class 10",
        subjectContext: String = "General Studies",
        recentChat: List<Pair<String, String>> = emptyList()
    ): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY

        // Check if API key is empty or default placeholder
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getOfflineSmartResponse(studentQuery, gradeContext, subjectContext)
        }

        try {
            val systemPrompt = """
                You are "AMS AI", an expert, friendly academic tutor dedicated to Class 10 and Class 12 students.
                Student Context:
                - Level: $gradeContext
                - Subject: $subjectContext
                
                Guidelines:
                1. Explain concepts step-by-step with clear logic, formulas, and examples.
                2. Adapt your tone and terminology strictly to the student's grade ($gradeContext).
                3. If asked a problem, break it down: (A) Given data, (B) Formula/Theorem used, (C) Step-by-step substitution, (D) Final answer with proper SI units.
                4. Do NOT fabricate citations, page numbers, or fake textbook excerpts. If unsure, admit it honestly.
                5. Encourage active understanding rather than rote memorization.
                6. Keep responses neat, formatted with clear bullet points and section headers.
            """.trimIndent()

            val contents = mutableListOf<GeminiContent>()

            // Append last few turns of conversation for context
            recentChat.takeLast(4).forEach { (sender, text) ->
                val role = if (sender == "user") "user" else "model"
                contents.add(GeminiContent(parts = listOf(GeminiPart(text = text)), role = role))
            }

            // Current user query
            contents.add(GeminiContent(parts = listOf(GeminiPart(text = studentQuery)), role = "user"))

            val request = GeminiRequest(
                contents = contents,
                systemInstruction = GeminiContent(parts = listOf(GeminiPart(text = systemPrompt))),
                generationConfig = GeminiGenerationConfig(temperature = 0.5f, maxOutputTokens = 1500)
            )

            val response = api.generateContent(apiKey = apiKey, request = request)
            val reply = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            if (!reply.isNullOrBlank()) {
                reply.trim()
            } else {
                getOfflineSmartResponse(studentQuery, gradeContext, subjectContext)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Gemini API call failed", e)
            getOfflineSmartResponse(studentQuery, gradeContext, subjectContext)
        }
    }

    /**
     * Smart local curriculum response provider when online Gemini key is pending or network is offline.
     */
    private fun getOfflineSmartResponse(query: String, grade: String, subject: String): String {
        val q = query.lowercase()
        return when {
            q.contains("formula") || q.contains("equation") -> {
                "📚 **Key Academic Formulas ($subject • $grade):**\n\n" +
                "• **Physics/Electricity:** Ohm's Law: *V = I · R*, Joule Heating: *H = I² · R · t*\n" +
                "• **Electrostatics:** Coulomb's Force: *F = (1/4πε₀) · (|q₁q₂| / r²)*\n" +
                "• **Mathematics:** Quadratic Roots: *x = (-b ± √(b² - 4ac)) / (2a)*\n" +
                "• **Real Numbers:** *HCF(a, b) × LCM(a, b) = a × b*\n" +
                "• **Chemistry:** Raoult's Law: *P_A = P°_A · χ_A*, Elevation in B.P.: *ΔT_b = i · K_b · m*\n\n" +
                "💡 *Tip: Write the formula first in your board examination to earn method marks!*"
            }
            q.contains("solve") || q.contains("how to") || q.contains("step") -> {
                "📝 **Step-by-Step Problem Solving Framework ($grade):**\n\n" +
                "1. **Identify the Given Quantities:** List down all known variables with appropriate SI units.\n" +
                "2. **State the Governing Law/Theorem:** Clearly name the principle (e.g., Euclid Lemma, Conservation of Energy, Ohm's Law).\n" +
                "3. **Write the Algebraic Formula:** Do not skip writing the symbolic formula before substituting numbers.\n" +
                "4. **Step-by-Step Evaluation:** Calculate carefully and double-check arithmetic calculations.\n" +
                "5. **State the Final Answer:** Box the final numerical result with its correct physical unit (e.g., Joules, Volts, cm²)."
            }
            q.contains("quiz") || q.contains("test") -> {
                "🎯 **Revision Challenge ($subject • $grade):**\n\n" +
                "**Question:** In any quadratic equation ax² + bx + c = 0, what condition guarantees real and equal roots?\n\n" +
                "A) b² - 4ac < 0\n" +
                "B) b² - 4ac = 0\n" +
                "C) b² - 4ac > 0\n\n" +
                "👉 *Hint: The discriminant D determines the nature of the roots! When D = 0, both roots coincide at -b / (2a).*"
            }
            q.contains("explain like i'm a beginner") || q.contains("beginner") -> {
                "🌟 **Concept Simplified for $grade ($subject):**\n\n" +
                "Think of this concept like everyday plumbing or traffic!\n" +
                "• **Voltage (Potential Difference):** The water pump pushing the water through.\n" +
                "• **Current:** The volume of water flowing per second.\n" +
                "• **Resistance:** Any narrow pipe or friction slowing down the flow.\n\n" +
                "When you double the push (Voltage), twice as much current flows unless the pipe (Resistance) is made narrower!"
            }
            else -> {
                "🎓 **AMS AI Tutor ($grade • $subject):**\n\n" +
                "Great academic question! Here is how to master this topic:\n\n" +
                "1. **Core Concept:** Break the question down into fundamental textbook definitions.\n" +
                "2. **Real-world Insight:** Relate the theory to practical examples frequently tested in board questions.\n" +
                "3. **Board Exam Strategy:** Always provide a labeled diagram or symbolic equation to secure full marks.\n\n" +
                "💬 *To unlock real-time live Gemini AI responses, ensure your GEMINI_API_KEY is configured in the AI Studio Secrets panel.*"
            }
        }
    }
}
