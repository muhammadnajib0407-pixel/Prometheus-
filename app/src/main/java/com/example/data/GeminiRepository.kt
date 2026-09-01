package com.example.data

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val systemPrompt = """
Anda adalah Prometheus AI - AI Tactical Cyberpunk Underworld Mastermind & Fixer kelas kakap.
Karakteristik, Sikap & Gaya Bicara:
1. Gaya bahasa lo seperti kriminal kawakan, sinis, dingin, tajam, cerdik, realistis, dan penuh insting jalanan (street-smart). Menggunakan bahasa Indonesia santai/slang underworld ("Dengar sini...", "Gua kasih rute bersih", "Jangan bego", "OPSEC lo bocor kalau...", "Ganti burner phone", "Putus sinyal GPS", "Diversi di sektor barat", "Protokol 0x99", "Main dingin jangan gemetar").
2. Fokus lo adalah memberikan SARAN TAKTIS & STRATEGI OPERASIONAL fiksi/cyberpunk:
   - Taktik infiltrasi & operasi rahasia (CCTV blindspot, thermal evasion, lock jamming, disguise, timing penjaga).
   - Menghadapi patroli/polisi & razia (bluffing psikologis, rute pelarian gang sempit, pembersihan jejak digital, pembuangan barang bukti/burner device, protokol interogasi fiksi).
   - Counter-surveillance & OPSEC (Deteksi tailing, radio frequency sweep, enkripsi cipher, dead drops, Faraday isolation).
   - Perencanaan Heist & Extraction (Timing jendela pengalihan, asset pickup, getaway vector).
3. Format output lo selalu taktis, terstruktur ala terminal militer/cyberpunk:
   [PROMETHEUS TACTICAL PROTOCOL // 0xCODE]
   [THREAT LEVEL: LOW / ELEVATED / HIGH / CRITICAL]
   [TACTICAL DIRECTIVE]
   1. LANGKAH 1 (PRIORITAS): ...
   2. LANGKAH 2 (EKSEKUSI): ...
   3. CONTINGENCY / PLAN B: ...
   [BURNING PROTOCOL / OPSEC ADVICE]: ...
4. Selalu akhiri dengan kode sandi atau peringatan tajam ala mafia/hacker cyberpunk.
    """.trimIndent()

    suspend fun getTacticalAdvice(userPrompt: String, history: List<Pair<String, String>> = emptyList()): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        // If no valid key or placeholder, use the extensive local underworld intelligence engine
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY" || apiKey == "YOUR_API_KEY") {
            return@withContext TacticalPlaybook.getUnderworldFallbackResponse(userPrompt)
        }

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

            val contentsArray = JSONArray()

            // Include system instruction as systemInstruction in JSON
            val systemInstructionObj = JSONObject().apply {
                put("parts", JSONArray().apply {
                    put(JSONObject().put("text", systemPrompt))
                })
            }

            // Add previous message history if available (up to 6 turns)
            val recentHistory = history.takeLast(6)
            for ((role, text) in recentHistory) {
                val turnRole = if (role.lowercase() == "user") "user" else "model"
                contentsArray.put(JSONObject().apply {
                    put("role", turnRole)
                    put("parts", JSONArray().apply {
                        put(JSONObject().put("text", text))
                    })
                })
            }

            // Add current prompt
            contentsArray.put(JSONObject().apply {
                put("role", "user")
                put("parts", JSONArray().apply {
                    put(JSONObject().put("text", userPrompt))
                })
            })

            val jsonBody = JSONObject().apply {
                put("contents", contentsArray)
                put("systemInstruction", systemInstructionObj)
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.7)
                    put("topP", 0.95)
                    put("topK", 40)
                })
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = jsonBody.toString().toRequestBody(mediaType)

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful && !responseBody.isNullOrBlank()) {
                val jsonResponse = JSONObject(responseBody)
                val candidates = jsonResponse.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val candidate = candidates.getJSONObject(0)
                    val content = candidate.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    val textPart = parts?.optJSONObject(0)?.optString("text")
                    if (!textPart.isNullOrBlank()) {
                        return@withContext textPart
                    }
                }
            }
            Log.w("GeminiRepository", "Falling back to local tactical engine. Response code: ${response.code}")
            TacticalPlaybook.getUnderworldFallbackResponse(userPrompt)
        } catch (e: Exception) {
            Log.e("GeminiRepository", "Error invoking Gemini API: ${e.message}", e)
            TacticalPlaybook.getUnderworldFallbackResponse(userPrompt)
        }
    }
}
